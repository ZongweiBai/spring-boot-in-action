FROM openjdk:14

MAINTAINER zongwei.bai@foxmail.com

ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

VOLUME ["/usr/local/modules/File-Server/data", "/usr/local/modules/File-Server/log"]

RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' > /etc/timezone \
    && mkdir -p /usr/local/modules/File-Server/data \
    && mkdir -p /usr/local/modules/File-Server/log

ENV TARGET_JAR ${TARGET_JAR:-file-server-1.0.0-SNAPSHOT.jar}
ENV JAVA_VM_OPTS="-server -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xms512m -Xmx512m -Dspring.profiles.active=prod"
ENV JAVA_OPTS="-Dfile.encoding=UTF-8 -Djava.security.egd=file:/dev/./urandom -Djava.net.preferIPv4Stack=true"
ENV SERVER_LOG_PATH /usr/local/modules/File-Server/log

COPY ${TARGET_JAR} /usr/local/modules/File-Server/${TARGET_JAR}

WORKDIR /usr/local/modules/File-Server

EXPOSE 8080

ENTRYPOINT ["/bin/sh", "-c", "java $JAVA_VM_OPTS -jar $JAVA_OPTS -DFILE_SERVER_LOG_PATH=${SERVER_LOG_PATH} ${TARGET_JAR}"]