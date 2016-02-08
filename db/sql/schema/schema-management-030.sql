-- drop table if exists thing;
create table if not exists thing (
	id integer primary key autoincrement,
	name varchar unique
);

-- drop table if exists relation;
create table if not exists relation (
	id integer primary key autoincrement,
	name varchar unique
);

-- drop table if exists property;
create table if not exists property (
	id integer primary key autoincrement,
	name varchar not null,
	thing integer not null,
	type integer not null,
	relation integer not null,
	unique (name, thing) on conflict ignore,
	foreign key(thing) references thing(id),
	foreign key(type) references thing(id),
	foreign key(relation) references relation(id)
);

-- drop table if exists management;
create table if not exists management (
	id integer primary key autoincrement,
	name varchar unique
);

-- drop table if exists instance;
create table if not exists instance (
	id integer primary key autoincrement,
	management integer not null,
	thing integer not null,
	foreign key(management) references management(id),
	foreign key(thing) references thing(id)
);

-- drop table if exists value;
create table if not exists value (
	id integer primary key autoincrement,
	instance integer not null,
	property integer not null,
	value integer not null,
	foreign key(instance) references instance(id),
	foreign key(property) references property(id)
);
