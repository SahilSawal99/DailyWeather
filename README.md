# DailyWeather (Spring Boot)

## Setup

1. Either export the env var:

```bash
export OPENWEATHER_API_KEY="YOUR_KEY_HERE"
```

Or use a local-only Spring profile file:

- Edit `src/main/resources/application-local.properties` and set `openweather.api.key`

2. Run (env var method):

```bash
./mvnw spring-boot:run
```

Or run with the `local` profile (file method):

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

## API

Current weather by city:

```bash
curl "http://localhost:8080/api/v1/weather?city=Delhi&units=metric"
```
