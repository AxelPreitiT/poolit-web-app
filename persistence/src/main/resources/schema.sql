CREATE TABLE IF NOT EXISTS users(
    user_id SERIAL PRIMARY KEY,
    email TEXT NOT NULL,
    phone VARCHAR(20) NOT NULL,
    UNIQUE(email)
);