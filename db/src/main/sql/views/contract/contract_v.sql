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
    c.date_from,
    c.date_to,
    c.premium,
    c.create_date,
    c.construction_year,
    c.square,
    c.insured_sum,
    pt.id as property_type_id,
    pt.code as property_type_code,
    pt.name as property_type_name,
    c.calculation_date,
    c.additional_info,
    c.country,
    c.region,
    c.district,
    c.city,
    c.street,
    c.postcode,
    c.housing,
    c.letter,
    c.house,
    c.apartment

    from contract.contract c
    join nsi.insured i on c.insured_id = i.id
    join nsi.property_type pt on c.property_type_id = pt.id
