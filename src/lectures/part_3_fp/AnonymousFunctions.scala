package lectures.part_3_fp

object AnonymousFunctions extends App {

  // anonymous function (LAMBDA)
  val doubler = (x: Int) => x * 2

  val doubler2: Int => Int = x => x * 2  // 在函数声明中声明类型后，在lambda中就可以不用再额外声明

  // 多参数lambda
  val adder: (Int, Int) => Int = (a, b) => a + b

  // 无参数lambda
  val justDoSomething: () => Int = () => 3

  // 注意！！！
  println(justDoSomething)  // 这是函数本身
  println(justDoSomething()) // 这才是调用函数

  // curly braces with lambdas
  val stringToInt = { (str: String) =>
    str.toInt
  }

  // syntactic sugar
  val niceIncrementer: Int => Int = _ + 1  // equivalent to x => x + 1
  val niceAdder: (Int, Int) => Int = _ + _ // equivalent to (a, b) => a + b
  // 要用_，必须把函数的类型声明写完整。
  // 一个下划线代表了一个参数。所以，当有同一个参数在表达式中出现多次时，不能使用这种写法。因为一个下划线只能代表一个参数一次。

  val superAdd = (x: Int) => (y: Int) => x + y
  println(superAdd(3)(4))


}
