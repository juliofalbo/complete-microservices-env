#!/usr/bin/env bash

function print_green() {
    echo -e "\e[32m$1\e[0m"
}

function print_error() {
    echo -e "\e[31m[x] $1\e[0m"
}

print_green "███████╗███████╗████████╗██╗   ██╗██████╗ "
print_green "██╔════╝██╔════╝╚══██╔══╝██║   ██║██╔══██╗"
print_green "███████╗█████╗     ██║   ██║   ██║██████╔╝"
print_green "╚════██║██╔══╝     ██║   ██║   ██║██╔═══╝ "
print_green "███████║███████╗   ██║   ╚██████╔╝██║     "
print_green "╚══════╝╚══════╝   ╚═╝    ╚═════╝ ╚═╝     "
echo
print_green "Starting the Environment creation:";
print_green "- Grafana";
print_green "- Prometheus";
print_green "- RabbitMQ Cluster";
print_green "- Postgres";
print_green "- Cadvisor";

docker-compose up -d;

print_green "Waiting the RabbitMQ Cluster creation";

until $(curl --output /dev/null --silent --head --fail http://localhost:15693/metrics); do
    printf '.'
    sleep 5
done

until $(curl --output /dev/null --silent --head --fail http://localhost:15692/metrics); do
    printf '.'
    sleep 5
done

echo "Starting Zipikn";

docker-compose -f docker-compose-zipkin.yml up -d;