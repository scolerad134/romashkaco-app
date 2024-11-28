# Product REST API

## Описание
Этот проект представляет собой REST API для работы с товарами интернет-магазина "МоиТовары". Реализован полный CRUD функционал с использованием **Spring Boot**.

---

## Функциональные возможности

- **Получение списка товаров**  
  Эндпоинт: `GET /api/v1/products`  
  Возвращает список всех товаров в формате JSON.


- **Получение товара по ID**  
  Эндпоинт: `GET /api/v1/products/{id}`  
  Параметры:
    - `id` (PathVariable) — идентификатор товара.  
      Возвращает товар с указанным идентификатором. Если товар не найден, возвращает HTTP статус `404 Not Found`.


- **Создание товара**  
  Эндпоинт: `POST /api/v1/products`  
  Тело запроса (JSON):
  ```json
  {
      "name": "Название товара",
      "description": "Описание товара",
      "price": 100.0,
      "inStock": true
  }


- **Обновление товара**  
  **Эндпоинт:** `PUT /api/v1/products/{id}`  
  **Параметры:**
    - `id` (PathVariable) — идентификатор товара.

  **Тело запроса (JSON):**
  ```json
  {
      "name": "Обновленное название",
      "description": "Обновленное описание",
      "price": 200.0,
      "inStock": false
  }


- **Удаление товара по ID**  
  Эндпоинт: `DELETE /api/v1/products/{id}`  
  Параметры:
    - `id` (PathVariable) — идентификатор товара.  
      Удаляет товар с помощью идентификатора. Если товар найден - статус 204 No Content.
      Тело ответа отсутствует, если товар не найден - статус 404 Not Found

---

## Технические детали
- **Java**: версия 21
- **Spring Boot**: используется для создания REST API
- **Maven**: управление зависимостями и сборка проекта
- **JUnit + Mockito**: тестирование функционала

---

## Установка и запуск

1. **Клонировать репозиторий**:
   ```bash
   git clone https://github.com/scolerad134/romashkaco-app/tree/main
   cd romashkaco-app
   ./start.sh