/**
  * Scala函数式编程
  * @author imarklei90
  * @since 2019.05.26
  *
  */
object Scala02 {
  def main(args: Array[String]): Unit = {
    val n1 = 10
    val n2 = 20

    println(operation(n1, n2, '+'));

  }

  def operation(n1 : Int, n2 : Int, operator : Char) = {
    if (operator == '+'){
        n1 + n2
    }
  }

}
