\ir define.sql

---
\connect :SU_DB_NAME :SU_USER :HOST :PORT

\encoding UTF8
\set client_encoding to 'UTF8';

\ir drop//drop_all.sql
\ir init//create_db.sql

---
\connect :DB_NAME :SU_USER :HOST :PORT

\encoding UTF8
\set client_encoding to 'UTF8';

\ir init//create_extensions.sql
\ir schemas//create_schemas.sql
\ir tables//create_tables.sql
\ir data//_fill_all_data.sql

\ir views//_create_all_views.sql
\ir grants//create_grants.sql

---
\echo ===== finished =====