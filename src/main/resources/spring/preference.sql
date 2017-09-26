create table "public".pref
(
	prefid INT4 not null,
	prefemailaddress VARCHAR(30) not null,
	discriminator VARCHAR(255) not null,
	preftype VARCHAR(20) not null,
	prefname VARCHAR(30) not null,
	prefvalue VARCHAR(30) not null,
	prefdescription VARCHAR(50) not null,
	primary key (prefid, prefemailaddress)
)
