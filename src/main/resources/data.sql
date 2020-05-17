CREATE TABLE IF NOT EXISTS product
(
    sku         VARCHAR(36)  PRIMARY KEY,
    name        VARCHAR(250) NOT NULL,
    description VARCHAR(250) NOT NULL
);

-- Add some sample data to our project.
-- Generate random skus: https://www.uuidgenerator.net/version4
INSERT INTO product (sku, name, description) VALUES ('35154d5d-f581-49d7-a9c5-4aee92793cd2', 'Margherita', 'Everone''s favorite.');
INSERT INTO product (sku, name, description) VALUES ('0e9ee982-1fde-4f62-a4c6-ef4baaa41b42', 'Pepperoni', 'A classic.');
INSERT INTO product (sku, name, description) VALUES ('5ce23afe-8943-4961-9c53-d89ee1c0cb84', 'Hawaiian', 'Delicious vegetarian option.');

CREATE TABLE IF NOT EXISTS shopping_cart
(
    cart_id        VARCHAR(36)  NOT NULL,
    sku            VARCHAR(36)  NOT NULL,
    item_count     INT          NOT NULL,
    PRIMARY KEY (cart_id, sku)
);

CREATE TABLE IF NOT EXISTS order_item
(
    order_id       VARCHAR(36)  NOT NULL,
    sku            VARCHAR(36)  NOT NULL,
    item_count     INT          NOT NULL,
    PRIMARY KEY (order_id, sku)
);
