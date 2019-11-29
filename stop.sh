#!/usr/bin/env bash

function print_green() {
    echo -e "\e[32m$1\e[0m"
}

function print_error() {
    echo -e "\e[31m$1\e[0m"
}

print_error "███████╗████████╗ ██████╗ ██████╗   "
print_error "██╔════╝╚══██╔══╝██╔═══██╗██╔══██╗  "
print_error "███████╗   ██║   ██║   ██║██████╔╝"
print_error "╚════██║   ██║   ██║   ██║██╔═══╝ "
print_error "███████║   ██║   ╚██████╔╝██║     "
print_error "╚══════╝   ╚═╝    ╚═════╝ ╚═╝    "
echo
print_error "Stopping the Environment";

docker-compose -f docker-compose-services.yml down -v;
docker-compose -f docker-compose-zipkin-eureka.yml down -v;
docker-compose down -v;
