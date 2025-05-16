package com.bankapp.auth.swagger;

// Импорты необходимых библиотек для конфигурации Swagger/OpenAPI
import org.springframework.context.annotation.Bean; // Аннотация для создания Spring-бина
import org.springframework.context.annotation.Configuration; // Аннотация для обозначения конфигурационного класса
import io.swagger.v3.oas.models.OpenAPI; // Класс для создания спецификации OpenAPI
import io.swagger.v3.oas.models.info.Info; // Класс для описания общей информации об API
import io.swagger.v3.oas.models.info.Contact; // Класс для добавления контактной информации

// 💥 @Configuration указывает, что это конфигурационный класс Spring
// Spring сканирует этот класс и регистрирует бины, определенные в нем
@Configuration
public class OpenAPIConfig {

    // 💥 @Bean создает бин OpenAPI, который Spring будет использовать для генерации документации API
    // Этот бин настраивает Swagger для документирования всех эндпоинтов приложения
    @Bean
    public OpenAPI customOpenAPI() {
        // Создаем объект OpenAPI с информацией о API
        return new OpenAPI()
                // Настраиваем общую информацию об API
                .info(new Info()
                        // Заголовок API, отображается в Swagger UI
                        .title("Bank Application API")
                        // Версия API
                        .version("1.0")
                        // Описание API
                        .description("API for user authentication and management in Bank Application")
                        // Контактная информация разработчика
                        .contact(new Contact()
                                .name("Alex") // Имя контактного лица
                                .email("my@ya.ru") // Email для связи
                                .url("https://example.com")) // Ссылка на сайт или профиль
                );
    }
}