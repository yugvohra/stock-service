FROM openjdk:11-jdk as build
WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN chmod -R 777 ./mvnw
RUN ./mvnw package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:11-jdk
VOLUME /tmp
ARG DEPENDENCY=/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
RUN curl -X GET https://ce.contrastsecurity.com/Contrast/api/ng/2257955b-e61e-4159-9c47-f8e0f99ca74d/agents/default/JAVA -H 'Authorization: eXVnZXNoLmphdmFAZ21haWwuY29tOkdIV1UxMk81VzRQNU1SVjY=' -H 'API-Key: nEv5lVDRXpK2i85UyWTK4sHCn3qzlxK1' -H 'Accept: application/json' -o contrast.jar
ENTRYPOINT ["java","-javaagent:contrast.jar","-Dcontrast.agent.java.standalone_app_name=Stock-Service","-Dcontrast.server.name=DockerServer","-cp","app:app/lib/*","com.arctium.stockservice.StockserviceApplication"]