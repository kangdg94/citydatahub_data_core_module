#!/bin/bash

java 	-Dspring.profiles.active=dev \
	-Djava.net.preferIPv4Stack=true \
	-jar target/ingest-interface-2.0.1.jar > /dev/null 2>&1 &

sleep 2

tail -f logs/ingest-interface.log
