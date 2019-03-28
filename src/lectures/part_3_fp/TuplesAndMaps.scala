package lectures.part_3_fp

import scala.annotation.tailrec

object TuplesAndMaps extends App {

  // tuples = finite ordered "lists"
  val aTuple = new Tuple2(2, "hello, scala")  // Tuple2[Int, String] = (Int, String)
  val aTuple2 = (2, "hello, scala")  // 两种声明方式一样。
  // tuple一共可以包含最多22个不同类型的元素，跟function一样，可以有function0到function22。

  println(aTuple._1)  // tuple中的第一个元素
  println(aTuple.copy(_2 = "goodbye java")) // copy和case class的一样
  println(aTuple.swap)

  // Maps: keys -> values
  val aMap: Map[String, Int] = Map()

  val phonebook = Map(("Jim", 555), "Daniel" -> 789).withDefaultValue(-1)
  // a -> b is a syntax sugar for (a, b)
  println(phonebook)

  // map ops
  println(phonebook.contains("Jim"))
  println(phonebook("Jim"))
  // 如果不设置默认值，则调用不存在的key会报错。设置了，则返回默认值。
  println(phonebook("Mary"))

  // add a pairing
  val newPairing = "Mary" -> 678
  val newPhonebook = phonebook + newPairing  // Maps是immutable的，所以得创建一个新的map来更改内容。
  println(newPhonebook)

  // functionals on maps
  // map, flatMap, filter 都是操作Map里每个元素的整个pair（键值对）(即tuple)的.
  println(phonebook.map(pair => pair._1.toLowerCase -> pair._2))

  // filterKeys
  println(phonebook.filterKeys(x => x.startsWith("J")))
  // mapValues
  println(phonebook.mapValues(number => number * 10))
  println(phonebook.mapValues(number => "0245-" + number))

  // conversions to other collections
  println(phonebook.toList)
  println(List(("Daniel", 555)).toMap)

  val names = List("Bob", "James", "Angela", "Mary", "Daniel", "Jim")
  println(names.groupBy(name => name.charAt(0)))
  // 把首字母相同的名字放进同一个list里，作为value，它们的首字母作为key。返回这样的一个Map。
  // Spark中经常用到groupBy方法。


  // Exercise
  val phonebook2 = Map(("Jim", 555), "Daniel" -> 789, "JIM" -> 2355).withDefaultValue(-1)
  println(phonebook2.map(pair => pair._1.toLowerCase -> pair._2))
  // Mapping keys的时候要小心！！如果map之后的key有重复，就会丢失数据！！！！！

  def add(network: Map[String, Set[String]], person: String): Map[String, Set[String]] =
    network + (person -> Set())

  def friend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {
    val friendsA = network(a)
    val friendsB = network(b)

    network + (a -> (friendsA + b)) + (b -> (friendsB + a))
  }

  def unfriend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {
    val friendsA = network(a)
    val friendsB = network(b)

    network + (a -> (friendsA - b)) + (b -> (friendsB - a))
  }

  def remove(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {
    def removeAux(friends: Set[String], networkAcc: Map[String, Set[String]]): Map[String, Set[String]] =
      if (friends.isEmpty) networkAcc
      else removeAux(friends.tail, unfriend(networkAcc, person, friends.head))

    val unfriended = removeAux(network(person), network)
    unfriended - person
  }

  val empty: Map[String, Set[String]] = Map()
  val network = add(add(empty, "Bob"), "Mary")
  println(network)
  println(friend(network, "Bob", "Mary"))
  println(unfriend(friend(network, "Bob", "Mary"), "Bob", "Mary"))
  println(remove(friend(network, "Bob", "Mary"), "Bob"))

  // Jim, Bob, Mary
  val people = add(add(add(empty, "Bob"), "Mary"), "Jim")
  val jimBob = friend(people, "Bob", "Jim")
  val testNet = friend(jimBob, "Bob", "Mary")

  println(testNet)

  def nFriends(network: Map[String, Set[String]], person: String): Int =
    if (!network.contains(person)) 0
    else network(person).size

  println(nFriends(testNet, "Bob"))

  def mostFriends(network: Map[String, Set[String]]): String =
    network.maxBy(pair => pair._2.size)._1      // maxBy接受一个以tuple pair为参数的lambda，并返回map中这个lambda表达式值最大的那个pair

  println(mostFriends(testNet))

  def nPeopleWithNoFriends(network: Map[String, Set[String]]): Int =
    network.filterKeys(k => network(k).isEmpty).size
  // 或者: network.filter(pair => pair._2.isEmpty).size
  // 或者: network.count(pair => pair._2.isEmpty)
  //      network.count(_._2.isEmpty)
  // count方法计数使得lambda表达式为true的pair有几个

  println(nPeopleWithNoFriends(testNet))

  // 判断两个人之间是否有直接或间接联系
  def socialConnection(network: Map[String, Set[String]], a: String, b: String): Boolean = {
    // 广度优先搜索
    @tailrec
    def bfs(target: String, consideredPeople: Set[String], discoveredPeople: Set[String]): Boolean = {
      if (discoveredPeople.isEmpty) false   // 已经没有要搜索的备选人了，但是还没有找到，说明没有联系。
      else {
        val person = discoveredPeople.head
        if (person == target) true  // 找到
        else if (consideredPeople.contains(person)) bfs(target, consideredPeople, discoveredPeople.tail)  // 这个人之前已经被搜索过
        else bfs(target, consideredPeople + person, discoveredPeople.tail ++ network(person))  // 这个人之前没有被搜索过，因此把他加到被搜索过的set里，并且把他的好友列表加到将要被搜索的备选人set里。
      }
    }

    bfs(b, Set(), network(a) + a)  // +a是为了应对b=a的情况。即自己跟自己也是有联系的。
  }

  println(socialConnection(testNet, "Mary", "Jim"))
  println(socialConnection(network, "Mary", "Bob"))


}
