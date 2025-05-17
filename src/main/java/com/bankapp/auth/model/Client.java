package com.bankapp.auth.model;

// Импорты необходимых библиотек для работы с JPA и коллекциями
import jakarta.persistence.Entity; // Аннотация JPA для обозначения класса как сущности
import jakarta.persistence.Id; // Аннотация для первичного ключа
import jakarta.persistence.GeneratedValue; // Аннотация для автоматической генерации значений ключа
import jakarta.persistence.GenerationType; // Перечисление стратегий генерации ключей
import jakarta.persistence.Column; // Аннотация для настройки столбцов в БД
import jakarta.persistence.OneToMany; // Аннотация для связи "один ко многим"
import java.util.ArrayList; // Класс для работы со списками
import java.util.List; // Интерфейс для работы со списками

// 💥 @Entity указывает, что этот класс является JPA-сущностью, т.е. соответствует таблице в PostgreSQL (по умолчанию таблица будет называться `client`)
@Entity
public class Client {

    // 💥 @Id обозначает первичный ключ таблицы
    // 💥 @GeneratedValue(strategy = GenerationType.IDENTITY) указывает, что ID будет автоматически генерироваться PostgreSQL (тип SERIAL)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Уникальный идентификатор клиента, хранится в столбце `id`. Используется Long вместо UUID для простоты и совместимости с БД

    // 💥 @Column(nullable = false) указывает, что поле `fullName` обязательно (не может быть null) в таблице
    @Column(nullable = false)
    private String fullName; // Полное имя клиента, хранится в столбце `full_name`

    // 💥 @Column(nullable = false, unique = true) указывает, что номер телефона обязателен и должен быть уникальным
    @Column(nullable = false, unique = true)
    private String phone; // Номер телефона клиента, хранится в столбце `phone`

    // 💥 @Column(nullable = false, unique = true) указывает, что логин обязателен и должен быть уникальным
    @Column(nullable = false, unique = true)
    private String username; // Логин клиента, хранится в столбце `username`

    // 💥 @Column(nullable = false) указывает, что пароль обязателен
    @Column(nullable = false)
    private String password; // Пароль клиента, хранится в столбце `password` (в реальном проекте должен быть захеширован)

    // 💥 @OneToMany(mappedBy = "client") устанавливает связь "один ко многим" с классом `Account`
    // Один клиент может иметь несколько аккаунтов, `mappedBy = "client"` указывает, что связь определяется полем `client` в классе `Account`
    // ⚠️ Поскольку `Account` не используется, эта связь в текущем проекте неактивна
    @OneToMany(mappedBy = "client")
    private List<Account> accounts = new ArrayList<>(); // Список счетов клиента, инициализируется пустым ArrayList

    // Конструктор без параметров
    // Необходим для JPA, так как Hibernate использует рефлексию для создания объектов
    public Client() {
    }

    // Конструктор с параметрами
    // Используется для создания объекта `Client` с указанными данными (например, при регистрации)
    public Client(String fullName, String phone, String username, String password) {
        this.fullName = fullName;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    // Геттер для ID
    // Возвращает уникальный идентификатор клиента, сгенерированный БД
    public Long getId() {
        return id;
    }

    // Сеттер для ID
    // Устанавливает ID клиента (обычно используется JPA при загрузке из БД)
    public void setId(Long id) {
        this.id = id;
    }

    // Геттер для полного имени
    // Возвращает полное имя клиента
    public String getFullName() {
        return fullName;
    }

    // Сеттер для полного имени
    // Устанавливает полное имя клиента
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    // Геттер для номера телефона
    // Возвращает номер телефона клиента
    public String getPhone() {
        return phone;
    }

    // Сеттер для номера телефона
    // Устанавливает номер телефона клиента
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Геттер для логина
    // Возвращает логин клиента
    public String getUsername() {
        return username;
    }

    // Сеттер для логина
    // Устанавливает логин клиента
    public void setUsername(String username) {
        this.username = username;
    }

    // Геттер для пароля
    // Возвращает пароль клиента (в реальном проекте должен быть захеширован)
    public String getPassword() {
        return password;
    }

    // Сеттер для пароля
    // Устанавливает пароль клиента
    public void setPassword(String password) {
        this.password = password;
    }

    // Геттер для списка счетов
    // Возвращает список счетов клиента (пустой, если `Account` не используется)
    public List<Account> getAccounts() {
        return accounts;
    }

    // Сеттер для списка счетов
    // Устанавливает список счетов клиента (использовался бы, если `Account` был активен)
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}