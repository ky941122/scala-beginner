package lectures.part_2_oop

import java.sql

import playground.{Cinderella, PrinceCharming}
import java.util.Date
import java.sql.{Date => SqlDate}

object PackagingAndImports extends App {
  // 同个package内的成员可以直接通过simple name调用
  val writer = new Writer("Daniel", "RockTheJVM", 2018)

  // 不同package内的成员，可以通过import来使用其simple name。或者通过fully qualified name调用
  val princess = new Cinderella
  val princess2 = new playground.Cinderella

  // package的层级是跟文件夹相同的。很少会打破。

  // 每个package可以且只能有一个package object。里面定义的成员可以在整个package内使用simple name调用。
  // package object实际中极少用到
  sayHello
  println(SPEED_OF_LIGHT)

  // imports
  val prince = new PrinceCharming
  // 可以使用 playground._  来import整个package。只有在需要这样做的时候才这样。

  // 别名的运用：当你有来自不同的包中的同名的class需要import时。一般情况下使用simple name就默认调用第一个import的。
  // 有两种方法解决：
  // 1、使用fully qualified name：
  val date = new Date  // java.util.Date
  val sqlDate = new java.sql.Date(2018, 5, 4)

  // 2. 使用别名：首先得在import的时候取个别名
  val sqlDate2 = new SqlDate(2018, 5, 4)

  // 默认imports：
  // java.lang - String, Object, Exception
  // scala - Int, Nothing, Function
  // scala.Predef - println, ???


}
