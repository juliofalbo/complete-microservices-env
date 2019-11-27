#!/usr/bin/env bash
#   Use this script to test if a given TCP host/port are available

until $(curl --output /dev/null --silent --head --fail http://localhost:15693/metrics); do
    printf '.'
    sleep 5
done

until $(curl --output /dev/null --silent --head --fail http://localhost:15692/metrics); do
    printf '.'
    sleep 5
done