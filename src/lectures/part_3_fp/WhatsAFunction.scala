package lectures.part_3_fp

object WhatsAFunction extends App {

  val doubler = new MyFunction[Int, Int] {
    override def apply (element: Int): Int = element * 2
  }

  println(doubler(2))

  // function types = Function1[A, B]
  // Function1 是接收1个参数的，Function2接收2个参数。依次类推，最多已经预定义好了Function22
  val stringToIntConverter = new Function1[String, Int] {
    override def apply(string: String): Int = string.toInt
  }

  println(stringToIntConverter("3") + 4)

  val adder: (Int, Int) => Int = new Function2[Int, Int, Int] {
    override def apply(a: Int, b: Int): Int = a + b
  }

  // Function types syntactic sugar: Function2[A, B, R] == (A, B) => R。其中，A，B是参数的类别，R是返回的类别。

  // 所有scala function 都是 objects，或者是继承了Function1,Function2 ...的类的实例。

  def concatenator: (String, String) => String = new Function2[String, String, String] {
    override def apply(a: String, b: String): String = a + b
  }
  println(concatenator("hello ", "scala"))

  // Function1[Int, Function1[Int, Int]]
  val superAdder: Function1[Int, Function1[Int, Int]] = new Function1[Int, Function1[Int, Int]] {
    override def apply(x: Int): Function1[Int, Int] = new Function1[Int, Int] {
      override def apply(y: Int): Int = x + y
    }
  }

  val adder3 = superAdder(3)
  println(adder3(4))
  println(superAdder(3)(4))  // curried function


}

trait MyFunction[A, B] {
  def apply(element: A): B
}
