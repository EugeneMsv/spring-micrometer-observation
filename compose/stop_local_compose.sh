#!/bin/bash

SCRIPT_DIR="$(dirname "$0")"

docker-compose -f $SCRIPT_DIR/docker-compose.local.yml -p spring-micrometer-observation stop
docker-compose -f $SCRIPT_DIR/docker-compose.local.yml -p spring-micrometer-observation rm -f
docker image prune -f

### UNINSTALL EXITED CONTAINERS###
if [ ! -z $(docker ps -a -f status=exited -q) ]; then
echo "Uninstalling unused containers..."
docker rm $(docker ps -a -f status=exited -q)
fi
