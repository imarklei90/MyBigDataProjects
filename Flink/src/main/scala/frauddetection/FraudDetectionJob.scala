package frauddetection

import org.apache.flink.streaming.api.scala._
import org.apache.flink.walkthrough.common.entity.{Alert, Transaction}
import org.apache.flink.walkthrough.common.sink.AlertSink
import org.apache.flink.walkthrough.common.source.TransactionSource

/**
 * 信用卡反欺诈信用检测系统
 *
 * @since 2020.8.10
 */
object FraudDetectionJob {

  @throws[Exception]
  def main(args: Array[String]): Unit = {

    // 设置 Flink 的执行环境：用于定义任务的属性、创建数据源、启动任务的执行
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    // 创建数据源：从外部系统（如：Kafka、Rabbit MQ等接收数据，然后将数据发送到Flink中）
    val transcations: DataStream[Transaction] = env
      .addSource(new TransactionSource)
      .name("transactions") // 绑定name是为了方便调试，在发生异常时能够快速定位问题

    // 对事件进行分区&欺诈检测
    val alerts : DataStream[Alert] = transcations
      .keyBy(transcations => transcations.getAccountId) // 同一个账号的所有交易行为数据要被同一个并发的task进行处理
      .process(new FraudDetector) // 对数据流绑定了一个操作，将会对流上的每一个消息调用定义好的函数（通常一个操作会紧跟着keyBy被调用）
      .name("fraud-detector")

    // 输出结果
    alerts.addSink(new AlertSink) // Sink会将DataStream写到外部系统，如Kafka、Cassandra等。AlertSink使用INFO的日志级别打印每个Alert的数据记录
      .name("send-alerts")

    // 运行Job：Flink是懒加载的，并且只有在完全搭建好之后，才能够发布到集群上执行，
    env.execute("Fraud  Detection") // 给任务传递一个任务名参数，就可以运行任务了


  }
}
