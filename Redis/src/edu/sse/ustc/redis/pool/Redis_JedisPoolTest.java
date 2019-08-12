package edu.sse.ustc.redis.pool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/** JedisPool Test
 * @author imarklei90
 * @since 2019.04.17
 */
public class Redis_JedisPoolTest {
    public static void main(String[] args) {
        JedisPool jedisPool = Redis_JedisPoolUtils.getInstance();

        Jedis jedis = null;

        try{
            jedis = jedisPool.getResource();
            jedis.set("jedisPool", "jedisPool");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            Redis_JedisPoolUtils.close(jedisPool, jedis);
        }
    }

}
