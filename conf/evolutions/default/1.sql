# --- breath database schema

# --- !Ups

CREATE SEQUENCE User_info_seq START WITH 1;

CREATE TABLE User_info (
   id             NUMERIC         PRIMARY KEY    DEFAULT nextval('User_info_seq')
  ,email		      VARCHAR(30)     NOT NULL
  ,password       CHAR(30)        NOT NULL       --hashsum of password
  ,username       VARCHAR(30)                    --nick name or login
  ,first_name     VARCHAR(30)     
  ,last_name      VARCHAR(30)     
  ,birthday       DATE
  ,gender         CHAR(1)
  ,level          INT             NOT NULL DEFAULT 1
  ,points         INT             NOT NULL DEFAULT 0
  ,completed      INT             NOT NULL DEFAULT 0
  ,todo_list      INT             NOT NULL DEFAULT 0
  ,status         VARCHAR(200)    DEFAULT 'I like real life!'

  ,CONSTRAINT User_gender CHECK (gender = 'M' or gender = 'F')
  ,CONSTRAINT User_greather_then_zero CHECK (level > 0 and points >= 0 and completed >= 0 and todo_list >= 0)
);

ALTER SEQUENCE User_info_seq OWNED BY User_info.id;

CREATE INDEX ix_user_info_email   ON User_info(email);


# --- !Downs

DROP TABLE IF EXISTS User_info;

DROP SEQUENCE IF EXISTS User_info_seq;
