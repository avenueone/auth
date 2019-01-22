FROM openjdk:8-jre-alpine

ENV AVO_DIR /avo/app

COPY image/auth-svc*.jar $AVO_DIR/auth-svc.jar
RUN chmod +x ${AVO_DIR}/auth-svc.jar

COPY entrypoint.sh ${AVO_DIR}/bin/
RUN chmod +x ${AVO_DIR}/bin/entrypoint.sh

EXPOSE 8950

# Command that is executed when you start the image
CMD ${AVO_DIR}/bin/entrypoint.sh
