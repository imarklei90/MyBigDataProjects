package edu.sse.ustc.kafaka.interceptor;

import org.apache.kafka.clients.producer.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**带有拦截器的生产者
 * @author imarklei90
 * @since 2019.01.17
 */
public class InterceptorProducers {
    public static void main(String[] args) {
        // 1. 设置配置信息
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

        // 2. 构建拦截器链
        List<String> interceptors = new ArrayList<>();
        interceptors.add("edu.sse.ustc.kafaka.interceptor.TimeInterceptor");
        interceptors.add("edu.sse.ustc.kafaka.interceptor.CounterInterceptor");

        properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptors);

        // 3. 创建生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        String topic = "firstTopic";
        // 4. 发送消息
        producer.send(new ProducerRecord<>(topic, "message" + i), new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                
            }
        })

        // 5. 关闭资源
    }
}
