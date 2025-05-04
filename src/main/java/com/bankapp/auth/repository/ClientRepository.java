package com.bankapp.auth.repository;

// Импорты необходимых библиотек
import com.bankapp.auth.model.Client; // Модель клиента для работы с данными пользователя
import java.util.*; // Классы Java для работы с коллекциями (HashMap, Optional, Collection)

// Класс-репозиторий для хранения и управления данными о клиентах
public class ClientRepository {
    // Хранилище клиентов: ключ — ID клиента, значение — объект Client
    // Используем HashMap для быстрого поиска по ID
    private static final Map<String, Client> clients = new HashMap<>();

    // Метод для сохранения клиента в хранилище
    public static Client save(Client client) {
        // Добавляем клиента в HashMap, используя его ID как ключ
        clients.put(client.getId(), client);
        // Возвращаем сохраненного клиента
        return client;
    }

    // Метод для поиска клиента по логину (username)
    public static Optional<Client> findByUsername(String username) {
        // Проходим по всем клиентам в хранилище
        // Фильтруем тех, у кого username совпадает с переданным
        // Возвращаем первого найденного клиента (или пустой Optional, если не найден)
        return clients.values().stream()
                .filter(c -> c.getUsername().equals(username))
                .findFirst();
    }

    // Метод для поиска клиента по ID
    public static Optional<Client> findById(String id) {
        // Ищем клиента в HashMap по ID
        // Возвращаем Optional, содержащий клиента (или пустой, если не найден)
        return Optional.ofNullable(clients.get(id));
    }

    // Метод для получения всех клиентов
    public static Collection<Client> getAllClients() {
        // Возвращаем коллекцию всех клиентов из HashMap
        return clients.values();
    }
}