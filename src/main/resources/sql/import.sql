/* Populate USER_PROFILE Table */
INSERT INTO USER_PROFILE(NAME,DESCRIPTION,EDITABLE)
VALUES ('USER','USER ROLE',0);
  
INSERT INTO USER_PROFILE(NAME,DESCRIPTION,EDITABLE)
VALUES ('ADMIN','ADMIN ROLE',1);
  
INSERT INTO USER_PROFILE(NAME,DESCRIPTION,EDITABLE)
VALUES ('SALES','SALES ROLE',1);
  
  
/* Populate one Admin User which will further create other users for the application using GUI */
INSERT INTO APP_USER(sso_id, password, first_name, last_name, email)
VALUES ('admin','$2a$10$RjhSq0P9u.pW4EfgJhGj.umty3hPfZDGG3odnQ30zfz3MEfgbx6w2', 'Admin','Admin','admin@STS.com');
  
  
/* Populate JOIN Table */
INSERT INTO APP_USER_USER_PROFILE (user_id, user_profile_id)
  SELECT user_.id, profile.id FROM app_user user_, user_profile profile
  where user_.sso_id='admin' and profile.name='ADMIN';
 