package edu.sse.ustc.redis.advanced;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/** Redis Pipeline
 * @author imarklei90
 * @since 2019.08.12
 */
public class RedisPipeline {

	public static void main(String[] args) throws InterruptedException {
		Jedis jedis = new Jedis("172.20.10.2", 6379);

		long start = System.currentTimeMillis();

		for (int i = 0; i < 1000; i++) {
			jedis.set(String.valueOf(i), String.valueOf(i));
		}

		long end = System.currentTimeMillis();

		System.out.println("Time Cost: " + (end - start));

		System.out.println("------------------");

		long start_pipeline = System.currentTimeMillis();

		// 先创建一个pipeline的链接对象
		Pipeline pipelined = jedis.pipelined();

		for (int i = 0; i < 1000; i++) {
			pipelined.set(String.valueOf(i), String.valueOf(i));
		}

		// 获取所有的Response
		pipelined.sync();

		long end_pipeline = System.currentTimeMillis();
		System.out.println("Pipe Time Cost: " + (end_pipeline - start_pipeline));

		System.out.println("------------------");
		long stat_queue = System.currentTimeMillis();

		LinkedBlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();
		for (int i = 0; i < 1000; i++) {
			blockingQueue.put(String.valueOf(i));
		}

		long end_queue = System.currentTimeMillis();
		System.out.println("Blocking Queue Time Cost :" + (end_queue - stat_queue));

	}
}
