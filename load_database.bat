

docker exec oracle-db ./setPassword.sh projektpap2024

docker cp create_database.sql oracle-db:/tmp/sql_commands.sql

docker exec oracle-db sqlplus system/projektpap2024@localhost/FREEPDB1 @/tmp/sql_commands.sql

docker cp Insert_data.sql oracle-db:/tmp/sql_commands.sql

docker exec oracle-db sqlplus system/projektpap2024@localhost/FREEPDB1 @/tmp/sql_commands.sql