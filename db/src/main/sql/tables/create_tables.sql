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
  middle_name   varchar(100) not null,
  birth_date    date not null,
  doc_number    bigint not null,
  doc_serial    bigint not null
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
  id          bigserial primary key,
  contract_no bigint not null ,
  insured_id  bigint not null,
  create_date date not null,
  date_from   date not null,
  date_to     date not null,
  premium     numeric,
  constraint contract_insured_fk foreign key (insured_id) references nsi.insured(id)
);
create unique index contract_contract_no_un on contract.contract(contract_no);

comment on table contract.contract is 'Договор';
comment on column contract.contract.contract_no is 'Номер договора';
comment on column contract.contract.insured_id is 'id страхователя';
comment on column contract.contract.insured_id is 'id страхователя';
comment on column contract.contract.date_from is 'Дата с';
comment on column contract.contract.date_to is 'Дата по';
comment on column contract.contract.create_date is 'Дата заключения';
comment on column contract.contract.premium is 'Премия';


-- create f
/*
------------------------------------------------------------

create table secure.admin (
    id              bigserial primary key,
    real_name       varchar(200),
    normalized_cell_phone   bigint not null
);

create unique index admin_norm_cell_phone_un on secure.admin(normalized_cell_phone);

comment on table secure.admin is 'Администраторы';
comment on column secure.admin.real_name is 'Настоящее имя (для обращения)';
comment on column secure.admin.normalized_cell_phone is 'Номер телефона в формате 79876543210';

--------------------------------------------------------------------------

create table nsi.region (
    id              bigserial primary key,
    code            varchar(100) not null,
    name            varchar(200) not null
);

comment on table nsi.region is 'Цвет';
comment on column nsi.region.code is 'Код субъекта';
comment on column nsi.region.name is 'Наименование субъекта';

------------------------------------------------------------

create table secure.customer (
    id                      bigserial primary key,
    real_name               varchar(200) not null,
    email                   varchar(200),
    normalized_cell_phone   bigint not null,
    reg_date                timestamp not null,
    city                    varchar(200) not null,
    card_number             varchar(10) not null,
    region_id               bigint not null,

    constraint customer_region_fk foreign key (region_id) references nsi.region(id)
);

create unique index customer_norm_cell_phone_un on secure.customer(normalized_cell_phone);
create unique index customer_card_number_un on secure.customer(card_number);
create index customer_region_idx on secure.customer(region_id);

comment on table secure.customer is 'Клиенты';
comment on column secure.customer.real_name is 'Настоящее имя (для обращения)';
comment on column secure.customer.email is 'Электронная почта';
comment on column secure.customer.normalized_cell_phone is 'Номер телефона в формате 79876543210';
comment on column secure.customer.reg_date is 'Дата регистрации в личном кабинете';
comment on column secure.customer.city is 'Город';
comment on column secure.customer.card_number is 'Номер Карты Королевы';
comment on column secure.customer.region_id is 'id региона';

------------------------------------------------------------

create table secure.account (
    id              bigserial primary key,
    password_hash   char(60) not null,
    admin_id        bigint,
    customer_id     bigint,

    constraint account_admin_fk foreign key (admin_id) references secure.admin(id),
    constraint account_customer_fk foreign key (customer_id) references secure.customer(id),
    constraint account_connect_check check ((admin_id is null) <> (customer_id is null))
);

comment on table secure.account is 'Аккаунты (как администраторов, так и клиентов)';
comment on column secure.account.password_hash is 'Хэш пароля (blowfish)';
comment on column secure.account.admin_id is 'id админа, если не null, то пользователь - админ';
comment on column secure.account.customer_id is 'id клиента, если не null, то пользователь - клиент';

------------------------------------------------------------

create table nsi.size (
    id              bigserial primary key,
    code            varchar(100) not null,
    name            varchar(200) not null,
    display_order   bigint,
    is_deleted      boolean default false not null
);

comment on table nsi.size is 'Размер одежды';
comment on column nsi.size.code is 'Код размера';
comment on column nsi.size.name is 'Название размера для отображения';
comment on column nsi.size.display_order is 'Порядковый номер для отображения';
comment on column nsi.size.is_deleted is 'Признак логического удаления';

------------------------------------------------------------

create table nsi.color (
    id              bigserial primary key,
    code            varchar(100) not null,
    name            varchar(200) not null,
    hex_code        char(6) not null,
    display_order   bigint,
    is_deleted      boolean default false not null
);

comment on table nsi.color is 'Цвет';
comment on column nsi.color.code is 'Код цвета';
comment on column nsi.color.name is 'Название для отображения';
comment on column nsi.color.hex_code is 'Шестнадцатеричный код цвета';
comment on column nsi.color.display_order is 'Порядковый номер для отображения';
comment on column nsi.color.is_deleted is 'Признак логического удаления';

------------------------------------------------------------

create table nsi.tailor (
    id              bigserial primary key,
    code            varchar(100) not null,
    name            varchar(200) not null,
    display_order   bigint,
    is_deleted      boolean default false not null
);

comment on table nsi.tailor is 'Пошив';
comment on column nsi.tailor.code is 'Код пошива';
comment on column nsi.tailor.name is 'Название пошива';
comment on column nsi.tailor.display_order is 'Порядковый номер для отображения';
comment on column nsi.tailor.is_deleted is 'Признак логического удаления';

------------------------------------------------------------

create table nsi.decoration (
    id              bigserial primary key,
    code            varchar(100) not null,
    name            varchar(200) not null,
    display_order   bigint,
    is_deleted      boolean default false not null
);

comment on table nsi.decoration is 'Отделка';
comment on column nsi.decoration.code is 'Код отделки';
comment on column nsi.decoration.name is 'Название отделки';
comment on column nsi.decoration.display_order is 'Порядковый номер для отображения';
comment on column nsi.tailor.is_deleted is 'Признак логического удаления';

------------------------------------------------------------

create table nsi.fabric (
    id              bigserial primary key,
    code            varchar(100) not null,
    name            varchar(200) not null,
    display_order   bigint,
    is_deleted      boolean default false not null
);

comment on table nsi.fabric is 'Ткань';
comment on column nsi.fabric.code is 'Код ткани';
comment on column nsi.fabric.name is 'Название ткани';
comment on column nsi.fabric.display_order is 'Порядковый номер для отображения';
comment on column nsi.fabric.is_deleted is 'Признак логического удаления';

------------------------------------------------------------

create table nsi.system_parameter (
    id      bigserial primary key,
    code    varchar(100) not null,
    name    varchar(200) not null,
    value   varchar(500)
);

comment on table nsi.system_parameter is 'Системный параметр';
comment on column nsi.system_parameter.code is 'Код параметра';
comment on column nsi.system_parameter.name is 'Имя параметра';
comment on column nsi.system_parameter.value is 'Значение параметра';

------------------------------------------------------------

create table nsi.integration_synonym (
    id                  bigserial primary key,
    dictionary          varchar(100) not null,
    master_code         varchar(100) not null,
    synonymous_codes    varchar(100)[] not null default array[]::varchar(100)[]
);

comment on table nsi.integration_synonym is 'Синонимы для интеграции';
comment on column nsi.integration_synonym.dictionary is 'Код словаря';
comment on column nsi.integration_synonym.master_code is 'Значение в системе';
comment on column nsi.integration_synonym.synonymous_codes is 'Массив синомов значения из выгрузки';

------------------------------------------------------------

create table nsi.integration_color_hex_code (
    id                  bigserial primary key,
    code                varchar(100) not null,
    hex_code            char(6) not null
);

comment on table nsi.integration_color_hex_code is 'Коды цветов для интеграции';
comment on column nsi.integration_color_hex_code.code is 'Код цвета из выгрузки (напр., синий)';
comment on column nsi.integration_color_hex_code.hex_code is 'Шестнадцатеричный код цвета';

------------------------------------------------------------

create table store.post (
    id              bigserial primary key,
    creator_id      bigint not null,
    create_date     timestamp not null,
    content         text not null,
    preview         text not null,
    title           text not null,
    post_type       varchar(100) not null,
    
    constraint post_creator_fk foreign key (creator_id) references secure.admin(id)
);

comment on table store.post is 'Пост';
comment on column store.post.creator_id is 'Администратор, создавший пост';
comment on column store.post.create_date is 'Дата создания поста';
comment on column store.post.title is 'Заголовок поста';
comment on column store.post.content is 'Содержание поста';
comment on column store.post.preview is 'Превью поста';
comment on column store.post.post_type is 'Тип поста';

------------------------------------------------------------

create table store.catalog (
    id              bigserial primary key,
    code            varchar(100) not null,
    name            varchar(200) not null,
    display_order   bigint,
    is_deleted      boolean default false not null
);

comment on table store.catalog is 'Каталог';
comment on column store.catalog.code is 'Код каталога';
comment on column store.catalog.name is 'Название каталога';
comment on column store.catalog.display_order is 'Порядковый номер для отображения';
comment on column store.catalog.is_deleted is 'Признак логического удаления';

------------------------------------------------------------

create table store.item (
    id                      bigserial primary key,
    code                    varchar(100) not null,
    name                    varchar(200) not null,
    catalog_id              bigint not null,
    fabric_id               bigint not null,
    tailor_id               bigint,
    decoration_id           bigint,
    description             text,
    is_prior_to_external    boolean default false not null,
    is_deleted              boolean default false not null,

    constraint item_catalog_fk foreign key (catalog_id) references store.catalog(id),
    constraint item_fabric_fk foreign key (fabric_id) references nsi.fabric(id),
    constraint item_tailor_fk foreign key (tailor_id) references nsi.tailor(id),
    constraint item_decoration_fk foreign key (decoration_id) references nsi.decoration(id)
);

create index item_catalog_idx on store.item(catalog_id);

comment on table store.item is 'Изделие (шуба/пальто/ и т.д.)';
comment on column store.item.code is 'Номенклатурный номер';
comment on column store.item.name is 'Название';
comment on column store.item.catalog_id is 'Каталог';
comment on column store.item.tailor_id is 'Пошив';
comment on column store.item.decoration_id is 'Отделка';
comment on column store.item.fabric_id is 'Материал';
comment on column store.item.description is 'Описание';
comment on column store.item.is_prior_to_external is 'Признак приоритета над загружаемыми извне';
comment on column store.item.is_deleted is 'Признак логического удаления';

------------------------------------------------------------

create table store.item_variant (
    id              bigserial primary key,
    item_id         bigint not null,
    size_id         bigint not null,
    color_id        bigint not null,

    constraint item_variant_item_fk foreign key (item_id) references store.item(id),
    constraint item_variant_size_fk foreign key (size_id) references nsi.size(id),
    constraint item_variant_color_fk foreign key (color_id) references nsi.color(id)
);

create index item_variant_item_idx on store.item_variant(item_id);
create unique index item_variant_un_idx on store.item_variant(item_id, size_id, color_id);

comment on table store.item_variant is 'Вариант изделия (с определённым цветом / размером)';
comment on column store.item_variant.item_id is 'Изделие';
comment on column store.item_variant.size_id is 'Размер';
comment on column store.item_variant.color_id is 'Цвет';

------------------------------------------------------------

create table store.item_photo (
    id              bigserial primary key,
    item_id         bigint not null,
    color_id        bigint not null,
    preview_path    varchar(200) not null,
    full_path       varchar(200) not null,
    display_order   bigint not null,

    constraint item_photo_item_fk foreign key (item_id) references store.item(id),
    constraint item_photo_color_fk foreign key (color_id) references nsi.color(id)
);

create index item_photo_item_idx on store.item_photo(item_id);

comment on table store.item_photo is 'Фотография изделия';
comment on column store.item_photo.item_id is 'Изделие';
comment on column store.item_photo.color_id is 'Цвет';
comment on column store.item_photo.preview_path is 'Путь до превью фото в хранилище';
comment on column store.item_photo.full_path is 'Путь до фото в хранилище';

------------------------------------------------------------

create table store.item_like (
    id              bigserial primary key,
    item_id         bigint not null,
    customer_id     bigint not null,

    constraint item_like_item_fk foreign key (item_id) references store.item(id),
    constraint item_like_customer_fk foreign key (customer_id) references secure.customer(id)
);

create unique index item_like_un on store.item_like(item_id, customer_id);

comment on table store.item_like is 'Изделие в избранном';
comment on column store.item_like.item_id is 'Изделие';
comment on column store.item_like.customer_id is 'Клиент';

------------------------------------------------------------

create table store.item_request (
    id                      bigserial primary key,
    item_id                 bigint not null,
    customer_id             bigint not null,
    item_request_date       timestamp not null,

    constraint item_request_item_fk foreign key (item_id) references store.item(id),
    constraint item_request_customer_fk foreign key (customer_id) references secure.customer(id)
);

comment on table store.item_request is 'Заявка на изделие';
comment on column store.item_request.item_id is 'Изделие';
comment on column store.item_request.item_request_date is 'Дата заявки на изделие';

---------------------------------------------------------------

create table store.feedback (
    id                      bigserial primary key,
    real_name               varchar(200) not null,
    email                   varchar(200),
    normalized_cell_phone   bigint not null,
    message                 text not null,
    message_date            timestamp not null,
    city                    varchar(200) not null,
    region_id               bigint not null,

    constraint feedback_region_fk foreign key (region_id) references nsi.region(id)
);

create index on store.feedback(normalized_cell_phone);
create index feedback_region_idx on store.feedback(region_id);

comment on table store.feedback is 'Сообщения от клиента';
comment on column store.feedback.real_name is 'Настоящее имя (для обращения)';
comment on column store.feedback.email is 'Электронная почта';
comment on column store.feedback.normalized_cell_phone is 'Номер телефона в формате 79876543210';
comment on column store.feedback.message is 'Текст сообщения';
comment on column store.feedback.message_date is 'Дата сообщения';
comment on column store.feedback.message_date is 'Город';
comment on column store.feedback.region_id is 'id региона';

---------------------------------------------------------------

create table store.mailing_list (
    id              bigserial primary key,
    code            varchar(100) not null,
    name            varchar(200) not null,
    address_list    text
);

create unique index mailing_list_un on store.mailing_list(code);

comment on table store.mailing_list is 'Список рассылки email';
comment on column store.mailing_list.code is 'Код списка рассылки email - new_user, new_feedback или new_request';
comment on column store.mailing_list.name is 'Название списка рассылки email';
comment on column store.mailing_list.address_list is 'Список email-адресов';

---------------------------------------------------------------

create table store.carousel_item (
    id              bigserial primary key,
    caption         varchar(100) not null,
    message         varchar(500) not null,
    picture_path    varchar(200) not null,
    post_id         bigint,
    display_order   bigint not null,

    constraint carousel_item_post_fk foreign key (post_id) references store.post(id)
);

comment on table store.carousel_item is 'Элемент карусели на главной странице';
comment on column store.carousel_item.caption is 'Заголовок';
comment on column store.carousel_item.message is 'Сообщение';
comment on column store.carousel_item.picture_path is 'Путь до картинки в хранилище';
comment on column store.carousel_item.post_id is 'Связанный пост';
comment on column store.carousel_item.display_order is 'Порядок отображения';

----------------------------------------------------------------------

create table store.view_statistics(
    id              bigserial primary key,
    item_id         bigint not null,
    color_id        bigint not null,
    counter         bigint not null,

    constraint view_statistics_item_fk foreign key (item_id) references store.item(id),
    constraint view_statistics_color_fk foreign key (color_id) references nsi.color(id)
);

create unique index view_statistics_un on store.view_statistics(item_id, color_id);

comment on table store.view_statistics is 'Статистика простора изделий';
comment on column store.view_statistics.item_id is 'Изделие';
comment on column store.view_statistics.color_id is 'Цвет';
*/
-- comment on column store.view_statistics.counter is 'Счетчик просмотров';