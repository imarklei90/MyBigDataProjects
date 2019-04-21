package edu.sse.ustc.elasticsearch.wordcount;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/** WordCount Split Bolt
 * @author imarklei90
 * @since 2019.01.23
 */
public class WordCountSplitBolt extends BaseRichBolt {

    private OutputCollector outputCollector;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.outputCollector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {

        // 接收数据
        String line = tuple.getString(0);

        // 切割数据
        String[] splits = line.split(" ");

        // 发送数据
        for (String data : splits) {
            outputCollector.emit(new Values(data, 1));
        }

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        // 声明字段
        outputFieldsDeclarer.declare(new Fields("word", "num"));
    }
}
