CREATE TABLE x6order.order(
    id SERIAL PRIMARY KEY,
    order_number VARCHAR NOT NULL,
    order_date DATE NOT NULL,
    user_id INTEGER NOT NULL,
    create_date TIMESTAMP WITHOUT time ZONE DEFAULT current_timestamp
);

CREATE TABLE x6order.order_product(
    id SERIAL PRIMARY KEY,
    order_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    amount_product INTEGER NOT NULL,
    CONSTRAINT order_product_order_fk FOREIGN KEY (order_id)
    REFERENCES x6order.ORDER(id)
);