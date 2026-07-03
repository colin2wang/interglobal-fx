# GlobalFX Forex Trading Platform - Configuration File Templates

## 1. Java Spring Boot Configuration - application.yml

```yaml
server:
  port: 8081
  servlet:
    context-path: /
  tomcat:
    threads:
      max: 200
      min-spare: 20
    max-connections: 10000
    accept-count: 100

spring:
  application:
    name: fx-trade-core
  profiles:
    active: ${SPRING_PROFILES_ACTIVE:dev}
  
  # Datasource configuration
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5432}/${DB_NAME:fx_trading}?useUnicode=true&characterEncoding=utf8&ssl=false&reWriteBatchedInserts=true
    username: ${DB_USERNAME:fxuser}
    password: ${DB_PASSWORD:fxpass}
    hikari:
      minimum-idle: 20
      maximum-pool-size: 100
      idle-timeout: 300000
      max-lifetime: 1800000
      connection-timeout: 30000
      pool-name: fx-trade-hikari-pool
  
  # Redis configuration
  data:
    redis:
      host: ${REDIS_HOST:localhost}
      port: ${REDIS_PORT:6379}
      password: ${REDIS_PASSWORD:}
      database: 0
      timeout: 5000ms
      lettuce:
        pool:
          max-active: 50
          max-idle: 20
          min-idle: 10
          max-wait: 3000ms
  
  # Kafka configuration
  kafka:
    bootstrap-servers: ${KAFKA_BOOTSTRAP_SERVERS:localhost:9092}
    consumer:
      group-id: fx-trade-consumer
      auto-offset-reset: earliest
      enable-auto-commit: false
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
      acks: all
      retries: 3
    listener:
      concurrency: 3
      ack-mode: manual
  
  # Jackson configuration
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: Asia/Shanghai
    serialization:
      write-dates-as-timestamps: false
      write-bigdecimal-as-plain: true
    default-property-inclusion: non_null

# MyBatis Plus configuration
mybatis-plus:
  mapper-locations: classpath*:mapper/**/*.xml
  type-aliases-package: com.globalfx.trade.module.*.entity
  configuration:
    map-underscore-to-camel-case: true
    cache-enabled: false
    log-impl: org.apache.ibatis.logging.slf4j.Slf4jImpl
  global-config:
    db-config:
      id-type: auto
      logic-delete-field: isDeleted
      logic-delete-value: 1
      logic-not-delete-value: 0

# Project custom configuration
fx:
  # JWT configuration
  jwt:
    secret: ${JWT_SECRET:your-256-bit-secret-key-here-change-in-production}
    expiration: 1800000  # 30 minutes
    refresh-expiration: 604800000  # 7 days
  
  # Trading configuration
  trade:
    max-slippage-points: 50
    default-swap-mode: points
    position-merge-enabled: true
  
  # Risk control configuration
  risk:
    margin-call-level: 100  # Margin Call threshold %
    stop-out-level: 50      # Forced liquidation threshold %
    partial-close-level: 80 # Partial forced liquidation threshold %
  
  # LP configuration
  lp:
    timeout-ms: 5000
    retry-count: 3
    quote-validity-ms: 500

# Logging configuration
logging:
  level:
    root: INFO
    com.globalfx: DEBUG
    org.springframework.kafka: WARN
    org.hibernate.SQL: DEBUG
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] [%-5level] [%X{traceId}] [%logger{36}] - %msg%n"
    file: "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] [%-5level] [%X{traceId}] [%logger{36}] - %msg%n"
  file:
    name: /var/log/fx-trade/app.log
    max-size: 100MB
    max-history: 30

# Expose Actuator endpoints
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: when_authorized
  metrics:
    tags:
      application: ${spring.application.name}
```

### 1.1 Development Environment Configuration - application-dev.yml

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/fx_trading
    username: fxuser
    password: fxpass
  data:
    redis:
      host: localhost
      password: ""
  kafka:
    bootstrap-servers: localhost:9092

fx:
  jwt:
    secret: dev-secret-key-change-in-production-minimum-256-bits
  trade:
    max-slippage-points: 100

logging:
  level:
    com.globalfx: DEBUG
```

### 1.2 Production Environment Configuration - application-prod.yml

```yaml
spring:
  datasource:
    url: jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    hikari:
      minimum-idle: 50
      maximum-pool-size: 200
  data:
    redis:
      host: ${REDIS_HOST}
      password: ${REDIS_PASSWORD}
  kafka:
    bootstrap-servers: ${KAFKA_BOOTSTRAP_SERVERS}

fx:
  jwt:
    secret: ${JWT_SECRET}
  trade:
    max-slippage-points: 50

logging:
  level:
    root: INFO
    com.globalfx: INFO
  file:
    name: /var/log/fx-trade/app.log
```

## 2. Go Market Data Service Configuration - config.yaml

```yaml
app:
  name: fx-quote
  host: 0.0.0.0
  port: 8082
  mode: ${APP_MODE:debug}  # debug, release, test

database:
  host: ${DB_HOST:localhost}
  port: 5432
  user: ${DB_USER:fxuser}
  password: ${DB_PASSWORD:fxpass}
  dbname: fx_quote
  max_open_conns: 50
  max_idle_conns: 20
  conn_max_lifetime: 3600

redis:
  host: ${REDIS_HOST:localhost}
  port: 6379
  password: ${REDIS_PASSWORD:}
  db: 0
  pool_size: 50

kafka:
  brokers:
    - ${KAFKA_BOOTSTRAP_SERVERS:localhost:9092}
  topic:
    quote_tick: fx.quote.tick
    kline_1m: fx.kline.1m
  consumer:
    group: fx-quote-consumer
    auto_offset_reset: earliest

quote:
  # Market data cache configuration
  cache:
    tick_ttl_seconds: 5
    book_depth: 10
  
  # K-line configuration
  kline:
    periods:
      - 1m
      - 5m
      - 15m
      - 1h
      - 4h
      - 1d
  
  # LP connection configuration
  lp:
    timeout_ms: 5000
    retry_interval_ms: 1000

ws:
  host: 0.0.0.0
  port: 8082
  read_buffer_size: 10240
  write_buffer_size: 102400
  ping_interval_seconds: 30
  pong_timeout_seconds: 60

logger:
  level: ${LOG_LEVEL:debug}
  format: json
  output: stdout
  file: /var/log/fx-quote/app.log
```

## 3. React Frontend Environment Variables

### 3.1 .env.development

```env
# Application configuration
VITE_APP_TITLE=GlobalFX Trading Platform (Development)
VITE_APP_VERSION=1.0.0

# API configuration
VITE_API_BASE_URL=http://localhost:8080
VITE_WS_URL=ws://localhost:8082

# Environment identifier
VITE_BUILD_ENV=development

# Feature toggles
VITE_ENABLE_MOCK=false
VITE_ENABLE_DEVTOOLS=true

# WebSocket configuration
VITE_WS_RECONNECT_INTERVAL=3000
VITE_WS_MAX_RECONNECT_ATTEMPTS=10

# Debug configuration
VITE_ENABLE_DEBUG_LOG=false
```

### 3.2 .env.production

```env
# Application configuration
VITE_APP_TITLE=GlobalFX Trading Platform
VITE_APP_VERSION=1.0.0

# API configuration
VITE_API_BASE_URL=https://api.fx-platform.com
VITE_WS_URL=wss://ws.fx-platform.com

# Environment identifier
VITE_BUILD_ENV=production

# Feature toggles
VITE_ENABLE_MOCK=false
VITE_ENABLE_DEVTOOLS=false

# WebSocket configuration
VITE_WS_RECONNECT_INTERVAL=5000
VITE_WS_MAX_RECONNECT_ATTEMPTS=5

# Debug configuration
VITE_ENABLE_DEBUG_LOG=false
```

## 4. Vue Frontend Environment Variables

### 4.1 .env.development

```env
VITE_APP_TITLE=GlobalFX Admin Backend (Development)
VITE_API_BASE_URL=http://localhost:8089
VITE_BUILD_ENV=development
VITE_OUTPUT_DIR=dist-dev
```

### 4.2 .env.production

```env
VITE_APP_TITLE=GlobalFX Admin Backend
VITE_API_BASE_URL=https://api.fx-platform.com
VITE_BUILD_ENV=production
VITE_OUTPUT_DIR=dist
```

## 5. Nginx Configuration

### 5.1 Trading Terminal Nginx Configuration

```nginx
upstream fx_trader_backend {
    server 10.2.1.101:8080;
    keepalive 64;
}

server {
    listen 443 ssl http2;
    server_name fx-platform.com;
    
    # SSL configuration
    ssl_certificate /etc/nginx/ssl/fx-platform.com.pem;
    ssl_certificate_key /etc/nginx/ssl/fx-platform.com.key;
    ssl_protocols TLSv1.2 TLSv1.3;
    ssl_ciphers HIGH:!aNULL:!MD5;
    ssl_prefer_server_ciphers on;
    ssl_session_cache shared:SSL:10m;
    ssl_session_timeout 10m;
    
    # Frontend static resources
    root /usr/share/nginx/html/fx-trader;
    index index.html;
    
    # Gzip compression
    gzip on;
    gzip_vary on;
    gzip_proxied any;
    gzip_comp_level 6;
    gzip_types text/plain text/css text/xml application/json application/javascript application/rss+xml application/atom+xml image/svg+xml;
    gzip_min_length 1000;
    
    # Frontend routing
    location / {
        try_files $uri $uri/ /index.html;
        expires 7d;
        add_header Cache-Control "public, no-transform";
    }
    
    # Static resource caching
    location /assets/ {
        expires 1y;
        add_header Cache-Control "public, immutable";
    }
    
    # API proxy
    location /api/ {
        proxy_pass http://fx_trader_backend/api/;
        proxy_http_version 1.1;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_set_header Connection "";
        
        # Timeout configuration
        proxy_connect_timeout 60s;
        proxy_read_timeout 120s;
        proxy_send_timeout 60s;
        
        # Rate limiting
        limit_req zone=api_limit burst=100 nodelay;
    }
    
    # WebSocket proxy
    location /ws/ {
        proxy_pass http://fx_trader_backend/ws/;
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_read_timeout 86400;
    }
    
    # File upload size limit
    client_max_body_size 20m;
    
    # Security headers
    add_header X-Frame-Options "SAMEORIGIN" always;
    add_header X-Content-Type-Options "nosniff" always;
    add_header X-XSS-Protection "1; mode=block" always;
}

# HTTP redirect to HTTPS
server {
    listen 80;
    server_name fx-platform.com;
    return 301 https://$server_name$request_uri;
}
```

### 5.2 Admin Backend Nginx Configuration

```nginx
upstream fx_admin_backend {
    server 10.2.1.102:8089;
    keepalive 64;
}

server {
    listen 443 ssl http2;
    server_name admin.fx-platform.com;
    
    ssl_certificate /etc/nginx/ssl/fx-platform.com.pem;
    ssl_certificate_key /etc/nginx/ssl/fx-platform.com.key;
    
    root /usr/share/nginx/html/fx-admin;
    index index.html;
    
    gzip on;
    gzip_types text/plain text/css application/json application/javascript;
    
    location / {
        try_files $uri $uri/ /index.html;
        expires 1d;
    }
    
    location /api/ {
        proxy_pass http://fx_admin_backend/api/;
        proxy_http_version 1.1;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_set_header Connection "";
        
        proxy_connect_timeout 60s;
        proxy_read_timeout 120s;
    }
}

server {
    listen 80;
    server_name admin.fx-platform.com;
    return 301 https://$server_name$request_uri;
}
```

## 6. Docker Configuration

### 6.1 Java Backend Dockerfile

```dockerfile
# Build stage
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /build

# Copy Maven dependencies
COPY pom.xml .
RUN apt-get update && apt-get install -y maven
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests -Pprod

# Runtime stage
FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="GlobalFX Tech Team <tech@globalfx.com>"
LABEL version="1.0.0"

WORKDIR /app

# Copy jar from build stage
COPY --from=builder /build/target/*.jar app.jar

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8081/actuator/health || exit 1

# Environment variables
ENV JAVA_OPTS="-Xms512m -Xmx2048m -XX:+UseG1GC -XX:MaxGCPauseMillis=200"
ENV SPRING_PROFILES_ACTIVE=prod
ENV TZ=Asia/Shanghai

EXPOSE 8081

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]
```

### 6.2 Go Service Dockerfile

```dockerfile
# Build stage
FROM golang:1.21-alpine AS builder

WORKDIR /build

# Install build dependencies
RUN apk add --no-cache git ca-certificates tzdata

# Copy source code
COPY go.mod go.sum ./
RUN go mod download

COPY . .

# Compile
ARG TARGETOS=linux
ARG TARGETARCH=amd64
RUN CGO_ENABLED=0 GOOS=${TARGETOS} GOARCH=${TARGETARCH} go build -ldflags="-w -s" -o fx-quote ./cmd/server

# Runtime stage
FROM alpine:3.19

WORKDIR /app

# Install runtime dependencies
RUN apk add --no-cache ca-certificates tzdata

# Copy binary from build stage
COPY --from=builder /build/fx-quote /app/fx-quote
COPY --from=builder /build/config.yaml /app/config.yaml

# Health check
HEALTHCHECK --interval=30s --timeout=5s --start-period=10s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8082/health || exit 1

ENV TZ=Asia/Shanghai

EXPOSE 8082

CMD ["/app/fx-quote"]
```

### 6.3 React Frontend Dockerfile

```dockerfile
# Build stage
FROM node:18-alpine AS builder

WORKDIR /app

# Install dependencies
COPY package*.json ./
RUN npm ci --only=production

# Copy source code
COPY . .

# Build production version
ARG VITE_BUILD_ENV=production
RUN npm run build

# Runtime stage
FROM nginx:alpine

# Copy build artifacts
COPY --from=builder /app/dist /usr/share/nginx/html/fx-trader

# Copy Nginx configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Health check
HEALTHCHECK --interval=30s --timeout=5s --start-period=10s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:80/index.html || exit 1

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
```

### 6.4 Docker Compose Development Environment

```yaml
version: '3.8'

services:
  # PostgreSQL database
  postgres:
    image: postgres:15-alpine
    container_name: fx-postgres
    ports:
      - "5432:5432"
    environment:
      POSTGRES_DB: fx_trading
      POSTGRES_USER: fxuser
      POSTGRES_PASSWORD: fxpass
      TZ: Asia/Shanghai
    volumes:
      - postgres_data:/var/lib/postgresql/data
      - ./sql/init.sql:/docker-entrypoint-initdb.d/init.sql
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U fxuser -d fx_trading"]
      interval: 10s
      timeout: 5s
      retries: 5
    networks:
      - fx-network

  # Redis cache
  redis:
    image: redis:7-alpine
    container_name: fx-redis
    ports:
      - "6379:6379"
    command: redis-server --appendonly yes
    volumes:
      - redis_data:/data
    healthcheck:
      test: ["CMD", "redis-cli", "ping"]
      interval: 10s
      timeout: 5s
      retries: 5
    networks:
      - fx-network

  # Kafka + Zookeeper
  zookeeper:
    image: confluentinc/cp-zookeeper:7.5.0
    container_name: fx-zookeeper
    ports:
      - "2181:2181"
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000
    networks:
      - fx-network

  kafka:
    image: confluentinc/cp-kafka:7.5.0
    container_name: fx-kafka
    ports:
      - "9092:9092"
    depends_on:
      - zookeeper
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_AUTO_CREATE_TOPICS_ENABLE: "true"
    networks:
      - fx-network

  # Backend service
  fx-trade-core:
    build:
      context: ./fx-trade-core
      dockerfile: Dockerfile
    container_name: fx-trade-core
    ports:
      - "8081:8081"
    environment:
      SPRING_PROFILES_ACTIVE: dev
      DB_HOST: postgres
      DB_PORT: 5432
      DB_NAME: fx_trading
      DB_USERNAME: fxuser
      DB_PASSWORD: fxpass
      REDIS_HOST: redis
      REDIS_PORT: 6379
      KAFKA_BOOTSTRAP_SERVERS: kafka:9092
    depends_on:
      postgres:
        condition: service_healthy
      redis:
        condition: service_healthy
      kafka:
        condition: service_started
    networks:
      - fx-network

  # Frontend development service
  fx-trader-web:
    image: node:18-alpine
    container_name: fx-trader-web-dev
    working_dir: /app
    ports:
      - "3000:3000"
    volumes:
      - ./fx-trader-web:/app
    command: sh -c "npm install && npm run dev -- --host"
    environment:
      VITE_API_BASE_URL: http://localhost:8081
    networks:
      - fx-network

networks:
  fx-network:
    driver: bridge

volumes:
  postgres_data:
  redis_data:
```

## 7. Kubernetes Deployment Configuration

### 7.1 Trading Core Service Deployment

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: fx-trade-core
  namespace: fx-production
  labels:
    app: fx-trade-core
    version: v1
spec:
  replicas: 5
  selector:
    matchLabels:
      app: fx-trade-core
  template:
    metadata:
      labels:
        app: fx-trade-core
        version: v1
    spec:
      serviceAccountName: fx-app
      securityContext:
        runAsNonRoot: true
        runAsUser: 1000
      containers:
        - name: fx-trade-core
          image: registry.fx-platform.com/fx-trade-core:v1.0.0
          ports:
            - containerPort: 8081
              name: http
          env:
            - name: SPRING_PROFILES_ACTIVE
              value: "prod"
            - name: JAVA_OPTS
              value: "-Xms1g -Xmx4g -XX:+UseG1GC"
          resources:
            requests:
              cpu: 1000m
              memory: 1Gi
            limits:
              cpu: 4000m
              memory: 4Gi
          livenessProbe:
            httpGet:
              path: /actuator/health
              port: 8081
            initialDelaySeconds: 60
            periodSeconds: 10
            timeoutSeconds: 5
            failureThreshold: 3
          readinessProbe:
            httpGet:
              path: /actuator/health
              port: 8081
            initialDelaySeconds: 30
            periodSeconds: 5
            timeoutSeconds: 3
            failureThreshold: 3
          volumeMounts:
            - name: app-log
              mountPath: /var/log/fx-trade
      volumes:
        - name: app-log
          emptyDir: {}
      affinity:
        podAntiAffinity:
          preferredDuringSchedulingIgnoredDuringExecution:
            - weight: 100
              podAffinityTerm:
                labelSelector:
                  matchExpressions:
                    - key: app
                      operator: In
                      values:
                        - fx-trade-core
                topologyKey: kubernetes.io/hostname
```

### 7.2 HPA Autoscaling Configuration

```yaml
apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
  name: fx-trade-core-hpa
  namespace: fx-production
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: fx-trade-core
  minReplicas: 3
  maxReplicas: 20
  metrics:
    - type: Resource
      resource:
        name: cpu
        target:
          type: Utilization
          averageUtilization: 70
    - type: Resource
      resource:
        name: memory
        target:
          type: Utilization
          averageUtilization: 80
    - type: Pods
      pods:
        metric:
          name: http_requests_per_second
        target:
          type: AverageValue
          averageValue: "1000"
```