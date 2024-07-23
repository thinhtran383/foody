-- remove contraint
alter table users drop constraint unique_userName;

alter table users add constraint unique_userName unique(userName);