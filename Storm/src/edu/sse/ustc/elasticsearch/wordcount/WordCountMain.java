package edu.sse.ustc.elasticsearch.wordcount;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/** WordCount 驱动
 * @author imarklei90
 * @since 2019.01.23
 */
public class WordCountMain {
    public static void main(String[] args) {

        // 创建一个Topology
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("WordCountSpout", new WordCountSpout(), 1);
        builder.setBolt("WordCountSplitBolt", new WordCountSplitBolt(), 4).fieldsGrouping("WordCountSpout", new Fields("wordcount"));
        builder.setBolt("WordCountBolt", new WordCountBolt(), 2).fieldsGrouping("WordCountSplitBolt", new Fields("word"));

        // 创建配置信息
        Config config = new Config();
        config.setNumWorkers(2);

        // 提交
        if(args.length > 0){
            // 集群提交
            try {
                StormSubmitter.submitTopology(args[1], config, builder.createTopology());
            } catch (AlreadyAliveException e) {
                e.printStackTrace();
            } catch (InvalidTopologyException e) {
                e.printStackTrace();
            } catch (AuthorizationException e) {
                e.printStackTrace();
            }
        }else{
            // 本地提交
            LocalCluster localCluster = new LocalCluster();
            localCluster.submitTopology("wordCount", config, builder.createTopology());
        }
    }
}
