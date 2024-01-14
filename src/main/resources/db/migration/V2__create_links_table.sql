CREATE TABLE IF NOT EXISTS l_link_s_link (
    id INT AUTO_INCREMENT PRIMARY KEY,
    long_link VARCHAR(255) NOT NULL,
    short_link VARCHAR(8) NOT NULL,
    id_user INT NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    expiration_date TIMESTAMP NOT NULL,
    clicks INT NOT NULL,
    FOREIGN KEY (id_user) REFERENCES users(id)
);