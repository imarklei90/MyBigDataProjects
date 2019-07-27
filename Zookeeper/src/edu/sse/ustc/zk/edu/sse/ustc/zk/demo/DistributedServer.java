package edu.sse.ustc.zk.edu.sse.ustc.zk.demo;

import org.apache.zookeeper.*;

import java.io.IOException;

/** 服务器动态上下线
 * @author imarklei90
 * @since 2019.07.27
 */
public class DistributedServer {

	private static String connectString = "hadoop101:2181,hadoop102:2181,hadoop103:2181";

	private ZooKeeper zk;
	private int sessionTimeout = 2000;

	/**
	 * 创建连接
	 */
	public void getConnection() throws IOException {
		new ZooKeeper(connectString, sessionTimeout, new Watcher() {
			@Override
			public void process(WatchedEvent event) {

			}
		});
	}

	/**
	 * 注册服务器
	 * @param hostname 主机名
	 */
	public void registeServer(String hostname) throws KeeperException, InterruptedException {
		zk.create("/servers", hostname.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
	}

	public void business(String hostname) throws InterruptedException {
		System.out.println("hostname is " + hostname);
		Thread.sleep(Long.MAX_VALUE);
	}

	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		DistributedServer distributedServer = new DistributedServer();
		distributedServer.getConnection();
		distributedServer.registeServer(args[0]);
		distributedServer.business(args[0]);

	}



}
