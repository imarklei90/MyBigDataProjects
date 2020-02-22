package edu.sse.ustc.sparkstreaming

import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Durations, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Spark Streaming Demo
  *
  * @author imarklei90
  * @since 2020.02.22
  *
  */
object BasicSparkStreamingDemo {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("BasicSparkStreamingDemo").setMaster("local[*]")

    val sparkContext = new SparkContext(conf)

    val ssc = new StreamingContext(sparkContext,Durations.seconds(1))

    // 获取数据
    val logs: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop101", 66666)

    // 计算
    val result: DStream[(String, Int)] = logs.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_)

    // 输出
    result.print()

    // 提交作业到集群，启动Executor执行
    ssc.start()
    // 线程等待，等待Executor执行完成
    ssc.awaitTermination()


  }

}
