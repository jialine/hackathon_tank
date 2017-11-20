#! /bin/bash

mapid=$MAPID
mapfile=/data/map$mapid.txt
echo "map file is $mapfile"
tank_no=4
tank_speed=2
shell_speed=4
tank_HP=1
tank_score=1
flag_score=1
max_round=100
round_timeout=2000
player1="red:80"
player2="blue:80"
if [ $# -gt 2 ]; then
	player1=$1
	player2=$2
fi
exec java -jar /data/tank-1.0-SNAPSHOT-jar-with-dependencies.jar $mapfile $tank_no $tank_speed $shell_speed $tank_HP $tank_score $flag_score $max_round $round_timeout $player1 $player2 >&1  | tee /data/logs/engine.log

