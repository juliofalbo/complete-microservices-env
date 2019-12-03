#!/usr/bin/env bash

function print_blue() {
    echo -e "\e[34m$1\e[0m"
}

print_blue "██████╗  ██████╗  ██████╗██╗  ██╗███████╗██████╗     ██████╗ ██╗   ██╗██╗██╗     ██████╗"
print_blue "██╔══██╗██╔═══██╗██╔════╝██║ ██╔╝██╔════╝██╔══██╗    ██╔══██╗██║   ██║██║██║     ██╔══██╗"
print_blue "██║  ██║██║   ██║██║     █████╔╝ █████╗  ██████╔╝    ██████╔╝██║   ██║██║██║     ██║  ██║"
print_blue "██║  ██║██║   ██║██║     ██╔═██╗ ██╔══╝  ██╔══██╗    ██╔══██╗██║   ██║██║██║     ██║  ██║"
print_blue "██████╔╝╚██████╔╝╚██████╗██║  ██╗███████╗██║  ██║    ██████╔╝╚██████╔╝██║███████╗██████╔╝"
print_blue "╚═════╝  ╚═════╝  ╚═════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝    ╚═════╝  ╚═════╝ ╚═╝╚══════╝╚═════╝"

echo
print_blue "Building Eureka";
docker-compose -f docker-compose-zipkin-eureka.yml build;
print_blue "Building Backend Services";
docker-compose -f docker-compose-backend-services.yml build;
print_blue "Building Frontend Service";
docker-compose -f docker-compose-frontend-service.yml build;
