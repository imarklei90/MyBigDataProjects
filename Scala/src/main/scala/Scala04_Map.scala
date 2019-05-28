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
      println("get().get : " + map1.get("Mark"))
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

      // Map映射高阶函数
      testMapAdvanced()

      testFlatMap()

      testFilter()

    }

  /**
    * Map高阶函数
    */
    def testMapAdvanced(): Unit ={
      val list = List(1,2,3,4,5)

      val list2 = list.map(multiple)
      println("List 2: " + list2)

    }

    def multiple(n : Int):Int ={
      2 * n
    }

    /**
      * FlatMap : 将集合中的每一个元素的子元素映射到某个函数并返回新的集合
      */
    def testFlatMap(): Unit ={
      val names = List("A", "B", "C")
      val names2 = names.flatMap(upper)
      println("Names : " + names2)

    }

    def upper(s : String): String ={
      s.toUpperCase
    }

    def testFilter(): Unit ={
      val names = List("Alice", "Bob", "Mark", "Mic")

      val names2 = names.filter(startA)

      println("Names2 :" + names2)
    }

    def startA(str : String): Boolean ={
      str.startsWith("M")
    }


}
