# Etapa 1: Construcción (Build) usando Maven y Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos el archivo de configuración de Maven
COPY pom.xml .

# Copiamos el código fuente
COPY src ./src

# Compilamos el proyecto y generamos el archivo .jar
# Usamos -DskipTests para que la subida a Render sea más rápida
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución (Run) usando un JRE ligero de Java 21
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copiamos el archivo JAR generado en la etapa anterior
# El nombre suele ser algo como tienda-0.0.1-SNAPSHOT.jar, por eso usamos el comodín *.jar
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto (informativo)
EXPOSE 8081

# Ejecutamos la aplicación
# Render asigna automáticamente un puerto dinámico en la variable $PORT.
# El comando siguiente toma ese puerto o usa el 8081 por defecto.
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8081}"]