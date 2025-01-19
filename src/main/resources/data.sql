-- Insert Cuisines
INSERT INTO cuisine (id, name) VALUES (1, 'Polish');
INSERT INTO cuisine (id, name) VALUES (2, 'Mexican');
INSERT INTO cuisine (id, name) VALUES (3, 'Italian');

-- Insert Menu Items (Main courses)
INSERT INTO menu_item (id, name, price, type, cuisine_id) VALUES (1, 'Pierogi', 1000, 'MAIN_COURSE', 1);
INSERT INTO menu_item (id, name, price, type, cuisine_id) VALUES (2, 'Tacos', 1250, 'MAIN_COURSE', 2);
INSERT INTO menu_item (id, name, price, type, cuisine_id) VALUES (3, 'Lasagna', 1400, 'MAIN_COURSE', 3);

-- Insert Menu Items (Desserts)
INSERT INTO menu_item (id, name, price, type, cuisine_id) VALUES (4, 'Polish Cheesecake', 600, 'DESSERT', 1);
INSERT INTO menu_item (id, name, price, type, cuisine_id) VALUES (5, 'Churros', 550, 'DESSERT', 2);
INSERT INTO menu_item (id, name, price, type, cuisine_id) VALUES (6, 'Tiramisu', 700, 'DESSERT', 3);

-- Insert Menu Items (Drinks)
INSERT INTO menu_item (id, name, price, type) VALUES (7, 'Water', 100, 'DRINK');
INSERT INTO menu_item (id, name, price, type) VALUES (8, 'Soda', 25, 'DRINK');

-- Insert Menu Items (Additions)
INSERT INTO menu_item (id, name, price, type) VALUES (10, 'Ice', 0, 'DRINK_ADDITION');
INSERT INTO menu_item (id, name, price, type) VALUES (11, 'Lemon', 75, 'DRINK_ADDITION');
