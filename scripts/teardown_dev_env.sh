#! /bin/bash

pushd dev-env

docker-compose kill
docker-compose rm

popd
