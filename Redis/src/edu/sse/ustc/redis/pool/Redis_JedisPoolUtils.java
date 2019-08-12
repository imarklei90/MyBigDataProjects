package edu.sse.ustc.redis.pool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/** Redis Connection Pool 单例模式
 * @author imarklei90
 * @since 2019.04.17
 */
public class Redis_JedisPoolUtils {
    private static volatile JedisPool jedisPool = null;

    private Redis_JedisPoolUtils(){

    }

    // DCL(Double Check Look) 双端检锁
    public static JedisPool getInstance(){
        if(null == jedisPool){
            synchronized (Redis_JedisPoolUtils.class){
                if(null == jedisPool){
                    JedisPoolConfig poolConfig = new JedisPoolConfig();
                    poolConfig.setMaxActive(100);
                    poolConfig.setMaxIdle(32);
                    poolConfig.setMaxWait(100 * 1000);
                    poolConfig.setTestOnBorrow(true);
                    jedisPool = new JedisPool(poolConfig, "192.168.188.69", 6379);
                }
            }
        }
        return jedisPool;
    }

    // 回收资源
    public static void close(JedisPool jedisPool, Jedis jedis){
        if(null != jedis){
            jedisPool.returnResourceObject(jedis);
        }
    }
}
