FROM java:8
ENV port = 8087
EXPOSE 8087:8087
ADD /target/auth-1.0.jar auth.jar
ENTRYPOINT java -jar $EXTRA_JAR_ARGS auth.jar auth
