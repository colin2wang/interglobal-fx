# Interglobal FX Trading Platform - Documentation

Official documentation for the **Interglobal FX (环球汇通)** B2B2C Forex Trading Management Platform.

## Overview

This platform provides a secure, efficient, and compliant forex trading ecosystem supporting MT4/MT5 integration, LP liquidity access, multi-currency wallets, and 200+ currency pairs. It is designed for retail traders, professional investors, and institutional clients worldwide.

## Documentation Index

| # | Document | Description | EN | 中文 |
|---|----------|-------------|-----|------|
| 01 | PRD - Project Requirements | Business requirements, project scope, target users, and functional modules | [EN](./en/01-PRD-Project-Requirements.md) | [中文](./cn/01-PRD-项目需求说明书.md) |
| 02 | User Stories & Use Cases | Detailed user stories and acceptance criteria for all system workflows | [EN](./en/02-User-Stories-and-Use-Cases.md) | [中文](./cn/02-用户故事与用例.md) |
| 03 | Database High-Level Design | Database selection, naming conventions, table structures, and sharding strategy | [EN](./en/03-Database-High-Level-Design.md) | [中文](./cn/03-数据库概要设计.md) |
| 04 | Database Detailed Design | ER relationships, full table schemas, and index design | [EN](./en/04-Database-Detailed-Design.md) | [中文](./cn/04-数据库详细设计.md) |
| 05 | API Specification | Request/response conventions, authentication, error codes, and pagination | [EN](./en/05-API-Specification.md) | [中文](./cn/05-接口规范总文档.md) |
| 06 | API Detailed Documentation | Complete endpoint reference with request/response examples | [EN](./en/06-API-Detailed-Documentation.md) | [中文](./cn/06-接口详细文档.md) |
| 07 | Frontend Tech Stack | Technology choices for React trading terminal, Vue admin panel, and Flutter mobile app | [EN](./en/07-Frontend-Tech-Stack.md) | [中文](./cn/07-前端技术栈规范.md) |
| 08 | Page Prototypes & Description | Route definitions, page layouts, and UI wireframes | [EN](./en/08-Page-Prototypes-and-Description.md) | [中文](./cn/08-页面原型与说明.md) |
| 09 | Component Design | Reusable component specifications for React, Vue, and Flutter | [EN](./en/09-Component-Design.md) | [中文](./cn/09-组件设计文档.md) |
| 10 | System Architecture Design | Microservice architecture, deployment topology, and infrastructure | [EN](./en/10-System-Architecture-Design.md) | [中文](./cn/10-系统架构设计.md) |
| 11 | Project Directory Structure | Backend and frontend directory layout conventions | [EN](./en/11-Project-Directory-Structure.md) | [中文](./cn/11-工程目录规范.md) |
| 12 | Coding Standards | Naming, formatting, code quality, and review guidelines | [EN](./en/12-Coding-Standards.md) | [中文](./cn/12-编码规范.md) |
| 13 | Environment Configuration | Dev/test/staging/prod environment setup and middleware config | [EN](./en/13-Environment-Configuration.md) | [中文](./cn/13-环境配置文档.md) |
| 14 | Configuration File Templates | Spring Boot, Nginx, Docker, and environment variable templates | [EN](./en/14-Configuration-File-Templates.md) | [中文](./cn/14-配置文件模板.md) |
| 15 | Permission Design | RBAC model, role definitions, and data scope rules | [EN](./en/15-Permission-Design.md) | [中文](./cn/15-权限设计文档.md) |
| 16 | Dictionary Documentation | System-wide enumeration values and status codes | [EN](./en/16-Dictionary-Documentation.md) | [中文](./cn/16-字典文档.md) |
| 17 | Global Utility Classes | Backend utility classes for security, date, money, ID generation, etc. | [EN](./en/17-Global-Utility-Classes.md) | [中文](./cn/17-全局工具类说明.md) |

## Languages

| Language | Directory | Description |
|----------|-----------|-------------|
| [English](./en/) | `en/` | English documentation — full project documentation written in English |
| [中文](./cn/) | `cn/` | 中文文档 — 完整的项目中文技术文档 |

## Project Architecture (Summary)

```
Client Layer        →  React (Trading) | Vue3 (Admin) | Flutter (Mobile) | MT4/MT5 Bridge
API Gateway         →  Kong / Nginx (routing, auth, rate limiting)
Application Layer   →  Java/Spring Boot (Trade Core) | Go (Quote & Risk) | Node.js (CRM)
Data Layer          →  PostgreSQL 15 + TimescaleDB | Redis Cluster 7 | Kafka | Elasticsearch 8
Infrastructure      →  Kubernetes | Consul | Jaeger + SkyWalking | ELK Stack
```

## Key Metrics

- **Transaction latency**: < 10ms
- **Market data latency**: < 50ms
- **System availability**: 99.99% (financial-grade SLA)
- **Currency pairs**: 200+
- **Regulatory compliance**: ESMA / FCA / ASIC / NFA / CIMA
