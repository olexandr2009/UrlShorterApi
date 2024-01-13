CREATE TABLE IF NOT EXISTS users(
    id SERIAL PRIMARY KEY,
    username VARCHAR UNIQUE,
    password VARCHAR
);

CREATE TABLE IF NOT EXISTS roles(
    id SERIAL PRIMARY KEY,
    name VARCHAR UNIQUE
);

CREATE TABLE IF NOT EXISTS users_roles(
    user_id INTEGER,
    role_id INTEGER,
    PRIMARY KEY(user_id, role_id),
    FOREIGN KEY(user_id) REFERENCES users(id),
    FOREIGN KEY(role_id) REFERENCES roles(id)
);
