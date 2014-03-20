# --- breath database schema

# --- !Ups

-------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS Level (
    id              INT             PRIMARY KEY
  , name            VARCHAR(20)     UNIQUE
  , image           VARCHAR(25)     UNIQUE
  , needToNextLevel INT             UNIQUE
  , CONSTRAINT Level_greather_then_zero CHECK (id > 0 and needToNextLevel > 0)
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
  , gender         INT
  , level          INT             NOT NULL      DEFAULT 1
  , points         INT             NOT NULL      DEFAULT 0
  , completed      INT             NOT NULL      DEFAULT 0
  , todo_list      INT             NOT NULL      DEFAULT 0
  , status         VARCHAR(200)                  DEFAULT 'I like real life!'
  , avatar         VARCHAR(30)

  , CONSTRAINT Profile_Level FOREIGN KEY (level) REFERENCES Level(id)
  , CONSTRAINT Profile_gender CHECK (gender = 1 or gender = 0)
  , CONSTRAINT Profile_greather_then_zero CHECK (points >= 0 
                                            and completed >= 0 
                                            and todo_list >= 0)
);

ALTER SEQUENCE Profile_seq OWNED BY Profile.id;


-------------------------------------------------------------------------------
CREATE SEQUENCE Follower_seq START WITH 1;

CREATE TABLE Follower (
    id             NUMERIC         PRIMARY KEY   DEFAULT nextval('Follower_seq')
  , profileId      NUMERIC         NOT NULL
  , follow         NUMERIC         NOT NULL

  , CONSTRAINT Follower_profileId FOREIGN KEY (profileId) REFERENCES Profile(id)
  , CONSTRAINT Follower_follow    FOREIGN KEY (follow)    REFERENCES Profile(id)
  , CONSTRAINT Follower_unique    UNIQUE (profileId, follow)
  , CONSTRAINT Follower_different CHECK (profileId != follow)
);

ALTER SEQUENCE Follower_seq OWNED BY Follower.id;


-------------------------------------------------------------------------------
CREATE SEQUENCE Task_seq START WITH 1;

CREATE TABLE IF NOT EXISTS Task (
    id             NUMERIC         PRIMARY KEY   DEFAULT nextval('Task_seq')
  , title          VARCHAR(200)    NOT NULL
  , level          INT             NOT NULL
  , datetime       DATE            NOT NULL      DEFAULT CURRENT_DATE
  , liked          INT             NOT NULL      DEFAULT 0
  , disliked       INT             NOT NULL      DEFAULT 0

  , CONSTRAINT Task_Level FOREIGN KEY (level) REFERENCES Level(id)
  , CONSTRAINT Task_datetime CHECK (datetime < CURRENT_DATE + 366)
  , CONSTRAINT Task_greather_then_zero CHECK (liked >= 0 
                                          and disliked >= 0)
);

ALTER SEQUENCE Task_seq OWNED BY Task.id;


-------------------------------------------------------------------------------
CREATE TYPE UserTask_status AS ENUM ('rejected', 'pending', 'approved');

CREATE SEQUENCE UserTask_seq START WITH 1;

CREATE TABLE IF NOT EXISTS UserTask (
    id             NUMERIC         PRIMARY KEY   DEFAULT nextval('UserTask_seq')
  , profileId      NUMERIC         NOT NULL
  , taskId         NUMERIC         NOT NULL
  , datetime       DATE            NOT NULL      DEFAULT CURRENT_DATE
  , approved       INT             NOT NULL      DEFAULT 0
  , rejected       INT             NOT NULL      DEFAULT 0
  , status         VARCHAR(100)    NOT NULL      DEFAULT 'pending'
  , image          VARCHAR(100)    NOT NULL
  , liked          INT             NOT NULL      DEFAULT 0

  , CONSTRAINT UserTask_profileId FOREIGN KEY (profileId) REFERENCES Profile(id)
  , CONSTRAINT UserTask_taskId    FOREIGN KEY (taskId)    REFERENCES Task(id)
  , CONSTRAINT UserTask_unique    UNIQUE (profileId, taskId)
  , CONSTRAINT UserTask_greather_then_zero CHECK (liked >= 0
                                              and approved >= 0
                                              and rejected >= 0)
);

ALTER SEQUENCE UserTask_seq OWNED BY UserTask.id;


-------------------------------------------------------------------------------
CREATE SEQUENCE Checks_seq START WITH 1;

CREATE TABLE Checks (
    id             NUMERIC         PRIMARY KEY   DEFAULT nextval('Checks_seq')
  , profileId      NUMERIC         NOT NULL
  , usertaskId     NUMERIC         NOT NULL
  , status         INT             NOT NULL
  , datetime       DATE            NOT NULL      DEFAULT CURRENT_DATE

  , CONSTRAINT Checks_unique     UNIQUE (profileId, usertaskId)
  , CONSTRAINT Checks_profileId  FOREIGN KEY (profileId)  REFERENCES Profile(id)
  , CONSTRAINT Checks_usertaskId FOREIGN KEY (usertaskId) REFERENCES UserTask(id)
  , CONSTRAINT Checks_status CHECK (status = -1 or status = 1)
);

ALTER SEQUENCE Checks_seq OWNED BY Checks.id;


-------------------------------------------------------------------------------
# --- !Downs

DROP TABLE IF EXISTS Checks;

DROP TABLE IF EXISTS UserTask;
DROP TYPE  IF EXISTS UserTask_status;

DROP TABLE IF EXISTS Task;

DROP TABLE IF EXISTS Follower;

DROP TABLE IF EXISTS Profile;

DROP TABLE IF EXISTS Level;
