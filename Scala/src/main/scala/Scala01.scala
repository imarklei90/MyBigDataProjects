/**
  * Scala 变量
  * @author imarklei90
  * @since 2019.05.26
  *
  */
object Scala01 {
  def main(args: Array[String]): Unit = {
    val age :Int = 10

    val salary : Double = 1.0

    val isPass : Boolean = true

    /**
      * 三种输出方式：
      *   1. 字符串连接 +
      *   2. printf
      *   3. $引用字符串
      */
    println(s"$age, $salary, $isPass")

    /**
      * 数据类型：
      *   AnyVal值类型
      *   AnyRef引用类型
      */
    val num : Int = 20
    println(num.toDouble)


    /**
      * 循环
      */
    for(i <- 1 to 10){
      println(i)
    }

    var list = List("Scala", 1, 2.0, true)
    for (item <- list){
      println(item)
    }


  }
}
