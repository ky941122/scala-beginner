package lectures.part_2_oop

object MethodNotations extends App{

  class Person(val name: String, favoriteMovie: String, val age: Int = 0) {
    def likes(movie: String): Boolean = movie == favoriteMovie
    def +(person: Person): String = s"${this.name} is hanging out with ${person.name}."
    def +(nickname: String): Person = new Person(s"$name ($nickname)", favoriteMovie)
    def unary_! : String = s"$name, what the heck?!"  //String前的冒号和感叹号之间的空格不能省，不然compiler会弄以为冒号也是方法名的一部分。
    def unary_+ : Person = new Person(name, favoriteMovie, age + 1)

    def unary_-(): String = s"$name minus!!!!!"

    def isAlive: Boolean = true
    def learns(subject: String): String = s"$name learns $subject"
    def learnsScala: String = this learns "Scala"    // 把 this 当作这个object本身来调用别的method. infix notation
    def apply(): String = s"Hi, my name is $name, I am $age years old and I like $favoriteMovie." //apply可以传参数。
    def apply(times: Int): String = s"$name has watched $favoriteMovie $times times"
  }

  val mary = new Person("Mary", "Inception")
  println(mary.likes("Inception"))
  println(mary likes "Inception")   //equivalent
  //infix notation = operator notation (syntactic sugar)

  // "operators" in Scala
  val tom = new Person("Tom", "Fight Club")
  println(mary + tom)
  println(mary.+(tom))

  println(1 + 2)
  println(1.+(2))

  // all operators are methods

  // prefix notation
  val x = -1  // equivalent with 1.unary_-
  val y = 1.unary_-
  println(x == y)
  // unary_ prefix only works with: - + ~ !

  println(!mary)
  println(mary.unary_!)

  // postfix notation
  println(mary.isAlive)
  println(mary isAlive) // equivalent. 但没什么人这样用。一般还是用点号。

  // apply
  println(mary.apply())
  println(mary()) // equivalent

  //exercise
  println((mary + "rock star")())

  println((+mary).age)
  println(mary())
  println((+mary)())

  println(mary learnsScala)

  println(mary(5))

  println(-mary) // prefix notation method 不能有参数



}
