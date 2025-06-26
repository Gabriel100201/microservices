# Colección de Postman - Microservicios UCC

Esta carpeta contiene una colección completa de Postman para probar todos los endpoints de los microservicios de Productos y Órdenes.

## 📋 Contenido

- `Microservicios UCC.postman_collection.json` - Colección principal de Postman
- `README.md` - Este archivo con instrucciones

## 🚀 Configuración Inicial

### 1. Importar la Colección

1. Abre Postman
2. Haz clic en "Import"
3. Selecciona el archivo `Microservicios UCC.postman_collection.json`
4. La colección se importará automáticamente

### 2. Configurar Variables de Entorno

La colección incluye variables predefinidas, pero puedes modificarlas según tu configuración:

- `base_url_product`: http://localhost:8080
- `base_url_orders`: http://localhost:8081
- `username`: admin
- `password`: 1234

## 📖 Flujo de Pruebas Recomendado

### Orden de Ejecución

Para probar completamente el sistema, sigue este orden específico:

#### 1. Configuración Inicial
- ✅ Verificar que las bases de datos estén corriendo (`docker-compose up -d`)
- ✅ Verificar que ambos servicios estén ejecutándose
- ✅ Importar la colección de Postman

#### 2. Servicio de Productos (Puerto 8080)

**Paso 1: Crear Categorías**
1. `Crear Categoría` - Crear la primera categoría
2. `Obtener Todas las Categorías` - Verificar que se creó correctamente

**Paso 2: Crear Productos**
3. `Crear Producto` - Crear el primer producto
4. `Crear Segundo Producto` - Crear un segundo producto
5. `Obtener Todos los Productos` - Verificar que se crearon correctamente

**Paso 3: Verificar Información de Productos**
6. `Obtener Producto por ID` - Verificar detalles de un producto
7. `Obtener Stock de Producto` - Verificar stock disponible
8. `Obtener Precio de Producto` - Verificar precio

#### 3. Servicio de Órdenes (Puerto 8081)

**Paso 4: Crear y Gestionar Órdenes**
9. `Crear Orden` - Crear una orden con productos
10. `Obtener Todas las Órdenes` - Verificar que se creó
11. `Obtener Orden por ID` - Verificar detalles de la orden

**Paso 5: Gestionar Estados de Órdenes**
12. `Confirmar Orden` - Cambiar estado a CONFIRMED
13. `Verificar Stock Después de Orden` - Verificar que el stock se actualizó
14. `Completar Orden` - Cambiar estado a COMPLETED

#### 4. Casos de Prueba Avanzados

**Paso 6: Probar Manejo de Errores**
15. `Crear Orden con Stock Insuficiente` - Probar validación de stock
16. `Crear Orden con Producto Inexistente` - Probar validación de productos
17. `Cancelar Orden` - Probar cancelación y reintegro de stock

## 🔄 Estados de las Órdenes

El sistema maneja los siguientes estados:

- **PENDING**: Estado inicial al crear la orden
- **CONFIRMED**: Orden confirmada (actualiza stock automáticamente)
- **COMPLETED**: Orden completada y entregada
- **CANCELLED**: Orden cancelada (reintegra stock)

### Transiciones Válidas:
- PENDING → CONFIRMED
- CONFIRMED → COMPLETED
- Cualquier estado → CANCELLED

## 🔐 Autenticación

Todos los endpoints requieren autenticación básica HTTP:
- **Usuario**: admin
- **Contraseña**: 1234

La colección incluye automáticamente estas credenciales en todas las llamadas.

## 📊 Endpoints Disponibles

### Servicio de Productos (Puerto 8080)

#### Categorías
- `GET /api/categories` - Obtener todas las categorías
- `GET /api/categories/{id}` - Obtener categoría por ID
- `POST /api/categories` - Crear categoría
- `PUT /api/categories/{id}` - Actualizar categoría
- `DELETE /api/categories/{id}` - Eliminar categoría

#### Productos
- `GET /api/products` - Obtener todos los productos
- `GET /api/products/{id}` - Obtener producto por ID
- `GET /api/products/true` - Obtener productos activos
- `GET /api/products/{id}/stock` - Obtener stock de producto
- `GET /api/products/{id}/price` - Obtener precio de producto
- `POST /api/products` - Crear producto
- `PUT /api/products/{id}` - Actualizar producto
- `PUT /api/products/{id}/stock` - Actualizar stock
- `DELETE /api/products/{id}` - Eliminar producto

### Servicio de Órdenes (Puerto 8081)

#### Órdenes
- `GET /api/orders` - Obtener todas las órdenes
- `GET /api/orders/{id}` - Obtener orden por ID
- `POST /api/orders` - Crear orden
- `PUT /api/orders/{id}/status` - Actualizar estado de orden

## 🐛 Casos de Error a Probar

1. **Stock Insuficiente**: Intentar crear una orden con cantidad mayor al stock disponible
2. **Producto Inexistente**: Intentar crear una orden con un producto que no existe
3. **Transición de Estado Inválida**: Intentar cambiar de CONFIRMED a PENDING
4. **Datos Inválidos**: Enviar datos malformados en las peticiones

## 📚 Documentación Adicional

- **Swagger Productos**: http://localhost:8080/swagger-ui.html
- **Swagger Órdenes**: http://localhost:8081/swagger-ui.html

## ⚠️ Notas Importantes

1. **Orden de Ejecución**: Es crucial seguir el orden recomendado para que las pruebas funcionen correctamente
2. **Dependencias**: Las órdenes dependen de que existan productos, y los productos dependen de que existan categorías
3. **Stock**: El sistema verifica automáticamente el stock disponible al crear órdenes
4. **Estados**: Las órdenes tienen un flujo de estados específico que debe respetarse

## 🚀 Ejecución Rápida

Para una prueba rápida del sistema completo:

1. Ejecuta las llamadas en el orden numérico de la colección
2. Verifica las respuestas en cada paso
3. Usa los casos de prueba avanzados para validar el manejo de errores

¡Listo para probar! 🎉 