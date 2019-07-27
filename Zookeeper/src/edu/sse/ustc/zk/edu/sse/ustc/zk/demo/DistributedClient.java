package edu.sse.ustc.zk.edu.sse.ustc.zk.demo;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** 服务器动态上下线 - 客户端
 * @author imarklei90
 * @since 2019.07.27
 */
public class DistributedClient {

	private static final String connectString = "hadoop101:2181,hadoop102:2181,hadoop103:2181";
	private static int sessionTimeout = 2000;
	private ZooKeeper zk = null;

	/**
	 * 创建客户端连接
	 */
	public void getConnection() throws IOException {
		zk = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				try {
					getServerLists();
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void getServerLists() throws KeeperException, InterruptedException {
		// 获取服务器节点信息，监听父节点
		List<String> children = zk.getChildren("/server", true);

		// 存储服务器列表信息
		ArrayList<String> serverLists = new ArrayList<>();

		// 遍历节点，获取主机名称信息
		for (String child: children) {
			byte[] data = zk.getData("/server/" + child, false, null);
			serverLists.add(new String(data));
		}

		// 打印服务器列表
		System.out.println(serverLists);
	}

	// 业务功能
	public void business() throws Exception{

		System.out.println("client is working ...");
		Thread.sleep(Long.MAX_VALUE);
	}

	public static void main(String[] args) throws Exception {

		// 1获取zk连接
		DistributedClient client = new DistributedClient();
		client.getConnection();

		// 2获取servers的子节点信息，从中获取服务器信息列表
		client.getServerLists();

		// 3业务进程启动
		client.business();
	}


}
