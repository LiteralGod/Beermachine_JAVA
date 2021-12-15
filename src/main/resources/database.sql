DROP TABLE IF EXISTS beer_type CASCADE;
DROP TABLE IF EXISTS default_product CASCADE;
DROP TABLE IF EXISTS batches CASCADE;
DROP TABLE IF EXISTS humidity cascade;
DROP TABLE IF EXISTS temperature cascade;


create table beer_type
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

create table default_product
(
    id            SERIAL PRIMARY KEY,
    beerTypeID    INTEGER references beer_type (id),
    defaultSpeed  INTEGER NOT NULL,
    defaultAmount INTEGER NOT NULL
);

create table batches
(
    id          serial PRIMARY KEY,
    batchID     INTEGER NOT NULL,
    productName varchar(50) references beer_type (name),
    speed       integer not null,
    totalAmount integer not null,
    goodAmount  integer not null,
    badAmount   integer not null
);

create table temperature
(
    batchID INTEGER NOT NULL references batches (batchID),
    tempValue   float NOT NULL
);

create table humidity
(
    batchID INTEGER NOT NULL references batches (batchID),
    humiValue   INTEGER NOT NULL
);



INSERT INTO beer_type(name)
values ('Pilsner'),
       ('Wheat'),
       ('IPA'),
       ('Stout'),
       ('Ale'),
       ('Alcohol Free');

INSERT INTO default_product(beerTypeID, defaultSpeed, defaultAmount)
values (1, 456, 500),
       (2, 168, 500),
       (3, 99, 500),
       (4, 86, 500),
       (5, 84, 500),
       (6, 85, 500);

INSERT INTO batches(batchID, productName, speed, totalAmount, goodAmount, badAmount)
values (1, (select name from beer_type where id = 1), 250, 500, 400, 100),
       (2, (select name from beer_type where id = 1), 250, 500, 400, 100);


INSERT INTO temperature(batchID, tempValue)
values (1,35.1),
       (1,35.1),
       (1,35.1),
       (1,35.1),
       (1,35.1),
       (1,35.1),
       (1,35.1),
       (1,35.1),
       (1,35.1),
       (1,35.1),
       (1,35.1),
       (1,35.1),
       (1,35.1);
INSERT INTO humidity(batchID, humiValue)
values (1,25),
       (1,25),
       (1,25),
       (1,25),
       (1,25),
       (1,25),
       (1,25),
       (1,25);

select * from temperature