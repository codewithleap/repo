#!/bin/bash
mvn package assembly:single
echo "Deploying to local ant lib folder (usr/share/ant/lib on a mac)"
sudo cp target/ant-leap-jar-with-dependencies.jar /usr/share/ant/lib
