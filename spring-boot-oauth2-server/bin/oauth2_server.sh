#!/bin/bash
#################
# Component:        OAUTH2-SERVER
# Required-Start:   $all
# Required-Stop:    $all
# Default-Start:    2 3 4 5
# Default-Stop:     0 1 6
# Description:      OAUTH2 Server
# chkconfig: 345 90 10
##################

NAME=jxd-server
CMD=$1
SERVER_ROOT_PATH=/home/dev-platform/oauth2

APP_PATH=$SERVER_ROOT_PATH/webapp/$NAME.jar
SERVER_CONFIG_PATH=$SERVER_ROOT_PATH/config
SERVER_LOG_PATH=$SERVER_ROOT_PATH/log
APP_USER=root

# JVM Args
JVM_XMS_OAUTH2=2048m
JVM_XMX_OAUTH2=2048m
JVM_MetaspaceSize_OAUTH2=512m
JVM_MaxMetaspaceSize_OAUTH2=512m

DEFAULT_PORT=18181
SERVER_JAVA_OPTS="-XX:MetaspaceSize=$JVM_MetaspaceSize_OAUTH2 -XX:MaxMetaspaceSize=$JVM_MaxMetaspaceSize_OAUTH2 -Xms$JVM_XMS_OAUTH2 -Xmx$JVM_XMX_OAUTH2"
export JAVA_HOME=/usr/local/jdk1.8.0_181/jre
export PATH=$JAVA_HOME/bin:$PATH

start() {
        echo "Starting $NAME ..."
        if [ ! -d $SERVER_ROOT_PATH/log/ ]; then
            mkdir $SERVER_ROOT_PATH/log/
        fi
        if [ ! -f $SERVER_ROOT_PATH/log/nohup.out ]; then
            echo "" > $SERVER_ROOT_PATH/log/nohup.out
        fi
        if [ ! -d $SERVER_ROOT_PATH/static ]; then
            mkdir $SERVER_ROOT_PATH/static/
        fi
        if [[ $EUID -ne 0 ]]; then
            nohup java $SERVER_JAVA_OPTS -jar -Djava.security.egd=file:/dev/./urandom -Dserver.port=$DEFAULT_PORT -DOAUTH2_CONFIG_PATH=$SERVER_CONFIG_PATH -DOAUTH2_LOG_PATH=$SERVER_LOG_PATH $APP_PATH --spring.config.location=classpath:/application.yml,file:$SERVER_CONFIG_PATH/application.yml,file:$SERVER_CONFIG_PATH/platformApplication.yml > $SERVER_ROOT_PATH/log/nohup.out 2>&1 &
        else
            su  -c "nohup java $SERVER_JAVA_OPTS -jar -Djava.security.egd=file:/dev/./urandom -Dserver.port=$DEFAULT_PORT -DOAUTH2_CONFIG_PATH=$SERVER_CONFIG_PATH -DOAUTH2_LOG_PATH=$SERVER_LOG_PATH $APP_PATH --spring.config.location=classpath:/application.yml,file:$SERVER_CONFIG_PATH/application.yml,file:$SERVER_CONFIG_PATH/platformApplication.yml > $SERVER_ROOT_PATH/log/nohup.out 2>&1 &" $APP_USER
        fi
        echo "java $SERVER_JAVA_OPTS -jar -Djava.security.egd=file:/dev/./urandom -Dserver.port=$DEFAULT_PORT -DOAUTH2_CONFIG_PATH=$SERVER_CONFIG_PATH -DOAUTH2_LOG_PATH=$SERVER_LOG_PATH $APP_PATH --spring.config.location=classpath:/application.yml,file:$SERVER_CONFIG_PATH/application.yml,file:$SERVER_CONFIG_PATH/platformApplication.yml > $SERVER_ROOT_PATH/log/nohup.out 2>&1 &"
        sleep 5
        PID=`ps -ef | grep $APP_PATH | grep java | grep -v grep | awk '{print $2}'`
        echo "$NAME started with PID $PID"
}

stop() {
        PID=`ps -ef | grep $APP_PATH | grep java | grep -v grep | awk '{print $2}'`
        echo "Stopping $NAME ($PID) ..."
        kill -9 $PID
        RETVAL=3
        sleep 5
}

status() {
        PID=`ps -ef | grep $APP_PATH | grep java | grep -v grep | awk '{print $2}'`
        if [ -n "$PID" ]; then
          echo -e "$NAME is running with PID $PID\t\e[1;32mPASSED\e[0m"
          curl -s http://localhost:$DEFAULT_PORT/actuator/healthcheck
          echo -ne '\n'
        else
          echo -e "$NAME is not running \t\t\e[1;31mFAILED\e[0m"
        fi
}

restart() {
        echo "Restarting $NAME ..."
        stop
        start
        status
}


case "$CMD" in
        start)
                start
                ;;
        stop)
                stop
                ;;
        restart)
                restart
                ;;
        status)
                status
                ;;
        *)
                echo "Usage $0 {start|stop|restart|status}"
esac

