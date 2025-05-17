package com.bankapp.auth.util;

// Импорты необходимых классов
import com.bankapp.auth.model.Client; // Модель клиента, представляющая пользователя
import org.springframework.stereotype.Component; // Аннотация для обозначения компонента Spring

// 💥 @Component указывает, что это компонент Spring
// Spring автоматически создает бин этого класса и управляет его жизненным циклом
@Component
public class SessionManager {

    // Поле для хранения текущего авторизованного клиента
    // Если null, значит, пользователь не авторизован
    private Client loggedInClient;

    // 💥 Метод для авторизации пользователя
    // Сохраняет переданного клиента как текущего авторизованного
    public void login(Client client) {
        this.loggedInClient = client; // Устанавливает клиента в поле loggedInClient
    }

    // 💥 Метод для получения текущего авторизованного клиента
    // Возвращает объект Client или null, если никто не авторизован
    public Client getLoggedInClient() {
        return loggedInClient;
    }

    // 💥 Метод для завершения сессии
    // Очищает текущего авторизованного клиента
    public void logout() {
        this.loggedInClient = null; // Устанавливает loggedInClient в null, что означает выход из системы
    }

    // 💥 Метод для проверки статуса авторизации
    // Возвращает true, если пользователь авторизован, иначе false
    public boolean isLoggedIn() {
        return loggedInClient != null; // Проверяет, установлен ли loggedInClient
    }

    // 💥 Метод для получения текстового статуса авторизации
    // Возвращает строку "аутентифицирован" или "не аутентифицирован"
    public String getLoginStatus() {
        if (isLoggedIn()) { // Использует метод isLoggedIn для проверки
            return "аутентифицирован";
        } else {
            return "не аутентифицирован";
        }
    }
}