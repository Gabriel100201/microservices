# Microservicio de Órdenes

Este microservicio maneja la gestión de órdenes de compra, incluyendo la creación, actualización y seguimiento de órdenes.

## Tecnologías Utilizadas

- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL
- Spring Security
- Swagger/OpenAPI
- Lombok
- Maven
- Spring Cloud (para comunicación entre microservicios)

## Configuración

### Variables de Entorno

Crear un archivo `application.properties` con las siguientes configuraciones:

```properties
server.port=8081
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.datasource.url=jdbc:postgresql://localhost:5432/orders
spring.datasource.username=admin
spring.datasource.password=1234
```

## Endpoints

### Órdenes

- `GET /api/orders` - Obtener todas las órdenes
- `GET /api/orders/{id}` - Obtener una orden por ID
- `POST /api/orders` - Crear una nueva orden
- `PUT /api/orders/{id}` - Actualizar una orden existente
- `DELETE /api/orders/{id}` - Eliminar una orden
- `GET /api/orders/status/{status}` - Obtener órdenes por estado

### Documentación Swagger

La documentación completa de la API está disponible en:
- Swagger UI: http://localhost:8080/swagger-ui.html

## Manejo de Excepciones

El servicio incluye manejo personalizado de excepciones:

- `OrderNotFoundException`: Cuando no se encuentra una orden
- `OrderValidationException`: Para errores de validación
- `ProductNotAvailableException`: Cuando un producto no está disponible

## Seguridad

El servicio implementa autenticación básica HTTP. Para acceder a los endpoints:

1. Usar las credenciales configuradas en el servicio:
   - Usuario: admin
   - Contraseña: admin

## Comunicación entre Microservicios

El servicio de órdenes se comunica con el servicio de productos para:
- Verificar la disponibilidad de productos
- Obtener información de productos
- Actualizar el inventario

## Ejecución

### Usando Maven
```bash
mvn spring-boot:run
```

### Usando Docker
```bash
docker build -t orders-service .
docker run -p 8081:8081 orders-service
```

## Flujo de Trabajo

1. Creación de Orden
   - Validación de productos
   - Cálculo de totales
   - Generación de número de orden

2. Procesamiento de Orden
   - Actualización de inventario
   - Cambio de estado
   - Notificaciones

3. Cancelación de Orden
   - Validación de estado
   - Reversión de inventario
   - Actualización de estado 