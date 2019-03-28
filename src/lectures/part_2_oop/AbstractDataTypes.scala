package lectures.part_2_oop

object AbstractDataTypes extends App {
  //abstract
  abstract class Animal {
    val creatureType: String
    def eat: Unit
  }

  class Dog extends Animal {
    override val creatureType: String = "K9"
    def eat: Unit = println("crunch crunch")    // 可以不用加override，因为是从抽象方法继承来的
  }

  // traits
  trait Carnivore {
    def eat(animal: Animal): Unit
  }

  class Crocodile extends Animal with Carnivore {
    val creatureType: String = "croc"
    def eat: Unit = println("nomnomnom")
    def eat(animal: Animal): Unit = println(s"I'm a croc and I'm eating ${animal.creatureType}")
  }

  val dog = new Dog
  val croc = new Crocodile
  croc.eat(dog)

  // traits和抽象类都可以同时包含抽象和非抽象的属性和方法

  // traits VS abstract classes
  // 1 - traits不能传入构造器参数
  // 2 - 一个类可以继承多个traits，但是只能继承一个抽象类
  // 3 - traits一般用来描述行为，抽象类一般用来描述事物

}
