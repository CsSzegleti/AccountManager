FROM openjdk:21-bullseye
ENV LANG C.UTF-8

WORKDIR app
COPY background-security-check-1.0.0.jar application.jar

EXPOSE 9095

ENTRYPOINT java -jar ./application.jar