package util

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.connectors.kafka.{FlinkKafkaConsumer011, FlinkKafkaProducer011}

/**
  * Kafka Utils
  *
  * @author imarklei90
  * @since 2019.08.24
  *
  */
object MyKafkaUtils {

  val prop = new Properties()

  prop.setProperty("bootstrap.servers", "hadoop101:9002")
  prop.setProperty("group.id", "flink")


  def getKafkaSource(topic : String): FlinkKafkaConsumer011[String] ={
    val kafkaConsumer = new FlinkKafkaConsumer011[String](topic, new SimpleStringSchema(), prop)
    kafkaConsumer
  }

  def getKafkaSink(topic : String)={
    new FlinkKafkaProducer011[String]("hadoop101:9092", topic,new SimpleStringSchema())
  }

}
