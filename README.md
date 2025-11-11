# Kafka Event Sourcing (Full) - Multi-module Spring Boot project

This repository is a full production-oriented sample implementing Event Sourcing with Kafka + Schema Registry.
Modules: common, order-app, inventory-service, billing-service, readmodel-service.

Run:
1. docker-compose up -d
2. mvn clean package -DskipTests
3. mvn -pl order-app spring-boot:run (then run other modules similarly)


---

Что включено

docker-compose.yml с Zookeeper, Kafka и Confluent Schema Registry (порт 8081).

Модули:

common — содержит Avro-схему OrderCreatedAvro.avsc и avro-maven-plugin (генерация Java-классов).

order-app — фронтенд (Thymeleaf) + producer, использует KafkaAvroSerializer и регистрирует схемы в Schema Registry.

inventory-service — consumer, idempotent (H2 + JPA), сохраняет обработанные eventId.

billing-service — consumer, idempotent.

readmodel-service — consumer, строит проекцию (OrderProjection) и предоставляет REST API /orders.

Dockerfile для каждого модуля (чтобы можно было собрать образ).

README в корне и README в каждом модуле (описание бизнес-задачи + краткие инструкции).

Idempotency: все консьюмеры записывают eventId в локальную H2 базу, чтобы игнорировать повторную обработку.

Примеры кода: контроллер создания заказа, слушатели, проекция, репозитории.

Краткие инструкции по запуску

Подними инфраструктуру:

docker-compose up -d


Schema Registry: http://localhost:8081

Kafka bootstrap: localhost:29092 (advertised)

Собери проект:

mvn clean package -DskipTests


Запусти сервисы (рекомендуется в этом порядке, но не обязательно):

mvn -pl order-app spring-boot:run
mvn -pl inventory-service spring-boot:run
mvn -pl billing-service spring-boot:run
mvn -pl readmodel-service spring-boot:run


Открой http://localhost:8080 — создавай заказы.
Проверяй логи inventory/billing/readmodel — они будут показывать обработку событий.
Список проекций: GET http://localhost:8083/orders.

Ограничения / заметки

Avro-классы генерируются плагином avro-maven-plugin при mvn generate-sources/mvn package. Убедись, что common модуль корректно соберётся первым.

Для production нужно:

ограничить spring.json.trusted.packages (если используешь JSON) — здесь мы используем Avro/Schema Registry;

настроить безопасный доступ к Kafka (SASL/SSL) и Schema Registry;

внедрить устойчивое хранилище для проекций (не in-memory H2);

обеспечить транзакционную согласованность, мониторинг, управление схемами (как политика версионирования).

Если хочешь, я могу:

добавить docker-compose сервисы для сборки и запуска всех модулей как Docker-контейнеров (images) и показать пример docker-compose.override.yml для запуска всего в контейнерах;

сгенерировать OpenAPI (springdoc) для readmodel-service;

или распаковать и показать конкретные файлы (например, OrderController.java или ReadModelListener.java) прямо в чате.