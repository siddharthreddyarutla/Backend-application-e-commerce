CREATE TABLE IF NOT EXISTS PRE_ORDER_DETAILS (
USER_ID bigint NOT NULL COMMENT "user table id as foreign key in this table",
EXPECTED_DELIVERY_DATE DATE NOT NULL COMMENT "expected delivery date",
ORDER_ELIGIBILITY VARCHAR(255) NOT NULL COMMENT "whether the products in cart are eligible for free delivery or not",
TOTAL_ITEMS bigint NOT NULL COMMENT "total items added to cart",
TOTAL_AMOUNT DOUBLE NOT NULL COMMENT "total price of the items that are added to the cart",
FOREIGN KEY (USER_ID) REFERENCES USER(USER_ID) ON DELETE CASCADE
);