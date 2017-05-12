# --- !Ups

create table todos (
  id bigint(20) not null auto_increment,
  title varchar(255) not null,
  created_at datetime not null default current_timestamp,
  primary key (id)
);

# --- !Downs

drop table todos;