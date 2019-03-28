package lectures.part_4_pm

import exercises.{Cons2, Empty2, MyList2}

object AllThePatterns extends App {

  // 1 - constants
  val x: Any = "Scala"
  val constants = x match {
    case 1 => "a number"
    case "Scala" => "the scala"
    case true => "the truth"
    case AllThePatterns => "a singleton object"
  }

  // 2 - match anything
  // 2.1 wildcard

  val matchAnything = x match {
    case _ => 1
  }

  // 2.2 variable
  val matchAVariable = x match {
    case something => s"I've found $something"     // 这个something就是个变量，可以匹配任意东西，并且提取出匹配到的东西在return expression中使用
  }
  println(matchAVariable)

  // 3 - tuples
  val aTuple = (1, 2)
  val matchATuple = aTuple match {
    case (1, 1) => 1
    case (something, 2) => s"I've found $something"
  }

  val nestedTuple = (1, (2, 3))
  val matchANestedTuple = nestedTuple match {
    case (_, (2, v)) => v
  }
  // PMs can be nested!!!

  // 4 - case classes - constructor pattern
  // PMs can be nested with CCs as well
  val aList: MyList2[Int] = Cons2(1, Cons2(2, Empty2))
  val matchAList = aList match {
    case Empty2 => 111
    case Cons2(head, tail) => println(head + tail.toString)
    case Cons2(head, Cons2(subhead, subtail)) => head + subhead + subtail.toString
  }

  // 5 - list patterns
  val aStandardList = List(1, 2, 3, 42)
  val standardListMatching = aStandardList match {
    case List(1, _, _, _) => 111   // extractor - advanced   匹配有四个元素，且1打头的List
    case List(1, _*) => 222  // list of arbitrary（任意的） length - advanced
    case 1 :: List(_) => 333  // infix pattern
    case List(1, 2, 3) :+ 42 => 444   // infix pattern
  }

  // 6 - type specifiers
  val unknown: Any = 2
  val unknownMatch = unknown match {
    case list: List[Int] => 111   // explicit type specifier
    case _ => 222
  }

  // 7 - name binding
  // 类似上面的variable，但不是匹配任意东西。给匹配到的东西取个名，留在return expression中用。
  val nameBindingMatch = aList match {
    case nonEmptyList @ Cons2(_, _) => nonEmptyList.toString + "name binding"   // name binding => use the name later(here)
    case Cons2(1, rest @ Cons2(2, _)) => 333   // name binding inside nested patterns
  }
  println(nameBindingMatch)

  // 8 - multi-patterns
  // 当有多个case希望返回同样的东西时使用。
  val multiPattern = aList match {
    case Empty2 | Cons2(0, _) => 222   // compound pattern(multi-pattern)
    case _ => 111
  }

  // 9 - if guards
  val secondElementSpecial = aList match {
    case Cons2(_, Cons2(specialElement, _)) if specialElement % 2 == 0 => specialElement
    case _ => 112
  }

  // That's ALL!!

  /*
    Question.
   */
  val numbers = List(1, 2, 3)
  val numbersMatch = numbers match {
    case listOfStrings: List[String] => "a list of Strings"
    case listOfNumbers: List[Int] => "a list of numbers"
    case _ => ""
  }

  println(numbersMatch)  // JVM trick question

  // 返回的是list of strings。因为JVM为了能编译JAVA5之前的JAVA代码，编译器在类型检查的时候是把类型参数去掉的。
  // 所以编译器看到的是：
  /*
    val numbersMatch = numbers match {
      case listOfStrings: List => "a list of Strings"
      case listOfNumbers: List => "a list of numbers"
      case _ => ""
    }

    即没有[String]和[Int]。因此，第一个case就成立了，因为确实是List。
   */


}
