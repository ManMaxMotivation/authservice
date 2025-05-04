package com.bankapp.auth.controller;

// Импорты необходимых библиотек и классов
import com.bankapp.auth.model.Client; // Модель клиента для работы с данными пользователя
import com.bankapp.auth.service.ClientService; // Сервис для обработки логики регистрации и входа
import com.bankapp.auth.service.CustomMetricsService; // Сервис для сбора пользовательских метрик
import com.bankapp.auth.util.SessionManager; // Утилита для управления сессиями пользователей
import io.micrometer.core.instrument.MeterRegistry; // Инструмент Micrometer для работы с метриками Prometheus
import io.swagger.v3.oas.annotations.Operation; // Аннотации Swagger для документирования API
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger; // Логирование событий
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired; // Аннотация для внедрения зависимостей
import org.springframework.web.bind.annotation.*; // Аннотации для создания REST-эндпоинтов

import java.util.Optional;

// Объявляем класс как REST-контроллер, который обрабатывает HTTP-запросы
@RestController
// Все эндпоинты начинаются с "/auth" (например, /auth/register)
@RequestMapping("/auth")
// Документируем контроллер в Swagger, чтобы он отображался как "Аутентификация"
@Tag(name = "Аутентификация", description = "Методы регистрации, входа, выхода и проверки сессии пользователя")
public class AuthController {

    // Зависимости, которые нужны для работы контроллера
    private final ClientService clientService; // Сервис для работы с клиентами (регистрация, логин)
    private final SessionManager sessionManager; // Управление сессиями (кто вошел, кто вышел)
    private final TimeoutController timeoutController; // Управление задержкой ответа (для нагрузочного тестирования)
    private final CustomMetricsService metricsService; // Сбор пользовательских метрик для Prometheus
    private final MeterRegistry meterRegistry; // Инструмент для записи метрик в Prometheus
    private final Logger log = LoggerFactory.getLogger(AuthController.class); // Логгер для записи событий

    // Конструктор с внедрением зависимостей через @Autowired
    @Autowired
    public AuthController(ClientService clientService, SessionManager sessionManager,
                          TimeoutController timeoutController, CustomMetricsService metricsService,
                          MeterRegistry meterRegistry) {
        this.clientService = clientService;
        this.sessionManager = sessionManager;
        this.timeoutController = timeoutController;
        this.metricsService = metricsService;
        this.meterRegistry = meterRegistry;
    }

    // Эндпоинт для регистрации нового пользователя
    @PostMapping("/register")
    // Документируем эндпоинт в Swagger: описание, пример запроса
    @Operation(
            summary = "Регистрация нового пользователя",
            description = "Создает нового пользователя в системе и сохраняет его в базе данных.",
            requestBody = @RequestBody(
                    description = "Данные для регистрации",
                    required = true,
                    content = @Content(
                            mediaType = "application/x-www-form-urlencoded",
                            schema = @Schema(implementation = Void.class),
                            examples = @ExampleObject(
                                    name = "Register Example",
                                    summary = "Пример запроса регистрации",
                                    value = "fullName=Иван Иванов&phone=+79001112233&username=user1&password=pass1"
                            )
                    )
            )
    )
    public Client register(
            // Параметры запроса, передаваемые в форме
            @RequestParam @Schema(description = "Полное имя пользователя", example = "Иван Иванов") String fullName,
            @RequestParam @Schema(description = "Телефон пользователя с +7", example = "+79001112233") String phone,
            @RequestParam @Schema(description = "Уникальный логин пользователя", example = "user1") String username,
            @RequestParam @Schema(description = "Пароль", example = "pass1") String password
    ) {
        // Логируем попытку регистрации
        log.info("Register attempt for username: {}", username);
        // Применяем искусственную задержку ответа (для нагрузочного тестирования)
        timeoutController.applyTimeout("register");
        // Запоминаем время начала выполнения для метрик
        long startTime = System.nanoTime();

        try {
            // Увеличиваем счетчик регистраций (метрика)
            metricsService.incrementRegisterCounter();
            // Записываем длину имени как метрику (для анализа)
            metricsService.recordRegisterSummary(fullName.length());
            // Вызываем сервис для создания нового пользователя
            Client client = clientService.register(fullName, phone, username, password);
            // Записываем время выполнения регистрации
            metricsService.recordRegisterTimer(System.nanoTime() - startTime);
            // Возвращаем созданного клиента
            return client;
        } finally {
            // Записываем текущее количество регистраций как gauge (метрика для Prometheus)
            meterRegistry.gauge("auth_register_active", metricsService.getRegisterCount());
        }
    }

    // Эндпоинт для входа пользователя в систему
    @PostMapping("/login")
    // Документируем эндпоинт в Swagger
    @Operation(
            summary = "Вход в систему",
            description = "Авторизует пользователя по логину и паролю. Возвращает сообщение об успехе или ошибке.",
            requestBody = @RequestBody(
                    description = "Учетные данные",
                    required = true,
                    content = @Content(
                            mediaType = "application/x-www-form-urlencoded",
                            schema = @Schema(implementation = Void.class),
                            examples = @ExampleObject(
                                    name = "Login Example",
                                    summary = "Пример логина",
                                    value = "username=user1&password=pass1"
                            )
                    )
            )
    )
    public String login(
            // Параметры запроса для логина
            @RequestParam @Schema(description = "Логин пользователя", example = "user1") String username,
            @RequestParam @Schema(description = "Пароль пользователя", example = "pass1") String password
    ) {
        // Логируем попытку входа
        log.info("Login attempt for username: {}", username);
        // Применяем задержку ответа
        timeoutController.applyTimeout("login");
        // Запоминаем время начала
        long startTime = System.nanoTime();

        try {
            // Увеличиваем счетчик попыток входа
            metricsService.incrementLoginCounter();
            // Записываем длину пароля как метрику
            metricsService.recordLoginSummary(password.length());
            // Проверяем логин и пароль через сервис
            Optional<Client> clientOpt = clientService.login(username, password);
            // Записываем время выполнения входа
            metricsService.recordLoginTimer(System.nanoTime() - startTime);
            // Если пользователь найден
            if (clientOpt.isPresent()) {
                // Сохраняем его в сессии
                sessionManager.login(clientOpt.get());
                // Увеличиваем счетчик успешных входов
                meterRegistry.counter("auth_login_success_total").increment();
                // Возвращаем сообщение об успехе
                return "✅ Успешный вход: " + username;
            }
            // Увеличиваем счетчик неудачных входов
            meterRegistry.counter("auth_login_failure_total").increment();
            // Возвращаем сообщение об ошибке
            return "❌ Ошибка: Неверный логин или пароль";
        } finally {
            // Записываем текущее количество попыток входа как gauge
            meterRegistry.gauge("auth_login_active", metricsService.getLoginCount());
        }
    }

    // Эндпоинт для выхода из системы
    @PostMapping("/logout")
    // Документируем в Swagger
    @Operation(
            summary = "Выход из системы",
            description = "Завершает текущую сессию пользователя."
    )
    public String logout() {
        // Логируем запрос на выход
        log.info("Logout request");
        // Применяем задержку ответа
        timeoutController.applyTimeout("logout");
        // Запоминаем время начала
        long startTime = System.nanoTime();

        try {
            // Увеличиваем счетчик выходов
            metricsService.incrementLogoutCounter();
            // Записываем метрику выхода
            metricsService.recordLogoutSummary(1);
            // Очищаем сессию
            sessionManager.logout();
            // Записываем время выполнения выхода
            metricsService.recordLogoutTimer(System.nanoTime() - startTime);
            // Возвращаем сообщение об успехе
            return "✅ Успешный выход";
        } finally {
            // Записываем текущее количество выходов как gauge
            meterRegistry.gauge("auth_logout_active", metricsService.getLogoutCount());
        }
    }

    // Эндпоинт для проверки статуса авторизации
    @GetMapping("/isLogged")
    // Документируем в Swagger
    @Operation(
            summary = "Проверка авторизации",
            description = "Возвращает статус, авторизован ли пользователь."
    )
    public String isLogged() {
        // Логируем запрос проверки статуса
        log.info("Checking login status");
        // Запоминаем время начала
        long startTime = System.nanoTime();

        try {
            // Увеличиваем счетчик проверок статуса
            metricsService.incrementIsLoggedCounter();
            // Записываем метрику проверки
            metricsService.recordIsLoggedSummary(1);
            // Получаем статус сессии
            String status = sessionManager.getLoginStatus();
            // Записываем время выполнения
            metricsService.recordIsLoggedTimer(System.nanoTime() - startTime);
            // Возвращаем статус
            return status;
        } finally {
            // Записываем текущее количество проверок как gauge
            meterRegistry.gauge("auth_is_logged_active", metricsService.getIsLoggedCount());
        }
    }

    // Эндпоинт для получения имени авторизованного пользователя
    @GetMapping("/user")
    // Документируем в Swagger
    @Operation(
            summary = "Получить имя пользователя",
            description = "Возвращает логин авторизованного пользователя. Если пользователь не авторизован — сообщение об ошибке."
    )
    public String getUser() {
        // Логируем запрос на получение пользователя
        log.info("Fetching logged-in user");
        // Запоминаем время начала
        long startTime = System.nanoTime();

        try {
            // Увеличиваем счетчик запросов пользователя
            metricsService.incrementGetUserCounter();
            // Записываем метрику запроса
            metricsService.recordGetUserSummary(1);
            // Получаем текущего пользователя из сессии
            Client loggedInClient = sessionManager.getLoggedInClient();
            // Записываем время выполнения
            metricsService.recordGetUserTimer(System.nanoTime() - startTime);
            // Если пользователь авторизован, возвращаем его логин
            if (loggedInClient != null) {
                return loggedInClient.getUsername();
            }
            // Иначе возвращаем ошибку
            return "❌ Ошибка: Пользователь не авторизован";
        } finally {
            // Записываем текущее количество запросов как gauge
            meterRegistry.gauge("auth_get_user_active", metricsService.getGetUserCount());
        }
    }
}