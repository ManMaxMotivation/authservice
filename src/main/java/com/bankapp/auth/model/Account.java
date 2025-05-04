// Этот файл не используется в проекте
package com.bankapp.auth.model;

// Импорты необходимых библиотек
import lombok.Data; // Аннотация Lombok для автоматической генерации геттеров, сеттеров, toString и др.
import java.util.UUID; // Класс Java для генерации уникальных идентификаторов

// Аннотация Lombok @Data автоматически создает геттеры, сеттеры, equals, hashCode и toString
@Data
public class Account {
    // Поля класса для хранения данных об аккаунте
    private String id; // Уникальный идентификатор аккаунта
    private String accountNumber; // Номер счета (уникальный, 12 символов)
    private String cardNumber; // Номер карты (уникальный, 16 символов)
    private double balance; // Баланс счета

    // Конструктор без параметров
    public Account() {
        // Генерируем уникальный ID для аккаунта с помощью UUID
        this.id = UUID.randomUUID().toString();
        // Генерируем номер счета: берем UUID, убираем дефисы и обрезаем до 12 символов
        this.accountNumber = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
        // Генерируем номер карты: берем UUID, убираем дефисы и обрезаем до 16 символов
        this.cardNumber = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }

    // Геттер для ID аккаунта (возвращает уникальный идентификатор)
    public String getId() {
        return id;
    }

    // Геттер для номера счета (возвращает номер счета)
    public String getAccountNumber() {
        return accountNumber;
    }

    // Геттер для номера карты (возвращает номер карты)
    public String getCardNumber() {
        return cardNumber;
    }

    // Геттер для баланса (возвращает текущий баланс счета)
    public double getBalance() {
        return balance;
    }

    // Сеттер для баланса (позволяет установить новое значение баланса)
    public void setBalance(double balance) {
        this.balance = balance;
    }
}