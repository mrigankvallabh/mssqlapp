if not exists (select * from sys.objects where object_id = object_id('[dbo].[TACO_ORDER]') and type in ('U'))
create table TACO_ORDER (
  id int identity primary key,
  delivery_Name varchar(50) not null,
  delivery_Street varchar(50) not null,
  delivery_City varchar(50) not null,
  delivery_State varchar(2) not null,
  delivery_Zip varchar(10) not null,
  cc_number varchar(16) not null,
  cc_expiration varchar(5) not null,
  cc_cvv varchar(3) not null,
  placed_at timestamp not null
);

if not exists (select * from sys.objects where object_id = object_id('[dbo].[TACO]') and type in ('U'))
create table TACO (
  id int identity primary key,
  [name] varchar(50) not null,
  taco_order_id int not null references TACO_ORDER(id),
  taco_order_key bigint not null,
  created_at timestamp not null
);

if not exists (select * from sys.objects where object_id = object_id('[dbo].[INGREDIENT]') and type in ('U'))
create table INGREDIENT (
  id varchar(4) not null primary key,
  [name] varchar(25) not null,
  [type] varchar(10) not null
);

if not exists (select * from sys.objects where object_id = object_id('[dbo].[INGREDIENT_REF]') and type in ('U'))
create table INGREDIENT_REF (
  ingredient_id varchar(4) not null references INGREDIENT(id),
  taco bigint not null,
  taco_key bigint not null
);
