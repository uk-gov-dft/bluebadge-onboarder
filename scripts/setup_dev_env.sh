#! /bin/bash

git clone git@github.com:uk-gov-dft/dev-env.git

pushd dev-env

./rebuild.sh all

popd
