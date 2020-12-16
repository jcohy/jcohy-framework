#!/bin/bash

WORK_DIR="/var/www/html/jcohy-framework"

version=$1

echo "version $version"

if [ -d $WORK_DIR ]; then
    yes | rm -r $WORK_DIR
fi

mkdir -p $WORK_DIR

cd $WORK_DIR

wget -N $2

url=$2

unzip ${url##*/}

echo "Document Deploy Is Successful with path http://192.168.11.231/jcohy-framework/$version/javadoc-api/ And http://192.168.11.231/jcohy-framework/$version/jcohy-framework-reference/"
