# Stock Trading Platform - Microservices

A comprehensive stock trading platform built with Spring Boot microservices architecture.

## Architecture

The platform consists of 5 microservices:

1. **API Gateway** (Port 8080) - Routes requests to appropriate microservices
2. **Account Service** (Port 8081) - Manages user accounts and balances
3. **Demat Service** (Port 8082) - Manages demat accounts and holdings
4. **Trade Service** (Port 8083) - Handles trade orders and execution
5. **Settlement Service** (Port 8084) - Manages trade settlements

## Prerequisites

- Java 17 or higher
- Maven 3.8+
- MySQL (SQLyog for database management)
- IDE (IntelliJ IDEA / Eclipse / VS Code)

## Database Setup

1. Open SQLyog and connect to your MySQL server (default: localhost:3306, user: root, password: root)

2. Execute the SQL scripts in order:
   - `db-scripts/01_account_db.sql`
   - `db-scripts/02_demat_db.sql`
   - `db-scripts/03_trade_db.sql`
   - `db-scripts/04_settlement_db.sql`

3. Alternatively, Spring Boot will auto-create the databases and tables on first run based on JPA entities.

## Configuration

Update database credentials in each service's `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/{database_name}
    username: root
    password: your_password
```

## Running the Services

### Option 1: Run all services from IDE

Open each service's main application class and run them individually:
- `AccountServiceApplication.java`
- `DematServiceApplication.java`
- `TradeServiceApplication.java`
- `SettlementServiceApplication.java`
- `ApiGatewayApplication.java`

### Option 2: Run from command line

```bash
# Build the project
cd D:\Micro_service
mvn clean install -DskipTests

# Run each service in separate terminals
mvn spring-boot:run -pl account-service
mvn spring-boot:run -pl demat-service
mvn spring-boot:run -pl trade-service
mvn spring-boot:run -pl settlement-service
mvn spring-boot:run -pl api-gateway
```

## API Endpoints

### API Gateway (localhost:8080)

All requests go through the gateway:

#### Account Service APIs
- `POST /api/accounts` - Create new account
- `GET /api/accounts` - Get all accounts
- `GET /api/accounts/{id}` - Get account by ID
- `PUT /api/accounts/{id}` - Update account
- `POST /api/accounts/{id}/deposit` - Deposit funds
- `POST /api/accounts/{id}/withdraw` - Withdraw funds
- `DELETE /api/accounts/{id}` - Delete account

#### Demat Service APIs
- `POST /api/demat/accounts` - Create demat account
- `GET /api/demat/accounts` - Get all demat accounts
- `GET /api/demat/accounts/{id}` - Get demat account by ID
- `POST /api/demat/holdings` - Add/update holdings
- `GET /api/demat/holdings/{dematAccountId}` - Get holdings

#### Trade Service APIs
- `POST /api/trades` - Place a trade order
- `GET /api/trades` - Get all trades
- `GET /api/trades/{id}` - Get trade by ID
- `POST /api/trades/{id}/execute` - Execute trade
- `POST /api/trades/{id}/cancel` - Cancel trade

#### Settlement Service APIs
- `POST /api/settlements` - Create settlement
- `GET /api/settlements` - Get all settlements
- `GET /api/settlements/{id}` - Get settlement by ID
- `POST /api/settlements/{id}/process` - Process settlement

### Direct Service Access

You can also access services directly:
- Account Service: http://localhost:8081
- Demat Service: http://localhost:8082
- Trade Service: http://localhost:8083
- Settlement Service: http://localhost:8084

## Sample Requests

### Create Account
```json
POST http://localhost:8081/api/accounts
{
  "accountNumber": "ACC004",
  "accountHolderName": "Test User",
  "email": "test@email.com",
  "phone": "+1234567899",
  "accountType": "TRADING"
}
```

### Place Trade
```json
POST http://localhost:8083/api/trades
{
  "accountId": 1,
  "dematAccountId": 1,
  "stockSymbol": "TCS",
  "stockName": "Tata Consultancy Services",
  "isin": "INE467B01029",
  "tradeType": "BUY",
  "orderType": "MARKET",
  "quantity": 10,
  "price": 3500.00
}
```

### Create Settlement
```json
POST http://localhost:8084/api/settlements
{
  "tradeId": 3,
  "accountId": 1,
  "dematAccountId": 1,
  "stockSymbol": "TCS",
  "settlementType": "BUY",
  "quantity": 10,
  "pricePerShare": 3500.00,
  "totalAmount": 35000.00
}
```

## Inter-Service Communication

- Trade Service uses Feign clients to communicate with Account and Demat services
- Settlement Service uses Feign client to communicate with Trade Service
- All services communicate synchronously via REST APIs

## Technology Stack

- Spring Boot 3.2.0
- Spring Cloud 2023.0.0
- Spring Cloud Gateway
- OpenFeign
- Spring Data JPA
- MySQL
- Lombok
- Maven
