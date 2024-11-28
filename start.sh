#!/bin/bash
mvn clean package docker:build
docker-compose up --build
