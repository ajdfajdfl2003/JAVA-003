create table JAVA003.USER
(
    ID        bigint auto_increment      not null,
    FULL_NAME varchar(30)  not null,
    ACCOUNT   varchar(30)  not null,
    PASS      varchar(50)  null,
    EMAIL     varchar(255) null,
    ADDRESS   varchar(255) null,
    PHONE     varchar(50)  null,
    constraint ID_ACCOUNT
        unique (ID, ACCOUNT)
)
    comment '買家資料';

# ==========================

create table JAVA003.SHOPPINGCART
(
    ID bigint auto_increment,
    BUYER_ID bigint not null,
    PRODUCT_ID bigint not null,
    AMOUNT int not null,
    PAYMENT bigint not null,
    constraint SHOPPINGCART_ID_uindex
        unique (ID)
)
    comment '購物車';

alter table JAVA003.SHOPPINGCART
    add primary key (ID);

# ==========================

create table JAVA003.`ORDER`
(
    ID bigint auto_increment,
    BUYER_ID bigint not null,
    DETAIL_ID bigint not null,
    PAYMENT bigint not null,
    STATUS varchar(10) not null,
    ORDER_TIME mediumtext not null,
    constraint ORDER_ID_uindex
        unique (ID)
)
    comment '訂單清單';

alter table JAVA003.`ORDER`
    add primary key (ID);

# ==========================

create table JAVA003.ORDER_DETAIL
(
    ID bigint auto_increment,
    ORDER_ID bigint not null,
    PRODUCT_ID bigint not null,
    AMOUNT int not null,
    PAYMENT bigint not null,
    constraint ORDER_DETAIL_ID_uindex
        unique (ID)
);

alter table JAVA003.ORDER_DETAIL
    add primary key (ID);

# ==========================

create table JAVA003.PRODUCT
(
    ID bigint auto_increment,
    NAME varchar(50) not null,
    INVENTORY varchar(50) not null,
    UNIT varchar(3) not null,
    COST bigint not null,
    STOCK int not null,
    constraint PRODUCT_ID_uindex
        unique (ID)
)
    comment '商品表';

alter table JAVA003.PRODUCT
    add primary key (ID);
