-- ============================================
-- Trade Service Database Setup
-- ============================================

CREATE DATABASE IF NOT EXISTS trade_db;
USE trade_db;

-- Trades table
CREATE TABLE IF NOT EXISTS trades (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT NOT NULL,
    demat_account_id BIGINT NOT NULL,
    stock_symbol VARCHAR(20) NOT NULL,
    stock_name VARCHAR(100) NOT NULL,
    isin VARCHAR(20) NOT NULL,
    trade_type ENUM('BUY', 'SELL') NOT NULL,
    order_type ENUM('MARKET', 'LIMIT', 'STOP_LOSS') NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    total_amount DECIMAL(15, 2) NOT NULL,
    status ENUM('PENDING', 'EXECUTED', 'PARTIALLY_EXECUTED', 'CANCELLED', 'REJECTED', 'SETTLED') NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    executed_at TIMESTAMP NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Sample data
INSERT INTO trades (account_id, demat_account_id, stock_symbol, stock_name, isin, trade_type, order_type, quantity, price, total_amount, status, created_at, executed_at) VALUES
(1, 1, 'RELIANCE', 'Reliance Industries', 'INE001A01015', 'BUY', 'MARKET', 10, 2500.00, 25000.00, 'EXECUTED', NOW() - INTERVAL 2 DAY, NOW() - INTERVAL 2 DAY),
(1, 1, 'INFY', 'Infosys Ltd', 'INE009A01021', 'BUY', 'LIMIT', 5, 1450.00, 7250.00, 'EXECUTED', NOW() - INTERVAL 1 DAY, NOW() - INTERVAL 1 DAY),
(2, 2, 'HDFCBANK', 'HDFC Bank', 'INE040A01034', 'BUY', 'MARKET', 15, 1650.00, 24750.00, 'PENDING', NOW(), NULL),
(1, 1, 'RELIANCE', 'Reliance Industries', 'INE001A01015', 'SELL', 'LIMIT', 5, 2600.00, 13000.00, 'PENDING', NOW(), NULL);
