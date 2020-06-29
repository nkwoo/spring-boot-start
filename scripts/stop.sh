#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

IDLE_PORT=$(find_idle_port)

IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})

if [ -z ${IDLE_PID} ]
then
  echo "> Not working.... No More Exit Application"
else
  echo "> $IDLE_PID working Application PID Check.."
  kill -15 ${IDLE_PID}
  sleep 5
fi