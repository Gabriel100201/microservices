# Microservicios UCC

Este proyecto consiste en una arquitectura de microservicios desarrollada con Spring Boot, que incluye dos servicios principales: Product y Orders.

## Estructura del Proyecto

```
microservice/
├── product/           # Microservicio de Productos
├── orders/           # Microservicio de Órdenes
└── docker-compose.yml # Configuración de Docker para las bases de datos
```

## Requisitos Previos

- Java 17 o superior
- Maven
- Docker y Docker Compose
- PostgreSQL

## Configuración del Entorno

1. Clonar el repositorio:
```bash
git clone [URL_DEL_REPOSITORIO]
cd microservice
```

2. Configurar las bases de datos:
El proyecto utiliza Docker Compose para configurar las bases de datos PostgreSQL. Las credenciales por defecto son:

- Base de datos de Productos:
  - Puerto: 5431
  - Usuario: admin
  - Contraseña: 1234
  - Base de datos: product

- Base de datos de Órdenes:
  - Puerto: 5432
  - Usuario: admin
  - Contraseña: 1234
  - Base de datos: orders

## Ejecución del Proyecto

### Iniciar las Bases de Datos

```bash
docker-compose up -d
```

### Ejecución Manual de los Servicios

1. Compilar los servicios:
```bash
mvn clean install
```

2. Ejecutar cada servicio por separado:
```bash
# Servicio de Productos
cd product
mvn spring-boot:run

# Servicio de Órdenes
cd orders
mvn spring-boot:run
```

## Documentación

Cada microservicio incluye su propia documentación Swagger UI, accesible en:

- Product Service: http://localhost:8080/swagger-ui.html
- Orders Service: http://localhost:8081/swagger-ui.html

## Seguridad

El proyecto implementa autenticación básica HTTP. Para acceder a los endpoints protegidos:

1. Usar las credenciales configuradas en cada servicio
2. Incluir en el header de las peticiones:
```
Authorization: Basic <usuario:contraseña>
```

Los servicios utilizan autenticación básica con usuario y contraseña directamente, sin necesidad de codificación en base64.