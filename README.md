# featureflags


## Run Flagd

docker run --rm -it --name flagd -p 8013:8013 -v $(pwd)/flagd-docker:/etc/flagd ghcr.io/open-feature/flagd:latest start --uri file:./etc/flagd/flagd.json