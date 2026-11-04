# Harz Dam Monitor

A web app that shows water data for six dams in the Harz region. The start page lists all dams, and each dam has a detail page with a chart of inflow, outflow and storage over the last 14 days.

Built for the Software Engineering course at Ostfalia University of Applied Sciences.

## Tech stack

- Java 17, Spring Boot
- Vaadin for the UI
- InfluxDB (time series database)
- SO Charts for the charts
- Maven, npm

## Running it

Requirements: Java 17 and npm 10 or newer.

```
mvn spring-boot:run
```

The app runs on http://localhost:8081

## Data source

The real data comes from an InfluxDB server at Ostfalia, which is only reachable through the university VPN. To use it, set your access token before starting:

```
export INFLUX_TOKEN=your-token
```

Without a token the app starts in demo mode and shows generated sample data, so it can be tried without access to the server.
