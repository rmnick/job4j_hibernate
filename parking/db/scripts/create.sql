create table if not exists engines(
	id serial primary key,
	name varchar(50)
);

create table if not exists transmissions(
	id serial primary key,
	name varchar(50)
);

create table if not exists body_cars(
	id serial primary key,
	name varchar(50)
);

create table if not exists persons(
    id serial primary key,
    name varchar (50),
    login varchar (50),
    date_birth timestamp,
    address varchar (100),
    phone varchar (50)
);

create table if not exists cars(
	id serial primary key,
	name varchar(50),
	yearOfManufact timestamp,
	id_engine int references engines(id),
	id_transmission int references transmissions(id),
	id_body_car int references body_cars(id),
	id_person int references persons(id)
);
