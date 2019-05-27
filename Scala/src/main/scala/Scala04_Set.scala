import scala.collection.mutable

/**
  * Scala Set
 *
  * @author imarklei90
  * @since 2019.05.27
  *
  */
object Scala04_Set {
  def main(args: Array[String]): Unit = {
    val set = Set(1,2,3,4,5,6,6)

    println("Set : " + set)


    val mutableSet = mutable.Set(1, "abc", true)
    // 添加
    mutableSet.add("false")
    mutableSet += 123

    // 删除
    mutableSet -= 1
    mutableSet.remove("abc")

    println("Set: " + mutableSet)

    // 遍历
    for (v <- mutableSet){
      println(v)
    }

  }
}
