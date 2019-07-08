\echo ===== creating tables =====
-------------------------------------------------------------
create table nsi.property_type (
  id      bigserial primary key,
  code    varchar(100) not null,
  name    varchar(200) not null
);

comment on table nsi.property_type is 'Тип недвижимости';
comment on column nsi.property_type.code is 'Код недвижимости';
comment on column nsi.property_type.name is 'Наименование недвижимости';

-------------------------------------------------------------
create table nsi.insured (
  id            bigserial primary key,
  first_name    varchar(50) not null,
  last_name     varchar(100) not null,
  middle_name   varchar(100),
  birth_date    date not null,
  doc_number    integer not null,
  doc_serial    integer not null
);

comment on table nsi.insured is 'Страхователь';
comment on column nsi.insured.first_name is 'Имя';
comment on column nsi.insured.middle_name is 'Отчество';
comment on column nsi.insured.last_name is 'Фамилия';
comment on column nsi.insured.birth_date is 'Дата рождения';
comment on column nsi.insured.doc_number is 'Номер паспорта';
comment on column nsi.insured.doc_serial is 'Серия паспорта';

---------------------------------------------------------------

create table contract.contract (
  id                  bigserial primary key,
  contract_no         integer not null,
  insured_id          bigint not null,
  create_date         date not null,
  date_from           date not null,
  date_to             date not null,
  premium             double precision not null,
  property_type_id    bigint not null,
  insured_sum         double precision not null,
  square              double precision not null,
  construction_year   integer not null,
  calculation_date    date not null,
  additional_info     varchar(500),
  country             varchar(50) not null,
  region              varchar(100) not null,
  district            varchar(100),
  city                varchar(50) not null,
  street              varchar(150) not null,
  house               integer,
  apartment           integer not null,
  postcode            varchar(15),
  letter              varchar(15),
  housing             varchar(15),


  constraint contract_insured_fk foreign key (insured_id) references nsi.insured(id),
  constraint contract_property_type_fk foreign key (property_type_id) references nsi.property_type(id)
);
create unique index contract_contract_no_un on contract.contract(contract_no);

comment on table contract.contract is 'Договор';
comment on column contract.contract.contract_no is 'Номер договора';
comment on column contract.contract.insured_id is 'id страхователя';
comment on column contract.contract.date_from is 'Дата с';
comment on column contract.contract.date_to is 'Дата по';
comment on column contract.contract.create_date is 'Дата заключения';
comment on column contract.contract.premium is 'Премия';
comment on column contract.contract.insured_sum is 'Сумма страхования';
comment on column contract.contract.square is 'Площадь';
comment on column contract.contract.country is 'Страна';
comment on column contract.contract.region is 'Регион';
comment on column contract.contract.district is 'Район';
comment on column contract.contract.city is 'Населенный пункт';
comment on column contract.contract.street is 'Улица';
comment on column contract.contract.house is 'Дом';
comment on column contract.contract.postcode is 'Индекс';
comment on column contract.contract.housing is 'Корпус';
comment on column contract.contract.apartment is 'Квартира';
comment on column contract.contract.letter is 'Строение';

-------------------------------------------------------
