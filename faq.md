## Q: 怎么知道旗子有没有出现？
A: 如果有旗子，游戏引擎下发的GameState中的flagPos会标示出旗子的位置。否则这个字段为空。

## Q：怎么知道游戏结束呢？
A：选手的服务不会被复用，每一局比赛都会启动新的容器。所以不用关心游戏是否结束。

## Q： 选手程序的启动方式是什么？
A： 
```
docker run -it <image url> /data/start.sh
```

## Q: 怎样调试自己的docker镜像
A：按如下方式调试
```
docker run -it <your docker image> /data/start.sh
docker run -it <your docker image> /data/start.sh
docker run -e MAPID=1  --link=<container id1>:red --link=<container id2>:blue docker-hack.ele.me/jiangang.lan/tankengine:10 /data/start.sh
```
把上述命令中的\<container id1\>和\<container id2\>替换为前面启动的两个container id。
可以通过如下命令获取container id
```
docker ps
```
