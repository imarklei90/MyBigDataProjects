/**
  * Scala模式匹配
  * @author imarklei90
  * @since 2019.05.28
  *
  */
object Scala05_Match {
  def main(args: Array[String]): Unit = {

    val oper = '+'
    val n1 = 10
    val n2 = 20
    var result = 0

    /**
      * case中不需要使用break，程序自动中断case
      */
    oper match {
      case '+' => result = n1 + n2
      case '-' => result = n1 - n2
      case '*' => result = n1 * n2
      case '/' => result = n1 / n2
      case _ =>println("oper error")
    }

    println("result : " + result)

  }
}
