#!/bin/bash

if [ -d "compilation" ]; then
   rm -rf compilation
fi
mkdir compilation

javac src/**/*.java -d compilation
jar cf ComponentSearch.jar -C compilation/ .

rm -rf compilation
