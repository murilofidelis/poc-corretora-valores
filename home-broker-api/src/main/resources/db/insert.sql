
--- TB_CLIENT
INSERT INTO home_broker.tb_client
(id, identification_document, full_name)
VALUES(nextval('home_broker.client_seq'), '85360516046', 'Client 6');

--- TB_SHARE
INSERT INTO home_broker.tb_share
(id, cod, description, last_price)
VALUES(nextval('home_broker.share_seq'), 'VIIA3', 'GRUPO CASAS BAHIA S.A.', null);

INSERT INTO home_broker.tb_share
(id, cod, description, last_price)
VALUES(nextval('home_broker.share_seq'), 'MGLU3', 'MAGAZINE LUIZA ON NM', null);

INSERT INTO home_broker.tb_share
(id, cod, description, last_price)
VALUES(nextval('home_broker.share_seq'), 'ABEV3', 'AMBEV S/A ON', null);

INSERT INTO home_broker.tb_share
(id, cod, description, last_price)
VALUES(nextval('home_broker.share_seq'), 'VALE3', 'VALE ON NM', null);


INSERT INTO home_broker.tb_share
(id, cod, description, last_price)
VALUES(nextval('home_broker.share_seq'), 'HAPV3', 'HAPVIDA ON NM', null);


INSERT INTO home_broker.tb_share
(id, cod, description, last_price)
VALUES(nextval('home_broker.share_seq'), 'B3SA3', 'B3 ON NM', null);

INSERT INTO home_broker.tb_share
(id, cod, description, last_price)
VALUES(nextval('home_broker.share_seq'), 'PETR3', 'PETROBRAS', null);

INSERT INTO home_broker.tb_share
(id, cod, description, last_price)
VALUES(nextval('home_broker.share_seq'), 'BBAS3', 'BRASIL ON EJ NM', null);

INSERT INTO home_broker.tb_share
(id, cod, description, last_price)
VALUES(nextval('home_broker.share_seq'), 'SUZB3', 'SUZANO S.A. ON NM', null);

INSERT INTO home_broker.tb_share
(id, cod, description, last_price)
VALUES(nextval('home_broker.share_seq'), 'CPLE3', 'COPEL ON N2', null);