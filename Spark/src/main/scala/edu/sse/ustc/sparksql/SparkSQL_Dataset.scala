package edu.sse.ustc.sparksql

import org.apache.spark.sql.SparkSession

/**
  * Spark SQL Datasets
  *
  * @author imarklei90
  * @since 2019.09.21
  *
  */
object SparkSQL_Dataset {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .master("local[2]")
      .appName("Spark SQL Dataset")
      .getOrCreate()

    import spark.implicits._

    /**
      * +----+---+
      * |name|age|
      * +----+---+
      * |Andy| 32|
      * +----+---+
      */
    val peopleDS = Seq(Person("Andy", 32)).toDS()

    peopleDS.show()

    val primitiveDS = Seq(1,2,3).toDS

    primitiveDS.map(_+1).collect()

    /**
      * +----+-------+
      * | age|   name|
      * +----+-------+
      * |null|Michael|
      * |  30|   Andy|
      * |  19| Justin|
      * +----+-------+
      */
    // DF -> DS
    val personDS2 = spark.read.json("Spark/data/people.json").as[Person]

    personDS2.show()
  }
}

case class Person(name: String, age : Long)
