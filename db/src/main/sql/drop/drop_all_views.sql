\echo ===== dropping all views ======

create temporary table drop_commands (cmd text);

insert into drop_commands
    select 'drop view if exists ' || table_schema || '.' || table_name || ' cascade;'
    from information_schema.views
    where table_catalog = :'DB_NAME'
          and table_schema not like 'pg_%'
          and table_schema <> 'information_schema';

do $$
declare
    line record;
begin
    for line in (select cmd from drop_commands) loop
        execute line.cmd;
    end loop;
end;
$$ language plpgsql;

drop table drop_commands;
