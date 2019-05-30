FROM openjdk:8
COPY target/pokemon-search.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar", "--weatherApi.appId=${OPEN_WEATHER_APP_ID}"]
