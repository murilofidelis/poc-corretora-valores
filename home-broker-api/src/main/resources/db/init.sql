CREATE SCHEMA IF NOT EXISTS home_broker;

create table home_broker.TB_SHARE(
  id INTEGER NOT NULL,
  cod varchar(15) NOT NULL,
  description varchar(255),
  last_prince decimal,
  CONSTRAINT share_pk PRIMARY KEY (id)
);

CREATE SEQUENCE home_broker.share_seq START 1;

create table home_broker.TB_ORDER(
  id INTEGER NOT NULL,
  id_share INTEGER NOT NULL,
  id_client INTEGER NOT NULL,
  min_price decimal,
  max_price decimal,
  amount INTEGER  NOT NULL,
  status varchar(45) NOT NULL,
  order_type varchar(45)NOT NULL,
  CONSTRAINT order_pk PRIMARY KEY (id),
  CONSTRAINT share_fk FOREIGN KEY (id_share) REFERENCES home_broker.TB_SHARE (id),
  CONSTRAINT client_fk FOREIGN KEY (id_client) REFERENCES home_broker.TB_CLIENT (id)
);

CREATE SEQUENCE home_broker.order_seq START 1;

create table home_broker.TB_CLIENT(
  id INTEGER NOT NULL,
  identification_document varchar(45) NOT NULL,
  full_name varchar(255),
  CONSTRAINT client_pk PRIMARY KEY (id)
);

CREATE SEQUENCE home_broker.client_seq START 1;
