-- Сначала заполняем тестовые каталоги изделий и сами изделия
do $$
declare
    insuredId numeric;
begin
  insert into nsi.insured(first_name,middle_name, last_name, birth_date, doc_number, doc_serial)
    values ('Иван', 'Петрович', 'Сидоров', '1994-01-20', 4,6 ) returning id into insuredId;
  insert into contract.contract(contract_no, insured_id, create_date, date_from, date_to, premium)
  values (123456, insuredId, '2019-07-01', '2019-07-01', '2020-06-30', 2402);
end;
$$ language plpgsql;