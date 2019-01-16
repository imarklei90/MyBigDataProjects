#!/bin/bash
echo "------ 开始启动所有节点服务 ------"
echo "------ 开始启动 Zookeeper ------"
for i in hadoop@hadoop101 hadoop@hadoop102 hadoop@hadoop103
do
    ssh $i '/opt/modules/zookeeper-3.4.5/bin/zkServer.sh start'
done
echo "------ 开始启动 HDFS ------"
ssh hadoop@hadoop101 '/opt/modules/hadoop-2.7.2/sbin/start-dfs.sh'
echo "------ 开始启动 YARN ------"
ssh hadoop@hadoop102 '/opt/modules/hadoop-2.7.2/sbin/start-yarn.sh'
echo "------ 启动 JobHistory ------"
ssh hadoop@hadoop101 '/opt/modules/hadoop-2.7.2/sbin/mr-jobhistory-daemon.sh start historyserver'