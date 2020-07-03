#!/usr/bin/env bash

BUILD=$1;

function print_green() {
    echo -e "\e[32m$1\e[0m"
}

function print_error() {
    echo -e "\e[31m[x] $1\e[0m"
}

function print_blue() {
    echo -e "\e[34m$1\e[0m"
}

print_green "███████╗███████╗████████╗██╗   ██╗██████╗ "
print_green "██╔════╝██╔════╝╚══██╔══╝██║   ██║██╔══██╗"
print_green "███████╗█████╗     ██║   ██║   ██║██████╔╝"
print_green "╚════██║██╔══╝     ██║   ██║   ██║██╔═══╝ "
print_green "███████║███████╗   ██║   ╚██████╔╝██║     "
print_green "╚══════╝╚══════╝   ╚═╝    ╚═════╝ ╚═╝     "
echo


if [[ "$BUILD" = '--build' ]] ; then
    print_blue "Starting docker build"
    echo;
    ./build.sh
fi

print_blue "Starting the Environment SetUp";
print_blue "- Grafana";
print_blue "- Prometheus";
print_blue "- RabbitMQ Cluster";
print_blue "- Splunk";
print_blue "- Postgres";
print_blue "- Redis";
print_blue "- CAdvisor";
print_blue "- Zipkin";
print_blue "- Eureka";

echo;

print_blue "Starting Grafana, Prometheus, RabbitMQ Cluster, Postgres, Redis, CAdvisor, Splunk";

docker-compose -f docker-compose-infra.yml up -d;

print_blue "Starting the RabbitMQ Cluster creation";

docker-compose -f docker-compose-rabbitmq.yml up -d;

./wait.sh 10 http://localhost:15692/metrics "docker-compose-rabbitmq.yml" "rabbitmq-zipkin"
./wait.sh 10 http://localhost:15693/metrics "docker-compose-rabbitmq.yml" "rabbitmq-business"

print_green "RabbitMQ Cluster is running";
echo;

print_blue "Waiting Splunk setup";

./wait.sh 5 http://localhost:8000

print_green "Splunk is running";
echo;

print_blue "Starting Zipkin and Eureka";
echo;

docker-compose -f docker-compose-zipkin-eureka.yml up -d;

print_green "Zipkin and Eureka is created";
echo;

print_blue "Waiting Eureka startup";

./wait.sh 5 http://localhost:8761/actuator/health

print_green "Eureka is running";
echo;

docker-compose -f docker-compose-backend-services.yml up -d;

print_blue "Starting Booking Services";
echo;

./wait.sh 5 http://localhost:8100/actuator/health
./wait.sh 5 http://localhost:8101/actuator/health
./wait.sh 5 http://localhost:8102/actuator/health

print_blue "Starting Searching Services";
echo;

./wait.sh 5 http://localhost:8200/actuator/health
./wait.sh 5 http://localhost:8201/actuator/health

print_blue "Starting Financial Service";
echo;

./wait.sh 5 http://localhost:8300/actuator/health

print_green "All Backend services are running";
echo;

docker-compose -f docker-compose-frontend-service.yml up -d;

print_blue "Starting Frontend Service";
echo;

./wait.sh 5 http://localhost:8400/actuator/health

print_green "Frontend Service is running";
echo;