CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    brand VARCHAR(50) NOT NULL,
    price DECIMAL(12,2) NOT NULL,
    stock INT NOT NULL
);
select *from products;
CREATE TYPE user_role AS ENUM ('ADMIN', 'CUSTOMER');
CREATE TABLE customers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role user_role DEFAULT 'CUSTOMER',
    address VARCHAR(255)
);
-- tài khoản á
INSERT INTO customers(name, phone, email, password, role, address)
VALUES (
    'Administrator',
    '0123456789',
    'admin@gmail.com',
    '123456',
    'ADMIN',
    'Hà Nội'
);
SELECT * FROM customers;
select *from invoices;
CREATE TABLE invoices (
    id SERIAL PRIMARY KEY,
    customer_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(12,2) NOT NULL,

    CONSTRAINT fk_invoice_customer
        FOREIGN KEY (customer_id)
        REFERENCES customers(id)
);
CREATE TABLE invoice_details (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    invoice_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(12,2) NOT NULL,

    CONSTRAINT fk_invoice_details_invoice
        FOREIGN KEY (invoice_id)
        REFERENCES invoices(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_invoice_details_product
        FOREIGN KEY (product_id)
        REFERENCES products(id)
);
select *from invoice_details;