-- 1|car
-- 2|textfield
-- 3|dropdown
-- 4|numberplate
-- 5|driver
-- 6|garage
-- 7|list
-- 15|parking-lot
-- 16|number
-- 17|storey
-- 18|multi-storey-car-park

-- select thing.name, property.name, relation.name, type.name from thing inner join property on thing.id = property.thing inner join relation on property.relation = relation.id inner join thing as type on property.type = type.id;

-- management
delete from management;
delete from sqlite_sequence where name='management';
insert or ignore into management (name) values ('management');
-- relation
delete from relation;
delete from sqlite_sequence where name='relation';
insert or ignore into relation (name) values ('single'), ('many');
-- thing
delete from thing;
delete from sqlite_sequence where name='thing';
insert or ignore into thing (name) values ('car')
	, ('textfield')
	, ('dropdown')
	, ('numberplate')
	, ('driver')
	, ('garage')
	, ('list')
	, ('parking-lot')
	, ('number')
	, ('storey')
	, ('multi-storey-car-park');
-- property
delete from property;
delete from sqlite_sequence where name='property';
insert or ignore into property (name, thing, type, relation) values ('cars', 6, 1, 2)
	, ('numberplate', 1, 4, 1)
	, ('parking-lots', 6, 15, 2)
	, ('number', 15, 16, 1)
	, ('parking-lots', 18, 15, 2)
	, ('storeys', 18, 17, 2)
	, ('name', 5, 2, 1)
	, ('age', 5, 16, 1)
	, ('driving', 5, 1, 1);
