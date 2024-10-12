#!/bin/bash
REPOSITORY=/home/ec2-user

cd $REPOSITORY/o2o-backend

echo "> 🔵 Remove docker services."
docker system prune --all -f



echo "> 🟢 Run new docker services."
sudo docker load -i o2o-backend.tar.gz
sudo docker-compose up -d o2o-be
sudo docker-compose up -d nginx --force-recreate
