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
create unique index UINDEX_course_name
    on `course` (`name`, `user_id`);

create table `course_user`
(
    `course_id` int           not null,
    `user_id`   int           not null,
    /*
        0 - requested
        1 - on course
        2 - finished
    */
    `status`    tinyint       not null,
    `rating`    numeric(5, 2) null,
    constraint PK_course_user
        primary key (`course_id`, `user_id`),
    constraint FK_course_user_course
        foreign key (`course_id`) references course (`id`)
            ON UPDATE cascade ON DELETE cascade,
    constraint FK_course_user_user
        foreign key (`user_id`) references user (`id`)
            ON UPDATE cascade ON DELETE cascade
);

create table `lesson`
(
    `id`         int auto_increment,
    `title`      varchar(150) not null,
    `course_id`  int          not null,
    `start_date` timestamp    not null,
    `end_date`   timestamp    not null,
    constraint PK_lesson
        primary key (`id`),
    constraint FK_lesson_group
        foreign key (`course_id`) references `course` (`id`)
            ON UPDATE cascade ON DELETE cascade
);

create table `attendance`
(
    `id`        int auto_increment,
    `lesson_id` int        not null,
    `user_id`   int        not null,
    `mark`      int,
    /*
        0 - not present,
        1 - attended
        2 - late
     */
    attended    tinyint(1) not null,
    constraint PK_attendance
        primary key (`id`),
    constraint FK_attendance_course
        foreign key (`lesson_id`) references lesson (`id`)
            ON UPDATE cascade ON DELETE cascade,
    constraint FK_attendance_student
        foreign key (`user_id`) references user (`id`)
            ON UPDATE cascade ON DELETE cascade
);
