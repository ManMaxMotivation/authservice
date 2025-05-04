# Auth Mock Service

Сервис-заглушка для эмуляции работы микросервиса авторизации в нагрузочном тестировании.

## 📌 Функции
- ✅ Регистрация новых пользователей
- 🔑 Аутентификация по логину/паролю
- 📊 Сбор метрик для Prometheus
- 📚 Swagger-документация API

## 🔹 Назначение
- Эмулирует API авторизации для основного сервиса `standartmock`
 ```bash
   git clone https://github.com//ManMaxMotivation/standartmock
   cd auth-service
   ```
- Позволяет тестировать:
    - Обработку таймаутов
    - Устойчивость к ошибкам
    - Работу с метриками Prometheus

## 📌 Особенности
- Все операции работают с "фейковыми" данными в памяти
- Поддерживает искусственные задержки ответов
- Интегрирован с Prometheus для сбора метрик
- Автодокументирование API через Swagger UI

## 🚀 Запуск
1. **Склонируйте репозиторий**:
   ```bash
   git clone https://github.com/ManMaxMotivation/authservice
   cd auth-service
   ```
2. **Запустите приложение**:
   ```bash
   mvn spring-boot:run
   ```

## 🔗 API Endpoints
| Метод | Путь           | Описание                  |
|-------|----------------|---------------------------|
| POST  | `/auth/register` | Регистрация пользователя  |
| POST  | `/auth/login`    | Вход в систему            |
| POST  | `/auth/logout`   | Выход из системы          |
| GET   | `/auth/user`     | Текущий пользователь      |

## 📊 Метрики
Доступны на: `http://localhost:8080/actuator/prometheus`
- Количество запросов
- Время ответа
- Статусы операций

## 📜 Требования
- Java 17
- Maven 3.8+
- Prometheus (для мониторинга)

## 📝 Пример запроса
```bash
curl -X POST "http://localhost:8080/auth/login" \
  -d "username=test&password=12345"
```

## 🔍 Документация
Swagger UI: `http://localhost:8080/swagger-ui.html`  