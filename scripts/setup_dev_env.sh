#! /bin/bash

git clone git@github.com:uk-gov-dft/dev-env.git

pushd dev-env

bash load-modules.sh

docker-compose up postgresql

./wait_for_it.sh localhost:5432 

popd
