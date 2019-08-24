/**
  * scala lazy: 惰性加载，实现延迟加载
  * 是不可变变量，只有在调用惰性变量时，才会实例化变量
  * @author imarklei90
  * @since 2019.08.14
  *
  */
class ScalaLazyDemo {

}

object ScalaLazyDemo{

  def init(): Unit = {
    print("call init")
  }

  def main(args: Array[String]): Unit = {
    val property = init()
    print("after init()")
    print(property)
  }
}

object ScalaLazy2{
  def init() : Unit ={
    print("lazy call init")
  }

  def main(args: Array[String]): Unit = {
    lazy val property = init()
    print("after init")
    print(property)
  }
}
