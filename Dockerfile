# Usa una imagen base de Java con JDK 17
FROM eclipse-temurin:17-jdk

# Crea un directorio para la app
WORKDIR /app

# Copia el JAR del proyecto
COPY target/ceciferreyra-0.0.1.jar app.jar

# Expone el puerto 8080
EXPOSE 8080

# Comando para correr la app
ENTRYPOINT ["java", "-jar", "app.jar"]
