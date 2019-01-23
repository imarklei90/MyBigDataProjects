package edu.sse.ustc.wordcount;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;

/** WordCount Bolt
 * @author imarklei90
 * @since 2019.01.23
 */
public class WordCountBolt extends BaseRichBolt {

    private Map<String, Integer> wordCountMaps = new HashMap<>();

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {

    }

    @Override
    public void execute(Tuple tuple) {

        // 获取数据
        String word = tuple.getString(0);
        Integer num = tuple.getInteger(1);

        // 业务逻辑
        if(wordCountMaps.containsKey(word)){
            Integer count = wordCountMaps.get(word);
            wordCountMaps.put(word, count + num);
        }else{
            wordCountMaps.put(word, num);
        }

        // 输出
        System.out.println(Thread.currentThread().getId() + ", word : " + word + ", num : " + wordCountMaps.get(word));

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
