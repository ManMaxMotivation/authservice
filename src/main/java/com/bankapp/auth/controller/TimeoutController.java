package com.bankapp.auth.controller;

// Импорты для работы с Swagger (документация API), Spring (REST-контроллер) и Java коллекциями
import io.swagger.v3.oas.annotations.Operation; // Для описания эндпоинтов в Swagger
import io.swagger.v3.oas.annotations.Parameter; // Для описания параметров в Swagger
import io.swagger.v3.oas.annotations.media.Content; // Для описания тела запроса в Swagger
import io.swagger.v3.oas.annotations.media.ExampleObject; // Для примеров запросов в Swagger
import io.swagger.v3.oas.annotations.media.Schema; // Для описания схемы данных в Swagger
import io.swagger.v3.oas.annotations.tags.Tag; // Для группировки эндпоинтов в Swagger
import org.springframework.web.bind.annotation.*; // Аннотации Spring для создания REST API
import java.util.HashMap; // Для хранения таймаутов в виде ключ-значение
import java.util.Map; // Интерфейс для работы с коллекцией таймаутов

// Объявляем класс как REST-контроллер, который обрабатывает HTTP-запросы
@RestController
// Все эндпоинты начинаются с "/timeout" (например, /timeout/get)
@RequestMapping("/timeout")
// Документируем контроллер в Swagger как группу "Таймауты"
@Tag(name = "Таймауты", description = "Управление таймаутами (задержками) для различных действий пользователей: логин, регистрация, выход и др.")
public class TimeoutController {

    // Хранилище таймаутов: ключ — действие (например, "login"), значение — задержка в секундах
    private final Map<String, Integer> timeouts = new HashMap<>();

    // Конструктор, вызывается при создании объекта
    public TimeoutController() {
        // Инициализируем стандартные таймауты для действий
        timeouts.put("login", 1); // Задержка для входа — 1 секунда
        timeouts.put("register", 2); // Задержка для регистрации — 2 секунды
        timeouts.put("logout", 2); // Задержка для выхода — 2 секунды
    }

    /**
     * Метод для применения задержки (таймаута) перед выполнением действия.
     * Используется AuthController для симуляции медленных ответов в нагрузочном тестировании.
     */
    public void applyTimeout(String action) {
        // Получаем таймаут для действия, если нет — используем 10 секунд по умолчанию
        int timeoutSeconds = timeouts.getOrDefault(action, 10);
        try {
            // Приостанавливаем выполнение потока на указанное количество секунд
            Thread.sleep(timeoutSeconds * 1000L); // Переводим секунды в миллисекунды
        } catch (InterruptedException e) {
            // Если поток прерван, восстанавливаем статус прерывания
            Thread.currentThread().interrupt();
        }
    }

    // Эндпоинт для получения таймаута для конкретного действия
    @GetMapping("/get")
    // Документируем эндпоинт в Swagger
    @Operation(
            summary = "Получить таймаут по действию",
            description = "Возвращает значение таймаута (в секундах), установленного для указанного действия.",
            parameters = {
                    @Parameter(
                            name = "action",
                            description = "Название действия (например, login, register, logout)",
                            example = "login",
                            required = true
                    )
            }
    )
    public int getTimeout(@RequestParam String action) {
        // Возвращаем таймаут для действия или 10 секунд, если действие не найдено
        return timeouts.getOrDefault(action, 10);
    }

    // Эндпоинт для установки нового таймаута для действия
    @PostMapping("/set")
    // Документируем эндпоинт в Swagger, включая пример запроса
    @Operation(
            summary = "Установить таймаут по действию",
            description = "Позволяет задать или изменить значение таймаута (в секундах) для конкретного действия.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Параметры запроса для установки таймаута",
                    required = true,
                    content = @Content(
                            mediaType = "application/x-www-form-urlencoded",
                            examples = @ExampleObject(
                                    name = "Set Timeout Example",
                                    summary = "Пример установки таймаута",
                                    value = "action=login&timeoutSeconds=5"
                            )
                    )
            )
    )
    public String setTimeout(
            // Параметр: название действия (например, "login")
            @RequestParam
            @Schema(description = "Название действия (login, register, logout и т.д.)", example = "login")
            String action,

            // Параметр: значение таймаута в секундах
            @RequestParam
            @Schema(description = "Значение таймаута в секундах", example = "5")
            int timeoutSeconds
    ) {
        // Сохраняем новый таймаут для действия
        timeouts.put(action, timeoutSeconds);
        // Возвращаем подтверждение с указанием действия и нового таймаута
        return "Таймаут для '" + action + "' установлен на " + timeoutSeconds + " секунд.";
    }

    // Эндпоинт для получения всех текущих таймаутов
    @GetMapping("/all")
    // Документируем эндпоинт в Swagger
    @Operation(
            summary = "Получить все текущие таймауты",
            description = "Возвращает карту всех действий и соответствующих им значений таймаутов (в секундах)."
    )
    public Map<String, Integer> getAllTimeouts() {
        // Возвращаем полную карту таймаутов
        return timeouts;
    }
}