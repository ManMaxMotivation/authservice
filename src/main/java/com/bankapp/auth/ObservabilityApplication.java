package com.bankapp.auth;

// Импорты необходимых библиотек для Spring Boot
import org.springframework.boot.SpringApplication; // Класс для запуска Spring Boot приложения
import org.springframework.boot.autoconfigure.SpringBootApplication; // Аннотация для конфигурации Spring Boot

// 💥 @SpringBootApplication объединяет три аннотации:
// - @Configuration: указывает, что это конфигурационный класс
// - @EnableAutoConfiguration: включает автоконфигурацию Spring Boot
// - @ComponentScan: сканирует компоненты (контроллеры, сервисы, репозитории) в указанных пакетах
// scanBasePackages = {"observability", "com.bankapp"} указывает пакеты для сканирования
@SpringBootApplication(scanBasePackages = {"observability", "com.bankapp"})
public class ObservabilityApplication {

    // 💥 Точка входа в приложение (main метод)
    // Запускает Spring Boot приложение
    public static void main(String[] args) {
        // Выводит сообщение в консоль для отладки
        System.out.println("This is it");
        // 💥 Запускает приложение, инициализируя Spring контекст
        // SpringApplication.run создает контекст приложения и запускает встроенный сервер (например, Tomcat)
        SpringApplication.run(ObservabilityApplication.class, args);
    }
}