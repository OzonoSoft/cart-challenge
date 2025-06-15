# Gestion de Talleres.
Una API RESTful para la gestión de talleres, desarrollada con Java 17 y Spring Boot.

## Detalles del Proyecto


[RFC] 

## Tecnologías

- Java 17
- Spring Boot
- Spring Data JPA
- Base de datos H2 (en memoria)
- Spring Security (JWT para autenticación)
- Loombok

## Diagramas del proceso 

## Swagger del proyecto

 - http://localhost:8082/swagger-ui/index.html#/

## Modelos

- **AppUser**: Clase que representa usuarios de la aplicacion, son quienes gestionan sus carritos
    - `id`: UUID  
    - `username`: String  
    - `password`: String
    - `status`: UserStatus



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