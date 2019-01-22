-- CREATE USER auth WITH PASSWORD 'auth1';
-- CREATE DATABASE auth;
-- GRANT ALL PRIVILEGES ON DATABASE auth TO auth;

CREATE SCHEMA IF NOT EXISTS auth;

CREATE SEQUENCE auth.user_group_id_seq;
CREATE TABLE auth.user_group (
	name varchar(100) NOT NULL,
	id bigserial NOT NULL PRIMARY KEY
) ;

ALTER SEQUENCE auth.user_group_id_seq
OWNED BY auth.user_group.id;

CREATE SEQUENCE auth.user_id_seq;
CREATE TABLE auth.user (
	username varchar(100) NOT NULL,
	password varchar(100) NOT NULL,
	first_name varchar(100) NOT NULL,
	last_name varchar(100) NOT NULL,
	user_group_id integer REFERENCES auth.user_group (id),
	id bigserial NOT NULL PRIMARY KEY
) ;
ALTER SEQUENCE auth.user_id_seq
OWNED BY auth.user.id;

