package com.bankapp.auth.repository;

// Импорты необходимых классов и интерфейсов
import com.bankapp.auth.model.Client; // Модель клиента, представляющая таблицу в БД
import org.springframework.data.jpa.repository.JpaRepository; // Интерфейс Spring Data JPA для работы с БД
import org.springframework.stereotype.Repository; // Аннотация для обозначения компонента репозитория
import java.util.Optional; // Класс для представления результата, который может быть пустым

// 💥 @Repository указывает, что это компонент Spring, реализующий доступ к данным (репозиторий)
// Spring автоматически создает реализацию этого интерфейса для работы с БД
@Repository
// 💥 Наследуется от JpaRepository, который предоставляет стандартные методы CRUD (create, read, update, delete)
// Client — сущность, с которой работает репозиторий, Long — тип первичного ключа (id)
public interface ClientRepository extends JpaRepository<Client, Long> {

    // 💥 Метод для поиска клиента по ID
    // Возвращает Optional<Client>, чтобы безопасно обрабатывать случаи, когда клиент не найден
    // Генерирует SQL-запрос: SELECT * FROM client WHERE id = ?
    Optional<Client> findById(Long id);

    // 💥 Метод для поиска клиента по логину (username)
    // Автоматически генерируется Spring Data JPA на основе имени метода
    // Генерирует SQL-запрос: SELECT * FROM client WHERE username = ?
    Optional<Client> findByUsername(String username);

    // 💥 Метод для проверки существования клиента с указанным номером телефона
    // Возвращает true, если клиент с таким телефоном существует, иначе false
    // Генерирует SQL-запрос: SELECT EXISTS (SELECT 1 FROM client WHERE phone = ?)
    boolean existsByPhone(String phone);
}