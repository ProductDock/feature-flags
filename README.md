# featureflags

## Run Flagd

From project root run: `docker run --rm -it --name flagd -p 8013:8013 -v $(pwd)/flagd-docker:/etc/flagd ghcr.io/open-feature/flagd:latest start --uri file:./etc/flagd/flagd.json`

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```