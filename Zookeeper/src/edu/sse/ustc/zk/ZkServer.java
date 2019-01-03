package edu.sse.ustc.zk;

import org.apache.zookeeper.*;
import sun.rmi.runtime.Log;

import java.io.IOException;

/**
 * Zookeeper上下线(服务端)
 *
 * @author imarklei90
 * @since 2019.01.03
 */
public class ZkServer {

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

            }
        });
    }

    // 2. 注册
    public void regist() throws KeeperException, InterruptedException {
        zk.create(parentNode + "/server", "newData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
    }

    // 3. 业务逻辑
    public void business() throws InterruptedException {
        System.out.println("Business ...");
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZkServer zkServer = new ZkServer();
        // 1. 建立连接
        zkServer.getConnect();

        // 2. 注册（创建节点）
        zkServer.regist();

        // 3. 业务逻辑
        zkServer.business();
    }
}
