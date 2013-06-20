# --- breath database schema

# --- !Ups

-------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS Level (
    id             INT             PRIMARY KEY
  , name           VARCHAR(20)     UNIQUE
  , image          VARCHAR(25)     UNIQUE
);


-------------------------------------------------------------------------------
CREATE SEQUENCE Profile_seq START WITH 1;

CREATE TABLE IF NOT EXISTS Profile (
    id             NUMERIC         PRIMARY KEY   DEFAULT nextval('Profile_seq')
  , email		       VARCHAR(30)     UNIQUE
  , password       CHAR(40)        NOT NULL      --hashsum of password
  , username       VARCHAR(30)                   --nick name or login
  , first_name     VARCHAR(30)     
  , last_name      VARCHAR(30)     
  , birthday       DATE
  , gender         CHAR(1)
  , level          INT             NOT NULL      DEFAULT 1
  , points         INT             NOT NULL      DEFAULT 0
  , completed      INT             NOT NULL      DEFAULT 0
  , todo_list      INT             NOT NULL      DEFAULT 0
  , status         VARCHAR(200)                  DEFAULT 'I like real life!'
  , avatar         VARCHAR(30)

  , CONSTRAINT Profile_Level FOREIGN KEY (level) REFERENCES Level(id)
  , CONSTRAINT Profile_gender CHECK (gender = 'M' or gender = 'F')
  , CONSTRAINT Profile_greather_then_zero CHECK (level > 0
                                            and points >= 0 
                                            and completed >= 0 
                                            and todo_list >= 0)
);

ALTER SEQUENCE Profile_seq OWNED BY Profile.id;


-------------------------------------------------------------------------------


# --- !Downs

DROP TABLE    IF EXISTS Profile;
DROP SEQUENCE IF EXISTS Profile_seq;

DROP TABLE    IF EXISTS Level;
