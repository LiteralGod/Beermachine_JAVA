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


INSERT INTO beer_type(name)
values ('Pilsner'),
       ('Wheat'),
       ('IPA'),
       ('Stout'),
       ('Ale'),
       ('Alcohol Free');

INSERT INTO default_product(beerTypeID, defaultSpeed, defaultAmount)
values(1, 300, 500),
       (2, 150, 500),
       (3, 75, 500),
       (4,100,500),
       (5, 50, 500),
       (6,62,500);

