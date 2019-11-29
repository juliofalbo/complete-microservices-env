#!/usr/bin/env bash
#   Use this script to test if a given TCP host/port are available

until $(curl --output /dev/null --silent --head --fail $1); do
    printf '.'
    sleep 5
done
echo;