INSERT INTO users_balance (user_id, balance)
VALUES (1, 1000.00) ON CONFLICT (user_id) DO NOTHING;

INSERT INTO users_balance (user_id, balance)
VALUES (2, 500.00) ON CONFLICT (user_id) DO NOTHING;

INSERT INTO users_balance (user_id, balance)
VALUES (3, 2500.00) ON CONFLICT (user_id) DO NOTHING;

INSERT INTO users_balance (user_id, balance)
VALUES (4, 3500.00) ON CONFLICT (user_id) DO NOTHING;

INSERT INTO users_balance (user_id, balance)
VALUES (5, 2000.00) ON CONFLICT (user_id) DO NOTHING;

INSERT INTO users_balance (user_id, balance)
VALUES (6, 200.00) ON CONFLICT (user_id) DO NOTHING;

INSERT INTO users_balance (user_id, balance)
VALUES (7, 2000.00) ON CONFLICT (user_id) DO NOTHING;

INSERT INTO users_balance (user_id, balance)
VALUES (8, 3000.00) ON CONFLICT (user_id) DO NOTHING;

INSERT INTO users_balance (user_id, balance)
VALUES (9, 50000.00) ON CONFLICT (user_id) DO NOTHING;

INSERT INTO users_balance (user_id, balance)
VALUES (10, 125000.00) ON CONFLICT (user_id) DO NOTHING;
