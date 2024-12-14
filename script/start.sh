#!/usr/bin/env bash

PROJECT_ROOT="/home/innerjoin/deployment"
JAR_FILE="$PROJECT_ROOT/innerjoin-exec.jar"

APP_LOG="$PROJECT_ROOT/application.log"
ERROR_LOG="$PROJECT_ROOT/error.log"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)

# build 파일 복사
echo "$TIME_NOW > $JAR_FILE 파일 복사" >> $DEPLOY_LOG
cp $PROJECT_ROOT/build/libs/*.jar $JAR_FILE

# jar 파일 실행
echo "$TIME_NOW > $JAR_FILE 파일 실행" >> $DEPLOY_LOG
sudo nohup java -jar\
 -DMYSQL_URL=$MYSQL_URL -DMYSQL_USERNAME=$MYSQL_USERNAME -DMYSQL_PASSWORD=$MYSQL_PASSWORD\
  -DCONNECTION_STRING=$CONNECTION_STRING -DUNIV_CERT_API_KEY=$UNIV_CERT_API_KEY\
  -DMAIL_USERNAME=$MAIL_USERNAME -DMAIL_PASSWORD=$MAIL_PASSWORD\
   $JAR_FILE > $APP_LOG 2> $ERROR_LOG &


sleep 10  # 잠시 대기 후 프로세스 확인

CURRENT_PID=$(pgrep -f $JAR_FILE)

if [ -z "$CURRENT_PID" ]; then
  echo "$TIME_NOW > JAR 파일 실행 실패 또는 프로세스가 시작되지 않았습니다." >> $DEPLOY_LOG
  exit 1
fi

echo "$TIME_NOW > 실행된 프로세스 아이디 $CURRENT_PID 입니다." >> $DEPLOY_LOG