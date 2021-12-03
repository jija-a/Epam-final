use `online-testing`;

insert into user(id, login, first_name, last_name, password, role)
    VALUE (1, 'admin', 'admin', 'admin', 'admin', 0);

insert into question_type(id, name)
VALUES (1, 'yes/no'),
       (2, 'multiple'),
       (3, 'short'),
       (4, 'choice');

insert into course_category(id, name)
VALUES (1, 'Computer Science');
