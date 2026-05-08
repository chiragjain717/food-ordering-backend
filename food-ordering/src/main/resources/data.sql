-- Food Items
INSERT INTO food_item (name, description, price, category, available) VALUES
('Butter Chicken', 'Creamy tomato-based chicken curry', 280.00, 'MAIN_COURSE', true),
('Paneer Tikka', 'Grilled paneer with spices', 220.00, 'STARTER', true),
('Veg Biryani', 'Fragrant basmati rice with vegetables', 180.00, 'MAIN_COURSE', true),
('Chicken Biryani', 'Aromatic rice with chicken', 260.00, 'MAIN_COURSE', true),
('Gulab Jamun', 'Sweet milk dumplings in sugar syrup', 80.00, 'DESSERT', true),
('Mango Lassi', 'Sweet yogurt drink with mango', 70.00, 'BEVERAGE', true),
('Garlic Naan', 'Soft bread with garlic butter', 50.00, 'BREAD', true),
('Dal Makhani', 'Slow-cooked black lentils', 160.00, 'MAIN_COURSE', true),
('Masala Chai', 'Indian spiced tea', 30.00, 'BEVERAGE', true),
('Samosa (2 pcs)', 'Crispy fried pastry with potato filling', 40.00, 'STARTER', true);

-- Customers
INSERT INTO customer (name, email, phone, address) VALUES
('Rahul Sharma', 'rahul@example.com', '9876543210', 'MG Road, Indore'),
('Priya Verma', 'priya@example.com', '9812345678', 'Vijay Nagar, Indore'),
('Amit Patel', 'amit@example.com', '9898989898', 'AB Road, Indore');
