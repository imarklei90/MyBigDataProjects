import scala.collection.mutable

/**
  * Scala List Queue
 *
  * @author imarklei90
  * @since 2019.05.27
  *
  */
object Scala04_List {

  def main(args: Array[String]): Unit = {

    val list01 = List(1,2,3)
    println("list01: " + list01)

    val list02 = list01:+4
    println("list02 : " + list02)


    // 定义一个空列表
    val listNil = Nil
    println("listNil : " + listNil)

    testQueue()

  }

  def testQueue(): Unit ={
    val q1 = new mutable.Queue[Int]()

    // 添加数据
    q1 += 1
    q1 +=2
    q1 ++=List(1,2,3)

    // 删除数据(头部)
    q1.dequeue()

    // 入队列（尾部）
    q1.enqueue(5,6)

    println("Queue: " + q1)
    println("Queue Head :" + q1.head)
    println("Queue Tail : " + q1.tail) // 返回除第一个以外的元素
    println("Queue Multi Tail : " + q1.tail.tail.tail)

  }

}
