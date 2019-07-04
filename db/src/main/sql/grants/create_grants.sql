\echo ===== granting access to :DB_USER =====

grant usage on schema contract to :DB_USER;
grant all privileges on all tables in schema contract to :DB_USER;
grant all privileges on all sequences in schema contract to :DB_USER;

grant usage on schema nsi to :DB_USER;
grant all privileges on all tables in schema nsi to :DB_USER;
grant all privileges on all sequences in schema nsi to :DB_USER;