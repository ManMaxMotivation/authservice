// Этот файл не используется в проекте
package com.bankapp.auth.model;

// Импорты необходимых библиотек
import jakarta.persistence.*; // Импортируем аннотации JPA для работы с базой данных (PostgreSQL в данном случае)
import lombok.Data; // Аннотация Lombok для автоматической генерации геттеров, сеттеров, toString, equals, hashCode
import java.util.UUID; // Класс Java для генерации уникальных идентификаторов

// Аннотация Lombok @Data автоматически создает геттеры, сеттеры, equals, hashCode и toString
// 💥 @Entity указывает, что этот класс является JPA-сущностью, т.е. соответствует таблице в БД
@Entity
@Data
public class Account {

    // 💥 @Id обозначает первичный ключ таблицы
    // 💥 @GeneratedValue(strategy = GenerationType.IDENTITY) указывает, что ID будет автоматически генерироваться БД (в PostgreSQL это SERIAL)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Уникальный идентификатор аккаунта, хранится в столбце id таблицы

    // 💥 @Column определяет свойства столбца в таблице
    // nullable = false: значение не может быть null
    // unique = true: номер счета должен быть уникальным
    // length = 12: максимальная длина строки 12 символов
    @Column(nullable = false, unique = true, length = 12)
    private String accountNumber; // Номер счета, хранится в столбце account_number

    // 💥 Аналогично для номера карты: уникальный, не null, длина 16 символов
    @Column(nullable = false, unique = true, length = 16)
    private String cardNumber; // Номер карты, хранится в столбце card_number

    // 💥 Баланс счета, не может быть null
    @Column(nullable = false)
    private double balance; // Баланс счета, хранится в столбце balance

    // 💥 @ManyToOne указывает на связь "многие-к-одному": много аккаунтов могут принадлежать одному клиенту
    // 💥 @JoinColumn(name = "client_id", nullable = false) создает внешний ключ client_id, ссылающийся на таблицу клиентов
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client; // Владелец аккаунта, ссылка на объект Client (в БД это внешний ключ)

    // Конструктор без параметров
    public Account() {
        // Генерируем номер счета: берем UUID, убираем дефисы и обрезаем до 12 символов
        // UUID создает уникальную строку, чтобы гарантировать уникальность номера счета
        this.accountNumber = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
        // Генерируем номер карты: аналогично, но обрезаем до 16 символов
        this.cardNumber = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }

    // Геттер для ID аккаунта
    // Возвращает уникальный идентификатор аккаунта, сгенерированный БД
    public Long getId() {
        return id;
    }

    // Геттер для номера счета
    // Возвращает уникальный номер счета (12 символов)
    public String getAccountNumber() {
        return accountNumber;
    }

    // Геттер для номера карты
    // Возвращает уникальный номер карты (16 символов)
    public String getCardNumber() {
        return cardNumber;
    }

    // Геттер для баланса
    // Возвращает текущий баланс счета
    public double getBalance() {
        return balance;
    }

    // Сеттер для баланса
    // Позволяет установить новое значение баланса (например, при пополнении или списании)
    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Геттер для клиента
    // Возвращает объект Client, которому принадлежит аккаунт
    public Client getClient() {
        return client;
    }

    // Сеттер для клиента
    // Устанавливает владельца аккаунта (объект Client)
    public void setClient(Client client) {
        this.client = client;
    }
}