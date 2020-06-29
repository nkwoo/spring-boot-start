#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh
source ${ABSDIR}/switch.sh

IDLE_PORT=$(find_idle_port)

echo "> Health Check Start"
echo "> IDLE_PORT: $IDLE_PORT"
ech "> curl -s http://localhost:${IDLE_PORT}/profile "
sleep 10

for RETRY_COUNT in {1..10}
do
  RESPONSE=$(curl -s http://localhost:${IDLE_PORT}/profile)
  UP_COUNT=$(echo ${RESPONSE} | grep 'real' | wc -l)

  # Check "real" in Response

  if [ ${UP_COUNT} -ge 1]
  then
    echo "> Health Check Success"
    switch_proxy
    break
  else
    echo "> Health Check Not Running OR No Response"
    echo "> Please Check Application : ${RESPONSE}"
  fi

  if [ ${RETRY_COUNT} -eq 10 ]
  then
    echo "> Health Check Fail"
    echo "> Exit Deploy And Nginx Connect"
    exit 1
  fi

  echo "> Health Check Connect Fail.... Retry.."
  sleep 10
done


