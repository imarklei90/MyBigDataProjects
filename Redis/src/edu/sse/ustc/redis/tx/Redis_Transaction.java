package edu.sse.ustc.redis.tx;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/** Redis Transaction
 * @author imarklei90
 * @since 2019.04.17
 */
public class Redis_Transaction {
    public static void main(String[] args) throws InterruptedException {
        Jedis jedis = new Jedis("192.168.188.69", 6379);

        // 开启事务
        // Transaction transaction = jedis.multi();

        // transaction.set("k5", "v5");
        // transaction.set("k6", "v6");
        // 执行事务
        // transaction.exec();
        // 放弃事务
//        transaction.discard();

        new Redis_Transaction().execTx();
    }

    public boolean execTx() throws InterruptedException {
        Jedis jedis = new Jedis("192.168.188.69", 6379);
        int balance; // 可用余额
        int debt; // 欠额
        int consume = 10; // 消费金额

        jedis.watch("balance");
        Thread.sleep(5000);
        balance = Integer.parseInt(jedis.get("balance"));
        if(balance < consume){
            jedis.unwatch();
            System.out.println("Data Update Before Commit");
            return false;
        }else{
            System.out.println("----- Transaction -----");
            Transaction transaction = jedis.multi();
            transaction.decrBy("balance", consume);
            transaction.incrBy("debt", consume);
            transaction.exec();

            balance = Integer.parseInt(jedis.get("balance"));
            debt = Integer.parseInt(jedis.get("debt"));

            System.out.println("balance : " + balance + "debt : " + debt);
            return true;
        }
    }
}
