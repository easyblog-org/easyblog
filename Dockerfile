#Copyright (c) 2019-2022 EasyBlog and/or its affiliates. All rights reserved.
#
# EASYBLOG DOCKERFILES PROJECT
#---------------------------

# Pull base image
# ---------------
FROM openjdk:8

# Maintainer
# ----------
LABEL "Maintainer"="Frank.HUANG <huangxin981230@163.com>"

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

WORKDIR  "/usr/local/app"

# Add files required to build this image
# ---------------
COPY  ${JAR_FILE_PATH}  app.jar

# Expose default port
# ---------------
EXPOSE ${SERVER_PORT}

# Container entry
CMD ["${JAVA_OPTS}","${JVM_PARAMS}"]
ENTRYPOINT java -jar ${JAVA_OPTS} ${JVM_PARAMS} app.jar
