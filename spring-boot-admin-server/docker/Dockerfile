FROM openjdk:14

MAINTAINER zongwei.bai@foxmail.com

ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8
ENV DATADIR /usr/local/modules/Boot-Admin-Server/data
ENV LOGDIR /usr/local/modules/Boot-Admin-Server/log
ENV CONFIGDIR /usr/local/modules/Boot-Admin-Server/config

VOLUME ["${DATADIR}", "${LOGDIR}", "${CONFIGDIR}"]

RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' > /etc/timezone \
    && mkdir -p ${DATADIR} \
    && mkdir -p ${LOGDIR} \
    && mkdir -p ${CONFIGDIR}

ENV TARGET_JAR ${TARGET_JAR:-boot-admin-server-1.0.0-SNAPSHOT.jar}
ENV JAVA_OPTS="-server -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xms512m -Xmx512m -Dspring.config.additional-location=${CONFIGDIR}/ -Dspring.profiles.active=prod"

COPY ${TARGET_JAR} /usr/local/modules/Boot-admin-Server/${TARGET_JAR}
COPY application-prod.yml ${CONFIGDIR}

WORKDIR /usr/local/modules/Boot-admin-Server

EXPOSE 8080

ENTRYPOINT ["/bin/sh", "-c", "java $JAVA_OPTS -jar -Dfile.encoding=UTF-8 -Djava.security.egd=file:/dev/./urandom -Djava.net.preferIPv4Stack=true -DFILE_SERVER_LOG_PATH=${LOGDIR} ${TARGET_JAR}"]
