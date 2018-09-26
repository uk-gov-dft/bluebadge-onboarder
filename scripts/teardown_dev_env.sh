#! /bin/bash

pushd scripts/dev-env

docker-compose kill
docker-compose rm

popd
