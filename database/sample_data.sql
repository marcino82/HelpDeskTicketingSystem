DECLARE
@james_id INT;
DECLARE
@callum_id INT;
DECLARE
@lewis_id INT;
DECLARE
@tomas_id INT;

-- Users

INSERT INTO users (first_name, last_name, email, phone_number)
VALUES ('James', 'Murray', 'j.murray@gmail.com', '07775 443 344');

SET
@james_id = SCOPE_IDENTITY();

INSERT INTO users (first_name, last_name, email, phone_number)
VALUES ('Callum', 'Stewart', 'c.stewart@yahoo.com', '07199 556 789');

SET
@callum_id = SCOPE_IDENTITY();

INSERT INTO users (first_name, last_name, email, phone_number)
VALUES ('Lewis', 'Brown', 'l.brown@yahoo.com', '07766 516 382');

SET
@lewis_id = SCOPE_IDENTITY();

INSERT INTO users (first_name, last_name, email, phone_number)
VALUES ('Tomas', 'Cook', 't.cook@gmail.com', '07786 564 332');

SET
@tomas_id = SCOPE_IDENTITY();

-- User roles

INSERT INTO customers (user_id)
VALUES (@james_id);

INSERT INTO customers (user_id)
VALUES (@tomas_id);

INSERT INTO support_agents (user_id)
VALUES (@callum_id);

INSERT INTO support_agents (user_id)
VALUES (@lewis_id);

-- Tickets

INSERT INTO tickets (customer_id, title, description, priority, status)
VALUES (@james_id, 'Printer problem', 'The printer can''t detect the Wi-Fi', 'MEDIUM', 'OPEN');

INSERT INTO tickets (customer_id, agent_id, title, description, priority, status)
VALUES (@james_id, @callum_id, 'Power critical issue', 'The system is experiencing power losses', 'MEDIUM', 'ASSIGNED');

INSERT INTO tickets (customer_id, agent_id, title, description, priority, status)
VALUES (@tomas_id, @callum_id, 'Laptop problem', 'The laptop doesn''t turn on after pressing power button', 'MEDIUM',
        'ASSIGNED');

INSERT INTO tickets (customer_id, agent_id, title, description, priority, status)
VALUES (@tomas_id, @lewis_id, 'Security issue!', 'Probably I have opened the phishing email!', 'HIGH', 'ASSIGNED');

INSERT INTO tickets (customer_id, agent_id, title, description, priority, status)
VALUES (@james_id, @lewis_id, 'Screen problem', 'Monitor''s screen occasionally turns black', 'LOW', 'ASSIGNED');

INSERT INTO tickets (customer_id, agent_id, title, description, priority, status)
VALUES (@james_id, @lewis_id, 'Email problem', 'I can''t access my email, need help ASAP!', 'HIGH', 'ASSIGNED');

INSERT INTO tickets (customer_id, title, description, priority, status)
VALUES (@tomas_id, 'I have been hacked!', 'Someone took control over my account', 'HIGH', 'OPEN');

INSERT INTO tickets (customer_id, title, description, priority, status)
VALUES (@james_id, 'Keyboard issue', 'Some buttons don''t work', 'LOW', 'OPEN');
