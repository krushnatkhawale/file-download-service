#!/usr/bin/env bash

sudo rm -f file-download-service.deleted

nohup java -jar file-download-service.jar & echo $! > file-download-service.pid &