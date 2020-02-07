

create table doctor (
  id bigint not null,
  name varchar(255),
  surname varchar(255),
  patronymic varchar(255),
  specialisation varchar(255),
  primary key (id)
);
create table patient (
  id bigint not null,
  name varchar(255),
  surname varchar(255),
  patronymic varchar(255),
  phone varchar(255),
  primary key (id)
);
create table recipe (
  id bigint not null,
  dateofcreation varchar(255),
  deadlines varchar(255),
  description varchar(2000),
  priorityr varchar(255),
  doctor_id bigint FOREIGN KEY REFERENCES doctor(id),
  patient_id bigint FOREIGN KEY REFERENCES patient(id),
  primary key (id)
);

