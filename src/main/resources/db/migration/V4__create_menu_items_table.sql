CREATE TABLE menu_items (
                            id UUID PRIMARY KEY,

                            name VARCHAR(150) NOT NULL,

                            description TEXT,

                            price DECIMAL(10,2) NOT NULL,

                            dine_in_only BOOLEAN NOT NULL,

                            photo_path VARCHAR(500),

                            restaurant_id UUID NOT NULL,

                            created_at TIMESTAMP NOT NULL,

                            updated_at TIMESTAMP NOT NULL,

                            CONSTRAINT fk_menu_item_restaurant
                                FOREIGN KEY (restaurant_id)
                                    REFERENCES restaurants(id)
);
CREATE INDEX idx_menu_items_restaurant
    ON menu_items(restaurant_id);

CREATE INDEX idx_menu_items_name
    ON menu_items(name);