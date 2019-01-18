package edu.sse.ustc.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/** Kafka的消费者
 * @author imarklei90
 * @since 2019.01.16
 */
public class KafkaConsumers {
    public static void main(String[] args) {
        // 1. 配置参数
        Properties properties = new Properties();
        // 配置Kafka服务器地址
        properties.put("bootstrap.servers", "hadoop101:9092");
        // 定义消费组
        properties.put("group.id", "test");
        // 自动提交（offset）
        properties.put("enable.auto.commit", "true");
        // 自动处理间隔时间
        properties.put("auto.commit.interval.ms", "1000");
        // 配置Key的序列化
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // 配置Value的序列化
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        // 2. 创建消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);

        // 3. 订阅消费Topic
        consumer.subscribe(Arrays.asList("firstTopic"));

        // 4. 执行消费操作
        while(true){
            // 100ms消费一次
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.offset() + "-->" + record.partition() + "- Value : " + record.value());
            }
        }
    }
}
