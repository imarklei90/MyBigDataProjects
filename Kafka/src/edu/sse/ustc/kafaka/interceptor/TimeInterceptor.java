package edu.sse.ustc.kafaka.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/** Kafka 拦截器
 * @author imarklei90
 * @since 2019.01.17
 */
public class TimeInterceptor implements ProducerInterceptor<String, String> {

    @Override
    public void configure(Map<String, ?> map) {
        // 获取配置信息、初始化操作
    }
    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {
        // 拦截数据后，对数据进行加工处理

        // 在Value值上增加时间戳前缀
        return new ProducerRecord<>(producerRecord.topic(), producerRecord.partition(), producerRecord.timestamp(),
                producerRecord.key(), System.currentTimeMillis() + "," + producerRecord.value());
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {

    }

    @Override
    public void close() {

    }


}
