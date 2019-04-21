package edu.sse.ustc.elasticsearch.weblog;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;

/**
 * @author imarklei90
 * @since 2019.01.22
 */
public class WebLogMain {
    public static void main(String[] args) {
        // 1. 创建拓扑
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("WebLogSpout", new WebLogSpout(), 1);
        builder.setBolt("WebLogBolt", new WebLogBolt(), 1).shuffleGrouping("WebLogSpout");

        // 2. 创建配置对象信息
        Config config = new Config();
        config.setNumWorkers(2);

        // 3. 提交程序
        if(args.length > 0){ // 集群提交
            try {
                StormSubmitter.submitTopology(args[0], config, builder.createTopology());
            } catch (AlreadyAliveException e) {
                e.printStackTrace();
            } catch (InvalidTopologyException e) {
                e.printStackTrace();
            } catch (AuthorizationException e) {
                e.printStackTrace();
            }
        }else{ // 本地提交
            LocalCluster localCluster = new LocalCluster();
            localCluster.submitTopology("WebTopology", config, builder.createTopology());
        }
    }
}
