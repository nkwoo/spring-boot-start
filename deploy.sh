#!/bin/bash

REPOSITORY=/home/ubuntu/app/step1
PROJECT_NAME=boot-start

cd $REPOSITORY/$PROJECT_NAME/

echo "> Git pull"

git pull

echo "> Project Build Start"

./gradlew build

echo "> step1 Directory Move"

cd $REPOSITORY

echo "> Build File Copy"

cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/

echo "> Now Working Application pid Check..."

CURRENT_PID=$(pgrep -f ${PROJECT_NAME}*.jar)

if [ -z "$CURRENT_PID" ]; then
  echo "> No Search Application. The Application Will Start Soon.."
else
  echo "> Now Working Application pid : $CURRENT_PID"
  echo "> Process Killing...."

  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> Make Deploy File..."

JAR_NAME=$(ls -tr $REPOSITORY/ | grep *.jar | tail -n 1)

echo "> JAR Name : $JAR_NAME"

echo "> Starting Application..."

nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &

