#!/bin/bash

java 	-Dspring.profiles.active=dev \
  -Djava.net.preferIPv4Stack=true \
	-jar target/data-manager-1.0.1.jar > /dev/null 2>&1 &

sleep 2

tail -f logs/data-manager.log
