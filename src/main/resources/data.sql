-- Insert Cuisines
INSERT INTO cuisine (id, name) VALUES (1, 'Polish');
INSERT INTO cuisine (id, name) VALUES (2, 'Mexican');
INSERT INTO cuisine (id, name) VALUES (3, 'Italian');

-- Insert Menu Items (Meals)
INSERT INTO menu_item (id, name, price, type, cuisine_id) VALUES (1, 'Pierogi', 10.0, 'MEAL', 1);
INSERT INTO menu_item (id, name, price, type, cuisine_id) VALUES (2, 'Tacos', 12.5, 'MEAL', 2);
INSERT INTO menu_item (id, name, price, type, cuisine_id) VALUES (3, 'Lasagna', 14.0, 'MEAL', 3);

-- Insert Menu Items (Desserts)
INSERT INTO menu_item (id, name, price, type, cuisine_id) VALUES (4, 'Polish Cheesecake', 6.0, 'DESSERT', 1);
INSERT INTO menu_item (id, name, price, type, cuisine_id) VALUES (5, 'Churros', 5.5, 'DESSERT', 2);
INSERT INTO menu_item (id, name, price, type, cuisine_id) VALUES (6, 'Tiramisu', 7.0, 'DESSERT', 3);

-- Insert Menu Items (Drinks)
INSERT INTO menu_item (id, name, price, type) VALUES (7, 'Water', 1.0, 'DRINK');
INSERT INTO menu_item (id, name, price, type) VALUES (8, 'Soda', 2.5, 'DRINK');

-- Insert Menu Items (Additions)
INSERT INTO menu_item (id, name, price, type) VALUES (10, 'Ice', 0.5, 'DRINK_ADDITION');
INSERT INTO menu_item (id, name, price, type) VALUES (11, 'Lemon', 0.75, 'DRINK_ADDITION');
