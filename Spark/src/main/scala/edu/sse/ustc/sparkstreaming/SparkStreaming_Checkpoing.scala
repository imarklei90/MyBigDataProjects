package edu.sse.ustc.sparkstreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Spark Streaming - Checkpoint Demo
  *
  * @author imarklei90
  * @since 2020.02.22
  *
  */
object SparkStreaming_Checkpoing {
  def main(args: Array[String]): Unit = {

    val checkpointPath = "dir"

    val context: StreamingContext = StreamingContext.getOrCreate(checkpointPath, functionToCreateContext _)

    context.start()
    context.awaitTermination()

  }

  def functionToCreateContext:StreamingContext ={
    val sparkConf = new SparkConf().setAppName("Streaming_Checkpoint").setMaster("local[2]")
    val ssc: StreamingContext = new StreamingContext(sparkConf, Seconds(1))

    ssc.checkpoint("")

    ssc

  }
}
