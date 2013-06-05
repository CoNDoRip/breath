# --- !Ups

INSERT INTO User_info (email, password, first_name, last_name, gender) 
          VALUES ('cndr.ip@gmail.com', 'password', 'Idel', 'Pivnitskiy', 'M');
INSERT INTO User_info (email, password, first_name, last_name, gender) 
          VALUES ('artyom.keydunov@gmail.com', 'password', 'Artyom', 'Keydunov', 'M');
INSERT INTO User_info (email, password, first_name, last_name, gender) 
          VALUES ('shch.dima@yandex.ru', 'password', 'Dmitry', 'Shchetinin', 'M');


# --- !Downs

DELETE FROM User_info;
