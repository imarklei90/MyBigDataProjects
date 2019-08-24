import java.io.File

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._

/**
  * 离线数据处理
  * @author imarklei90
  * @since 2019.08.24
  *
  */
object DataSetApp {

  def main(args: Array[String]): Unit = {
    // env
    val environment = ExecutionEnvironment.getExecutionEnvironment

    val filePath = System.getProperty("user.dir")  + File.separator + "Flink" + File.separator + "data" + File.separator + "input.txt"

    // source
    val dataSet = environment.readTextFile(filePath)

    // transformer
    val sumDataSet = dataSet.flatMap(_.split(" ")).map((_,1)).groupBy(0).sum(1)

    // sink
    //sumDataSet.print()

    // 输出
    sumDataSet.writeAsCsv("").setParallelism(1)

  }

}
