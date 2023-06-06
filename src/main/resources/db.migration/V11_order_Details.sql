CREATE TABLE IF NOT EXISTS ORDER_DETAILS (
ORDER_ID bigint PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT "order id",
USER_ID bigint NOT NULL COMMENT "user id refers respective user",
ADDRESS_ID bigint NOT NULL COMMENT "address id for tracking the location added for delivery",
PRODUCT_ID VARCHAR(255) NOT NULL COMMENT "to store all product ids of the user whose products are added to cart/ordered",
ORDER_PLACED_DATE DATE NOT NULL COMMENT "order placed date",
DELIVERY_DATE DATE NOT NULL COMMENT "expected delivery date before delivery and delivered date after product delivery",
PAYMENT_METHOD VARCHAR(255) NOT NULL COMMENT "payment method choosed",
TOTAL_ITEMS bigint NOT NULL COMMENT "total items ordered at once",
DELIVERY_CHARGES bigint NOT NULL COMMENT "delivery charges applicable for the product",
TOTAL_AMOUNT DOUBLE NOT NULL COMMENT "total amount for the items or products ordered",
ORDER_STATE VARCHAR(255) NOT NULL COMMENT "gives the state of the product",
FOREIGN KEY (USER_ID) REFERENCES USER(USER_ID) ON DELETE CASCADE,
FOREIGN KEY (ADDRESS_ID) REFERENCES ADDRESS(ADDRESS_ID) ON DELETE CASCADE
);