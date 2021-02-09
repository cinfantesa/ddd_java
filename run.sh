#!/bin/bash

mvn clean install -f shared/pom.xml
mvn clean install -f user/pom.xml

docker build -t cinfantes/code-challenge user/
docker run --name agiletv-challenge -d -p8080:8080 cinfantes/code-challenge