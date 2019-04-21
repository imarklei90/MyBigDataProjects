package edu.sse.ustc.elasticsearch.weblog;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.io.*;
import java.util.Map;

/** Storm Spout
 * @author imarklei90
 * @since 2019.01.22
 */
public class WebLogSpout implements IRichSpout {

    private BufferedReader reader;
    private SpoutOutputCollector spoutOutputCollector;
    private String data;

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.spoutOutputCollector = spoutOutputCollector;

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(System.getProperty("user.dir") +
                    File.separator + "Storm/data/" + "log.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void close() {

    }

    @Override
    public void activate() {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void nextTuple() {
        // 发送数据
        try {
            while ((data = reader.readLine()) != null){
                spoutOutputCollector.emit(new Values(data));
                Thread.sleep(500);
            }
        }catch (Exception e){

        }
    }

    @Override
    public void ack(Object o) {

    }

    @Override
    public void fail(Object o) {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("log"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
