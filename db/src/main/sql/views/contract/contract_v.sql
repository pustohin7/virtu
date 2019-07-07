create or replace view contract.contract_v as
  select
    c.id,
    c.contract_no,
    i.id as insured_id,
    i.doc_serial,
    i.doc_number,
    i.birth_date,
    i.first_name,
    i.last_name,
    i.middle_name,
    concat_ws(' ', i.last_name, i.first_name, i.middle_name) as insured_fio,
    concat_ws('-', to_char(c.date_from, 'DD.MM.YYYY'), to_char(c.date_to, 'DD.MM.YYYY')) as validaty,
    c.premium,
--     to_char(c.create_date, 'DD.MM.YYYY') as create_date
    c.create_date
    from contract.contract c
    join nsi.insured i on c.insured_id = i.id
