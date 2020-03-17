create table if not exists washing_program
(id uuid default random_uuid() primary key not null,
name varchar2(100 char) not null,
material varchar2 (50 char) not null,
temperature int (2) not null,
duration int (3) not null,

spinning_speed int (4) not null
);

alter table washing_program add constraint unique_name unique (name);