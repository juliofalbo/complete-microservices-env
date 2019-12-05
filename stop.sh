#!/usr/bin/env bash

REMOVEVOLUMES=$1;

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

if [[ "$REMOVEVOLUMES" = true ]]
then
    print_error "Removing all Volumes (you will lose your data)";
    docker-compose -f docker-compose-backend-services.yml down -v;
    docker-compose -f docker-compose-frontend-service.yml down -v;
    docker-compose -f docker-compose-zipkin-eureka.yml down -v;
    docker-compose -f docker-compose-rabbitmq.yml down -v;
    docker-compose -f docker-compose-infra.yml down -v;
else
    print_error "Removing all containers keeping the volumes";
    docker-compose -f docker-compose-backend-services.yml down;
    docker-compose -f docker-compose-frontend-service.yml down;
    docker-compose -f docker-compose-zipkin-eureka.yml down;
    docker-compose -f docker-compose-rabbitmq.yml down;
    docker-compose -f docker-compose-infra.yml down;
fi
