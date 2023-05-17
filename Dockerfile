#Copyright (c) 2019-2022 EasyBlog and/or its affiliates. All rights reserved.
#
# EASYBLOG DOCKERFILES PROJECT
#---------------------------

# Pull base image
# ---------------
FROM java:8

# Maintainer
# ----------
MAINTAINER Frank.HUANG <hx981230@163.com>

# BUILD ARG: target application file path
# ---------------
ARG JAR_FILE_PATH

# BUILD ARG: applicatgion run profile
# ---------------
ARG ACTIVE_PROFILE

# BUILD ARG: applicatgion run port
# ---------------
ARG SERVER_PORT

# BUILD ARG: applicatgion log home dir
# ---------------
ARG LOG_BASE_DIR

ENV WORK_HOME='/usr/local/app'

WORKDIR  ${WORK_HOME}

#挂载宿主机${WORK_HOME}/data/logs目录
VOLUME ["${LOG_BASE_DIR}","/data/logs"]

# Add files required to build this image
# ---------------
COPY  $JAR_FILE_PATH  app.jar

# Expose default port
# ---------------
EXPOSE ${SERVER_PORT}

# Container entry
ENTRYPOINT ["java","${JAVA_OPTS}","${JVM_PARAMS}","-jar"," app.jar"]
