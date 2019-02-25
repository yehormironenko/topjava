DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, datetime, description, calories) VALUES
(100000, '2019-02-22 15:00', 'Breakfast', 500),
(100000, '2019-02-23 10:00', 'Lunch', 1000),
(100000, '2019-02-23 20:00', 'Dinner', 510),
(100001, '2019-02-22 10:00', 'Breakfast', 500),
(100001, '2019-02-22 13:00', 'Lunch', 1000),
(100001, '2019-02-23 21:00', 'Dinner', 500);