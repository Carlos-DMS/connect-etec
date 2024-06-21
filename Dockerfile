# Usando uma imagem base oficial do OpenJDK 21
FROM openjdk:21-jdk-slim

# Definindo o diretório de trabalho
WORKDIR /app

# Copiando o arquivo JAR gerado pela sua aplicação Spring Boot para o contêiner
COPY target/connectEtec-0.0.1-SNAPSHOT.jar /app/connectEtec.jar

# Definindo a porta que a aplicação vai usar
EXPOSE 8080

# Comando para rodar a aplicação
CMD ["java", "-jar", "/app/connectEtec.jar"]