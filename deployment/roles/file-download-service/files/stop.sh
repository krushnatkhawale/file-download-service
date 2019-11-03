echo "\nProcesses before shutdown:\n" > file-download-service.deleted

ps -ef | grep "java -jar file-download-service.jar" | grep ? >> file-download-service.deleted

echo "\nExisting process pid: $(cat file-download-service.pid) \n" >> file-download-service.deleted

echo "\nKilling process with pid - $(cat file-download-service.pid)\n" >> file-download-service.deleted

sudo kill -9 $(cat file-download-service.pid)

sudo rm -f file-download-service.pid