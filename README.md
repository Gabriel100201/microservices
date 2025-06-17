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
  - Usuario: axel
  - Contraseña: axel
  - Base de datos: product

- Base de datos de Órdenes:
  - Puerto: 5432
  - Usuario: axel
  - Contraseña: axel
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
Authorization: Basic <credenciales_en_base64>
```

## Contribución

1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request