#!/usr/bin/env bash
#   Use this script to test if a given TCP host/port are available

TIMEOUT=$1;
URL=$2;
FILENAME=$3;
SERVICE=$4;

counter=1
until $(curl --output /dev/null --silent --head --fail "$URL"); do
    if (("$counter" % 10 == 0)) && [[ "$FILENAME" ]]; then
        echo "Retrying to start service $FILENAME"
        echo;
        docker-compose -f "$FILENAME" restart "$SERVICE";
    fi
    printf '.'
    sleep "$TIMEOUT"
    counter=$((counter+1))
done
echo;