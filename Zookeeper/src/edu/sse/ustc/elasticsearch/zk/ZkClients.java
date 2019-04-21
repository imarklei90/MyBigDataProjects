package edu.sse.ustc.elasticsearch.zk;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**ZooKeeper上下线（客户端）
 * @author imarklei90
 * @since 2019.0.03
 */
public class ZkClients {

    private ZooKeeper zk = null;
    private String parentNode = "/servers";

    // 连接Zookeeper的地址及端口号
    private String connectAddress = "172.20.10.10:2181,172.20.10.11:2181,172.20.10.12:2181";
    // 设置超时时间（单位：毫秒）
    private int sessionTimeout = 2000;

    // 1. 获取连接
    public void getConnect() throws IOException {
        zk = new ZooKeeper(connectAddress, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println(event.getPath() + " ----> " + event.getType());
                // 保证循环监听
                try {
                    getServers();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // 2. 监听节点变化
    public void getServers() throws KeeperException, InterruptedException {
        // 监听ParentNode路径子节点
        List<String> children = zk.getChildren(parentNode, true);

        List<String> servers = new ArrayList<>();

        // 获取所有子节点 信息
        for (String child : children) {
            byte[] data = zk.getData(parentNode + "/" + child, false, null);

            // 保存信息
            servers.add(new String(data));
        }

        System.out.println(servers);

    }

    // 3. 业务逻辑
    public void business() throws InterruptedException {
        System.out.println("Business ...");
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZkClients zkClient = new ZkClients();
        // 1. 获取连接
        zkClient.getConnect();

        // 2. 监听节点变化
        zkClient.getServers();

        // 3. 业务逻辑
        zkClient.business();

    }
}
