# --- !Ups

INSERT INTO Profile (email, password, first_name, last_name, gender) 
          VALUES ('cndr.ip@gmail.com', 'password', 'Idel', 'Pivnitskiy', 'M');
INSERT INTO Profile (email, password, first_name, last_name, gender) 
          VALUES ('patric@mail.ru', 'password', 'Patric', 'Grey', 'M');
INSERT INTO Profile (email, password, first_name, last_name, gender) 
          VALUES ('eva@mail.ru', 'password', 'Eva', 'Jones', 'F');


# --- !Downs

DELETE FROM Profile;
