package edu.sse.ustc.sparksql

import org.apache.spark.sql.SparkSession


/**
  * Spark SQL
  * @author imarklei90
  * @since 2019.09.21
  *
  */
object SparkSessionTest {

  def main(args: Array[String]): Unit = {
    // 创建SparkSession对象
   val spark = SparkSession
     .builder()
     .master("local[2]")
     .appName("Spark SQL Basic Example")
     .config("spark.some.config.option", "some-value")
     .getOrCreate()

    import spark.implicits._

    val df = spark.read.json("Spark/data/people.json")

    /**
      * root
      * |-- age: long (nullable = true)
      * |-- name: string (nullable = true)
      */
    df.printSchema()

    /**
      * +----+-------+
      * | age|   name|
      * +----+-------+
      * |null|Michael|
      * |  30|   Andy|
      * |  19| Justin|
      * +----+-------+
      */
    df.show()

    /**
      * +-------+
      * |   name|
      * +-------+
      * |Michael|
      * |   Andy|
      * | Justin|
      * +-------+
      */
    df.select("name").show()

    /**
      * +-------+----------+
      * |   name|(age + 10)|
      * +-------+----------+
      * |Michael|      null|
      * |   Andy|        40|
      * | Justin|        29|
      * +-------+----------+
      */
    df.select($"name", $"age" + 10).show()

    /**
      * +---+----+
      * |age|name|
      * +---+----+
      * | 30|Andy|
      * +---+----+
      */
    df.filter($"age" > 20).show()

    /**
      * +----+-----+
      * | age|count|
      * +----+-----+
      * |  19|    1|
      * |null|    1|
      * |  30|    1|
      * +----+-----+
      */
    df.groupBy("age").count().show()

    spark.stop()

  }

}
