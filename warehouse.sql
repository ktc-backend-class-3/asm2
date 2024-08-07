--admin	YWRtaW4=	1

DROP TABLE TBL_USERS;
CREATE TABLE TBL_USERS 
(
  ID INT GENERATED ALWAYS AS IDENTITY NOT NULL 
, USERNAME VARCHAR2(50) 
, PASSWORD VARCHAR2(50) 
, ROLE_ID INT 
, WAREHOUSE_ID INT
, CREATED_BY INT
, CREATED_DATE DATE
, UPDATED_BY INT
, UPDATED_DATE DATE
, CONSTRAINT TBL_USERS_PK PRIMARY KEY 
  (
    ID 
  )
  ENABLE 
);

DROP TABLE TBL_ROLES;
CREATE TABLE TBL_ROLES 
(
  ID INT GENERATED ALWAYS AS IDENTITY NOT NULL 
, ROLE_NAME VARCHAR2(50) 
, CONSTRAINT TBL_ROLES_PK PRIMARY KEY 
  (
    ID 
  )
  ENABLE 
);

DROP TABLE TBL_WAREHOUSES;
CREATE TABLE TBL_WAREHOUSES
(
  ID INT GENERATED ALWAYS AS IDENTITY NOT NULL 
, NAME VARCHAR2(50)
, ADDRESS VARCHAR2(100)
, CREATED_BY INT
, CREATED_DATE DATE
, UPDATED_BY INT
, UPDATED_DATE DATE
, CONSTRAINT TBL_WAREHOUSES_PK PRIMARY KEY 
  (
    ID 
  )
  ENABLE 
);

DROP TABLE TBL_PRODUCTS;
CREATE TABLE TBL_PRODUCTS
(
  ID INT GENERATED ALWAYS AS IDENTITY NOT NULL 
, WAREHOUSE_ID INT 
, NAME VARCHAR2(50)
, DESCRIPTION VARCHAR2(200)
, QUANTITY INT
, PRICE DECIMAL
, CREATED_BY INT
, CREATED_DATE DATE
, UPDATED_BY INT
, UPDATED_DATE DATE
, CONSTRAINT TBL_PRODUCTS_PK PRIMARY KEY 
  (
    ID 
  )
  ENABLE 
);