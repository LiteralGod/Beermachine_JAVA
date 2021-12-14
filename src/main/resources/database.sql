DROP TABLE IF EXISTS beer_type CASCADE;
DROP TABLE IF EXISTS default_product CASCADE;

create table beer_type(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

create table default_product(
    id SERIAL PRIMARY KEY,
    beerTypeID INTEGER references beer_type(id),
    defaultSpeed INTEGER NOT NULL,
    defaultAmount INTEGER NOT NULL
);

create table batches(
    id serial PRIMARY KEY,
    batchID INTEGER NOT NULL,
    productName varchar(50) references beer_type(name),
    speed integer not null,
    totalAmount integer not null,
    goodAmount integer not null,
    badAmount integer not null
);


INSERT INTO beer_type(name)
values ('Pilsner'),
       ('Wheat'),
       ('IPA'),
       ('Stout'),
       ('Ale'),
       ('Alcohol Free');

INSERT INTO default_product(beerTypeID, defaultSpeed, defaultAmount)
values(1, 456, 500),
       (2, 168, 500),
       (3, 99, 500),
       (4,86,500),
       (5, 84, 500),
       (6,85,500);

INSERT INTO batches(batchID, productName, totalAmount, speed, goodAmount, badAmount)
values (1, 'Pilsner', 500, 400, 100)


