do $$
declare
    insuredId numeric;
begin

  insert into nsi.property_type(code, name)
    values ('apartment', 'Квартира'), ('house', 'Дом'), ('room', 'Комната');

  insert into nsi.insured(first_name, middle_name, last_name, birth_date, doc_number, doc_serial)
    values ('Иван', 'Петрович', 'Сидоров', '1994-01-20', 4,6 ) returning id into insuredId;

  insert into contract.contract(contract_no, insured_id, create_date, date_from, date_to, insured_sum,
  square, construction_year, premium, property_type_id, calculation_date, additional_info)
  values (123456, insuredId, '2019-07-01', '2019-07-02', '2020-06-30', 12, 45, 123, 22, 1, '2020-06-30', 'новый договор по Сидорову');
end;
$$ language plpgsql;