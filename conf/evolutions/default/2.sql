# --- !Ups

INSERT INTO Level VALUES (1,  'Newbie',              'Newbie.gif',             300);
INSERT INTO Level VALUES (2,  'Pupil',               'Pupil.png',              1300);
INSERT INTO Level VALUES (3,  'Teen',                'Teen.png',               3700);
INSERT INTO Level VALUES (4,  'Adult',               'Adult.png',              8500);
INSERT INTO Level VALUES (5,  'Expert',              'Expert.png',             17000);
INSERT INTO Level VALUES (6,  'Master',              'Master.png',             30800);
INSERT INTO Level VALUES (7,  'Professional',        'Professional.png',       51800);
INSERT INTO Level VALUES (8,  'Leader',              'Leader.png',             82200);
INSERT INTO Level VALUES (9,  'Guru',                'Guru.png',               124500);
INSERT INTO Level VALUES (10, 'Idol',                'Idol.png',               181500);
INSERT INTO Level VALUES (11, 'Higher Intelligence', 'HigherIntelligence.png', 256300);
INSERT INTO Level VALUES (12, 'God',                 'God.png',                352300);



INSERT INTO Profile (email, password, first_name, last_name, gender) 
          VALUES ('cndr.ip@gmail.com', '0aa371f7f51bd1312cef02e827f35122c46aa011', 'Idel', 'Pivnitskiy', 1);
INSERT INTO Profile (email, password, first_name, last_name, gender) 
          VALUES ('patric@mail.ru', '0aa371f7f51bd1312cef02e827f35122c46aa011', 'Patric', 'Grey', 1);
INSERT INTO Profile (email, password, first_name, last_name, gender) 
          VALUES ('eva@mail.ru', '0aa371f7f51bd1312cef02e827f35122c46aa011', 'Eva', 'Jones', 0);



INSERT INTO Follower (profileId, follow)
          VALUES (3, 2);



INSERT INTO Task (title, level)
          VALUES ('Write BREATH by your left hand. It should be nice', 1);
INSERT INTO Task (title, level)
          VALUES ('Cut paper pigeon', 1);
INSERT INTO Task (title, level)
          VALUES ('Run a Chinese lantern', 1);
INSERT INTO Task (title, level)
          VALUES ('Find the girl with three orange items', 2);
INSERT INTO Task (title, level)
          VALUES ('Draw a girl on the box of pizza', 2);
INSERT INTO Task (title, level)
          VALUES ('Fires sausages on the fire with your friends', 2);
INSERT INTO Task (title, level)
          VALUES ('Find the energy drink and show how it makes you fly', 3);
INSERT INTO Task (title, level)
          VALUES ('Make a photo between 2 girls with big boobs =)', 3);
INSERT INTO Task (title, level)
          VALUES ('Shake the hand of a police officer', 3);



INSERT INTO UserTask (profileId, taskId, approved, image)
          VALUES (1, 1, 1, 'task1.jpg');
INSERT INTO UserTask (profileId, taskId, approved, image)
          VALUES (1, 3, 1, 'task3.jpg');
INSERT INTO UserTask (profileId, taskId, approved, image)
          VALUES (2, 1, 0, 'task1.jpg');
INSERT INTO UserTask (profileId, taskId, approved, image)
          VALUES (2, 2, 2, 'task2.jpg');
INSERT INTO UserTask (profileId, taskId, approved, image)
          VALUES (2, 3, 1, 'task3.jpg');
INSERT INTO UserTask (profileId, taskId, approved, image)
          VALUES (3, 1, 1, 'task1.jpg');



INSERT INTO Checks (profileId, usertaskId, status)
          VALUES (1, 4, 1);
INSERT INTO Checks (profileId, usertaskId, status)
          VALUES (1, 6, 1);
INSERT INTO Checks (profileId, usertaskId, status)
          VALUES (2, 1, 1);
INSERT INTO Checks (profileId, usertaskId, status)
          VALUES (3, 2, 1);
INSERT INTO Checks (profileId, usertaskId, status)
          VALUES (3, 4, 1);
INSERT INTO Checks (profileId, usertaskId, status)
          VALUES (3, 5, 1);


-------------------------------------------------------------------------------
# --- !Downs

DELETE FROM Checks;

DELETE FROM UserTask;

DELETE FROM Task;

DELETE FROM Follower;

DELETE FROM Profile;

DELETE FROM Level;
