package edu.sse.ustc.sparksql

import org.apache.spark.sql.SparkSession

/**
  * SparkSQL-Reflection
 *
  * @author imarklei90
  * @since 2019.09.21
  *
  */
object SparkSQL_UsingReflection {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .appName("Spark SQL Using Reflection")
      .master("local[2]")
      .getOrCreate()

    import spark.implicits._

    // read txt file,convert to DF
    val peopleDF = spark.sparkContext
        .textFile("Spark/data/people.txt")
        .map(_.split(","))
        .map(attr => Person2(attr(0), attr(1).trim.toLong))
        .toDF

    peopleDF.filter(peopleDF.col("age") > 20).show()

    peopleDF.createOrReplaceTempView("people")

    val teenagersDF = spark.sql("select name,age from people where age between 13 and 19")

    teenagersDF.map(teenager => "Name: " + teenager(0)).show()

    teenagersDF.map(teenager => "Name: " + teenager.getString(0))

    spark.stop

  }

}

case class Person2(name:String, age:Long)
