package edu.sse.ustc.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * 创建Zookeeper客户端
 *
 * @author imarklei90
 * @since 2019.01.03
 */
public class zkClient {

    private ZooKeeper zkClient = null;

    // 连接Zookeeper的地址及端口号
    private String connectAddress = "172.20.10.10:2181,172.20.10.11:2181,172.20.10.12:2181";
    // 设置超时时间（单位：毫秒）
    private int sessionTimeout = 2000;

    // 创建客户端
    @Before
    public void createZkClient() throws IOException {
        zkClient = new ZooKeeper(connectAddress, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                // 监听发生后触发的事件
//                System.out.println("event : " + event);
//                System.out.println(event.getPath());
//                System.out.println(event.getState());
//                System.out.println(event.getType());
//                System.out.println(event.getWrapper());
            }
        });
    }

    // 创建子节点
    @Test
    public void createNode() throws KeeperException, InterruptedException {
        zkClient.create("/sampleNode", "sampleData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    // 获取节点下的子节点
    @Test
    public void getNodeData() throws KeeperException, InterruptedException {
        List<String> childrenLists = zkClient.getChildren("/", false);
        for (String child : childrenLists) {
            System.out.println(child);
        }
    }

    // 判断某个节点是否存在
    @Test
    public void isExisting() throws KeeperException, InterruptedException {
        Stat stat = zkClient.exists("/sampleNode", true);

        System.out.println(null == stat ? "不存在" : "存在");
    }

}
