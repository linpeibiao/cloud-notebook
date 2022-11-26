#!/bin/bash
git stash save
git pull
mvn clean package -DskipTests=true
docker stop service_note
docker stop service_user
docker rm service_note
docker rm service_user:
docker image rm service_note:v1.0.0
docker image rm service_user:v1.0.0
docker build . -f Dockerfile_note -t service_note:v1.0.0
docker build . -f Dockerfile_user -t service_user:v1.0.0
docker run -d --name service_note -p 9100:9100 service_note:v1.0.0
docker run -d --name service_user -p 9000:9000 service_user:v1.0.0