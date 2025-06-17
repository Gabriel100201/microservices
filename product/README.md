# Microservicio de Productos

Este microservicio maneja la gestión de productos, incluyendo operaciones CRUD y validaciones de negocio.

## Tecnologías Utilizadas

- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL
- Spring Security
- Swagger/OpenAPI
- Lombok
- Maven

## Configuración

### Variables de Entorno

Crear un archivo `application.properties` con las siguientes configuraciones:

```properties
server.port=8080

spring.datasource.url=jdbc:postgresql://localhost:5431/product
spring.datasource.username=axel
spring.datasource.password=axel
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

## Endpoints

### Productos

- `GET /api/products` - Obtener todos los productos
- `GET /api/products/{id}` - Obtener un producto por ID
- `POST /api/products` - Crear un nuevo producto
- `PUT /api/products/{id}` - Actualizar un producto existente
- `DELETE /api/products/{id}` - Eliminar un producto

### Documentación Swagger

La documentación completa de la API está disponible en:
- Swagger UI: http://localhost:8080/swagger-ui.html

## Manejo de Excepciones

El servicio incluye manejo personalizado de excepciones:

- `ProductNotFoundException`: Cuando no se encuentra un producto
- `ProductValidationException`: Para errores de validación
- `ProductNotExistException`: Para productos que no existen

## Seguridad

El servicio implementa autenticación básica HTTP. Para acceder a los endpoints:

1. Usar las credenciales configuradas en el servicio:
   - Usuario: admin
   - Contraseña: admin

## Ejecución

### Usando Maven
```bash
mvn spring-boot:run
```

### Usando Docker
```bash
docker build -t product-service .
docker run -p 8080:8080 product-service
``` 