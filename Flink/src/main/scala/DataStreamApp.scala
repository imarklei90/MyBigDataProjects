
import org.apache.flink.api.java.utils.ParameterTool
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

    var host = "localhost"
    var port = 7777

/*    // 获取参数
    host = try{
      ParameterTool.fromArgs(args).get(host)
    }catch {
      case e: Exception => {
        System.err.println("No host specified. Please run 'DataStreamApp --host <host> --port <port>'")
        return
      }
    }

    port = try{
      ParameterTool.fromArgs(args).getInt("port")
    }catch {
      case e: Exception => {
        System.err.println("No port specified. Please run 'DataStreamApp --host <host> --port <port>'")
        return
      }
    }*/

    // 获取流处理的执行环境
    val environment = StreamExecutionEnvironment.getExecutionEnvironment

    // 获取Socket文本数据
    val dataStream = environment.socketTextStream(host,port)

    val sumDataStream = dataStream.flatMap(_.split(" ")).filter(_.nonEmpty).map((_,1)).keyBy(0).sum(1)

    // 结果输出
    sumDataStream.print().setParallelism(1)

    // 执行Job
    environment.execute() // 使用默认的job名称执行任务

    /**
     * 执行脚本：
     *  ./bin/spark run ***.jar --host localhost --port 7777
     */

  }

}
