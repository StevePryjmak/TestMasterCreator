#!/bin/bash

# oracle stuff
docker run -d --name oracle-db -p 1521:1521 container-registry.oracle.com/database/free:23.5.0.0-lite

sleep 90

docker exec oracle-db ./setPassword.sh projektpap2024

docker cp clean_database.sql oracle-db:/tmp/sql_commands.sql

docker exec oracle-db sqlplus system/projektpap2024@localhost/FREEPDB1 @/tmp/sql_commands.sql

docker cp create_database.sql oracle-db:/tmp/sql_commands.sql

docker exec oracle-db sqlplus system/projektpap2024@localhost/FREEPDB1 @/tmp/sql_commands.sql

docker cp Insert_data.sql oracle-db:/tmp/sql_commands.sql

docker exec oracle-db sqlplus system/projektpap2024@localhost/FREEPDB1 @/tmp/sql_commands.sql

# Run Gradle build tasks
echo "Running Gradle wrapper tasks..."
if [ -f "./gradlew" ]; then
  chmod +x ./gradlew
  ./gradlew build || error_exit "Gradle build failed."
else
  error_exit "Gradle wrapper (gradlew) not found in the current directory."
fi

# Start the server and client

(./gradlew :client:run ) &
sleep 5
./gradlew :server:run 

echo "All tasks completed successfully!"
