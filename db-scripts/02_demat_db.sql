-- ============================================
-- Demat Service Database Setup
-- ============================================

CREATE DATABASE IF NOT EXISTS demat_db;
USE demat_db;

-- Demat Accounts table
CREATE TABLE IF NOT EXISTS demat_accounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    demat_account_number VARCHAR(50) NOT NULL UNIQUE,
    account_id BIGINT NOT NULL,
    account_holder_name VARCHAR(100) NOT NULL,
    dp_id VARCHAR(20) NOT NULL UNIQUE,
    status ENUM('ACTIVE', 'INACTIVE', 'FROZEN', 'CLOSED') NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES account_db.accounts(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Demat Holdings table
CREATE TABLE IF NOT EXISTS demat_holdings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    demat_account_id BIGINT NOT NULL,
    isin VARCHAR(20) NOT NULL,
    stock_symbol VARCHAR(20) NOT NULL,
    stock_name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    average_price DECIMAL(10, 2) NOT NULL,
    current_price DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (demat_account_id) REFERENCES demat_accounts(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Sample data
INSERT INTO demat_accounts (demat_account_number, account_id, account_holder_name, dp_id, status) VALUES
('DEMAT001', 1, 'John Doe', 'DP12345', 'ACTIVE'),
('DEMAT002', 2, 'Jane Smith', 'DP12346', 'ACTIVE'),
('DEMAT003', 3, 'Robert Johnson', 'DP12347', 'ACTIVE');

INSERT INTO demat_holdings (demat_account_id, isin, stock_symbol, stock_name, quantity, average_price, current_price) VALUES
(1, 'INE001A01015', 'RELIANCE', 'Reliance Industries', 100, 2500.00, 2550.00),
(1, 'INE009A01021', 'INFY', 'Infosys Ltd', 50, 1450.00, 1480.00),
(2, 'INE001A01015', 'RELIANCE', 'Reliance Industries', 75, 2480.00, 2550.00),
(2, 'INE040A01034', 'HDFCBANK', 'HDFC Bank', 60, 1650.00, 1680.00),
(3, 'INE009A01021', 'INFY', 'Infosys Ltd', 30, 1420.00, 1480.00);
