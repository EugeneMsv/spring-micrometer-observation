#!/bin/bash

SCRIPT_DIR="$(dirname "$0")"

# Fail fast on errors
set -e

## Build spring boot service
echo "Building spring-micrometer-observation docker image"
sh $SCRIPT_DIR/../mvnw clean spotless:apply package jib:dockerBuild

## Remove existing installation of spring-micrometer-observation
sh $SCRIPT_DIR/stop_local_compose.sh

## Update images
docker-compose -f $SCRIPT_DIR/docker-compose.local.yml  pull --ignore-pull-failures

## Start mysql
docker-compose -f $SCRIPT_DIR/docker-compose.local.yml -p spring-micrometer-observation up -d

# To enable logs:
docker-compose -p spring-micrometer-observation logs -f -t
