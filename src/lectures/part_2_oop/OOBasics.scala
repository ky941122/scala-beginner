package lectures.part_2_oop

object OOBasics extends App{

  val person = new Person("John")
  println(person.age)
  person.greet("Bob")
  person.greet()

}

class Person(name: String, val age: Int){
  val x = 2
  println("This is a person")

  def greet(name: String): Unit = println(s"${this.name} says: Hi, $name")
  // overloading
  def greet(): Unit = println(s"Hi, I am $name")
  // multiple constructors
  def this(name: String) = this(name, 100)
}
