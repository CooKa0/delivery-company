INSERT INTO customers (name, email, phone_number, created_at)
VALUES ('John Doe', 'john.doe@example.com', '123-456-7890', NOW());

INSERT INTO orders (customer_id, company_id, order_date, total_price, invoices_id)
VALUES (1, 1, NOW(), 150.00, 1);

INSERT INTO products (name, price)
VALUES ('Product A', 50.00);

INSERT INTO employees (name, role, hire_date, company_id)
VALUES ('Jane Smith', 'Driver', NOW(), 1);

INSERT INTO deliveries (order_id, employee_id, vehicle_id, delivery_date, company_id)
VALUES (1, 2, 1, NOW(), 1);

INSERT INTO customers (name, email, phone_number, created_at)
VALUES 
  ('Alice Green', 'alice.green@example.com', '555-1234', NOW()),
  ('Bob Brown', 'bob.brown@example.com', '555-5678', NOW());
  
  INSERT INTO orders (customer_id, company_id, order_date, total_price, invoices_id)
VALUES 
  (2, 1, NOW(), 100.00, 2),
  (3, 1, NOW(), 200.00, 3);
  
  INSERT INTO products (name, price, warehouse_id)
VALUES 
  ('Product B', 75.00, 1),
  ('Product C', 90.00, 2);
  
  INSERT INTO employees (name, role, hire_date, company_id)
VALUES 
  ('Emily White', 'Manager', NOW(), 1),
  ('David Black', 'Sales', NOW(), 1);
  
  INSERT INTO deliveries (employee_id, delivery_date, company_id)
VALUES 
  (3, NOW(), 1),
  (4, NOW(), 1);
  
  UPDATE customers
SET email = 'john.newemail@example.com'
WHERE id = 1;
  
UPDATE orders
SET total_price = 175.00
WHERE id = 1;

UPDATE products
SET price = 60.00
WHERE id = 1;

UPDATE employees
SET role = 'Senior Driver'
WHERE id = 2;

UPDATE deliveries
SET delivery_date = NOW()
WHERE id = 1;

UPDATE customers
SET phone_number = '999-9999'
WHERE id IN (2, 3);

UPDATE orders
SET total_price = total_price * 1.1
WHERE customer_id = 1;

UPDATE products
SET warehouse_id = 2
WHERE id = 1;

UPDATE employees
SET hire_date = '2024-01-01'
WHERE id = 3;

UPDATE deliveries
SET employee_id = 4
WHERE id = 2;

DELETE FROM customers
WHERE id = 3;

DELETE FROM orders
WHERE id = 1;

DELETE FROM products
WHERE id = 1;

DELETE FROM employees
WHERE id = 2;

DELETE FROM deliveries
WHERE id = 1;

DELETE FROM customers
WHERE id IN (1, 2);

DELETE FROM orders
WHERE customer_id = 2;

DELETE FROM products
WHERE warehouse_id = 2;

DELETE FROM employees
WHERE company_id = 1;

DELETE FROM deliveries
WHERE employee_id = 3;

ALTER TABLE customers
ADD COLUMN preferred_contact_method VARCHAR(50) NOT NULL DEFAULT 'email';

ALTER TABLE orders
MODIFY COLUMN total_price DECIMAL(10, 2) NOT NULL;

ALTER TABLE products
DROP COLUMN price;

ALTER TABLE employees
RENAME COLUMN role TO position;

ALTER TABLE warehouses
ADD COLUMN capacity INT NOT NULL DEFAULT 1000;

SELECT c.name AS customer_name, o.id AS order_id, p.name AS product_name, e.name AS employee_name, d.delivery_date
FROM customers c
JOIN orders o ON c.id = o.customer_id
JOIN order_items oi ON o.id = oi.order_id
JOIN products p ON oi.product_id = p.id
JOIN deliveries d ON o.id = d.order_id
JOIN employees e ON d.employee_id = e.id
WHERE c.company_id = 1;

SELECT c.name, o.id, o.total_price
FROM customers c
LEFT JOIN orders o ON c.id = o.customer_id
WHERE c.company_id = 1;

SELECT p.name, oi.quantity, o.id
FROM products p
RIGHT JOIN order_items oi ON p.id = oi.product_id
RIGHT JOIN orders o ON oi.order_id = o.id;

SELECT o.id, e.name, d.delivery_date
FROM orders o
INNER JOIN deliveries d ON o.id = d.order_id
INNER JOIN employees e ON d.employee_id = e.id;

SELECT o.id AS order_id, e.name AS employee_name
FROM orders o
LEFT JOIN employees e ON o.customer_id = e.id;

SELECT w.location, p.name AS product_name
FROM warehouses w
RIGHT JOIN products p ON w.id = p.warehouse_id;

SELECT c.name, SUM(o.total_price) AS total_sales
FROM customers c
JOIN orders o ON c.id = o.customer_id
GROUP BY c.name
HAVING total_sales > 100;

SELECT p.name, AVG(oi.quantity) AS avg_quantity
FROM products p
JOIN order_items oi ON p.id = oi.product_id
GROUP BY p.name
HAVING avg_quantity > 5;

SELECT e.name, COUNT(d.id) AS delivery_count
FROM employees e
JOIN deliveries d ON e.id = d.employee_id
GROUP BY e.name
HAVING delivery_count > 2;

SELECT c.name, COUNT(o.id) AS total_orders
FROM customers c
JOIN orders o ON c.id = o.customer_id
GROUP BY c.name
HAVING total_orders > 5;

SELECT w.location, COUNT(p.id) AS total_products
FROM warehouses w
JOIN products p ON w.id = p.warehouse_id
GROUP BY w.location
HAVING total_products > 50;

SELECT o.id, MIN(oi.quantity) AS min_quantity
FROM orders o
JOIN order_items oi ON o.id = oi.order_id
GROUP BY o.id
HAVING min_quantity > 1;

SELECT e.company_id, MAX(e.salary) AS max_salary
FROM employees e
GROUP BY e.company_id
HAVING max_salary > 5000;

SELECT c.name, AVG(o.total_price) AS avg_price
FROM customers c
JOIN orders o ON c.id = o.customer_id
GROUP BY c.name;

SELECT p.name, COUNT(oi.id) AS order_count
FROM products p
JOIN order_items oi ON p.id = oi.product_id
GROUP BY p.name;

SELECT e.name, SUM(o.total_price) AS total_sales
FROM employees e
JOIN deliveries d ON e.id = d.employee_id
JOIN orders o ON d.order_id = o.id
GROUP BY e.name;

SELECT o.id, MAX(oi.quantity) AS max_quantity
FROM orders o
JOIN order_items oi ON o.id = oi.order_id
GROUP BY o.id;

SELECT w.location, COUNT(p.id) AS product_count
FROM warehouses w
JOIN products p ON w.id = p.warehouse_id
GROUP BY w.location;

SELECT e.name, MIN(e.salary) AS min_salary
FROM employees e
GROUP BY e.name;

SELECT e.name, COUNT(d.id) AS delivery_count
FROM employees e
JOIN deliveries d ON e.id = d.employee_id
GROUP BY e.name;