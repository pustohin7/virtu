\echo ===== setting up connection =====

-- Адрес БД
\set HOST localhost
\set PORT 5432

-- БД для приложения
\set DB_NAME virtu
\set DB_USER virtu
\set DB_PASSWORD virtu

-- Суперюзер, под которым все будет выполняться
-- Все эти параметры передаются из командной строки
\set SU_DB_NAME :SU_DB_NAME
\set SU_USER :SU_USER
\set SU_PASSWORD :SU_PASSWORD