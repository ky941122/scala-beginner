package lectures.part_2_oop

object CaseClasses extends App {
  /*
    equals, hashCode, toString
   */

  case class Person(name: String, age: Int)

  // 1. 类参数自动就是field。
  val jim = new Person("Jim", 34)
  println(jim.name)

  // 2. 自动实现好的toString方法，返回的是类内容的字符串，而不是哈希值。
  // println(instance) = println(instance.toString)   这是个JAVA的syntactic sugar
  println(jim)

  // 3. 自动实现的，合理的equals、hashCode方法。out of the box
  val jim2 = new Person("Jim", 34)
  println(jim == jim2)   // 在 case class中，会得到true，因为两个实现的参数是一样的。但是若是普通class，则会返回false，因为这是两个不同的对象。

  // 4. CCs 有非常方便的copy方法
  val jim3 = jim.copy(age=45)   // copy方法中可以传入参数，这样，除了传入的参数项不同，其余项跟被拷贝的项相同。
  println(jim3)

  // 5. CCs自动有companion objects
  val thePerson = Person
  val mary = Person("Mary", 23)    // 这是调用了companion object的apply方法，所以创建对象可以不用使用new关键字，通过companion object创建

  // 6. CCs are serializable
  // Akka中一般使用的都是CCs

  // 7. CCs have extractor patterns = CCs can be used in PATTERN MATCHING

  case object UnitedKingdom {
    def name: String = "The UK of GB and NI"
  }

  // case object 和 case class完全一样，除了case object没有companion object，并且是个object以外。



}
