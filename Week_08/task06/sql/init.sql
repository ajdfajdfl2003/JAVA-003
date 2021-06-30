create schema ds0;
create schema ds1;
# --==========================
create table if not exists ds0.`ORDER_0`( order_id bigint not null, buyer_id bigint not null, PAYMENT bigint not null, STATUS varchar(10) not null, ORDER_TIME bigint not null, primary key (order_id)) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 comment '訂單清單';
create table if not exists ds0.`ORDER_1`( order_id bigint not null, buyer_id bigint not null, PAYMENT bigint not null, STATUS varchar(10) not null, ORDER_TIME bigint not null, primary key (order_id)) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 comment '訂單清單';
create table if not exists ds0.`ORDER_2`( order_id bigint not null, buyer_id bigint not null, PAYMENT bigint not null, STATUS varchar(10) not null, ORDER_TIME bigint not null, primary key (order_id)) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 comment '訂單清單';
create table if not exists ds0.`ORDER_3`( order_id bigint not null, buyer_id bigint not null, PAYMENT bigint not null, STATUS varchar(10) not null, ORDER_TIME bigint not null, primary key (order_id)) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 comment '訂單清單';
create table if not exists ds1.`ORDER_0`( order_id bigint not null, buyer_id bigint not null, PAYMENT bigint not null, STATUS varchar(10) not null, ORDER_TIME bigint not null, primary key (order_id)) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 comment '訂單清單';
create table if not exists ds1.`ORDER_1`( order_id bigint not null, buyer_id bigint not null, PAYMENT bigint not null, STATUS varchar(10) not null, ORDER_TIME bigint not null, primary key (order_id)) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 comment '訂單清單';
create table if not exists ds1.`ORDER_2`( order_id bigint not null, buyer_id bigint not null, PAYMENT bigint not null, STATUS varchar(10) not null, ORDER_TIME bigint not null, primary key (order_id)) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 comment '訂單清單';
create table if not exists ds1.`ORDER_3`( order_id bigint not null, buyer_id bigint not null, PAYMENT bigint not null, STATUS varchar(10) not null, ORDER_TIME bigint not null, primary key (order_id)) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 comment '訂單清單';
# --==========================
create table if not exists ds0.`ORDER_DETAIL_0`( o_detail_id bigint not null auto_increment, order_id bigint not null, PRODUCT_ID bigint not null, AMOUNT int not null, PAYMENT bigint not null, primary key (o_detail_id)) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 comment '訂單詳細資料';
create table if not exists ds0.`ORDER_DETAIL_1`( o_detail_id bigint not null auto_increment, order_id bigint not null, PRODUCT_ID bigint not null, AMOUNT int not null, PAYMENT bigint not null, primary key (o_detail_id)) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 comment '訂單詳細資料';
create table if not exists ds0.`ORDER_DETAIL_2`( o_detail_id bigint not null auto_increment, order_id bigint not null, PRODUCT_ID bigint not null, AMOUNT int not null, PAYMENT bigint not null, primary key (o_detail_id)) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 comment '訂單詳細資料';
create table if not exists ds0.`ORDER_DETAIL_3`( o_detail_id bigint not null auto_increment, order_id bigint not null, PRODUCT_ID bigint not null, AMOUNT int not null, PAYMENT bigint not null, primary key (o_detail_id)) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 comment '訂單詳細資料';
create table if not exists ds1.`ORDER_DETAIL_0`( o_detail_id bigint not null auto_increment, order_id bigint not null, PRODUCT_ID bigint not null, AMOUNT int not null, PAYMENT bigint not null, primary key (o_detail_id)) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 comment '訂單詳細資料';
create table if not exists ds1.`ORDER_DETAIL_1`( o_detail_id bigint not null auto_increment, order_id bigint not null, PRODUCT_ID bigint not null, AMOUNT int not null, PAYMENT bigint not null, primary key (o_detail_id)) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 comment '訂單詳細資料';
create table if not exists ds1.`ORDER_DETAIL_2`( o_detail_id bigint not null auto_increment, order_id bigint not null, PRODUCT_ID bigint not null, AMOUNT int not null, PAYMENT bigint not null, primary key (o_detail_id)) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 comment '訂單詳細資料';
create table if not exists ds1.`ORDER_DETAIL_3`( o_detail_id bigint not null auto_increment, order_id bigint not null, PRODUCT_ID bigint not null, AMOUNT int not null, PAYMENT bigint not null, primary key (o_detail_id)) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 comment '訂單詳細資料';
# --==========================
