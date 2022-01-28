use `online-testing`;

INSERT INTO `user`(id, login, first_name, last_name, password, role)
VALUES (1, 'bboyse0', 'Malorie', 'Revie',
        '$s0$41010$8M89tDuPkHLWEzelh7zkHQ==$7KL+cjOHkWTL+g6Sp9G8DTUzYDm2c4YuxdBWqIHw8RA=', 0); /*xMbu9AgEC96*/

INSERT INTO course_category(id, title)
VALUES (1, 'Информационные технологии'),
       (2, 'Иностранные языки'),
       (3, 'Бизнес'),
       (4, 'Продажи'),
       (5, 'Маркетинг и реклама'),
       (6, 'Финансы, экономика'),
       (7, 'Красота и здоровье'),
       (8, 'Развитие личности'),
       (9, 'Дизайн'),
       (10, 'Творчество и искусство'),
       (11, 'Мода'),
       (12, 'Разработка игр');

/* Test data */
INSERT INTO `user`(id, login, first_name, last_name, password, role)
VALUES (2, 'ptruran1', 'Paddie', 'Truran',
        '$s0$41010$eGXQvJODDU65gd/V0MuzIw==$dFc4AgNfyYJcEG8Lh0FofT8Xws9mre5+OBn7wvhIR3o=', 1), /*Zy53Ut9ONqC5*/
       (3, 'etowner2', 'Emmalynne', 'Towner',
        '$s0$41010$5wMEl6aEXL2iqRvbdG4WdA==$lI6Acm/dRjpkSj58azc3maDY42Qb/JJEoF+HL36KgEI=', 2), /*EENwjJQ*/
       (4, 'mbennedsen3', 'Milo', 'Bennedsen',
        '$s0$41010$UeNVykIVrqfMlNgtp8msPA==$3duZsgE7I0aC2ja1ChSpU54X9ySc2lh9fGp2lqsW9G0=', 2), /*aAdEFvA1*/
       (5, 'vsterley4', 'Vanda', 'Sterley',
        '$s0$41010$RHM5Jh6J5+ATI6gfJgs1zw==$vs9LxswvIlthN83xtGmJR+bjZOLcWS5d1OXPrlNTHzU=', 2), /*3ZpIRPKpOp*/
       (6, 'mweich5', 'Markus', 'Weich',
        '$s0$41010$dE56AO0/LS/o0O8EB8YaMQ==$1RwdrdumIhOt14NNcvuO2bITPWpCl40tmLnWZKjnAfo=', 2), /*xLBih71YqjN*/
       (7, 'bbenettini6', 'Brittney', 'Benettini',
        '$s0$41010$lVPXEQhlj95n2jgpo3D79Q==$vYdT8cdiKF9uA0yT6xvNOYMR/19kLe7FgdMMRdWxXdQ=', 2), /*Wuz4jpUfG*/
       (8, 'clevene7', 'Clywd', 'Levene',
        '$s0$41010$741iT9yVj0nE04uHIKDLSA==$ZXDbHyX/qity50kVvMVn2mWYRL81KK6zxt4JQknlKMs=', 2), /*2FVBZAr22*/
       (9, 'rbineham8', 'Rickey', 'Bineham',
        '$s0$41010$nsTwo99q3ab2DxK8yn4Olw==$CsPqLZDQa93puB/V5XV95tzsIGtQN1lDy8bjxWO1mhU=', 2), /*cKa9d0mE*/
       (10, 'menticknap9', 'Michale', 'Enticknap',
        '$s0$41010$eP91FLISrqlUV+7TRUhS8g==$VJsZI7V8iMTQ/xrHN0TKPR1SdHmk3f995VBM7kJSKBY=', 2); /*7bUcWR7vrk2p*/

INSERT INTO course(id, title, user_id, course_category_id)
VALUES (1, 'Информатика', 2, 1),
       (2, 'Английский', 2, 2),
       (3, 'C++', 2, 12),
       (4, 'Экономика', 2, 6),
       (5, 'Photoshop', 2, 9),
       (6, 'Java Core', 2, 1);

INSERT INTO course_user(course_id, user_id, status, rating, attendance_percent)
VALUES (6, 3, 1, 9, 100),
       (6, 4, 1, 6, 100),
       (6, 5, 1, 7, 100),
       (6, 6, 1, null, 100),
       (6, 7, 1, null, 0),
       (6, 8, 1, 10, 100);

INSERT INTO lesson(id, title, course_id, start_date, end_date)
VALUES (1, 'OOP Basics', 6, '2021-11-25 19:02', '2021-11-25 21:14');

INSERT INTO attendance(id, lesson_id, user_id, mark, attended)
VALUES (1, 1, 3, 9, true),
       (2, 1, 4, 6, true),
       (3, 1, 5, 7, true),
       (4, 1, 6, null, true),
       (5, 1, 7, null, false),
       (6, 1, 3, 10, true);
