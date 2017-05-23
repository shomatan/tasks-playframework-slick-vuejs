# --- !Ups

create table tasks (
  id bigint(20) not null auto_increment,
  title varchar(255) not null,
  content text not null,
  created_at bigint not null,
  deadline_at bigint not null,
  primary key (id)
);

# --- !Downs

drop table tasks;