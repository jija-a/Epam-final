use `online-testing`;

create table `user`
(
    `id`         int auto_increment,
    `login`      varchar(255) not null,
    `first_name` varchar(35)  not null,
    `last_name`  varchar(35)  not null,
    `password`   nchar(128)   not null,
    /*
      0-admin,
      1-teacher,
      2-student
    */
    `role`       tinyint(1)   not null,
    constraint PK_user
        primary key (`id`)
);
create unique index UINDEX_user_login
    on `user` (`login`);

create table `course_category`
(
    `id`   int auto_increment,
    `name` varchar(128) not null,
    constraint PK_course_category
        primary key (`id`)
);
create unique index UINDEX_course_category_name
    on `course_category` (`name`);

create table `course`
(
    `id`                 int auto_increment,
    `name`               varchar(150) not null,
    `user_id`            int          not null,
    `course_category_id` int          not null,
    constraint PK_course
        primary key (`id`),
    constraint FK_course_teacher
        foreign key (`user_id`) references user (`id`)
            ON UPDATE cascade ON DELETE cascade,
    constraint FK_course_course_category
        foreign key (`course_category_id`) references course_category (`id`)
            ON UPDATE cascade ON DELETE cascade
);

create table `course_user`
(
    `course_id` int     not null,
    `user_id`   int     not null,
    /*
        0 - requested
        1 - on course
    */
    `status`    tinyint not null,
    constraint PK_course_user
        primary key (`course_id`, `user_id`),
    constraint FK_course_user_course
        foreign key (`course_id`) references course (`id`)
            ON UPDATE cascade ON DELETE cascade,
    constraint FK_course_user_user
        foreign key (`user_id`) references user (`id`)
            ON UPDATE cascade ON DELETE cascade
);

create table `test`
(
    `id`             int auto_increment,
    `title`          varchar(150) not null,
    `course_id`      int          not null,
    `attempts`       int          not null,
    `start_date`     datetime     not null,
    `end_date`       datetime     not null,
    `time_to_answer` int          not null,
    `max_score`      int          not null,
    constraint PK_test
        primary key (`id`),
    constraint FK_test_user
        foreign key (`course_id`) references course (`id`)
            ON UPDATE cascade ON DELETE cascade
);

create table question_type
(
    `id`   tinyint auto_increment,
    `name` varchar(32) not null,
    constraint PK_question_type
        primary key (`id`)
);
create unique index UINDEX_question_type_name
    on `question_type` (`name`);

create table question
(
    `id`               int auto_increment,
    `question_type_id` tinyint      not null,
    `test_id`          int          not null,
    `title`            varchar(255) not null,
    `body`             varchar(1024),
    `points`           int          not null,
    constraint PK_question
        primary key (id),
    constraint FK_question_question_type
        foreign key (`question_type_id`) references question_type (`id`)
            ON UPDATE cascade ON DELETE cascade,
    constraint FK_question_test
        foreign key (`test_id`) references test (`id`)
            ON UPDATE cascade ON DELETE cascade
);

create table `answer`
(
    `id`          int auto_increment,
    `title`       varchar(1024) not null,
    `is_right`    tinyint(1)    not null,
    `percent`     decimal(5, 2) null,
    `question_id` int           not null,
    constraint PK_answer
        primary key (`id`),
    constraint FK_answer_question
        foreign key (`question_id`) references question (`id`)
            ON UPDATE cascade ON DELETE cascade
);

/*create table `test_question`
(
    `question_id` int   not null,
    `test_id`     int   not null,
    `score`       float not null,
    constraint PK_test_question
        primary key (`question_id`, `test_id`),
    constraint FK_test_question_question
        foreign key (`question_id`) references question (`id`)
            ON UPDATE cascade ON DELETE cascade,
    constraint FK_test_question_test
        foreign key (`test_id`) references test (`id`)
            ON UPDATE cascade ON DELETE cascade
);*/

create table `test_result`
(
    `id`           int auto_increment,
    `test_id`      int           not null,
    `user_id`      int           not null,
    `percent`      decimal(5, 2) not null,
    `test_started` datetime      not null,
    `test_ended`   datetime      not null,
    constraint PK_test_result
        primary key (`id`),
    constraint FK_test_result_user
        foreign key (`user_id`) references user (`id`)
            ON UPDATE cascade ON DELETE cascade,
    constraint FK_test_result_test
        foreign key (`test_id`) references test (`id`)
            ON UPDATE cascade ON DELETE cascade
);

create table `user_test_answer`
(
    `test_result_id` int     not null,
    `answer_id`      int     not null,
    `is_selected`    boolean not null,
    constraint FK_user_test_answer_answer
        foreign key (`answer_id`) references answer (`id`)
            ON UPDATE cascade ON DELETE cascade,
    constraint FK_user_test_answer_user
        foreign key (`test_result_id`) references test_result (`id`)
            ON UPDATE cascade ON DELETE cascade
);
