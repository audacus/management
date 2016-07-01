-- relation
delete from relation;
delete from sqlite_sequence where name='relation';
insert or ignore into relation (name) values ('single'), ('many');
-- thing
delete from thing;
delete from sqlite_sequence where name='thing';
insert or ignore into thing (name) values ('numberfield')
	, ('textfield')
	, ('number')
	, ('text');
-- property
delete from property;
delete from sqlite_sequence where name='property';
insert or ignore into property (name, thing, type, relation) values('value', 1, 3, 1)
	, ('value', 2, 4, 1);
