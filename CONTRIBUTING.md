# Contributing to GlobalFX Trading Platform

Thank you for your interest in contributing! This document provides guidelines and instructions for contributing to the project.

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Setup](#development-setup)
- [Branching Strategy](#branching-strategy)
- [Commit Convention](#commit-convention)
- [Code Style](#code-style)
- [Pull Request Process](#pull-request-process)
- [Testing](#testing)
- [Documentation](#documentation)

## Code of Conduct

- Be respectful and professional
- Focus on constructive feedback
- Prioritize security and correctness in all changes

## Getting Started

1. Fork the repository
2. Clone your fork
3. Create a feature branch from `develop`
4. Make your changes
5. Submit a pull request

## Development Setup

### Prerequisites

| Tool | Version | Purpose |
|------|---------|---------|
| Java | 21+ | Backend services |
| Go | 1.21+ | Quote/Risk/MT-Bridge/FIX services |
| Node.js | 18+ | Frontend builds |
| Flutter | 3.x | Mobile app |
| Docker | Latest | Local infrastructure |
| Maven | 3.9+ | Java builds |

### Backend (Java)

```bash
cd backend
mvn clean install -DskipTests
```

### Backend (Go)

```bash
cd go-services/fx-quote   # or any Go service
go mod tidy
go build ./...
```

### Frontend (React)

```bash
cd frontend/fx-trader-web
npm install
npm run lint
npm run build
```

### Frontend (Vue3)

```bash
cd frontend/fx-admin-web
npm install
npm run lint
npm run build
```

## Branching Strategy

```
main          ← production releases
  └── develop ← integration branch
       ├── feature/TICKET-123-add-order-api
       ├── feature/TICKET-456-fix-margin-calc
       └── bugfix/TICKET-789-fix-login-error
```

| Branch | Purpose | Protection |
|--------|---------|-----------|
| `main` | Production releases | PR required, 2 approvals |
| `develop` | Integration | PR required, 1 approval |
| `feature/*` | New features | Branch from develop |
| `bugfix/*` | Bug fixes | Branch from develop |
| `hotfix/*` | Production fixes | Branch from main |

## Commit Convention

Follow [Conventional Commits](https://www.conventionalcommits.org/):

```
<type>(<scope>): <description>

[optional body]

[optional footer]
```

### Types

| Type | Description |
|------|-------------|
| `feat` | New feature |
| `fix` | Bug fix |
| `docs` | Documentation only |
| `style` | Code style (no logic change) |
| `refactor` | Code refactoring |
| `perf` | Performance improvement |
| `test` | Adding tests |
| `chore` | Build/tooling changes |
| `ci` | CI/CD changes |

### Scopes

| Scope | Module |
|-------|--------|
| `trade` | fx-trade (orders, positions, accounts) |
| `quote` | fx-quote (market data) |
| `risk` | fx-risk / fx-risk (risk engine) |
| `clearing` | fx-clearing (settlement) |
| `crm` | fx-crm (customers, IB, tickets) |
| `system` | fx-system (auth, RBAC, config) |
| `report` | fx-report (reports, dashboard) |
| `mt-bridge` | fx-mt-bridge (MT4/MT5) |
| `fix-gw` | fx-fix-gateway (FIX protocol) |
| `trader-web` | React trading terminal |
| `admin-web` | Vue3 admin panel |
| `mobile` | Flutter mobile app |

### Examples

```
feat(trade): add trailing stop loss support
fix(quote): correct spread calculation for exotic pairs
docs(api): update order creation endpoint docs
refactor(risk): simplify rule engine evaluation logic
test(crm): add unit tests for KYC review service
```

## Code Style

### Java (21)

- 4-space indentation, 120 char line width
- Use Lombok annotations (`@Data`, `@Builder`, `@Slf4j`, `@RequiredArgsConstructor`)
- Use MyBatis Plus annotations (`@TableName`, `@TableId`, `@TableField`)
- Return `Result<T>` from all controllers
- Use `@Valid` on request DTOs
- Use `BigDecimal` for all financial amounts — never `Double`
- Methods ≤80 lines, classes ≤500 lines
- Boolean fields: `is`/`has`/`can` prefix

```java
// Good
@Data
@Builder
public class OrderVO {
    private String orderNo;
    private BigDecimal lotSize;
    private boolean isActive;
}

// Bad
public class orderVO {
    public double lotSize;
    public boolean active;
}
```

### Go (1.21+)

- Tab indentation, 120 char line width
- Use `context.Context` for request propagation
- Errors: `errors.New()` or custom error types, never `fmt.Errorf` for logic errors
- Constants: `PascalCase` (exported), `camelCase` (unexported)
- Prefix error variables with `Err`

### TypeScript (React / Vue3)

- 2-space indentation, 100 char line width
- Enable strict mode in `tsconfig.json`
- Use `interface` for object types, `type` for unions
- Component files: `PascalCase.tsx` / `PascalCase.vue`
- Utility files: `kebab-case.ts`
- Props interface: `XxxProps`
- Event handlers: `handleXxx`

### Flutter (Dart)

- 2-space indentation, 80 char line width
- File names: `snake_case.dart`
- Class names: `PascalCase`
- Use `const` constructors where possible
- Follow Material Design 3 guidelines

### SQL

- Keywords: UPPERCASE (`SELECT`, `FROM`, `WHERE`)
- Table/column names: `snake_case`
- Always use parameterized queries
- Add indexes for frequently queried columns

## Pull Request Process

### Before Submitting

1. **Sync with develop**: `git rebase develop`
2. **Run linters**: Ensure all lint checks pass
3. **Run tests**: All existing tests must pass
4. **Update docs**: If adding/changing API endpoints, update API docs
5. **Self-review**: Review your own diff before submitting

### PR Template

```markdown
## Summary
Brief description of changes.

## Changes
- Change 1
- Change 2

## Testing
How were these changes tested?

## Screenshots (if UI changes)

## Checklist
- [ ] Code follows project style guidelines
- [ ] Self-review completed
- [ ] Tests added/updated
- [ ] Documentation updated
- [ ] No secrets or credentials committed
- [ ] Database migrations included (if needed)
```

### Review Criteria

| Criterion | Requirement |
|-----------|-------------|
| Functionality | Feature works as described |
| Code Quality | Follows style guide, no code smells |
| Security | No SQL injection, XSS, or secret leaks |
| Performance | No N+1 queries, proper indexing |
| Testing | Adequate coverage for new code |
| Documentation | API docs updated if applicable |

## Testing

### Java

```bash
cd backend
mvn test
```

### Go

```bash
cd go-services/fx-quote
go test ./...
```

### React / Vue

```bash
cd frontend/fx-trader-web   # or fx-admin-web
npm run test
```

### Test Organization

| Type | Location | Purpose |
|------|----------|---------|
| Unit | `*Test.java` / `*_test.go` | Individual function tests |
| Integration | `*IT.java` | Service + database tests |
| E2E | `e2e/` | End-to-end workflow tests |

## Security Guidelines

- Never commit secrets, API keys, or credentials
- Use environment variables for configuration
- All financial calculations must use `BigDecimal` (Java) or exact types (Go)
- Validate all user input at API boundaries
- Use parameterized queries — never string concatenation for SQL
- Log security-relevant events (login, order creation, risk triggers)

## Documentation

- API endpoints: Update `docs/06-接口详细文档.md` for new/changed endpoints
- Database changes: Update `docs/04-数据库详细设计.md` for schema changes
- Architecture changes: Update `docs/10-系统架构设计.md`
- New features: Update relevant docs in `docs/`

## Questions?

Open an issue or reach out to the team for any questions about contributing.
