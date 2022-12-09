#!/bin/bash

packagename=$1
dbfspath=$2


copyToDBFS() {


    echo "==========================="
    echo "Deleting current Package..."
    echo "==========================="

    dbfs rm dbfs:/${dbfspath}/${packagename} --profile DEFAULT

    SOURCEJAR=velocity-lib/${packagename}

    echo $SOURCEJAR

    echo "================================================================"
    echo "Copying ${packagename}.jar to Databricks..." dbfs:/${dbfspath}/
    echo "================================================================"
    dbfs cp $SOURCEJAR dbfs:/${dbfspath}/${packagename} --profile DEFAULT

}

copyToDBFS $packagename $dbfspath