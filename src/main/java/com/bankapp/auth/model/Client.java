package com.bankapp.auth.model;

// Импорты необходимых библиотек
import lombok.Data; // Аннотация Lombok для автоматической генерации геттеров, сеттеров, toString и др.
import java.util.ArrayList; // Класс Java для работы с динамическими списками
import java.util.List; // Интерфейс для работы со списками
import java.util.UUID; // Класс Java для генерации уникальных идентификаторов

// Аннотация Lombok @Data автоматически создает геттеры, сеттеры, equals, hashCode и toString
@Data
public class Client {
    // Поля класса для хранения данных о клиенте
    private String id; // Уникальный идентификатор клиента
    private String fullName; // Полное имя клиента (например, "Иван Иванов")
    private String phone; // Номер телефона клиента (например, "+79001112233")
    private String username; // Уникальный логин клиента (например, "user1")
    private String password; // Пароль клиента (например, "pass1")
    private List<Account> accounts = new ArrayList<>(); // Список счетов клиента (инициализируется пустым)

    // Конструктор для создания нового клиента с указанными данными
    public Client(String fullName, String phone, String username, String password) {
        // Генерируем уникальный ID для клиента с помощью UUID
        this.id = UUID.randomUUID().toString();
        // Сохраняем переданные данные в поля класса
        this.fullName = fullName;
        this.phone = phone;
        this.username = username;
        this.password = password;
        // Список счетов уже инициализирован как пустой ArrayList
    }

    // Геттер для полного имени клиента (возвращает имя, например, "Иван Иванов")
    public String getFullName() {
        return fullName;
    }

    // Геттер для ID клиента (возвращает уникальный идентификатор)
    public String getId() {
        return id;
    }

    // Геттер для номера телефона клиента (возвращает телефон, например, "+79001112233")
    public String getPhone() {
        return phone;
    }

    // Геттер для логина клиента (возвращает логин, например, "user1")
    public String getUsername() {
        return username;
    }

    // Геттер для пароля клиента (возвращает пароль, например, "pass1")
    public String getPassword() {
        return password;
    }

    // Геттер для списка счетов клиента (возвращает список объектов Account)
    public List<Account> getAccounts() {
        return accounts;
    }
}