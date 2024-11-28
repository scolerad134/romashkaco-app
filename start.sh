#!/bin/bash
mvn clean package
chmod +x target/romashkaco-app-0.0.1-SNAPSHOT.jar
java -jar target/romashkaco-app-0.0.1-SNAPSHOT.jar
