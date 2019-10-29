package edu.sse.ustc.sparksql

import org.apache.spark.sql.SparkSession

/**
  * SparkSQL
  *
  * @author imarklei90
  * @since 2019.09.21
  *
  */
object SparkSQLTest {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .master("local[2]") // 在测试或者生产中AppName和Master可以通过脚本进行指定
      .appName("Spark SQL")
      .getOrCreate()

    val df = spark.read.json("Spark/data/people.json")

    df.createOrReplaceTempView("people")

    val sqlDF = spark.sql("select * from people")

    /**
      * +----+-------+
      * | age|   name|
      * +----+-------+
      * |null|Michael|
      * |  30|   Andy|
      * |  19| Justin|
      * +----+-------+
      */
    sqlDF.show()

    /**
      * +----+-------+
      * | age|   name|
      * +----+-------+
      * |null|Michael|
      * |  30|   Andy|
      * |  19| Justin|
      * +----+-------+
      */
    spark.newSession().sql("select * from global_temp.people").show()


    spark.stop()

  }
}
