#!/bin/bash

mkdir -p ${HOME}/tmp/
cd ${HOME}/tmp
git init config-data
cd config-data
mkdir nginx-conf-default

echo -e "server:\n  port: 8080\n" > ${HOME}/tmp/config-data/client-config-default.yml
echo -e "{\n \"a\": \"b\"\n}" > ${HOME}/tmp/config-data/nginx-conf-default/nginx.conf
git add .
git commit -m "initial commit"
