CREATE TABLE IF NOT EXISTS ORDER_DETAILS (
ORDER_DETAILS_ID bigint PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT "order details id",
ORDER_ID bigint NOT NULL COMMENT "order id of order table as foreign key",
SHIPPING_ADDRESS_ID bigint NOT NULL COMMENT "shipping address details",
BILLING_ADDRESS_ID bigint NOT NULL COMMENT "billing address details",
PAYMENT_METHOD VARCHAR(255) NOT NULL COMMENT "to track the payment method",
TOTAL_ITEMS bigint NOT NULL COMMENT "total items",
DELIVERY_CHARGES bigint NOT NULL COMMENT "delivery charges applicability",
TAX_CHARGES bigint DEFAULT NULL COMMENT "tax charges if applicable",
TOTAL_AMOUNT DOUBLE NOT NULL COMMENT "total amount for the product items",
FOREIGN KEY (ORDER_ID) REFERENCES ORDERS(ORDER_ID) ON DELETE CASCADE
);