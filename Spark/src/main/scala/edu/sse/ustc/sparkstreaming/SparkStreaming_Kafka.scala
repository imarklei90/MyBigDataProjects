package edu.sse.ustc.sparkstreaming

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Milliseconds, Seconds, StreamingContext}

/**
  * Spark Streaming 操作Kafka
  *
  * @author imarklei90
  * @since 2020.02.22
  *
  */
object SparkStreaming_Kafka {

  def main(args: Array[String]): Unit = {

    val checkpointDir = "Spark/data/cp-20200222-1"

    val ssc: StreamingContext = StreamingContext.getOrCreate(checkpointDir,() => createContext)

    ssc.start()
    ssc.awaitTermination()

  }

  def createContext: Unit ={
    val conf = new SparkConf().setAppName("Streaming_Kafka").setMaster("local[2]")
    val ssc = new StreamingContext(conf, Milliseconds(5000))

    // 指定请求Kafka的配置信息
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "hadoop101:9092,hadoop102:9092,hadoop103:9092",
      // 指定Key的反序列化方式
      "key.deserializer" -> classOf[StringDeserializer],
      // 指定value的反序列化方式
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "group1",
      // 指定消费位置
      "auto.offset.reset" -> "latest",
      // 如果value合法，自动提交offset
      "enable.auto.commit" -> true
    )

    val topics = Array("topicA", "topicB")

    // 消费数据
    val stream: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream(
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topics, kafkaParams)
    )

    stream.map(record => (record.key, record.value))



  }

}
