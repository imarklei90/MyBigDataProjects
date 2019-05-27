import scala.collection.mutable.ArrayBuffer

/**
  * Scala 数据结构
  *
  * @author imarklei90
  * @since 2019.05.27
  *
  */
object Scala04_Array {

  def main(args: Array[String]): Unit = {

    /**
      * 不可变集合：scala.collection.immutable 默认
      * 可变集合：scala.collection.mutable
      *
      * 集合三大类：
      * 序列Seq
      * 集Set
      * 映射Map
      */

    /**
      * 定长数组
      */
    val arr01 = new Array[Int](10)
    arr01(1) = 1
    for (i <- arr01){
      println(i)
    }

    /**
      * 定长数组
      */
    val arr02 = Array(1, "abc", true, 2.0)
    for (arr <- arr02){
      println(arr)
    }

    /**
      * 变长数组 ArrayBuffer类似于Java的ArrayList
      */
    val arr03 = ArrayBuffer[Any](1,2,3)
    println(arr03(1))

    // 添加
    arr03.append(true)
    arr03.append("abc")

    println(arr03(3))

    // 修改
    arr03(1) = 1.0

    // 删除
    arr03.remove(2)

    println("arr03 : " + arr03)

    /**
      * 定长数组与变长数组的转换
      * toBuffer
      * toArray
      */

    val arr1 = arr01.toBuffer
    arr1.append(12345)
    println("arr1:" + arr1)

    val arr3 = arr03.toArray
    arr3(2) = "arr3"
    println("arr3: " + arr3)

//    scalaArrayBufferConvertToJavaList();

  }

  /*def scalaArrayBufferConvertToJavaList(): Unit = {
    val array = ArrayBuffer("1","2","3")

    implicit def bufferAsJavaList[A](b: scala.collection.mutable.Buffer[A]):java.util.List[A]={

    }

    import scala.collection.JavaConversions.bufferAsJavaList

    val javaArray = new ProcessBuilder(array)
    val arrList = javaArray.command()
    println(arrList)
  }
*/
}
