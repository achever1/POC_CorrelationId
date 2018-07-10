# POC_CorrelationId
POC to handle a correlation ID in logs like MDC in an asynchronous and non blocking env (Play and Akka)

Principles:
 - A filter generates a UUID if it is not already present
 - The request headers are converted implicitly to a logback Marker which is used implicitly by Play Logger
 - Because Akka uses an actor Logging which doesn't handle Markers, the marker is added to command objects and converted to a MDC compliant object
https://doc.akka.io/docs/akka/2.5/logging.html#logging-thread-akka-source-and-actor-system-in-mdc


Impacts on code:
 - Add a filter to generate a UUID when not present
 - Add converter in the controllers to create a Marker for logging
 - Update methods that log information to allow them to take an implicit Marker
 - Add an implicit Marker to the Command Objects and convert the marker to a Map (akka logging doesn't handle markers)

## Build
```
sbt docker:publishLocal
```

## Deploy
```
docker-compose up -d
```
This deploys 2 http services one calling the other

## Usage
### Call the services
```
bash call.sh {nbCalls}
```

This trigger {nbCalls} http request to a service which does :
 - An asynchronous computation through multiple threads
 - A http call to a second service that does the same computation but in an actor system

### Check the logs
Correlation id is propagated accross threads
```
docker exec -it poccorrelationid_s1_1 tail -f logs/application.log
docker exec -it poccorrelationid_s1_2 tail -f logs/application.log
