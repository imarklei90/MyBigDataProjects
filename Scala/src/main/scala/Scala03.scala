/**
  * Scala隐式转换
  * @author imarklei90
  * @since 2019.05.27
  *
  */
object Scala03 {
  def main(args: Array[String]): Unit = {

    /**
      * 隐式转换函数（将Double类型转换为Int类型）
      * @param d Double类型
      * @return Int类型
      */
    implicit def f1(d : Double):Int ={
      d.toInt
    }

    val num : Int = 3.5
    println(num)
  }
}
