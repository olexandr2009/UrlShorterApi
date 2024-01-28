CREATE TABLE IF NOT EXISTS l_link_s_link (
    id UUID PRIMARY KEY,
    long_link VARCHAR(255) NOT NULL,
    short_link VARCHAR NOT NULL,
    owner_name VARCHAR NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    expiration_date TIMESTAMP NOT NULL,
    clicks INT NOT NULL,
    FOREIGN KEY (owner_name) REFERENCES users(username)
);