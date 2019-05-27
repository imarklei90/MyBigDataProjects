import scala.collection.mutable

/**
  * Scala Map
 *
  * @author imarklei90
  * @since 2019.05.27
  *
  */
object Scala04_Map {
    def main(args: Array[String]): Unit = {

      /**
        * Scala中不可变的Map是有序的（默认），可变的Map是无序的
        */
      // 构造不可变映射
      val map1 = Map("Amiy" -> 10, "Bob" -> 20, "Mark" -> 30)
      println("Map1:" + map1)
      val value = map1("Mark")
      println(value)
      println("get().get : " + map1.get("Mark").get)
      println("getOrElse : " + map1.getOrElse("aaa", "aaa"))

      // 构造可变映射
      val map2 = mutable.Map("A" -> 10, "B" -> 20, "C" -> 30)
      println(map2)

      // 构造空映射
      val map3 = new mutable.HashMap[String, Int]()
      println(map3)

      // 创建对偶元组
//      val map4 = new mutable.HashMap(("A",10),("B",20), ("C",30))
//      println(map4)

      // CRUD
      // 更新
      val map5 = mutable.Map(("A", 1), ("B", 2), ("C",3))
      map5("A")=11111
      println("map5 : " + map5)

      // 添加
      map5 +=("D" -> 4)
      val map6 = map5 + ("E" -> 5, "F" ->6)
      println("map6 : " + map6)

      // 删除
      map5 -=("A", "B", "M")
      println("Map5: " + map5)

      // 遍历
      val map7 = mutable.Map(("A", 1), ("B", 2), ("C", 3))
      for((k, v) <- map7){
        println(k + "->" + v)
      }

      for (v <- map7.keys){
        println(v)
      }

      for (v <- map7.values){
        println(v)
      }

      for (v <- map7){
        println(v)
      }

    }
}
