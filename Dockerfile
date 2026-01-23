FROM openjdk:21-rc-slim AS builder

WORKDIR /src
COPY . .
RUN ./gradlew build -x test

FROM openjdk:21-rc-slim

WORKDIR /application
EXPOSE 8080

COPY --from=builder /src/build/libs/application.jar ./application.jar

ENTRYPOINT ["sh", "-c"]
CMD ["java -jar application.jar"]

LABEL org.opencontainers.image.source=https://github.com/Lausi95/city-game-api
LABEL org.opencontainers.image.description="Backend of the city game"
LABEL org.opencontainers.image.licenses=MIT
# LABEL "com.datadoghq.ad.logs"='[{"service": "city-game-backend"}]'
