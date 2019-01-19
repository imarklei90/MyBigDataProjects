# 启动Kafka服务器
    bin/zookeeper-server-start.sh config/server.properties
# 创建Topic
    bin/kafka-topics.sh --create --zookeeper hadoop101:2181 --replication-factor 3 --partitions 1 --topic test

# 查询Topic
    bin/kafka-topics.sh --list --zookeeper hadoop101:2181

# 创建生产者发送消息
    bin/kafka-console-producer.sh --broker-list hadoop101:9092 --topic test

# 创建消费者接收消息
    bin/kafka-console-consumer.sh --bootstrap-server hadoop101:9092 --topic test --from-beginning

# 查看Topic的详细信息
    bin/kafka-topics.sh --describe --zookeeper hadoop101:2181 --topic test

# Kafka Connector
    bin/kafka-standalone.sh config/connect-standalone.properties config/connect-file-source.properties config/connect-file-sink.properties
    bin/kafka-console-consumer.sh --bootstrap-server hadoop101:9092 --topic connect-test --from-beginning