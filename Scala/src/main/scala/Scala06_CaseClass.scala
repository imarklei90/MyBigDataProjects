/**
  * Scala样例类
  * @author imarklei90
  * @since 2019.05.28
  *
  */
object Scala06_CaseClass {
  def main(args: Array[String]): Unit = {

    for (amt <- Array(Dollar(1000.0), Currency(2000.0, "RMB"), NoAccount)){
      val result = amt match {
        case Dollar(v) => "$" +v
        case Currency(v,u) => v + " " + u
        case NoAccount => ""//""
      }

      println(amt + "-> " + result)
    }

  }

  abstract class Account
  case class Dollar(value: Double) extends Account
  case class Currency(value: Double, unit: String) extends Account
  case object NoAccount extends Account
}
