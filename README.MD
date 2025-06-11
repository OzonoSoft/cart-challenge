# Cart API Challenge
Una API RESTful para la gestión de carritos de compras, desarrollada con Java 17 y Spring Boot.

## Detalles del Proyecto

Este proyecto ofrece endpoints para la gestión de carritos, incluyendo agregar/eliminar productos, ver el contenido del carrito y funcionalidad de checkout. Sigue buenas prácticas de seguridad (Auth by JWT) y organización del código.

## Tecnologías

- Java 17
- Spring Boot
- Spring Data JPA
- Base de datos H2 (en memoria)
- Spring Security (JWT para autenticación)
- Loombok

## Diagramas del proceso de crear un Carrito

- Secuencias
```mermaid
sequenceDiagram
    autonumber
    participant C as Client
    participant S as CartServiceImpl
    participant J as JwtUtil
    participant U as AppUserRepository
    participant R as CartRepository
    participant M as CartMapper
    participant L as Logger

    C->>S: createCart(req)
    S->>J: getUserName()
    J-->>S: username

    S->>U: findByUsername(username)
    U-->>S: AppUser

    S->>R: findByUser_IdAndStatus(user.id, PENDING)
    alt Carrito pendiente existe
        R-->>S: List<Cart>
        S->>M: toCartDTO(cart)
        M-->>S: CartDTO
        S-->>C: CartDTO
    else No existe carrito pendiente
        R-->>S: []
        S->>S: new Cart()
        S->>S: setUser(user), setStatus(PENDING)
        S->>R: save(cart)
        R-->>S: cart
        S->>M: toCartDTO(cart)
        M-->>S: CartDTO
        S-->>C: CartDTO
    end

    Note over S: catch DataIntegrityViolationException
    S->>L: log.error("...")
    S->>R: findByUser_IdAndStatus(user.id, PENDING)
    alt fallback no vacío
        R-->>S: List<Cart>
        S->>L: log.info("...")
        S->>M: toCartDTO(fallbackCart)
        M-->>S: CartDTO
        S-->>C: CartDTO
    else fallback vacío
        R-->>S: []
        S-->>C: throw IllegalStateException
    end

```
## Diagramas del proceso de añadir items al carrito.

- Secuencias
```mermaid
sequenceDiagram
    autonumber
    actor U as Usuario
    participant S as CartService
    participant J as jwtUtil
    participant AU as AppUserRepository
    participant CR as CartRepository
    participant CPS as CartPendingSpec
    participant PR as ProductRepository
    participant CM as CartMapper

    U ->> S: addItemToCart(req)
    S ->> J: getUserName()
    J -->> S: currentUser
    S ->> AU: findByUsername(currentUser)
    AU -->> S: user
    S ->> CR: findByIdAndUser_Id(cartId,\nuser.id)
    CR -->> S: cart
    S ->> CPS: check(cart)
    CPS -->> S: ✓
    S ->> PR: findById(productId)
    PR -->> S: product
    Note over S: Agrega o actualiza<br/>CartItem internamente
    S ->> CR: save(cart)
    CR -->> S: cart (actualizado)
    S ->> CM: toCartDTO(cart)
    CM -->> S: cartDTO
    S -->> U: cartDTO

```
## Diagramas del proceso para decrementar la cantidad de un producto.

- Secuencias
```mermaid
sequenceDiagram
    autonumber
    actor U as Usuario
    participant S as CartService
    participant J as jwtUtil
    participant AU as AppUserRepository
    participant CR as CartRepository
    participant CPS as CartPendingSpec
    participant CM as CartMapper

    U ->> S: decrementItemFromCart(req)
    S ->> J: getUserName()
    J -->> S: currentUser
    S ->> AU: findByUsername(currentUser)
    AU -->> S: user
    S ->> CR: findByIdAndUser_Id(cartId, user.id)
    CR -->> S: cart
    S ->> CPS: check(cart)
    CPS -->> S: ✓

    Note over S: Busca el CartItem con productId\n en cart.items

    alt Item no encontrado
        S -->> U: EntityNotFoundException
    else Cantidad ≤ 1
        S ->> S: cart.items.remove(item)
    else Cantidad > 1
        S ->> S: item.qty--
    end

    S ->> CR: save(cart)
    CR -->> S: cart actualizado
    S ->> CM: toCartDTO(cart)
    CM -->> S: cartDTO
    S -->> U: cartDTO
```

## Diagramas del proceso de Checkout

- Secuencias
```mermaid
sequenceDiagram
    autonumber
    actor U as Usuario
    participant S as CartService
    participant J as jwtUtil
    participant AU as AppUserRepository
    participant CR as CartRepository
    participant CPS as CartPendingSpec
    participant EP as EventPublisher

    U ->> S: checkoutCart(cartRequest)
    S ->> J: getUserName()
    J -->> S: currentUser
    S ->> AU: findByUsername(currentUser)
    AU -->> S: user
    S ->> CR: findByIdAndUser_Id(cartId,\nuser.id)
    CR -->> S: cart

    alt Carrito sin ítems
        S -->> U: IllegalStateException("El carrito está vacío")
    else Carrito con ítems
        S ->> CPS: check(cart)
        CPS -->> S: ✓
        S ->> S: cart.status = PROCESSING
        S ->> CR: save(cart)
        CR -->> S: cart actualizado
        S ->> EP: publishEvent(CheckoutRequestedEvent)
        EP -->> S: ✓
        S -->> U: CheckoutResponseDTO
    end

```

## Diagrama de actividades – addItemToCart

```mermaid
flowchart TD
    %% nodos principales
    A[Inicio] --> B[Obtener usuario actual]
    B --> C[Buscar usuario en BD]
    C -->|No encontrado| X[EntityNotFoundException] --> Z[Fin]

    C --> D[Buscar carrito por id y usuario]
    D -->|No encontrado| Y[EntityNotFoundException] --> Z

    D --> E[Verificar carrito PENDING]
    E -->|Finalizado| W[IllegalStateException] --> Z

    E --> F[Buscar producto]
    F -->|No encontrado| V[EntityNotFoundException] --> Z

    F --> G[Determinar cantidad default 1]
    G --> H{Item ya existe?}

    H -- Si --> I[Aumentar cantidad]
    H -- No --> J[Crear CartItem]

    I --> K[Guardar carrito]
    J --> K

    K --> L[Convertir a CartDTO]
    L --> Z[Fin]
```

## Diagrama de actividades – decrementItemFromCart
```mermaid
flowchart TD
    A([Inicio]) --> B[Obtener usuario actual]
    B --> C[Buscar usuario en BD]
    C -->|No encontrado| X[EntityNotFoundException] --> Z([Fin])
    C --> D[Buscar carrito por id y usuario]
    D -->|No encontrado| Y[EntityNotFoundException] --> Z
    D --> E[Verificar que el carrito esté PENDING]
    E -->|Finalizado| W[IllegalStateException] --> Z
    E --> F[Buscar CartItem por productId]
    F -->|No encontrado| V[EntityNotFoundException] --> Z
    F --> G{¿qty ≤ 1?}
    G -- Sí --> H[Eliminar CartItem]
    G -- No --> I[qty = qty - 1]
    H --> J[Guardar carrito]
    I --> J
    J --> K[Convertir a CartDTO]
    K --> Z

```

## Diagrama de actividades – checkoutCart
```mermaid
flowchart TD
    A([Inicio]) --> B[Obtener usuario actual]
    B --> C[Buscar usuario en BD]
    C -->|No encontrado| X[EntityNotFoundException] --> Z([Fin])
    C --> D[Buscar carrito por id y usuario]
    D -->|No encontrado| Y[EntityNotFoundException] --> Z
    D --> E{¿Carrito vacío?}
    E -- Sí --> V[IllegalStateException] --> Z
    E -- No --> F[Verificar que el carrito esté PENDING]
    F -->|Finalizado| W[IllegalStateException] --> Z
    F --> G[cart.status = PROCESSING]
    G --> H[Guardar carrito]
    H --> I[Publicar CheckoutRequestedEvent]
    I --> J[Crear CheckoutResponseDTO]
    J --> Z


```
## Swagger del proyecto

 - http://localhost:8082/swagger-ui/index.html#/

## Modelos

- **AppUser**: Clase que representa usuarios de la aplicacion, son quienes gestionan sus carritos
    - `id`: UUID  
    - `username`: String  
    - `password`: String
    - `status`: UserStatus

- **Product**:  Clase de productos
    - `id`: Long  
    - `name`: String  
    - `price`: Double  
    - `category`: Category

- **Cart**:  Clase principal de la aplicaion
    - `id`: UUID  
    - `user`: AppUser  
    - `items`: List of CartItem
    - `status`: CartStatus  
    - `subtotal`: Double  
    - `discount`: Double  
    - `total`: Double  
    - `version`: long  

- **CartItem**:  Clase que la lista de items de un carrito
    - `id`: Long  
    - `cart`: Cart  
    - `product`: Product  
    - `qty`: Integer

- **Discount**:  Clase que parametriza los descuentos
    - `id`: Long  
    - `category`: Category  
    - `percentage`: Double


- **Carpetas**: 
- `configuration/`: Configuraciones generales del proyecto y datos iniciales.
- `controllers/`: Controladores REST.(Interfaces + Implementaciones)
- `domain/`: Entidades y modelos de dominio, Enums y Specs.
- `repositories/`: Interfaces de acceso a datos (JPA).
- `services/`: Lógica de negocio.
- `security/`: Configuración y utilidades de seguridad.
- `resources/`: Propiedades y Comportamiento de la app.

## Cómo ejecutar la aplicación

Desde la raíz del proyecto, ejecuta:

```bash
./mvnw spring-boot:run
```

O si tienes Maven instalado globalmente:

```bash
mvn spring-boot:run
```

## Ejemplo de registro de usuario

Puedes registrar un usuario usando el siguiente comando `curl`:

```bash
curl --location 'http://localhost:8082/api/v1/users/create' \
--header 'Content-Type: application/json' \
--data '{
    "userName": "gabriel",
    "password": "1234"
}'
```
Response:
```
{
    "id": "e6e7da95-ff55-480d-98ae-fa183dcaad05",
    "username": "gabriel",
    "status": "USER_STATUS_ACTIVE"
}
```
## Ejemplo de login de usuario

Para iniciar sesión, utiliza el siguiente comando `curl`:

```bash
curl --location 'http://localhost:8082/api/v1/users/login' \
--header 'Content-Type: application/json' \
--data '{
    "codigo": "gabriel",
    "password": "1234"
}'
```
Response:
```
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnYWJyaWVsIiwiaWF0IjoxNzQ5NjI2MjU5LCJleHAiOjE3NDk2Mjk4NTl9.-SFR1qhm8oV16_h0BEe7N3ppKoCHPm89RAwkw7tsfSo",
    "user": {
        "id": "e6e7da95-ff55-480d-98ae-fa183dcaad05",
        "username": "gabriel",
        "status": "USER_STATUS_ACTIVE"
    }
}
```
## Ejemplo de obtener usuario por userName - Requiere token

Para obtener el user por username, utiliza el siguiente comando `curl`:

```bash
curl --location 'http://localhost:8082/api/v1/users/gabriel' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTc0OTUyODg1NiwiZXhwIjoxNzQ5NTMyNDU2fQ.8lOLY1eWdbQlpB83rEcQA6QI_f82aWGV21XP-XxdePQ'
```
Response:
```
{
    "id": "e6e7da95-ff55-480d-98ae-fa183dcaad05",
    "username": "gabriel",
    "status": "USER_STATUS_ACTIVE"
}
```
## Ejemplo de crear un carrito - Requiere token

Para crear un carrito, utiliza el siguiente comando `curl`:

```bash
curl --location 'http://localhost:8082/api/v1/carts/create' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTc0OTUyODg1NiwiZXhwIjoxNzQ5NTMyNDU2fQ.8lOLY1eWdbQlpB83rEcQA6QI_f82aWGV21XP-XxdePQ' \
--data '{
  "userId": "7ecf1917-780b-4582-8abc-a0249a214d3a"
}'
```
Response:
```
{
    "cartId": "7ecf1917-780b-4582-8abc-a0249a214d3a",
    "items": [],
    "total": 0.0,
    "status": "CART_STATUS_PENDING"
}
```
## Ejemplo de agregar item a un carrito - Requiere token

Para hacer el checkout, utiliza el siguiente comando `curl`:
```bash
curl --location 'http://localhost:8082/api/v1/carts/add' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnYWJyaWVsIiwiaWF0IjoxNzQ5NjI2MjU5LCJleHAiOjE3NDk2Mjk4NTl9.-SFR1qhm8oV16_h0BEe7N3ppKoCHPm89RAwkw7tsfSo' \
--data '{
  "cartId": "7ecf1917-780b-4582-8abc-a0249a214d3a",
  "productId": 3,
  "quantity": 3
}'
```
Response:
```
{
    "cartId": "7ecf1917-780b-4582-8abc-a0249a214d3a",
    "items": [
        {
            "id": 1,
            "cartId": "7ecf1917-780b-4582-8abc-a0249a214d3a",
            "product": {
                "id": 3,
                "name": "Product 3",
                "price": 130.0,
                "category": "ELECTRONICS"
            },
            "qty": 3
        }
    ],
    "total": 390.0,
    "status": "CART_STATUS_PENDING"
}
```
## Ejemplo de hacer checkout de un carrito - Requiere token

Para hacer el checkout, utiliza el siguiente comando `curl`:

```bash
curl --location 'http://localhost:8082/api/v1/carts/checkout' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnYWJyaWVsIiwiaWF0IjoxNzQ5NjI2MjU5LCJleHAiOjE3NDk2Mjk4NTl9.-SFR1qhm8oV16_h0BEe7N3ppKoCHPm89RAwkw7tsfSo' \
--data '{
  "cartId": "7ecf1917-780b-4582-8abc-a0249a214d3a",
  "paymentMethod": "TC",
  "shippingAddress": "SanLorenzo"
}'
```
Response:
```
{
    "message": "Estamos procesando su orden",
    "cartId": "7ecf1917-780b-4582-8abc-a0249a214d3a"
}
```

