package lectures.part_3_fp

import scala.util.Random

object Sequences extends App {

  // Seq
  val aSequence = Seq(1, 2, 3, 4)
  println(aSequence)   // Seq的companion object的apply method会自动创建一个Seq的subclass
  println(aSequence.reverse)
  println(aSequence(2))  // index
  println(aSequence ++ Seq(5, 6, 7))
  println(aSequence.sorted)

  // Ranges
  val aRange: Seq[Int] = 1 to 10  // 包括左右区间
  aRange.foreach(println)

  val aRange2: Seq[Int] = 1 until 10  // 包括左区间但不包括右区间
  aRange2.foreach(println)

  (1 to 5).foreach(x => println("hello"))   // 重复执行某个命令可以这样写

  // Lists
  val aList = List(1, 2, 3)
  val prepended = 42 :: aList
  println(prepended)

  val prepended2 = 42 +: aList
  println(prepended2)

  val appended = aList :+ 89  // prepend是 +:   append是:+    只要记住，冒号永远在list所在的那一侧。
  println(appended)

  val apples5 = List.fill(5)("apple")  // fill 是 curried function。用来设置n个一样的元素.
  println(apples5)

  println(aList.mkString("-|-"))

  // Arrays
  val numbers = Array(1, 2, 3, 4)
  val threeElements = Array.ofDim[Int](3)  // 基本数据类型，如int、double、boolean，是会有默认值的
  println(threeElements)
  threeElements.foreach(println)

  val threeElements2 = Array.ofDim[String](3) // reference数据类型，如String，类，则默认值是null
  threeElements2.foreach(println)

  // mutation
  // 将index为2的元素更新为0
  numbers(2) = 0  // syntax sugar for numbers.update(2, 0)
  println(numbers.mkString(" "))

  // Arrays and Seq
  val numbersSeq: Seq[Int] = numbers   // implicit conversion
  println(numbersSeq)

  // Vectors
  // the default implementation for immutable sequences
  // good performance for large sizes

  val vector: Vector[Int] = Vector(1, 2, 3)
  println(vector)

  // Vectors vs Lists

  val maxRuns = 1000
  val maxCapacity = 1000000

  def getWriteTime(collection: Seq[Int]): Double = {
    val r = new Random   // 随机数生成器
    val times = for {
      it <- 1 to maxRuns
    } yield {
      val currentTime = System.nanoTime()
      collection.updated(r.nextInt(maxCapacity), r.nextInt())
      System.nanoTime() - currentTime
    }

    times.sum * 1.0 / maxRuns
  }

  val numbersList = (1 to maxCapacity).toList
  val numbersVector = (1 to maxCapacity).toVector

  // 优点: keeps reference to tail, 可以高效的改变头节点
  // 缺点：更新中间元素的时候耗时较长
  println(getWriteTime(numbersList))

  // 优点：depth of the tree is small
  // 缺点：needs to replace an entire 32-element chunk
  println(getWriteTime(numbersVector))

  // List和Vector耗时差距巨大，并且随着容量增加，差距将变得更大。这也是为什么Seq默认实现是Vector的原因。



}
