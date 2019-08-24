
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

import org.apache.flink.api.scala._

/**
  * 实时数据处理
  * @author imarklei90
  * @since 2019.08.24
  *
  */
object DataStreamApp {

  def main(args: Array[String]): Unit = {

    val environment = StreamExecutionEnvironment.getExecutionEnvironment

    val dataStream = environment.socketTextStream("hadoop101",7777)

    val sumDataStream = dataStream.flatMap(_.split(" ")).filter(_.nonEmpty).map((_,1)).keyBy(0).sum(1)

    sumDataStream.print()

    environment.execute()

  }

}
