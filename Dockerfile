FROM openjdk:8-jdk-alpine
MAINTAINER whh
VOLUME /tmp
RUN echo 'Asia/Shanghai' > /etc/timezone
ADD ./target/basemall.jar basemall.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/basemall.jar"]