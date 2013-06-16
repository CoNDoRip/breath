# --- !Ups

INSERT INTO Profile (email, password, first_name, last_name, gender) 
          VALUES ('cndr.ip@gmail.com', '0aa371f7f51bd1312cef02e827f35122c46aa011', 'Idel', 'Pivnitskiy', 'M');
INSERT INTO Profile (email, password, first_name, last_name, gender) 
          VALUES ('patric@mail.ru', '0aa371f7f51bd1312cef02e827f35122c46aa011', 'Patric', 'Grey', 'M');
INSERT INTO Profile (email, password, first_name, last_name, gender) 
          VALUES ('eva@mail.ru', '0aa371f7f51bd1312cef02e827f35122c46aa011', 'Eva', 'Jones', 'F');


# --- !Downs

DELETE FROM Profile;
