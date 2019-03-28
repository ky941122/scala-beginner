package lectures.part_4_pm

import scala.util.Random

object PatternMatching extends App {

  // switch on steroids
  val random = new Random
  val x = random.nextInt(10)   // 0-10之间的随机数

  val description = x match {
    case 1 => "the one"
    case 2 => "double or nothing"
    case 3 => "third time is the charm"
    case _ => "something else"  // _ = WILDCARD  可以用来匹配任意东西
  }

  println(x)
  println(description)

  // 1. Decompose values
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 20)

  val greeting = bob match {
    case Person(n, a) if a < 21 => s"Hi, my name is $n and I can't drink in the US."  // guards
    case Person(n, a) => s"Hi, my name is $n, I am $a years old."
    case _ => "I do not know who I am."
  }
  println(greeting)

  /*
    1. cases按顺序match，第一个match上的作为整个pattern match expression的结果。
    2. 如果没有case能match，会抛出MatchError。所以要记得兜底，使用_。
    3. PM expression的返回类型，是里面所有case的返回类型的最低公共父类。如Int和String就会变成Any。
    4. PM和case classes搭配使用非常好。能够轻松提取出class中的参数。如果是普通class就不行，得再加点东西（advanced课程中会讲）。
   */

  // PM on sealed hierarchies
  sealed class Animal
  case class Dog(breed: String) extends Animal
  case class Parrot(greeting: String) extends Animal

  val animal: Animal = Dog("Terra Nova")
  animal match {
    case Dog(someBreed) => println(s"Matched a dog of the $someBreed breed.")
  }  // 没有覆盖所有可能的case，比如Parrot。如果编译，编译器会Warning。这是因为使用了sealed class。如果Animal是普通class，就不会Warning。
  // 所以，sealed class会帮你检查是否兜底了。


  // 不要什么都用match！！！！！
  val isEven = x match {
    case n if n % 2 == 0 => true
    case _ => false
  }

  val isEvenCond = if (x % 2 == 0) true else false
  //上面两种写法都不好，写成下面这样就行。
  val isEvenNormal = x % 2 == 0

  // Exercise
  trait Expr
  case class Number(n: Int) extends Expr
  case class Sum(e1: Expr, e2: Expr) extends Expr
  case class Prod(e1: Expr, e2: Expr) extends Expr

  // 实现一个方法，使用PM，接收一个Expr类型的参数，并将其转换为人类可读的格式。

  /*
    Sum(Number(2), Number(3)) => 2 + 3
    Sum(Sum(Number(2), Number(3)), Number(4)) => 2 + 3 + 4
    Prod(Sum(Number(2), Number(1)), Number(3)) => (2 + 1) * 3
    Sum(Prod(Number(2), Number(1)), Number(3)) => 2 * 1 + 3
   */

  def show(e: Expr): String = e match {
    case Number(n) => s"$n"
    case Sum(e1, e2) => show(e1) + " + " + show(e2)
    case Prod(e1, e2) => {
      def maybeShowParentheses(exp: Expr) = exp match {
        case Prod(_, _) => show(exp)
        case Number(_) => show(exp)
        case _ => "(" + show(exp) + ")"
      }

      maybeShowParentheses(e1) + " * " + maybeShowParentheses(e2)
    }
  }

  println(show(Sum(Number(2), Number(3))))
  println(show(Sum(Sum(Number(2), Number(3)), Number(4))))
  println(show(Prod(Sum(Number(2), Number(1)), Number(3))))
  println(show(Prod(Sum(Number(2), Number(1)), Sum(Number(3), Number(4)))))
  println(show(Sum(Prod(Number(2), Number(1)), Number(3))))


}
