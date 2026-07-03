# CI/CD Pipeline

## Overview

Automated pipeline using GitHub Actions:

```
Push/PR → Lint → Test → Build → Deploy (staging) → Manual Approve → Deploy (prod)
```

## Pipeline Stages

| Stage | Trigger | Duration | Action |
|-------|---------|----------|--------|
| Lint | Every push | ~1 min | ESLint, Prettier, Checkstyle |
| Test | Every push | ~5 min | Unit + Integration tests |
| Build | Test pass | ~3 min | Maven build, Go build, Vite build |
| Deploy Staging | Main branch | ~2 min | Auto-deploy to staging |
| Deploy Production | Manual approve | ~5 min | Blue-green deployment |

## GitHub Actions Workflows

### Main Workflow

```yaml
# .github/workflows/ci.yml
name: CI/CD Pipeline

on:
  push:
    branches: [main, develop]
  pull_request:
    branches: [main]

jobs:
  # ========== Lint ==========
  lint:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4

      - name: Setup Java
        uses: actions/setup-java@v4
        with:
          java-version: '21'
          distribution: 'temurin'

      - name: Java Lint (Checkstyle)
        run: mvn checkstyle:check -pl fx-common

      - name: Setup Go
        uses: actions/setup-go@v5
        with:
          go-version: '1.21'

      - name: Go Lint
        uses: golangci/golangci-lint-action@v3
        with:
          version: latest
          working-directory: go-services/fx-quote

      - name: Setup Node.js
        uses: actions/setup-node@v4
        with:
          node-version: '20'

      - name: Frontend Lint
        run: |
          cd frontend/fx-admin-web && pnpm install && pnpm run lint
          cd ../fx-trader-web && pnpm install && pnpm run lint

  # ========== Test ==========
  test:
    needs: lint
    runs-on: ubuntu-latest
    services:
      postgres:
        image: timescale/timescaledb:latest-pg15
        env:
          POSTGRES_DB: fx_trading_test
          POSTGRES_USER: fxuser
          POSTGRES_PASSWORD: fxpass
        ports:
          - 5432:5432
        options: >-
          --health-cmd pg_isready
          --health-interval 10s
          --health-timeout 5s
          --health-retries 5

      redis:
        image: redis:7-alpine
        ports:
          - 6379:6379

    steps:
      - uses: actions/checkout@v4

      - name: Setup Java
        uses: actions/setup-java@v4
        with:
          java-version: '21'
          distribution: 'temurin'

      - name: Java Tests
        run: mvn test
        env:
          DB_HOST: localhost
          REDIS_HOST: localhost

      - name: Setup Go
        uses: actions/setup-go@v5
        with:
          go-version: '1.21'

      - name: Go Tests
        run: |
          cd go-services/fx-quote && go test ./...
          cd ../fx-risk && go test ./...

      - name: Frontend Tests
        run: |
          cd frontend/fx-trader-web
          pnpm install
          pnpm run test

      - name: Upload Coverage
        uses: codecov/codecov-action@v3
        with:
          files: '**/coverage.out,**/jacoco.xml'

  # ========== Build ==========
  build:
    needs: test
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4

      - name: Setup Java
        uses: actions/setup-java@v4
        with:
          java-version: '21'
          distribution: 'temurin'

      - name: Build Java Services
        run: mvn clean package -DskipTests

      - name: Setup Go
        uses: actions/setup-go@v5
        with:
          go-version: '1.21'

      - name: Build Go Services
        run: |
          cd go-services/fx-quote && go build -o fx-quote ./cmd/server
          cd ../fx-risk && go build -o fx-risk ./cmd/server
          cd ../fx-mt-bridge && go build -o fx-mt-bridge ./cmd/server
          cd ../fx-fix-gateway && go build -o fx-fix-gateway ./cmd/server

      - name: Setup Node.js
        uses: actions/setup-node@v4
        with:
          node-version: '20'

      - name: Build Frontend
        run: |
          cd frontend/fx-admin-web && pnpm install && pnpm run build
          cd ../fx-trader-web && pnpm install && pnpm run build

      - name: Build Docker Images
        run: |
          docker build -t fx-system:latest -f backend/Dockerfile --build-arg SERVICE=fx-system backend/
          docker build -t fx-trade:latest -f backend/Dockerfile --build-arg SERVICE=fx-trade backend/
          docker build -t fx-admin-web:latest frontend/fx-admin-web/
          docker build -t fx-trader-web:latest frontend/fx-trader-web/

      - name: Save Build Artifacts
        uses: actions/upload-artifact@v4
        with:
          name: build-output
          path: |
            backend/*/target/*.jar
            go-services/*/fx-*
            frontend/*/dist/

  # ========== Deploy Staging ==========
  deploy-staging:
    needs: build
    if: github.ref == 'refs/heads/main'
    runs-on: ubuntu-latest
    environment: staging
    steps:
      - uses: actions/checkout@v4

      - name: Download Artifacts
        uses: actions/download-artifact@v4
        with:
          name: build-output

      - name: Deploy to Staging
        run: |
          echo "Deploying to staging environment..."
          # kubectl apply -f k8s/staging/
          # or docker compose -f docker-compose.staging.yml up -d

      - name: Health Check
        run: |
          sleep 30
          curl -f http://staging.fx-platform.com/health || exit 1

  # ========== Deploy Production ==========
  deploy-production:
    needs: deploy-staging
    runs-on: ubuntu-latest
    environment: production
    steps:
      - uses: actions/checkout@v4

      - name: Deploy to Production
        run: |
          echo "Deploying to production environment..."
          # kubectl apply -f k8s/production/

      - name: Verify Deployment
        run: |
          sleep 60
          curl -f https://fx-platform.com/health || exit 1
```

## Branch Strategy

```
main          ← production releases
  └── develop ← integration branch
       ├── feature/xxx  ← new features
       ├── fix/xxx      ← bug fixes
       └── hotfix/xxx   ← production hotfixes
```

| Branch | Deploy Target | Auto-deploy |
|--------|---------------|-------------|
| `main` | Production | Manual approval |
| `develop` | Staging | Auto |
| `feature/*` | None | PR only |
| `hotfix/*` | Production | Manual approval |

## Environment Variables (CI)

Store these in GitHub Secrets:

| Secret | Description |
|--------|-------------|
| `DOCKERHUB_USERNAME` | Docker Hub username |
| `DOCKERHUB_TOKEN` | Docker Hub access token |
| `STAGING_SSH_KEY` | SSH key for staging server |
| `PRODUCTION_SSH_KEY` | SSH key for production server |
| `KUBE_CONFIG` | Kubernetes config for production |

## Quality Gates

| Gate | Threshold | Action on Failure |
|------|-----------|-------------------|
| Test coverage | ≥ 80% | Block merge |
| Lint errors | 0 | Block merge |
| Build success | Must pass | Block merge |
| Security scan | 0 critical | Block merge |
| Performance | < 200ms p99 | Warning |

## Monitoring

After deployment, verify:

```bash
# Check pod status
kubectl get pods -n fx-production

# Check logs
kubectl logs -f deployment/fx-trade -n fx-production

# Check metrics
curl http://prometheus:9090/api/v1/query?query=up
```

## Rollback

```bash
# Kubernetes rollback
kubectl rollout undo deployment/fx-trade -n fx-production

# Docker Compose rollback
git checkout <previous-tag>
docker compose up -d --build
```
