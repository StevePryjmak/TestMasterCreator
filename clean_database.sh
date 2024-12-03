#!/bin/bash
docker run -d --name oracle-db -p 1521:1521 container-registry.oracle.com/database/free:23.5.0.0-lite

docker exec oracle-db ./setPassword.sh projektpap2024

docker cp clean_database.sql oracle-db:/tmp/sql_commands.sql

docker exec oracle-db sqlplus system/projektpap2024@localhost/FREEPDB1 @/tmp/sql_commands.sql