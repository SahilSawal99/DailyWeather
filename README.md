# DailyWeather (Spring Boot)

## How To Run (Local)

### Prerequisites
- Java `21` (see `<java.version>21</java.version>` in `pom.xml`)
- Internet access (calls OpenWeather)
- An OpenWeather API key

### 1) Configure API Key (choose one)

Option A: Environment variable (recommended)

macOS/Linux:
```bash
export OPENWEATHER_API_KEY="YOUR_KEY_HERE"
```

Windows PowerShell:
```powershell
$env:OPENWEATHER_API_KEY="YOUR_KEY_HERE"
```

Option B: Local Spring profile file (do not commit secrets)

1. Edit `src/main/resources/application-local.properties`
2. Set:
```properties
openweather.api.key=YOUR_KEY_HERE
```

### 2) Run the App

macOS/Linux:
```bash
./mvnw spring-boot:run
```

Windows:
```bat
mvnw.cmd spring-boot:run
```

If you used Option B (local profile):
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

### 3) Verify
```bash
curl "http://localhost:8080/api/v1/weather?city=Delhi&units=metric"
```

## API

Current weather by city:
```bash
curl "http://localhost:8080/api/v1/weather?city=Delhi&units=metric"
```

## Useful Commands

Run tests:
```bash
./mvnw test
```

Build a runnable jar:
```bash
./mvnw clean package
```

Run the jar:
```bash
java -jar target/dailyWeather-0.0.1-SNAPSHOT.jar
```

