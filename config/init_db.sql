create table resume
(
  uuid char(36) not null
    constraint resume_pk
      primary key,
  full_name varchar(20) not null
);

alter table resume owner to postgres;

create table contact
(
  id serial not null
    constraint contact_pk
      primary key,
  type varchar(10) not null,
  value char(20) not null,
  resume_uuid char(36) not null
    constraint contact_resume_uuid_fk
      references resume
      on delete cascade
);
create unique index if not exists contact_uuid_type_index
  on contact (resume_uuid, type);
alter table contact owner to postgres;

create table section
(
  id          serial    not null
    constraint section_pk
      primary key,
  type        char(20)  not null,
  value       char(100) not null,
  resume_uuid char(36)  not null
    constraint section_resume_uuid_fk
      references resume
      on delete cascade
);
create unique index if not exists section_uuid_type_index
  on section (resume_uuid, type);

alter table section
  owner to postgres;

