-- ============================================
-- Account Service Database Setup
-- ============================================

CREATE DATABASE IF NOT EXISTS account_db;
USE account_db;

-- Accounts table
CREATE TABLE IF NOT EXISTS accounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(50) NOT NULL UNIQUE,
    account_holder_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    balance DECIMAL(15, 2) NOT NULL DEFAULT 0.00,
    account_type ENUM('SAVINGS', 'CURRENT', 'TRADING') NOT NULL,
    status ENUM('ACTIVE', 'INACTIVE', 'SUSPENDED', 'CLOSED') NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Sample data
INSERT INTO accounts (account_number, account_holder_name, email, phone, balance, account_type, status) VALUES
('ACC001', 'John Doe', 'john.doe@email.com', '+1234567890', 50000.00, 'TRADING', 'ACTIVE'),
('ACC002', 'Jane Smith', 'jane.smith@email.com', '+1234567891', 75000.00, 'TRADING', 'ACTIVE'),
('ACC003', 'Robert Johnson', 'robert.j@email.com', '+1234567892', 25000.00, 'SAVINGS', 'ACTIVE');
