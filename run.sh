#!/bin/bash
git stash save
git pull
mvn clean package -DskipTests=true
docker stop service-note
docker stop service-user
docker rm service-note
docker rm service-user:
docker image rm service-note:v1.0.0
docker image rm service-user:v1.0.0
docker build . -f Dockerfile_note -t service-note:v1.0.0
docker build . -f Dockerfile_user -t service-user:v1.0.0
docker run -d --name service-note -p 9100:9100 service-note:v1.0.0
docker run -d --name service-user -p 9000:9000 service-user:v1.0.0