package lectures.part_2_oop

object Generics extends App {
  class MyList[+A] {
    // use the type A
    def add[B >: A](element: B): MyList[B] = ???
    // 即下面第一种情况COVARIANCE中提到的，Cat列表中加入Dog，就会变成Animal列表
    /*
      A = Cat
      B = Animal
     */
  }

  class MyMap[Key, Value]

  val listOfIntegers = new MyList[Int]
  val listOfStrings: MyList[String] = new MyList

  // generic methods
  object MyList {
    def empty[A]: MyList[A] = ???
  }
//  val emptyListOfIntegers = MyList.empty[Int]

  // variance problem
  class Animal
  class Cat extends Animal
  class Dog extends Animal

  // 1. COVARIANCE
  class CovariantList[+A]
  val animal: Animal = new Cat
  val animalList: CovariantList[Animal] = new CovariantList[Cat]
  // 如果，一个声明为Animal列表，但是实际为Cat列表的列表里，add进了一个Dog，那么这个列表就会变成Animal列表。即，会变为列表元素的公共父类

  // 2. INVARIANCE
  class InvariantList[A]
  val invariantAnimalList: InvariantList[Animal] = new InvariantList[Animal]
  // 声明是什么类型，实现就得是什么类型。不能是子类或父类

  // 3. CONTRAVARIANCE
  class Trainer[-A]
  val trainer: Trainer[Cat] = new Trainer[Animal]
  // 刚好与第一种反过来。

  // bounded types
  class Cage[A <: Animal](animal: A)   // A只能是Animal的子类。符号反过来则相反。
  val cage = new Cage(new Dog)







}
