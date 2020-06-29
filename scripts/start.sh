#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

REPOSITORY=/home/ubuntu/app/step3
PROJECT_NAME=spring-boot-start

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> Make Deploy File..."

JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name : $JAR_NAME"

echo "> add Run Access"

chmod +x $JAR_NAME

echo "> Starting Application..."

IDLE_PROFILE=$(find_idle_profile)

echo "> Run boot-start of Profile = $IDLE_PROFILE"

nohup java -jar \
-Dspring.config.location=classpath:/application-$IDLE_PROFILE.yaml,\
/home/ubuntu/app/application-oauth.yaml,/home/ubuntu/app/application-real-db.yaml \
-Dspring.profiles.active=$IDLE_PROFILE $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &

