package edu.sse.ustc.basic;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

/** 带回调函数的Kafka生产者
 * @author imarklei90
 * @since 2019.01.16
 */
public class CallbackProduceres {
    public static void main(String[] args) {
        // 1. 配置属性值
        Properties properties = new Properties();
        // 配置Kafka服务器地址
        properties.put("bootstrap.servers", "hadoop101:9092");
        // 配置所有副本必须应答后再发送
        properties.put("acks", "all");
        // 发送失败后，再重复发送的次数
        properties.put("retries", 0);
        // 批处理大小
        properties.put("batch.size", 16384);
        // 请求时间间隔
        properties.put("linger.ms", 1);
        // 设置缓存大小
        properties.put("buffer.memory", 33552233);
        // 配置Key的序列化
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // 配置Value的序列化
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // 2. 定义Kafka生产者
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        // 3. 发送消息
        for(int i = 0; i < 10; i++){
            producer.send(new ProducerRecord<String, String>("firstTopic", Integer.toString(i), Integer.toString(1)), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if(recordMetadata != null){
                        System.out.println(recordMetadata.offset() + "->" + recordMetadata.partition());
                    }
                }
            });
        }

        // 4. 关闭资源
        producer.close();
    }
}
