package edu.sse.ustc.redis.basic;

import redis.clients.jedis.Jedis;

/** Redis 连接
 * @author imarklei90
 * @since 2019.04.17
 */
public class Redis_Basic {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.188.69", 6379);
        String result = jedis.ping();
        System.out.println("ping : " + result);
    }
}
