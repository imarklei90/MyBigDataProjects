import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import util.MyKafkaUtils

import org.apache.flink.api.scala._

/**
  *
  * @author imarklei90
  * @since 2019.08.24
  *
  */
object StreamApiApp {

  def main(args: Array[String]): Unit = {

    val environment = StreamExecutionEnvironment.getExecutionEnvironment

    val kafkaSource = MyKafkaUtils.getKafkaSource("flink_topic")

    val dStream = environment.addSource(kafkaSource)

    dStream.print()

  }

}
