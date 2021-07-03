FROM azul/zulu-openjdk-alpine

LABEL maintainer Oraqus <cduque@protonmail.com>

ENV JAVA_OPTS=""

COPY target/uberjar/torneoencasa.jar app.jar
COPY resources/public resources/public

EXPOSE 29080

ENTRYPOINT [ "sh", "-c", "java -XX:+PrintFlagsFinal -XX:+PrintGCDetails $JAVA_OPTS -jar app.jar" ]
