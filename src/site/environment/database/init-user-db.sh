#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    
    CREATE USER javaeesamples WITH PASSWORD '@jeedeve17';

    CREATE DATABASE javaeesamples;
    
    GRANT ALL PRIVILEGES ON DATABASE javaeesamples TO javaeesamples;
    
    CREATE USER javaeesamplestest WITH PASSWORD '@jeetest17';
    
    CREATE DATABASE javaeesamplestest;
    
    GRANT ALL PRIVILEGES ON DATABASE javaeesamplestest TO javaeesamplestest;

EOSQL
