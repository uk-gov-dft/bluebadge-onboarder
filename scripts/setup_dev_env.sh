#! /bin/bash

git clone git@github.com:uk-gov-dft/dev-env.git

pushd dev-env

bash load-modules.sh

docker-compose kill
docker-compose rm

docker-compose build
docker-compose up -d --no-color

./wait_for_it.sh localhost:5432 
./wait_for_it.sh localhost:8681:/manage/actuator/health
./wait_for_it.sh localhost:8381:/manage/actuator/health
./wait_for_it.sh localhost:8281:/manage/actuator/health
./wait_for_it.sh localhost:8081:/manage/actuator/health
./wait_for_it.sh localhost:8481:/manage/actuator/health
./wait_for_it.sh localhost:8181:/manage/actuator/health
./wait_for_it.sh localhost:8581:/manage/actuator/health

psql -h localhost -U developer -d bb_dev -f ./scripts/db/setup-users.sql 

popd
