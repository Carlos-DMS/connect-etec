FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-21-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean install

FROM openjdk:21-jdk-slim

EXPOSE 8080

COPY --from=build target/connectEtec-0.0.1-SNAPSHOT.jar /app/connectEtec.jar

ENTRYPOINT ["java", "-jar", "/app/connectEtec.jar"]

## Usando uma imagem base oficial do OpenJDK 21
#FROM openjdk:21-jdk-slim
#
## Definindo o diretório de trabalho
#WORKDIR /app
#
## Copiando o arquivo JAR gerado pela sua aplicação Spring Boot para o contêiner
#COPY target/connectEtec-0.0.1-SNAPSHOT.jar /app/connectEtec.jar
#
## Definindo a porta que a aplicação vai usar
#EXPOSE 8080
#
## Comando para rodar a aplicação
#CMD ["java", "-jar", "/app/connectEtec.jar"]