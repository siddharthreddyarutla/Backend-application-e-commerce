CREATE TABLE IF NOT EXISTS CART(
    CART_ID bigint PRIMARY KEY AUTO_INCREMENT COMMENT 'cart id',
    USER_ID bigint NOT NULL COMMENT 'user id of user table',
    PRODUCT_ID bigint NOT NULL COMMENT 'product id of product id',
    QUANTITY bigint NOT NULL COMMENT 'quantity of the product',
    WISHLIST tinyint(1) DEFAULT 0 NOT NULL COMMENT 'To add to wishlist or not, 1 if added or 0 not added',
    FOREIGN KEY (USER_ID) REFERENCES USER(USER_ID) ON DELETE CASCADE,
    FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT_INFO(PRODUCT_ID)
);