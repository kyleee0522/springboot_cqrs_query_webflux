-- Create sequence if not exists
create sequence if not exists hibernate_sequence;

-- Create table for employees
create table if not exists employee (
    id              bigint      not null,
    empName         varchar(255),
    empDeptName     varchar(255),
    empTelNo        varchar(20),
    empMail         varchar(25),
    primary key (id)
);

alter table employee add constraint employee_pk primary key (id);
