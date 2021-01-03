INSERT INTO user (id, username, password, active)
values (1,
'admin',
'admin',
true);

INSERT INTO roles(id, name) VALUES
(1, 'Administrator'),
(2, 'AccountHolder');

INSERT INTO user_roles(user_id, role_id) VALUES
(1,1);