#!/bin/bash
#Copyright (c) 2019-2022 EasyBlog and/or its affiliates. All rights reserved.
#
# EASYBLOG STARTUP SCRIPTS
#============================================================================
set -e

#项目名称
ARTIFACT="easyblog"
echo ${ARTIFACT}

#版本
VERSION="1.0.0"
ARTIFACT_VERSION='1.0.0'
BUILD_VERSION='1.0.0'
BASE_DIR='/data/app/${ARTIFACT}'
LOG_BASE_DIR="/data/${ARTIFACT}/logs"
JAR_FILE_PATH="`pwd`/${ARTIFACT}-web/target/${ARTIFACT}-web-1.0.0.jar"
if [ ! -d $LOG_BASE_DIR ]; then
  mkdir -p $LOG_BASE_DIR
fi


#===========================================================================================
# Check JAVA_HOME Configuration
#===========================================================================================
[ ! -e "${JAVA_HOME}/bin/java" ] && JAVA_HOME=$HOME/jdk/java
[ ! -e "${JAVA_HOME}/bin/java" ] && JAVA_HOME=/usr/java
[ ! -e "${JAVA_HOME}/bin/java" ] && JAVA_HOME=/opt/taobao/java
[ ! -e "${JAVA_HOME}/bin/java" ] && unset JAVA_HOME

echo "JAVA_HOME:" ${JAVA_HOME}

if [ -z "${JAVA_HOME}" ]; then
  if $darwin; then

    if [ -x '/usr/libexec/java_home' ] ; then
      export {JAVA_HOME}=`/usr/libexec/java_home`

    elif [ -d "/System/Library/Frameworks/JavaVM.framework/Versions/CurrentJDK/Home" ]; then
      export {JAVA_HOME}="/System/Library/Frameworks/JavaVM.framework/Versions/CurrentJDK/Home"
    fi
  else
    JAVA_PATH=`dirname $(readlink -f $(which javac))`
    if [ "x${JAVA_PATH}" != "x" ]; then
      export {JAVA_HOME}=`dirname ${JAVA_PATH} 2>/dev/null`
    fi
  fi
  if [ -z "${JAVA_HOME}" ]; then
        error_exit "Please set the JAVA_HOME variable in your environment, We need java(x64)! jdk8 or later is better!"
  fi
fi


#===========================================================================================
# Running Params Configuration
#===========================================================================================
ACTIVE_PROFILE="dev"
SERVER_PORT="8001"
USE_DOCKER="false"
while getopts ":m:p:d:" opt
do
    case $opt in
        m)
            ACTIVE_PROFILE=$OPTARG;;
        p)
            SERVER_PORT=$OPTARG;;
        d)
            USE_DOCKER=$OPTARG;;        
        ?)
        echo "Unknown parameter"
        exit 1;;
    esac
done

#===========================================================================================
# Packing
#===========================================================================================
echo ">>>>>> Start package the project..."
mvn -v >/dev/null
# shellcheck disable=SC2181
if [ "$?" -ne 0 ]; then
    echo "Maven is not installed or properly configured on your device"
    exit 1
fi
mvn clean package
echo ">>>>>> Package the project successfully!"


#===========================================================================================
# JVM Configuration
#===========================================================================================
JVM_PARAMS="${JVM_PARAMS} -XX:+UseG1GC"
JVM_PARAMS="${JVM_PARAMS} -Xms512M -Xmx512M"
JVM_PARAMS="${JVM_PARAMS} -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps"
JVM_PARAMS="${JVM_PARAMS} -Xloggc:/data/logs/gc.log"
JVM_PARAMS="${JVM_PARAMS} -XX:NumberOfGCLogFiles=3"
JVM_PARAMS="${JVM_PARAMS} -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/data/logs/headdump.hprof"


#===========================================================================================
# Runing Configuration
#===========================================================================================
JAVA_OPTS="${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom"
JAVA_OPTS="${JAVA_OPTS} -Dlog4j2.formatMsgNoLookups=true"
JAVA_OPTS="${JAVA_OPTS} -Dfile.encoding=UTF-8"
JAVA_OPTS="${JAVA_OPTS} -Dspring.profiles.active=${ACTIVE_PROFILE}"


#===========================================================================================
# Build Docker Image
#===========================================================================================
buildDockerImage(){
  echo ">>>>>> Start to build docker image: ${ARTIFACT}"
  docker build --no-cache \
      --build-arg WORK_HOME="${BASE_DIR}" \
      --build-arg LOG_BASE_DIR="${LOG_BASE_DIR}" \
      --build-arg JAR_FILE_PATH="${JAR_FILE_PATH}" \
      --build-arg SERVER_PORT="${SERVER_PORT}" \
      --build-arg BASE_DIR="${BASE_DIR}" \
      -t "${ACTIVE_PROFILE}" .
}


#===========================================================================================
# Run applicaton on Dcoker
#===========================================================================================
runOnDocker(){
  echo ">>>>>> Starting application in ${ACTIVE_PROFILE} env on docker..."
  instances=$(docker ps -q -a --filter "name=${ARTIFACT}")
  # 检查并停止老的实例
  for ins in $instances; do
    docker stop "$ins" && docker rm "$ins"
  done

   #启动新实例
  latest_image="${ARTIFACT}"
  docker run -e JVM_PARAMS="${JVM_PARAMS}" -e JAVA_OPTS="${JAVA_OPTS}" --name ${ARTIFACT}-${SERVER_PORT} -p ${SERVER_PORT}:${SERVER_PORT} -d "${latest_image}"
  echo "Start application on port ${SERVER_PORT} successfully! You can check /data/${LOG_BASE_DIR}/${ARTIFACT}/logs/info.log"
}



#===========================================================================================
# Run applicaton on real server
#===========================================================================================
runOnReal(){
  echo ">>>>>> Starting application in ${ACTIVE_PROFILE} env on real physical machine..."
  if [ which lsof >/dev/null 2>&1 ]; then
    echo "Command 'lsof' is not exists,pls install manually!"
    exit 1
  fi
  PID=`lsof -i:${SERVER_PORT} | grep "${SERVER_PORT}" |  awk -F " " '{print $2}'`
  if [ "$?" == 0 -a "$PID" != "" ]; then
     echo ">>>>>> Stoping application already running on port $PID ..."
     kill $PID
     echo ">>>>>> Stop application already running on port $PID successfully!"
  fi
  nohup java -jar ${JVM_PARAMS} ${JAVA_OPTS} ${JAR_FILE_PATH} >${LOG_BASE_DIR}/server.log 2>&1 &
  echo "Start application on port ${SERVER_PORT} successfully! You can check ${LOG_BASE_DIR}/server.log"
}



#===========================================================================================
# main
#===========================================================================================
if [ ${USE_DOCKER} == "true" ]; then
  runOnDocker
elif [ ${USE_DOCKER} == "false" ]; then
  runOnReal
fi