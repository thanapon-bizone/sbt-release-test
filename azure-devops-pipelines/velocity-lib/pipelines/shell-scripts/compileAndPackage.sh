#!/bin/bash

packagename=$1
packageversion=$2
scalaversion=$3
velocitylibpath=$4

compileAndPackage() {


    echo "==========================="
    echo "Compiling and Packaging..."
    echo "==========================="

    ls $velocitylibpath

    (cd $velocitylibpath/; sbt compile assembly)

    compileResult=$?

    if [ $compileResult -ne 0 ]
    then
        echo "Error while compiling and packaging the code. Check the logs for more info."
        exit $compileResult
    fi

}

compileAndPackage