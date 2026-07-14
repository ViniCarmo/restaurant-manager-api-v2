CREATE TABLE users (
                       id UUID PRIMARY KEY,

                       name VARCHAR(150) NOT NULL,

                       email VARCHAR(255) NOT NULL UNIQUE,

                       password VARCHAR(255) NOT NULL,

                       user_type_id UUID NOT NULL,

                       created_at TIMESTAMP NOT NULL,

                       updated_at TIMESTAMP NOT NULL,

                       CONSTRAINT fk_users_user_type
                           FOREIGN KEY (user_type_id)
                               REFERENCES user_types(id)
);

CREATE INDEX idx_users_email
    ON users(email);

CREATE INDEX idx_users_user_type
    ON users(user_type_id);