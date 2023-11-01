FROM openjdk:11
LABEL maintainer="trains-problem-assessment"
ADD . /src/main/resources/files .
ADD target/trains-problem-assessment-0.0.1-SNAPSHOT.jar trains-assessment-problem.jar
ENTRYPOINT ["java", "-jar", "trains-assessment-problem.jar"]
