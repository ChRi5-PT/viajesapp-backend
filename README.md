# Microservicio de Usuarios

Microservicio REST desarrollado con Java y Spring Boot encargado de la gestión de usuarios, autenticación y seguridad dentro de la plataforma de viajes.

Este servicio constituye el núcleo de autenticación del sistema, permitiendo registrar usuarios, validar credenciales y generar tokens de acceso para los demás microservicios.

## Tecnologías

* Java 17
* Spring Boot
* Spring Web
* Spring Security
* JSON Web Token (JWT)
* MySQL
* Maven

## Descripción

El microservicio de usuarios permite:

* Registro de nuevos usuarios
* Autenticación mediante login
* Generación de tokens JWT
* Administración de perfiles
* Validación de datos

Se implementa una arquitectura en capas (Controller, Service, Repository) para mantener una adecuada separación de responsabilidades.

## Seguridad

El servicio implementa mecanismos de seguridad como:

* Encriptación de contraseñas mediante PasswordEncoder
* Generación de tokens JWT firmados
* Tokens con expiración
* Exclusión de contraseñas en las respuestas del sistema

### Flujo de autenticación

1. El usuario envía sus credenciales (email y contraseña).
2. Se valida la existencia del usuario en la base de datos.
3. Se verifica la contraseña encriptada.
4. Se genera un token JWT.
5. Se retorna el token junto con los datos del usuario (sin incluir la contraseña).

## Modelo de datos

Entidad Usuario:

* id
* nombres
* apellidos
* email (único)
* password (encriptado)
* pais
* tipoDocumento
* numeroDocumento

## DTOs

### LoginRequest

```json id="loginjson_clean"
{
  "email": "usuario@mail.com",
  "password": "123456"
}
```

### RegisterRequest

```json id="registerjson_clean"
{
  "nombres": "Juan",
  "apellidos": "Perez",
  "email": "juan@mail.com",
  "password": "123456",
  "pais": "Peru",
  "tipoDocumento": "DNI",
  "numeroDocumento": "12345678"
}
```

### AuthResponse

```json id="authjson_clean"
{
  "token": "jwt_token",
  "usuario": {
    "id": 1,
    "nombres": "Juan",
    "apellidos": "Perez",
    "email": "juan@mail.com",
    "pais": "Peru"
  }
}
```

## Endpoints

### Autenticación

* POST /auth/register → Registrar usuario
* POST /auth/login → Login y generación de token

### Usuarios

* GET /usuarios → Listar usuarios
* GET /usuarios/{id} → Obtener usuario por ID
* POST /usuarios → Crear usuario
* PUT /usuarios/{id} → Actualizar usuario
* DELETE /usuarios/{id} → Eliminar usuario

### Health Check

* GET /health → Estado del servicio

## Variables de entorno

```env id="env_clean"
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/usuarios_db
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=*****
JWT_SECRET=CLAVE_SECRETA_BASE64
```

## Ejecución del proyecto

### 1. Clonar repositorio

```bash id="clone_clean"
git clone <repo-url>
cd usuarios-service
```

### 2. Ejecutar el servicio

```bash id="run_clean"
mvn spring-boot:run
```

## Pruebas

Se puede probar el servicio utilizando herramientas como Postman o curl.

Ejemplo:

```bash id="test_clean"
curl -X POST http://localhost:8080/auth/login \
-H "Content-Type: application/json" \
-d '{"email":"test@mail.com","password":"123456"}'
```

## Características

* Arquitectura en capas
* Uso de DTOs para desacoplar la lógica
* Manejo de errores mediante excepciones HTTP
* Seguridad basada en JWT
* Integración con otros microservicios

## Rol dentro del sistema

Este microservicio es utilizado por otros componentes del sistema para la identificación y gestión de usuarios, incluyendo:

* Microservicio de reservas
* Microservicio de wishlist

## Notas

* Este servicio es importante para la seguridad del sistema
* En un entorno de producción se recomienda proteger los endpoints mediante validación de JWT
* Diseñado para integrarse en arquitecturas distribuidas basadas en microservicios
