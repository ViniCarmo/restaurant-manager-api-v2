    CREATE TABLE restaurants (
                                 id UUID PRIMARY KEY,

                                 name VARCHAR(150) NOT NULL,

                                 address VARCHAR(255) NOT NULL,

                                 kitchen_type VARCHAR(50) NOT NULL,

                                 opening_time TIME NOT NULL,

                                 closing_time TIME NOT NULL,

                                 restaurant_owner_id UUID NOT NULL,

                                 created_at TIMESTAMP NOT NULL,

                                 updated_at TIMESTAMP NOT NULL,

                                 CONSTRAINT fk_restaurant_owner
                                     FOREIGN KEY (restaurant_owner_id)
                                         REFERENCES users(id)
    );

    CREATE INDEX idx_restaurants_name
        ON restaurants(name);

    CREATE INDEX idx_restaurants_owner
        ON restaurants(restaurant_owner_id);

    CREATE INDEX idx_restaurants_kitchen_type
        ON restaurants(kitchen_type);