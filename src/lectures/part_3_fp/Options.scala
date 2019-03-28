package lectures.part_3_fp

import scala.util.Random

object Options extends App {

  // Option可以避免手动检查null值。
  // 如果编写的方法的返回值有可能是null，那就把方法返回类型定义为Option[type]。其中type为方法原本返回类型。

  val myFirstOption: Option[Int] = Some(4)
  val noOption: Option[Int] = None

  println(myFirstOption)

  // unsafe APIs
  def unsafeMethod(): String = null
  // val result = Some(unsafeMethod()) // WRONG!!!!Some里面永远得传入valid value，不能把可能出现null值的API直接传给Some。
  val result = Option(unsafeMethod())  // Option会自动判断应该创建Some还是None
  println(result)

  // chained methods
  def backupMethod(): String = "A valid result"
  val chainedResult = Option(unsafeMethod()).orElse(Option(backupMethod()))
  // unsafeMethod是想要优先执行的API，但有可能返回null，如果返回null了，就执行另一个备用API。

  // DESIGN unsafe APIs
  def betterUnsafeMethod(): Option[String] = None
  def betterBackupMethod(): Option[String] = Some("A valid result")
  val betterChainedResult = betterUnsafeMethod() orElse betterBackupMethod()
  // 在写API时，直接返回Option类型，省的调用时忘记用Option包起来。

  // functions on Options
  println(myFirstOption.isEmpty)
  println(noOption.isEmpty)   // 判断是不是空的，即判断是不是None

  println(myFirstOption.get)  // UNSAFE !!! 如果是None，则会报Exception。不要用这个方法。
//  println(noOption.get)

  // map, flatMap, filter
  println(myFirstOption.map(_ * 2))
  println(myFirstOption.filter(x => x > 10))   // Some可以转换成None
  println(myFirstOption.flatMap(x => Option(x * 10)))

  // 同样支持for-comprehensions

  // Exercise
  val config: Map[String, String] = Map(
    "host" -> "176.45.36.1",
    "port" -> "80"
  )

  class Connection {
    def connect = "Connected"
  }
  object Connection {
    val random = new Random(System.nanoTime())

    def apply(host: String, port: String): Option[Connection] =
      if (random.nextBoolean()) Some(new Connection)
      else None
  }

  // config map 里 host 和 port有可能缺失，即有可能没有这个key。
  // 尝试建立一个connection，如果成功了，打印connect method

  val host = config.get("host")  // Map的get方法返回Option。即host是一个Option[String]类型的。
  val port = config.get("port")

  /*
    if (h != null):
      if (p != null):
        return Connection.apply(h, p)

    return null
   */
  val connection = host.flatMap(h => port.flatMap(p => Connection.apply(h, p)))   // Option[Connection]
  /*
    if (c != null):
      return c.connect
    return null
   */
  val connectionStatus = connection.map(c => c.connect)   // Option[String]
  // if (connectionStatus == null) println(None) else println(Some(connectionStatus.get))
  println(connectionStatus)
  /*
    if (status != null):
      println(status)
   */
  connectionStatus.foreach(println)


  // chained calls。实际应用中经常见到这种写法。
  config.get("host")
    .flatMap(host => config.get("port")
        .flatMap(port => Connection(host, port))
        .map(connection => connection.connect))
    .foreach(println)

  // for-comprehensions
  val forConnectionStatus = for {
    host <- config.get("host")
    port <- config.get("port")
    connection <- Connection(host, port)
  } yield connection.connect

  forConnectionStatus.foreach(println)



}
