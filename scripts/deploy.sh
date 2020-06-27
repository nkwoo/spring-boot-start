#!/bin/bash

REPOSITORY=/home/ubuntu/app/step2
PROJECT_NAME=spring-boot-start

echo "> Build File Copy"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> Now Working Application pid Check..."

CURRENT_PID=$(pgrep -fl spring-boot-start | grep jar | awk '{print $1}')

if [ -z "$CURRENT_PID" ]; then
  echo "> No Search Application. The Application Will Start Soon.."
else
  echo "> Now Working Application pid : $CURRENT_PID"
  echo "> Process Killing...."

  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> Make Deploy File..."

JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name : $JAR_NAME"

echo "> add Run Access"

chmod +x $JAR_NAME

echo "> Starting Application..."

nohup java -jar -Dspring.config.location=classpath:/application-real.yaml,/home/ubuntu/app/application-oauth.yaml,/home/ubuntu/app/application-real-db.yaml -Dspring.profiles.active=real $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &

