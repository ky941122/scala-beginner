package lectures.part_2_oop

object Objects extends App {
  // Scala does not have class-level functionality ("static")
  object Person {  // type + its only instance
    // "static"/"class" - level functionality
    val n_Eyes = 2
    def canFly: Boolean = false

    // factory method
    def apply(mother: Person, father: Person): Person = new Person("Son")
  }

  class Person (val name: String) {
    // instance-level functionality
  }
  // 这种class和object同名且位于同一个scope内的结构叫做companions

  println(Person.n_Eyes)
  println(Person.canFly)

  // Scala object = singleton instance
  val mary = new Person("Mary")
  val john = new Person("John")
  println(mary == john)

  val person1 = Person
  val person2 = Person
  println(person1 == person2)

  val bobby = Person(mary, john)
  println(bobby.name)

  // Scala application = Scala object with
  // def main(args: Array[String]): Unit
}
