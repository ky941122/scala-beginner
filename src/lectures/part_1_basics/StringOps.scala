package lectures.part_1_basics

object StringOps extends App{

  val str: String = "Hello! I am learning Scala!"

  println(str.charAt(1))
  println(str.substring(7, 11))
  println(str.substring(0,1)) //含头不含尾
  println(str.split(" ").toList)
  println(str.startsWith("Hello!"))
  println(str.replace(" ", "=="))
  println(str.toLowerCase())
  println(str.toUpperCase)  //无参数函数可不带括号
  println(str.length)

  val aNumberString = "2"
  val aNumber = aNumberString.toInt
  println(aNumber)
  println("a" +: aNumberString :+ "z")  //prepending & appending
  println("a" + aNumberString + "z")

  println(str.reverse)
  println(str.take(3))

  // S-interpolators
  val name = "David"
  val age = 12
  val greeting = s"Hello, my name is $name, and I'm $age years old, I will be turning ${age + 1} years old."
  println(greeting)

  // F-interpolators
  val speed = 1.2f
  val myth = f"$name can eat $speed%5.2f burgers per minute."   //%4.2f代表浮点数，至少有4位字符，精度为2位小数。1.20占了4位字符，如果是%5.2f，则会在前面补一个空格，字符串总长度会从38变成39.
  val myth2 = f"$name can eat $speed%10.2f burgers per minute."
  println(myth)
  println(myth2)
  println(myth.length)
  println(myth2.length)

  // raw-interpolators
  println(raw"This is a \n new line.")
  val escaped = "This is a \n new line."
  println(raw"$escaped")


}
