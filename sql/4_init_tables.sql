use `online-testing`;

insert into user(id, login, first_name, last_name, password, role)
VALUES (1, 'admin', 'admin', 'admin', 'admin', 0);

INSERT INTO course_category(id, name)
VALUES (1, 'Computer Science'),
       (2, 'Physics');

INSERT INTO `user`(id, login, first_name, last_name, password, role)
VALUES (2, 'bboyse0', 'Malorie', 'Revie',
        '$s0$41010$8M89tDuPkHLWEzelh7zkHQ==$7KL+cjOHkWTL+g6Sp9G8DTUzYDm2c4YuxdBWqIHw8RA=', 0), /*xMbu9AgEC96*/
       (3, 'ptruran1', 'Paddie', 'Truran',
        '$s0$41010$eGXQvJODDU65gd/V0MuzIw==$dFc4AgNfyYJcEG8Lh0FofT8Xws9mre5+OBn7wvhIR3o=', 1), /*Zy53Ut9ONqC5*/
       (4, 'etowner2', 'Emmalynne', 'Towner',
        '$s0$41010$5wMEl6aEXL2iqRvbdG4WdA==$lI6Acm/dRjpkSj58azc3maDY42Qb/JJEoF+HL36KgEI=', 0), /*EENwjJQ*/
       (5, 'mbennedsen3', 'Milo', 'Bennedsen',
        '$s0$41010$UeNVykIVrqfMlNgtp8msPA==$3duZsgE7I0aC2ja1ChSpU54X9ySc2lh9fGp2lqsW9G0=', 2), /*aAdEFvA1*/
       (6, 'vsterley4', 'Vanda', 'Sterley',
        '$s0$41010$RHM5Jh6J5+ATI6gfJgs1zw==$vs9LxswvIlthN83xtGmJR+bjZOLcWS5d1OXPrlNTHzU=', 2), /*3ZpIRPKpOp*/
       (7, 'mweich5', 'Markus', 'Weich',
        '$s0$41010$dE56AO0/LS/o0O8EB8YaMQ==$1RwdrdumIhOt14NNcvuO2bITPWpCl40tmLnWZKjnAfo=', 2), /*xLBih71YqjN*/
       (8, 'bbenettini6', 'Brittney', 'Benettini',
        '$s0$41010$lVPXEQhlj95n2jgpo3D79Q==$vYdT8cdiKF9uA0yT6xvNOYMR/19kLe7FgdMMRdWxXdQ=', 2), /*Wuz4jpUfG*/
       (9, 'clevene7', 'Clywd', 'Levene',
        '$s0$41010$741iT9yVj0nE04uHIKDLSA==$ZXDbHyX/qity50kVvMVn2mWYRL81KK6zxt4JQknlKMs=', 2), /*2FVBZAr22*/
       (10, 'rbineham8', 'Rickey', 'Bineham',
        '$s0$41010$nsTwo99q3ab2DxK8yn4Olw==$CsPqLZDQa93puB/V5XV95tzsIGtQN1lDy8bjxWO1mhU=', 2), /*cKa9d0mE*/
       (11, 'menticknap9', 'Michale', 'Enticknap',
        '$s0$41010$eP91FLISrqlUV+7TRUhS8g==$VJsZI7V8iMTQ/xrHN0TKPR1SdHmk3f995VBM7kJSKBY=', 2); /*7bUcWR7vrk2p*/

INSERT INTO `course`(id, name, user_id, course_category_id)
VALUES (1, 'Физика 10 класс', 3, 2),
       (2, 'Информатика 7 класс 167 школа', 4, 1),
       (3, 'Физика 9 класс', 3, 2);

INSERT INTO course_user(course_id, user_id, status, rating)
VALUES (1, 1, 0, null),
       (1, 2, 1, null),
       (1, 3, 2, 8),
       (1, 4, 2, 3),
       (1, 5, 2, 6),
       (1, 6, 2, 3),
       (2, 5, 0, null),
       (2, 6, 0, null),
       (2, 7, 0, null),
       (2, 8, 0, null),
       (2, 9, 0, null),
       (2, 10, 0, null),
       (2, 11, 0, null);

INSERT INTO `lesson` (id, title, course_id, start_date, end_date)
VALUES (1, 'OOP', 1, '2021-11-26 19:02:26', '2021-11-26 21:30:26'),
       (2, 'Patterns', 2, '2021-11-30 19:08:26', '2021-11-30 21:15:26'),
       (3, 'Collections', 3, '2021-12-02 19:04:07', '2021-12-02 21:00:26'),
       (4, 'Collections', 1, '2021-11-26 19:05:02', '2021-11-26 20:15:26'),
       (5, 'OOP', 1, '2021-11-26 19:22:22', '2021-11-26 21:20:41'),
       (6, 'OOP', 2, '2021-11-26 19:12:30', '2021-11-26 21:10:46'),
       (7, 'Java 8', 3, '2021-11-26 18:55:26', '2021-11-26 21:40:15');

INSERT INTO `attendance` (id, lesson_id, user_id, mark, attended)
VALUES (1, 1, 1, 5, true),
       (2, 1, 2, null, true),
       (3, 1, 3, null, true),
       (4, 1, 4, null, false),
       (5, 1, 5, 8, true),
       (6, 2, 6, 9, true),
       (7, 2, 7, null, false),
       (8, 2, 8, 4, true),
       (9, 2, 9, 3, true),
       (10, 2, 10, 0, true);
