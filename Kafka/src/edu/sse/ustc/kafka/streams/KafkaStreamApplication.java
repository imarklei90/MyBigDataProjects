package edu.sse.ustc.kafka.streams;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorSupplier;
import org.apache.kafka.streams.processor.TopologyBuilder;

import java.util.Properties;

/** Kafka Stream
 * @author imarklei90
 * @since 2019.01.18
 */
public class KafkaStreamApplication {
    public static void main(String[] args) {
        // 1. 设置配置信息
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "filter");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop101:9092");

        StreamsConfig streamsConfig = new StreamsConfig(properties);

        // 2. 创建拓扑
        TopologyBuilder builder = new TopologyBuilder();
        // 参数说明： source表示输入源 first表示输入源的Topic
        builder.addSource("source", "firstTopic")
                // processor:处理数据阶段的名称，ProcessorSupplier具体处理数据的方法，source：本次模块的输入源
                .addProcessor("processor", new ProcessorSupplier<byte[], byte[]>() {
                    @Override
                    public Processor<byte[], byte[]> get() {
                        return new LoggerProcessor();
                    }
                }, "source")
                // 参数说明：second：
                .addSink("sink", "second", "processor");

        // 3. 创建输出流程
        KafkaStreams streams = new KafkaStreams(builder, properties);

        streams.start();
    }
}
