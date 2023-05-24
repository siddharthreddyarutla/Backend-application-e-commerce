CREATE TABLE IF NOT EXISTS PRODUCT_INFO(
PRODUCT_INFO_ID bigint PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT 'product info id',
PRODUCT_ID bigint NOT NULL COMMENT "product id to track",
QUANTITY bigint NOT NULL COMMENT "to store number of products available",
STATE VARCHAR(255) NOT NULL COMMENT "to store state of product whether OUT OF STOCK/IN STOCK/No longer available",
FOREIGN KEY (PRODUCT_ID) REFERENCES product(PRODUCT_ID) ON DELETE CASCADE
);