package edu.sse.ustc.weblog;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

/** Stream Bolt
 * @author imarklei90
 * @since 2019.01.22
 */
public class WebLogBolt implements IRichBolt {

    private int line_num;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {

    }

    @Override
    public void execute(Tuple tuple) {
        // 1. 获取数据
        String line = tuple.getString(0);

        // 2. 切割数据
        String[] splits = line.split("\t");
        String sessionId = splits[1];

        // 3. 统计发送行数
        line_num++;

        // 4. 打印
        System.out.println(Thread.currentThread().getId() + ", Session ID : " + sessionId + "line num : " + line_num);
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
