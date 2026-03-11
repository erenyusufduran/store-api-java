CREATE TABLE IF NOT EXISTS stores (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS inventories (
    id UUID PRIMARY KEY,
    store_id UUID NOT NULL,
    product_id UUID NOT NULL,
    quantity INTEGER NOT NULL,
    CONSTRAINT fk_inventories_store FOREIGN KEY (store_id) REFERENCES stores (id),
    CONSTRAINT fk_inventories_product FOREIGN KEY (product_id) REFERENCES products (id),
    CONSTRAINT uq_store_product UNIQUE (store_id, product_id)
);