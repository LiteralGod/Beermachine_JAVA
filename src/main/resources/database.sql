DROP TABLE IF EXISTS beer_type CASCADE;

create table beer_type(
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(50) UNIQUE NOT NULL
);


INSERT INTO beer_type(name)
values ('Pilsner'),
       ('Wheat'),
       ('IPA'),
       ('Stout'),
       ('Ale'),
       ('Alcohol Free');