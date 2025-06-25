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

## Flujo de Funcionamiento del Sistema

### Orden de Operaciones para Probar el Proyecto

Para probar completamente el sistema, debes seguir este orden específico de operaciones:

#### 1. Crear una Categoría
Primero, necesitas crear una categoría para los productos:

#### 2. Crear un Producto
Una vez creada la categoría, puedes crear productos asociados a ella

#### 3. Crear una Orden
Finalmente, puedes crear una orden con los productos disponibles:

### Estados de las Órdenes

El sistema maneja los siguientes estados para las órdenes:

#### Estados Disponibles:
- **PENDING**: Estado inicial de la orden cuando se crea
- **CONFIRMED**: Orden confirmada por el sistema
- **CANCELLED**: Orden cancelada
- **COMPLETED**: Orden completada y entregada

#### Comportamiento por Estado:

- **PENDING → CONFIRMED**: 
  - Se verifica el stock disponible
  - Se actualiza automáticamente el stock de los productos
  - La orden queda confirmada para procesamiento

- **CONFIRMED → COMPLETED**: 
  - La orden se marca como completada
  - Indica que el producto ha sido entregado

- **Cualquier estado → CANCELLED**: 
  - La orden se cancela
  - No se puede cambiar a otros estados
  - Se reintegra el stock

### Verificación de Stock

El sistema verifica automáticamente el stock disponible:

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