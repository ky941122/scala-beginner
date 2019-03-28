package lectures.part_2_oop

object AnonymousClasses extends App{
  abstract class Animal {
    def eat: Unit
  }

  // anonymous class
  val funnyAnimal: Animal = new Animal {
    override def eat: Unit = println("hahahahaha")
  }

  /*
    这样做，相当于：

    class AnonymousClasses$$anon$1 extends Animal {
      override def eat: Unit = println("hahahahaha")
    }
    val funnyAnimal: Animal = new AnonymousClasses$$anon$1
   */

  println(funnyAnimal.getClass)

  class Person(name: String) {
    def sayHi: Unit = println(s"Hi, my name is $name, how can I help?")
  }

  val jim: Person = new Person("Jim") {
    override def sayHi: Unit = println("Hi, my name is Jim.")
  }

  // 匿名类可以用于抽象类、非抽象类和trait。
  // 匿名类也必须实现所有的抽象方法和属性。
  // 匿名类需要提供完整的类构造器，如上面的  new Person("Jim")。这一点跟继承是一样的。

}
