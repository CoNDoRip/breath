# --- !Ups

INSERT INTO Level VALUES (1,  'Newbie',              'Newbie.gif');
INSERT INTO Level VALUES (2,  'Pupil',               'Pupil.png');
INSERT INTO Level VALUES (3,  'Teen',                'Teen.png');
INSERT INTO Level VALUES (4,  'Adult',               'Adult.png');
INSERT INTO Level VALUES (5,  'Expert',              'Expert.png');
INSERT INTO Level VALUES (6,  'Master',              'Master.png');
INSERT INTO Level VALUES (7,  'Professional',        'Professional.png');
INSERT INTO Level VALUES (8,  'Leader',              'Leader.png');
INSERT INTO Level VALUES (9,  'Guru',                'Guru.png');
INSERT INTO Level VALUES (10, 'Idol',                'Idol.png');
INSERT INTO Level VALUES (11, 'Higher Intelligence', 'HigherIntelligence.png');
INSERT INTO Level VALUES (12, 'God',                 'God.png');



INSERT INTO Profile (email, password, first_name, last_name, gender) 
          VALUES ('cndr.ip@gmail.com', '0aa371f7f51bd1312cef02e827f35122c46aa011', 'Idel', 'Pivnitskiy', 'M');
INSERT INTO Profile (email, password, first_name, last_name, gender) 
          VALUES ('patric@mail.ru', '0aa371f7f51bd1312cef02e827f35122c46aa011', 'Patric', 'Grey', 'M');
INSERT INTO Profile (email, password, first_name, last_name, gender) 
          VALUES ('eva@mail.ru', '0aa371f7f51bd1312cef02e827f35122c46aa011', 'Eva', 'Jones', 'F');


# --- !Downs

DELETE FROM Profile;

DELETE FROM Level;
