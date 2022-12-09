#!/bin/bash

packagename=$1
packageversion=$2
scalaversion=$3
dbfspath=$4

copyToDBFS() {


    echo "==========================="
    echo "Deleting current Package..."
    echo "==========================="

    dbfs rm dbfs:/${dbfspath}/${packagename}-${packageversion}.jar --profile DEFAULT

    SOURCEJAR=velocity-lib/${packagename}_${scalaversion}.jar

    echo $SOURCEJAR

    echo "================================================================"
    echo "Copying ${packagename}.jar to Databricks..." dbfs:/${dbfspath}/
    echo "================================================================"
    dbfs cp $SOURCEJAR dbfs:/${dbfspath}/${packagename}-${packageversion}.jar --profile DEFAULT

}

copyToDBFS $packagename $packageversion $scalaversion $dbfspath