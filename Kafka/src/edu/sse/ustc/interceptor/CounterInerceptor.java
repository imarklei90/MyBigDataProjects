package edu.sse.ustc.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/** Kafka拦截器
 * @author imarklei90
 * @since 2019.01.17
 */
public class CounterInerceptor implements ProducerInterceptor<String, String> {

    private int successCounter = 0; // 发送成功的次数
    private int failedCounter = 0; // 发送失败的次数


    @Override
    public void configure(Map<String, ?> map) {

    }

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {
        // 直接返回输入对象
        return producerRecord;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception exception) {
        // 发送回调接口
        if(exception != null){
            successCounter++; // 发送成功
        }else{
            failedCounter++; // 发送失败
        }

    }

    @Override
    public void close() {
        // 最终统计输出
    }


}
