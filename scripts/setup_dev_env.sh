#! /bin/bash

git clone git@github.com:uk-gov-dft/dev-env.git

pushd dev-env

./rebuild.sh all
./wait_for_it.sh localhost:5432 
./wait_for_it.sh localhost:8681:/manage/actuator/health
./wait_for_it.sh localhost:8381:/manage/actuator/health
./wait_for_it.sh localhost:8281:/manage/actuator/health
./wait_for_it.sh localhost:8081:/manage/actuator/health
./wait_for_it.sh localhost:8481:/manage/actuator/health
./wait_for_it.sh localhost:8181:/manage/actuator/health
./wait_for_it.sh localhost:8581:/manage/actuator/health

popd
