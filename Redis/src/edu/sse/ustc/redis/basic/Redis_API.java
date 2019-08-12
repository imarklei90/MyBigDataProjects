package edu.sse.ustc.redis.basic;

import redis.clients.jedis.Jedis;

import java.util.Set;

/** Redis Basic API
 * @author imarklei90
 * @since 2019.04.17
 */
public class Redis_API {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.188.69", 6379);

        Set<String> keys = jedis.keys("*");
        System.out.println(keys.size());

        jedis.set("k3", "v3");

        System.out.println(jedis.get("k3"));
    }
}
