#!/bin/bash

IS_GREEN=$(docker ps | grep app-green)

if [ -z "$IS_GREEN" ]; then
  echo "Switching to Green..."
  docker-compose pull app-green
  docker-compose up -d app-green

  for i in {1..10}; do
    echo "Health check on Green (${i}/10)"
    RESPONSE=$(curl -s http://127.0.0.1:8081/health)
    if [ "$RESPONSE" == "alive" ]; then
      echo "Green is healthy!"
      break
    fi
    sleep 10
  done

  if [ "$i" -eq 10 ]; then
    echo "Green deployment failed!"
    exit 1
  fi

  echo "Reloading NGINX to point to Green"
  sudo cp /etc/nginx/conf.d/app-green.conf /etc/nginx/conf.d/service-url.inc
  sudo nginx -s reload

  echo "Stopping Blue"
  docker-compose stop app-blue
else
  echo "Switching to Blue..."
  docker-compose pull app-blue
  docker-compose up -d app-blue

  for i in {1..10}; do
    echo "Health check on Blue (${i}/10)"
    RESPONSE=$(curl -s http://127.0.0.1:8080/health)
    if [ "$RESPONSE" == "alive" ]; then
      echo "Blue is healthy!"
      break
    fi
    sleep 10
  done

  if [ "$i" -eq 10 ]; then
    echo "Blue deployment failed!"
    exit 1
  fi

  echo "Reloading NGINX to point to Blue"
  sudo cp /etc/nginx/conf.d/app-blue.conf /etc/nginx/conf.d/service-url.inc
  sudo nginx -s reload

  echo "Stopping Green"
  docker-compose stop app-green
fi
