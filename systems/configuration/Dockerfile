FROM java:8
ENV port = 8888
EXPOSE 8888:8888
ADD /target/configuration-1.0.jar configuration.jar
ENTRYPOINT java -jar $EXTRA_JAR_ARGS configuration.jar configuration
