CREATE TABLE IF NOT EXISTS ORDER_DETAILS (
ORDER_ID bigint NOT NULL COMMENT "order id of order table as foreign key",
PRODUCT_IDS VARCHAR(255) NOT NULL COMMENT "to store the product ids of the products",
SHIPPING_ADDRESS_ID bigint NOT NULL COMMENT "shipping address details",
BILLING_ADDRESS_ID bigint NOT NULL COMMENT "billing address details",
PAYMENT_METHOD VARCHAR(255) NOT NULL COMMENT "to track the payment method",
TOTAL_ITEMS bigint NOT NULL COMMENT "total items",
TOTAL_AMOUNT DOUBLE NOT NULL COMMENT "total amount for the product items",
DELIVERY_CHARGES bigint NOT NULL COMMENT "delivery charges applicability",
TAX_CHARGES bigint DEFAULT NULL COMMENT "tax charges if applicable",
ORDER_AMOUNT DOUBLE NOT NULL COMMENT "total amount for the product items including all charges",
FOREIGN KEY (ORDER_ID) REFERENCES ORDERS(ORDER_ID) ON DELETE CASCADE
);