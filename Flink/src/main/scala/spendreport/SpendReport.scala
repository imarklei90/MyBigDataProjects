package spendreport

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.table.api.Tumble
import org.apache.flink.table.api.scala.BatchTableEnvironment
import org.apache.flink.walkthrough.common.table.{BoundedTransactionTableSource, TruncateDateToHour}

/**
 * Table API
 * 批处理
 * @since 2020.8.11
 */
object SpendReport {

  def main(args: Array[String]): Unit = {

    // 获取批处理的执行环境
    val env: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment

    // 将 env 包装进BatchTableEnvironment中，能够使用所有的Table API
    val tEnv = BatchTableEnvironment.create(env)
    /**
     * 注册表：将表注册到运行环境之中，可以连接外部系统读写数据（批数据或者流数据）
     * Source提供对存储再外部系统中的数据的访问，如：数据库、k-v存储、MQ、FileSystem
     * Sink将表中的数据发送到外部存储系统
     * 根据Source、Sink的类型，支持不同的格式，如：csv、json、avro、parquet
     */
    tEnv.registerTableSource("transactions", new BoundedTransactionTableSource)
    tEnv.registerTableSink("spend_report", new )

    // 注册UDF函数：将时间戳向下舍入到最接近的小时
    val truncateDateTohour = new TruncateDateToHour

    // 查询
    tEnv.scan("transactions")
      //.select("accountId", truncateDateTohour("timestamp") as "timestamp", "amount")
      /**
       * 添加window：基于时间的分组就是window，
       * Tumble Window滚动窗口，具有固定大小且窗口之间不重叠
       */
      .window(Tumble over 1.hour on "timestamp" as w)
      .groupBy("accountId", "timestamp", "amount sum as total")
      .insertInto("spend_report")

    // 执行Job
    env.execute("Spend Report")

  }
}
