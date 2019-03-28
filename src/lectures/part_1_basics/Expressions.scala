package lectures.part_1_basics

object Expressions extends App{
  // Instructions(TO DO) VS Expressions(VALUE)
  // scala中除了定义值以外，所有东西都是expressions。if是，while是，println也是。
  // 所有side effect的值都是unit。print是side effect,while也是。
  //if不是side effect，if是返回值的，跟1+2一样。
  var i = 0
  val whileValue = while (i < 10) {
    i += 1
  }
  println(whileValue)

  val x = if (0.3 > 2) "Hello" else 22
  println(x)

  val c = {
    if (true) 42 else 666
    44
    42
    40
  }       // code block的值是里面的最后一个表达式决定的。所以c=42
  println(c)

  val u = println(66666)
  println(u)
  var aVariable = 5
  println(aVariable)
  val unittt = (aVariable = 3)
  print(unittt)
  println(aVariable)
  println(if (aVariable < 4) 100 else 666)


}
