#!/bin/bash

java -Dspring.profiles.active=dev \
  -Djava.net.preferIPv4Stack=true \
	-jar target/datacore-usertool-2.1.1.jar > /dev/null 2>&1 &

sleep 2

tail -f logs/datacore-usertool.log
