package edu.sse.ustc.redis;

import redis.clients.jedis.Jedis;

/** Redis Replication 读写分离
 * @author imarklei90
 * @since 2019.04.17
 */
public class Redis_Replication {
    public static void main(String[] args) {
        Jedis jedisMaster = new Jedis("192.168.188.69", 6379);
        Jedis jedisSlaver = new Jedis("192.168.188.70", 6380);

        jedisSlaver.slaveof("192.168.188.69", 6370);

        jedisMaster.set("master", "slaver");

        String result = jedisSlaver.get("master");
        System.out.println("result : " + result);
    }
}
