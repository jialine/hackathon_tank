#! /bin/bash

mapid=$MAPID
mapfile=/data/map$mapid.txt
echo "map file is $mapfile"
exec java -jar /data/tank-1.0-SNAPSHOT-jar-with-dependencies.jar $mapfile 4 1 2 1 1 1 100 2000 red:80 blue:80 2>&1  | tee /data/logs/engine.log

