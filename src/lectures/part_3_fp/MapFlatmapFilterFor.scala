package lectures.part_3_fp

object MapFlatmapFilterFor extends App {

  val list = List(1, 2, 3)
  println(list)
  println(list.head)
  println(list.tail)

  // map
  println(list.map(_ + 1))
  println(list.map(_ + " is a number"))

  // filter
  println(list.filter(_ % 2 == 0))

  // flatMap
  val toPair = (x: Int) => List(x, x+1)
  println(list.flatMap(toPair))

  // print all combinations between two lists
  val numbers = List(1,2,3,4)
  val chars = List("a", "b", "c", "d")
  // List("a1", "a2", ..."d4")

  // "iterating"
  val combinations = numbers.flatMap(n => chars.map(c => "" + c + n))
  println(combinations)

  val colors = List("black", "white")
  val combinations2 = numbers.filter(_ % 2 == 0).flatMap(n => chars.flatMap(c => colors.map(color => "" + c + n + "-" + color)))
  println(combinations2)

  // foreach
  list.foreach(println)

  // for-comprehensions  编译器会自动将这种写法转换为上面的链式写法再进行执行。
  val forCombinations = for {
    n <- numbers if n % 2 == 0
    c <- chars
    color <- colors
  } yield "" + c + n + "-" + color
  println(forCombinations)

  // 也可以用于foreach
  for {
    n <- numbers
  } println(n)

  // syntax overload
  list.map { x =>
    x * 2
  }








}
