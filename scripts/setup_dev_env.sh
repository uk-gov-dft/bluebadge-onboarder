#! /bin/bash

git clone git@github.com:uk-gov-dft/dev-env.git

pushd dev-env

bash load-modules.sh

docker-compose kill
docker-compose rm


docker-compose up -d postgresql

./wait_for_it.sh localhost:5432 

popd
