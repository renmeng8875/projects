drop table if exists users;

/*==============================================================*/
/* Table: users                                                 */
/*==============================================================*/
create table users
(
   id                   bigint not null auto_increment,
   user_name            varchar(30),
   pwd                  varchar(60),
   nick_name            varchar(50),
   sex                  tinyint(1),
   logo                 varchar(100),
   birth_year           smallint,
   birth_month          smallint,
   birth_day            smallint,
   intro                varchar(200),
   primary key (id)
);

drop table if exists fans;

/*==============================================================*/
/* Table: fans                                                  */
/*==============================================================*/
create table fans
(
   id                   bigint not null auto_increment,
   listen_id            bigint,
   fans_id              bigint,
   time                 int,
   primary key (id)
);

drop table if exists weibos;

/*==============================================================*/
/* Table: weibos                                                */
/*==============================================================*/
create table weibos
(
   id                   bigint not null auto_increment,
   writer_id            bigint,
   content              varchar(200),
   img                  varchar(100),
   send_time            int,
   forward_id           bigint,
   primary key (id)
);

drop table if exists refer_weibo;

/*==============================================================*/
/* Table: refer_weibo                                           */
/*==============================================================*/
create table refer_weibo
(
   id                   bigint not null auto_increment,
   refer_id             bigint,
   weibo_id             bigint,
   primary key (id)
);
