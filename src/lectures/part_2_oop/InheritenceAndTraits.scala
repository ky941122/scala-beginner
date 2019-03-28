package lectures.part_2_oop

object InheritenceAndTraits extends App {

  // private access modifier会使得这个method只能在这个class内部被使用
  // protected access modifier使这个method可以在这个class以及它的subclass内部被使用
  class Animal {
    val creatureType = "wild"
    def eat = println("nomnomnom...")
  }

  // single class inheritance
  class Cat extends Animal {
    def crunch = {
      eat
      println("crunch, crunch!")
    }
  }

  val cat = new Cat
  // cat.eat        此时eat仍然不能在外部被调用，只能在subclass Cat内被调用
  cat.crunch

  // constructors
  class Person(name: String = "Bob", age: Int) {
    def this(name: String) = this(name, 0)
  }
  class Adult(name: String, age: Int, idCard: String) extends Person(name, age)   // 继承有参数的类只能这样继承，得先声明父类的参数

  class Child(name: String, age: Int, school: String) extends Person(name)  // 若父类定义了auxiliary constructor，则可以不用声明。但是定义默认参数没用，还是得声明那个参数。

  // override
  class Dog extends Animal {
    override val creatureType = "domestic"
    override def eat = println("XXXXXXX~~~")
  }
  val dog = new Dog
  dog.eat
  println(dog.creatureType)

  class Fish(override val creatureType: String) extends Animal   // 直接在定义参数时override field，省时省力。
  val fish = new Fish("K9") {   //在创建实例的时候override，则只有这个实例的eat方法被override了，其他fish实例的eat方法没有。
    override def eat = {
      super.eat
      println("gulululululu.....")
    }
  }
  println(fish.creatureType)

  // type substitution (broad: polymorphism)
  val unknownAnimal: Animal = new Dog
  unknownAnimal.eat    // 虽然声明成Animal，但是eat方法仍然是跑的Dog的，方法的调用总是会使用最override的的。
  // 注意，需要把Animal class里eat方法前的protected给删掉，不然仍然不能调用这个eat。因为声明的是Animal。

  // overRIDING VS overLOADING 两者要分清，overload指的是相同名字，不同signature的方法。

  // super
  // 用来在子类里调用父类
  fish.eat
  val fish2 = new Fish("K10")
  println(fish2.creatureType)
  fish2.eat

  // preventing overrides
  // 1、在方法或val或var前加final，使得其不能被override。
  // 2、在类前加final，使得其不能被extends
  // 3、在类前加sealed，使得这个类只能在本文件中被extends，在别的文件中不行。

}
