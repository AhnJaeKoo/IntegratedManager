#!/bin/sh
JAVA_HOME=/data/app/openJDK-8u41/bin
JAVA_ARG=-Dspring.profiles.active=prod,h2
APP_HOME=/data/groupMatching/IntegratedManager
#LOG_PATH=$APP_HOME/logs
LIB_PATH=$APP_HOME/lib
SERVICE_NAME=IntegratedManager
PID_FILE=$APP_HOME/$SERVICE_NAME.pid

case $1 in
    start)
        if [ ! -f $PID_FILE ]; then
            echo "Starting $SERVICE_NAME "
	    echo
	    $JAVA_HOME/java -jar $JAVA_ARG $LIB_PATH/IntegratedManager.war &>/dev/null &
	    echo $! > $PID_FILE
        else
            echo "$SERVICE_NAME is already running ..."
        fi
    ;;
    stop)
        if [ -f $PID_FILE ]; then
            PID=$(cat $PID_FILE);
            echo "$SERVICE_NAME stoping ..."
            kill -9 $PID;
            echo "$SERVICE_NAME stopped ..."
            rm $PID_FILE
        else
            echo "$SERVICE_NAME is not running ..."
        fi
    ;;
    restart)
	$0 stop
        sleep 5
	$0 start
    ;;
    status)
	
	if [ -e $PID_FILE ]; then
		echo "$SERVICE_NAME is running"
	else
		echo "$SERVICE_NAME is stopped"
	fi
	;;
    *)
	echo "Usage: $0 {start|stop|restart|status}"
	exit 1
esac

exit 0

