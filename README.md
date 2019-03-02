# Part 1
# User service

## Test (with documentation testing)
`gradle test`

## Build
`gradle bootJar`

## Run locally
`java -jar build/libs/kubemetrics-0.0.5-SNAPSHOT.jar`

Version and path can change

If you start application locally, in container or make requests to deployed app it will store data to a real db, so after number of requests to the app the db be populated with entries


## Create docker image and publish to docker hub
`gradle jib`

Requires credentials

## Run locally in docker
`docker run -d -p 8080:8080 sulevsky/kubemetrics:0.0.5-SNAPSHOT`

https://cloud.docker.com/repository/docker/sulevsky/kubemetrics

## Documentation 
http://demo-env.b7ajzwmzzp.us-east-1.elasticbeanstalk.com/docs/index.html

## Running app
http://demo-env.b7ajzwmzzp.us-east-1.elasticbeanstalk.com/

Example:
`curl http://demo-env.b7ajzwmzzp.us-east-1.elasticbeanstalk.com/users/5c7ad00146e0fb0001d3cc96`

# Part 2
Provide at least 3 (or more) design/solution challenges you will need to consider for building the remaining services

1. Why did you start with microservices, but not from the simpler solution? I see here a real problem.
2. Auth concerns will be the single point of coupling and have to be considered from the day 0.
3. Requirements for Chat functionality are much more severe comparing to other features. We have to bear in mind scalability, resilience, availability, etc.

