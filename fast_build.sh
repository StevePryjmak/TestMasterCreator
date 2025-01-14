#!/bin/bash
# Run Gradle build tasks
echo "Running Gradle wrapper tasks..."
if [ -f "./gradlew" ]; then
  chmod +x ./gradlew
  ./gradlew build || error_exit "Gradle build failed."
else
  error_exit "Gradle wrapper (gradlew) not found in the current directory."
fi

# Start the server and client

/usr/bin/dbus-launch /usr/bin/gnome-terminal -- bash -ic "./gradlew :server:run; exec bash"
sleep 5
./gradlew :client:run 

echo "All tasks completed successfully!"