package com.bankapp.auth.service;

// Импорты необходимых классов
import com.bankapp.auth.model.Client; // Модель клиента, представляющая сущность в БД
import com.bankapp.auth.repository.ClientRepository; // Репозиторий для работы с таблицей clients в PostgreSQL
import org.springframework.beans.factory.annotation.Autowired; // Аннотация для внедрения зависимостей
import org.springframework.stereotype.Service; // Аннотация для обозначения сервисного слоя
import java.util.Optional; // Класс для безопасной обработки результатов, которые могут быть пустыми

// 💥 @Service указывает, что это компонент сервисного слоя Spring
// Spring автоматически создает бин этого класса и управляет его жизненным циклом
@Service
public class ClientService {

    // Зависимость от репозитория для работы с клиентами в БД
    private final ClientRepository clientRepository;

    // 💥 Конструктор с внедрением зависимости через @Autowired
    // Spring автоматически предоставляет реализацию ClientRepository
    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    // 💥 Метод регистрации нового клиента
    // Создает нового клиента и сохраняет его в БД
    public Client register(String fullName, String phone, String username, String password) {
        // Создаем новый объект Client с переданными данными
        Client client = new Client(fullName, phone, username, password);
        // 💥 Сохраняем клиента в БД через репозиторий
        // Генерирует SQL: INSERT INTO client (full_name, phone, username, password) VALUES (?, ?, ?, ?)
        // Возвращает сохраненного клиента с установленным ID (сгенерированным БД)
        return clientRepository.save(client);
    }

    // 💥 Метод входа по логину и паролю
    // Проверяет существование клиента с указанным логином и паролем
    public Optional<Client> login(String username, String password) {
        // 💥 Ищем клиента по логину через репозиторий
        // Генерирует SQL: SELECT * FROM client WHERE username = ?
        // Затем проверяем, совпадает ли пароль
        return clientRepository.findByUsername(username)
                .filter(client -> client.getPassword().equals(password));
    }

    // 💥 Метод проверки существования клиента по номеру телефона
    // Используется для предотвращения дубликатов при регистрации
    public boolean existsByPhone(String phone) {
        // 💥 Вызываем метод репозитория для проверки
        // Генерирует SQL: SELECT EXISTS (SELECT 1 FROM client WHERE phone = ?)
        return clientRepository.existsByPhone(phone);
    }
}