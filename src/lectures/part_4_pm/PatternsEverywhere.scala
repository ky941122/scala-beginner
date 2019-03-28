package lectures.part_4_pm

object PatternsEverywhere extends App {

  // big idea #1
  try {
    // some code
  } catch {
    case e: RuntimeException => "runtime"
    case npe: NullPointerException => "npe"
    case _ => "something else"
  }

  // catches are actually MATCHES
  // 等价于：
  /*
    try {
    // some code
    } catch (e) {
      e match {
        case e: RuntimeException => "runtime"
        case npe: NullPointerException => "npe"
        case _ => "something else"
      }
    }
   */

  // big idea #2
  val list = List(1, 2, 3, 4)
  val evenOnes = for {
    x <- list if x % 2 == 0   // generator
  } yield 10 * x

  // generators are also based on PATTERN MATCHING
  val tuples = List((1, 2), (3, 4))
  val filterTuples = for {
    (first, second) <- tuples
  } yield first * second
  // case classes, :: operator, ...

  // big idea #3
  val tuple = (1, 2, 3)
  val (a, b, c) = tuple
  println(b)
  // multiple value definition based on PATTERN MATCHING
  // ALL THE POWER

  val head :: tail = list
  println(head)
  println(tail)

  // big idea #4 - NEW
  // partial function based on PATTERN MATCHING
  val mappedList = list.map {
    case v if v % 2 == 0 => v + " is even"
    case 1 => "the one"
    case _ => "something else"
  }  // partial function literal

  // 上下两个expression是等价的。

  val mappedList2 = list.map { x => x match {
      case v if v % 2 == 0 => v + " is even"
      case 1 => "the one"
      case _ => "something else"
    }
  }

  println(mappedList)


}
