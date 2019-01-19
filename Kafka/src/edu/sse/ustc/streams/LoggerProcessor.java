package edu.sse.ustc.streams;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;

/** 添加日志处理器
 * @author imarklei90
 * @since 2019.01.18
 */
public class LoggerProcessor implements Processor<byte[], byte[]> {

    private ProcessorContext context;

    @Override
    public void init(ProcessorContext processorContext) {
        // 初始化
        this.context = processorContext;
    }

    @Override
    public void process(byte[] key, byte[] value) {
        // 业务逻辑

        // 1. 获取数据
        String input = new String(value);

        // 2. 处理数据
        if(input.contains(">>>")){
            String[] split = input.split(">>>");

            // 3. 输出
            context.forward(key, split[1].trim());
        }else{
            context.forward(key, value);
        }
    }

    @Override
    public void punctuate(long l) {
        //  时间戳
    }

    @Override
    public void close() {
        // 关闭资源
    }
}
