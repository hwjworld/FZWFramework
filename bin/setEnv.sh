#!/bin/sh

#在此设置JAVA_HOME,CLASS_PATH
JAVA_HOME=$JAVA_HOME

for i in lib/*.jar 
do
CLASS_PATH=$PWD/$i:$CLASS_PATH
done

export CLASS_PATH=.:$CLASS_PATH
