#! /bin/bash

git clone git@github.com:uk-gov-dft/dev-env.git

pushd dev-env

bash load-modules.sh

docker-compose kill
docker-compose rm

docker-compose up -d postgresql

./wait_for_it.sh localhost:5432 

sleep 5

psql -h localhost -U developer -d bb_dev -f ./scripts/db/setup-users.sql 

popd
