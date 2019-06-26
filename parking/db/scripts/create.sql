create table if not exists engines(
	id serial primary key,
	name varchar(50),
	volume real
);

create table if not exists transmissions(
	id serial primary key,
	name varchar(50)
);

create table if not exists body_cars(
	id serial primary key,
	name varchar(50)
);

create table if not exists brands(
  id serial primary key,
  name varchar(50)
);

create table if not exists persons(
    id serial primary key,
    name varchar (50),
    login varchar (50),
    email varchar (50),
    phone varchar (50),
    password varchar (50)
);

create table if not exists models(
	id serial primary key,
	name varchar(50),
	id_brand int references brands(id),
	id_engine int references engines(id),
	id_transmission int references transmissions(id),
	id_body_car int references body_cars(id)
);

create table if not exists cars(
  id serial primary key,
  year timestamp,
  mileage int,
  id_model int references models(id),
  price int
);

create table if not exists ads(
  id serial primary key,
  create_date timestamp,
  path_picture varchar(100),
  sold boolean,
  id_car int references cars(id),
  id_person int references persons(id)
);

insert into engines (name, volume) values ('1.6i', 1.6),
('2.0i', 2),
('1.4i', 1.4),
('1.6td', 1.6),
('2.5tdi', 2.5),
('3.0i', 3);

insert into transmissions (name) values ('front wheel-drive'), ('rear wheel-drive'), ('all wheel-drive');

insert into body_cars (name) values ('sedan'), ('hatchback'), ('wagon'), ('suv');

insert into brands (name) values ('Skoda'), ('Ford'), ('Kia'), ('BMW');

insert into models (name, id_brand, id_engine, id_transmission, id_body_car) values ('Octavia', 1, 2, 1, 1),
('Rapid', 1, 1, 1, 1),
('Ceed', 3, 1, 1, 2),
('Ceed SW', 3, 1, 1, 3),
('Rio', 3, 3, 1, 1),
('Rio', 3, 1, 1, 1),
('Focus', 2, 1, 1, 2),
('Explorer', 2, 5, 3, 4),
('M5', 4, 6, 2, 1),
('X1', 4, 4, 2, 2);

insert into persons (name, login, email, phone, password) values ('Test Test Test', 'test', 'test@test.test', '79068510863', 'test');

