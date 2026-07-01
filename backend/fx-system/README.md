# fx-system

System management service handling authentication, authorization, user/role management, and dictionary configuration.

**Port**: 8089

## Modules

| Component | Description |
|-----------|-------------|
| Auth | JWT login/logout, token refresh |
| User | CRUD, password management, status control |
| Role | CRUD, permission assignment, data scope |
| Permission | Menu tree, button permissions, API permissions |
| Tenant | Multi-tenant configuration |
| Dict | Dictionary type and data management |

## API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/v1/auth/login` | User login |
| POST | `/api/v1/auth/refresh` | Refresh access token |
| POST | `/api/v1/auth/logout` | User logout |
| GET | `/api/v1/system/users` | List users |
| POST | `/api/v1/system/users` | Create user |
| PUT | `/api/v1/system/users/{id}` | Update user |
| DELETE | `/api/v1/system/users/{id}` | Delete user |
| GET | `/api/v1/system/roles` | List roles |
| POST | `/api/v1/system/roles` | Create role |
| GET | `/api/v1/system/permissions/tree` | Get permission tree |
| GET | `/api/v1/system/dict-types` | List dictionary types |
| GET | `/api/v1/system/dict-data` | List dictionary data |

## Security

- JWT authentication with access + refresh tokens
- BCrypt password hashing (strength 12)
- Spring Security filter chain
- Role-based access control (RBAC)
- Data scope: All / Tenant / Department / Self

## Configuration

```yaml
server:
  port: 8089
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/fx_trading
  data:
    redis:
      host: localhost
```

## Run

```bash
mvn spring-boot:run -pl fx-system
```
