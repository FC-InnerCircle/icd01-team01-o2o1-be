#!/bin/bash
REPOSITORY=/home/ec2-user

cd $REPOSITORY/o2o-backend

#echo "> 🔵 Stop & Remove docker services."
#cd ..
#docker compose down

echo "> 🟢 Run new docker services."
docker-compose up --build -d o2o-be
docker-compose up -d nginx --force-recreate
