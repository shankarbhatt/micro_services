-- ============================================
-- Settlement Service Database Setup
-- ============================================

CREATE DATABASE IF NOT EXISTS settlement_db;
USE settlement_db;

-- Settlements table
CREATE TABLE IF NOT EXISTS settlements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    trade_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    demat_account_id BIGINT NOT NULL,
    stock_symbol VARCHAR(20) NOT NULL,
    settlement_type ENUM('BUY', 'SELL') NOT NULL,
    quantity INT NOT NULL,
    price_per_share DECIMAL(10, 2) NOT NULL,
    total_amount DECIMAL(15, 2) NOT NULL,
    brokerage DECIMAL(10, 2) NOT NULL,
    taxes DECIMAL(10, 2) NOT NULL,
    net_amount DECIMAL(15, 2) NOT NULL,
    status ENUM('PENDING', 'IN_PROGRESS', 'COMPLETED', 'FAILED', 'CANCELLED') NOT NULL,
    settlement_reference_number VARCHAR(50) UNIQUE,
    settlement_date TIMESTAMP NULL DEFAULT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Sample data
INSERT INTO settlements (trade_id, account_id, demat_account_id, stock_symbol, settlement_type, quantity, price_per_share, total_amount, brokerage, taxes, net_amount, status, settlement_reference_number, settlement_date) VALUES
(1, 1, 1, 'RELIANCE', 'BUY', 10, 2500.00, 25000.00, 12.50, 25.00, 25037.50, 'COMPLETED', 'STL-A1B2C3D4', NOW() - INTERVAL 2 DAY),
(2, 1, 1, 'INFY', 'BUY', 5, 1450.00, 7250.00, 3.63, 7.25, 7260.88, 'COMPLETED', 'STL-E5F6G7H8', NOW() - INTERVAL 1 DAY);
