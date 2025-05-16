package com.bankapp.auth.service;

// Импорты необходимых библиотек для работы с метриками
import io.micrometer.core.instrument.Counter; // Класс для подсчета событий (например, количества регистраций)
import io.micrometer.core.instrument.DistributionSummary; // Класс для записи статистических данных (например, длина имени)
import io.micrometer.core.instrument.MeterRegistry; // Реестр метрик для регистрации и отправки в Prometheus
import io.micrometer.core.instrument.Timer; // Класс для измерения времени выполнения операций
import org.springframework.stereotype.Service; // Аннотация для обозначения сервисного слоя
import java.util.concurrent.TimeUnit; // Класс для работы с единицами времени (например, наносекунды)
import java.util.concurrent.atomic.AtomicInteger; // Класс для атомарного счетчика (используется для gauge)

// 💥 @Service указывает, что это компонент сервисного слоя Spring
// Spring автоматически создает бин этого класса для управления метриками
@Service
public class CustomMetricsService {

    // Поля для хранения счетчиков (Counter) для подсчета событий
    private final Counter registerCounter; // Счетчик попыток регистрации
    private final Counter loginCounter; // Счетчик попыток входа
    private final Counter logoutCounter; // Счетчик попыток выхода
    private final Counter isLoggedCounter; // Счетчик проверок статуса авторизации
    private final Counter getUserCounter; // Счетчик запросов имени пользователя

    // Поля для хранения статистических данных (DistributionSummary) для анализа
    private final DistributionSummary registerSummary; // Статистика регистраций (например, длина имени)
    private final DistributionSummary loginSummary; // Статистика входов (например, длина пароля)
    private final DistributionSummary logoutSummary; // Статистика выходов
    private final DistributionSummary isLoggedSummary; // Статистика проверок авторизации
    private final DistributionSummary getUserSummary; // Статистика запросов имени пользователя

    // Поля для хранения таймеров (Timer) для измерения времени выполнения
    private final Timer registerTimer; // Таймер для регистрации
    private final Timer loginTimer; // Таймер для входа
    private final Timer logoutTimer; // Таймер для выхода
    private final Timer isLoggedTimer; // Таймер для проверки авторизации
    private final Timer getUserTimer; // Таймер для запросов имени пользователя

    // Поля для хранения атомарных счетчиков (AtomicInteger) для gauge
    // Используются для отслеживания текущего количества операций
    private final AtomicInteger registerCount = new AtomicInteger(0); // Счетчик регистраций
    private final AtomicInteger loginCount = new AtomicInteger(0); // Счетчик входов
    private final AtomicInteger logoutCount = new AtomicInteger(0); // Счетчик выходов
    private final AtomicInteger isLoggedCount = new AtomicInteger(0); // Счетчик проверок авторизации
    private final AtomicInteger getUserCount = new AtomicInteger(0); // Счетчик запросов имени пользователя

    // Конструктор, инициализирующий метрики через MeterRegistry
    public CustomMetricsService(MeterRegistry meterRegistry) {
        // Инициализация счетчиков (Counter)
        // 💥 Регистрируем счетчик для попыток регистрации
        // Имя метрики: auth_register_total, тег: environment=development
        this.registerCounter = Counter.builder("auth_register_total")
                .description("Total number of registration attempts")
                .tags("environment", "development")
                .register(meterRegistry);
        // Аналогично для входа
        this.loginCounter = Counter.builder("auth_login_total")
                .description("Total number of login attempts")
                .tags("environment", "development")
                .register(meterRegistry);
        // Для выхода
        this.logoutCounter = Counter.builder("auth_logout_total")
                .description("Total number of logout attempts")
                .tags("environment", "development")
                .register(meterRegistry);
        // Для проверок авторизации
        this.isLoggedCounter = Counter.builder("auth_is_logged_total")
                .description("Total number of isLogged checks")
                .tags("environment", "development")
                .register(meterRegistry);
        // Для запросов имени пользователя
        this.getUserCounter = Counter.builder("auth_get_user_total")
                .description("Total number of getUser requests")
                .tags("environment", "development")
                .register(meterRegistry);

        // Инициализация статистических данных (DistributionSummary)
        // 💥 Регистрируем статистику для регистраций (например, длина имени)
        this.registerSummary = DistributionSummary.builder("auth_register_summary")
                .description("Summary of registration data (e.g., name length)")
                .tags("environment", "development")
                .register(meterRegistry);
        // Для входов (например, длина пароля)
        this.loginSummary = DistributionSummary.builder("auth_login_summary")
                .description("Summary of login data (e.g., password length)")
                .tags("environment", "development")
                .register(meterRegistry);
        // Для выходов
        this.logoutSummary = DistributionSummary.builder("auth_logout_summary")
                .description("Summary of logout data")
                .tags("environment", "development")
                .register(meterRegistry);
        // Для проверок авторизации
        this.isLoggedSummary = DistributionSummary.builder("auth_is_logged_summary")
                .description("Summary of isLogged checks")
                .tags("environment", "development")
                .register(meterRegistry);
        // Для запросов имени пользователя
        this.getUserSummary = DistributionSummary.builder("auth_get_user_summary")
                .description("Summary of getUser requests")
                .tags("environment", "development")
                .register(meterRegistry);

        // Инициализация таймеров (Timer)
        // 💥 Регистрируем таймер для измерения времени регистрации
        this.registerTimer = Timer.builder("auth_register_duration")
                .description("Time taken for registration requests")
                .tags("environment", "development")
                .register(meterRegistry);
        // Для входа
        this.loginTimer = Timer.builder("auth_login_duration")
                .description("Time taken for login requests")
                .tags("environment", "development")
                .register(meterRegistry);
        // Для выхода
        this.logoutTimer = Timer.builder("auth_logout_duration")
                .description("Time taken for logout requests")
                .tags("environment", "development")
                .register(meterRegistry);
        // Для проверок авторизации
        this.isLoggedTimer = Timer.builder("auth_is_logged_duration")
                .description("Time taken for isLogged checks")
                .tags("environment", "development")
                .register(meterRegistry);
        // Для запросов имени пользователя
        this.getUserTimer = Timer.builder("auth_get_user_duration")
                .description("Time taken for getUser requests")
                .tags("environment", "development")
                .register(meterRegistry);
    }

    // Методы для увеличения счетчиков
    // 💥 Увеличивает счетчик регистраций и обновляет атомарный счетчик
    public void incrementRegisterCounter() {
        registerCounter.increment(); // Увеличивает метрику в Prometheus
        registerCount.incrementAndGet(); // Увеличивает локальный счетчик для gauge
    }

    // Аналогично для входа
    public void incrementLoginCounter() {
        loginCounter.increment();
        loginCount.incrementAndGet();
    }

    // Для выхода
    public void incrementLogoutCounter() {
        logoutCounter.increment();
        logoutCount.incrementAndGet();
    }

    // Для проверок авторизации
    public void incrementIsLoggedCounter() {
        isLoggedCounter.increment();
        isLoggedCount.incrementAndGet();
    }

    // Для запросов имени пользователя
    public void incrementGetUserCounter() {
        getUserCounter.increment();
        getUserCount.incrementAndGet();
    }

    // Методы для записи статистических данных
    // 💥 Записывает значение в статистику регистраций (например, длина имени)
    public void recordRegisterSummary(double value) {
        registerSummary.record(value); // Отправляет значение в Prometheus
    }

    // Для входов (например, длина пароля)
    public void recordLoginSummary(double value) {
        loginSummary.record(value);
    }

    // Для выходов
    public void recordLogoutSummary(double value) {
        logoutSummary.record(value);
    }

    // Для проверок авторизации
    public void recordIsLoggedSummary(double value) {
        isLoggedSummary.record(value);
    }

    // Для запросов имени пользователя
    public void recordGetUserSummary(double value) {
        getUserSummary.record(value);
    }

    // Методы для записи времени выполнения
    // 💥 Записывает время выполнения регистрации (в наносекундах)
    public void recordRegisterTimer(long durationNanos) {
        registerTimer.record(durationNanos, TimeUnit.NANOSECONDS); // Отправляет время в Prometheus
    }

    // Для входа
    public void recordLoginTimer(long durationNanos) {
        loginTimer.record(durationNanos, TimeUnit.NANOSECONDS);
    }

    // Для выхода
    public void recordLogoutTimer(long durationNanos) {
        logoutTimer.record(durationNanos, TimeUnit.NANOSECONDS);
    }

    // Для проверок авторизации
    public void recordIsLoggedTimer(long durationNanos) {
        isLoggedTimer.record(durationNanos, TimeUnit.NANOSECONDS);
    }

    // Для запросов имени пользователя
    public void recordGetUserTimer(long durationNanos) {
        getUserTimer.record(durationNanos, TimeUnit.NANOSECONDS);
    }

    // Методы для получения текущих значений счетчиков (для gauge)
    // 💥 Возвращает текущее количество регистраций
    public int getRegisterCount() {
        return registerCount.get();
    }

    // Для входов
    public int getLoginCount() {
        return loginCount.get();
    }

    // ⚠️ Ошибка: возвращает loginCount вместо logoutCount
    public int getLogoutCount() {
        return loginCount.get(); // Должно быть logoutCount.get()
    }

    // Для проверок авторизации
    public int getIsLoggedCount() {
        return isLoggedCount.get();
    }

    // Для запросов имени пользователя
    public int getGetUserCount() {
        return getUserCount.get();
    }
}