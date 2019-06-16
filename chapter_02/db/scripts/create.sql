create table if not exists engine(
	id serial primary key,
	name varchar(500)
);

create table if not exists transmission(
	id serial primary key,
	name varchar(500)
);

create table if not exists body_car(
	id serial primary key,
	name varchar(500)
);

create table if not exists cars(
	id serial primary key,
	name varchar(500),
	yearOfManufact timestamp,
	id_engine int references engine(id),
	id_transmission int references transmission(id),
	id_body_car int references body_car(id)
);
