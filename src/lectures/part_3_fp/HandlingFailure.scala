package lectures.part_3_fp

import scala.util.{Failure, Random, Success, Try}

object HandlingFailure extends App {

  // 使用Try数据结构可以避免过多的嵌套try catch结构，使代码更易读。
  // 写的方法有可能返回null的时候用Option，有可能抛出异常时用Try

  // create success and failure
  val aSuccess = Success(3)
  val aFailure = Failure(new RuntimeException("SUPER FAILURE"))

  println(aSuccess)
  println(aFailure)

  // 多数情况下不用自己定义Success和Failure，Try会自动决定构建哪一个。
  def unsafeMethod(): String = throw new RuntimeException("NO STRING FOR YOU!!")

  // Try objects via the apply method
  val potentialFailure = Try(unsafeMethod())
  println(potentialFailure)

  // syntax sugar
  val anotherPotentialFailure = Try {
    // code that might throw
  }

  // utilities
  println(potentialFailure.isSuccess)

  // orElse
  def backupMethod(): String = "A valid result"
  val fallbackTry = Try(unsafeMethod()).orElse(Try(backupMethod()))
  println(fallbackTry)

  // IF you design the API
  def betterUnsafeMethod(): Try[String] = Failure(new RuntimeException)
  def betterBackupMethod(): Try[String] = Success("A valid result")
  val betterFallback = betterUnsafeMethod() orElse betterBackupMethod()

  // map, flatMap, filter
  println(aSuccess.map(_ * 2))
  println(aSuccess.flatMap(x => Success(x * 10)))
  println(aSuccess.filter(_ > 10))    // Success可以转换成Failure

  // for-comprehensions

  // Exercise

  val host = "localhost"
  val port = "8080"
  def renderHTML(page: String) = println(page)

  class Connection {
    def get(url: String): String = {
      val random = new Random(System.nanoTime())
      if (random.nextBoolean()) "<html>...</html>"
      else throw new RuntimeException("Connection interrupted")
    }

    def getSafe(url: String): Try[String] = Try(get(url))
  }

  object HttpService {
    val random = new Random(System.nanoTime())

    def getConnection(host: String, port: String): Connection = {
      if (random.nextBoolean()) new Connection
      else throw new RuntimeException("Someone else took the port!")
    }

    def getSafeConnection(host: String, port: String): Try[Connection] = Try(getConnection(host, port))
  }

  // 如果从connection中得到html页面，打印它。即调用renderHTML.
  val possibleConnection = HttpService.getSafeConnection(host, port)
  val possibleHTML = possibleConnection.flatMap(connection => connection.getSafe("/home"))   // Try[String]
  possibleHTML.foreach(renderHTML)

  // shorthand version
  HttpService.getSafeConnection(host, port)
    .flatMap(connection => connection.getSafe("/home"))
    .foreach(renderHTML)

  // for-comprehension version
  for {
    connection <- HttpService.getSafeConnection(host, port)
    html <- connection.getSafe("/home")
  } renderHTML(html)


  // 使用try catch结构，等价于：
  /*
    try {
      connection = HttpService.getConnection(host, port)
      try {
        page = connection.get("/home")
        renderHTML(page)
      } catch (some other exception) {

      }
    } catch (exception) {

    }
   */

  // 如果你写的方法有可能抛出异常，则定义该方法的返回类型为Try[type]，其中，type为方法的本来返回类型。


}
