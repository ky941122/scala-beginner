package exercises

abstract class MyList2[+A] {
  /*
    method:
    head = list的第一个元素
    tail = list中除了第一个元素以外剩下的部分
    isEmpty = 判断是否是空list
    add(int) => 返回一个新的列表，新加入的元素在新列表的头部
    toString => 返回list的字符串表示
   */

  def head: A
  def tail: MyList2[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList2[B]
  def printElements: String
  // polymorphic call
  override def toString: String = "[" + printElements + "]"

  // higher-order functions
  def map[B](transformer: A => B): MyList2[B]
  def flatMap[B](transformer: A => MyList2[B]): MyList2[B]
  def filter(predicate: A => Boolean): MyList2[A]

  // concatenation
  def ++[B >: A](list: MyList2[B]): MyList2[B]

  // HOFs
  def foreach(f: A => Unit): Unit
  def sort(compare: (A, A) => Int): MyList2[A]
  def zipWith[B, C](list: MyList2[B], zip: (A, B) => C): MyList2[C]
  def fold[B](start: B)(operator: (B, A) => B): B
}

///////////////////////////////////////////////////////////////////////////////////

// Empty需要满足：任何MyList2[A] = Empty都得是有效的
case object Empty2 extends MyList2[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: MyList2[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): MyList2[B] = new Cons2(element, Empty2)
  def printElements: String = ""

  def map[B](transformer: Nothing => B): MyList2[B] = Empty2
  def flatMap[B](transformer: Nothing => MyList2[B]): MyList2[B] = Empty2
  def filter(predicate: Nothing => Boolean): MyList2[Nothing] = Empty2

  def ++[B >: Nothing](list: MyList2[B]): MyList2[B] = list

  // HOFs
  def foreach(f: Nothing => Unit): Unit = ()
  def sort(compare: (Nothing, Nothing) => Int) = Empty2
  def zipWith[B, C](list: MyList2[B], zip: (Nothing, B) => C): MyList2[C] =
    if (!list.isEmpty) throw new RuntimeException("Lists do not have the same length")
    else Empty2
  def fold[B](start: B)(operator: (B, Nothing) => B): B = start
}

case class Cons2[+A](h: A, t: MyList2[A]) extends MyList2[A] {
  def head: A = h
  def tail: MyList2[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MyList2[B] = new Cons2(element, this)
  def printElements: String =
    if(t.isEmpty) "" + h
    else h + " " + t.printElements

  /*
    [1,2,3].filter(n % 2 == 0)
      = [2,3].filter(n % 2 == 0)
      = new Cons2(2, [3].filter(n % 2 == 0))
      = new Cons2(2, Empty2.filter(n % 2 == 0))
      = new Cons2(2, Empty2)
   */

  def filter(predicate: A => Boolean): MyList2[A] =
    if (predicate(h)) new Cons2(h, t.filter(predicate))
    else t.filter(predicate)

  /*
    [1,2,3].map(n * 2)
      = new Cons2(2, [2,3].map(n * 2))
      = new Cons2(2, new Cons2(4, [3].map(n * 2)))
      = new Cons2(2, new Cons2(4, new Cons2(6, Empty2.map(n * 2))))
      = new Cons2(2, new Cons2(4, new Cons2(6, Empty2)))
   */

  def map[B](transformer: A => B): MyList2[B] =
    new Cons2(transformer(h), t.map(transformer))

  /*
    [1,2] ++ [3,4,5]
    = new Cons2(1, [2] ++ [3,4,5])
    = new Cons2(1, new Cons2(2, Empty2 ++ [3,4,5]))
    = new Cons2(1, new Cons2(2, [3,4,5]))
   */

  def ++[B >: A](list: MyList2[B]): MyList2[B] = new Cons2(h, t ++ list)

  /*
    [1,2].flatMap(n => [n, n+1])
    = [1,2] ++ [2].flatMap(n => [n, n+1])
    = [1,2] ++ [2,3] ++ Empty2.flatMap(n => [n, n+1])
    = [1,2] ++ [2,3] ++ Empty2
    = [1,2,2,3]
   */

  def flatMap[B](transformer: A => MyList2[B]): MyList2[B] =
    transformer(h) ++ t.flatMap(transformer)

  // HOFs
  def foreach(f: A => Unit): Unit = {
    f(h)
    t.foreach(f)
  }

  def sort(compare: (A, A) => Int): MyList2[A] = {
    def insert(x: A, sortedList: MyList2[A]): MyList2[A] =
      if (sortedList.isEmpty) new Cons2(x, Empty2)
      else if (compare(x, sortedList.head) <= 0) new Cons2(x, sortedList)
      else new Cons2(sortedList.head, insert(x, sortedList.tail))

    val sortedTail = t.sort(compare)
    insert(h, sortedTail)
  }

  def zipWith[B, C](list: MyList2[B], zip: (A, B) => C): MyList2[C] =
    if (list.isEmpty) throw new RuntimeException("Lists do not have the same length")
    else new Cons2(zip(h, list.head), t.zipWith(list.tail, zip))

  /*
    [1,2,3].fold(0)(+)
    = [2,3].fold(1)(+)
    = [3].fold(3)(+)
    = [].fold(6)(+)
    = 6
   */
  def fold[B](start: B)(operator: (B, A) => B): B = {
    t.fold(operator(start, h))(operator)
  }

}

// 参数得用-T和-A，不然编译不过。具体原因太复杂。
//trait MyPredicate[-T] {
//  def test(elem: T): Boolean
//}
//
//trait MyTransformer[-A, B] {
//  def transform(elem: A): B
//}

object ListTest2 extends App {
  val listOfIntegers: MyList2[Int] = new Cons2(1, new Cons2(2, new Cons2(3, Empty2)))
  val cloneListOfIntegers: MyList2[Int] = new Cons2(1, new Cons2(2, new Cons2(3, Empty2)))
  val anotherListOfIntegers: MyList2[Int] = new Cons2(4, new Cons2(5, new Cons2(6, Empty2)))
  val listOfStrings: MyList2[String] = new Cons2("hello", new Cons2("scala", Empty2))
  val listOfBooleans: MyList2[Boolean] = Empty2

  println(listOfIntegers.toString)
  println(listOfStrings.toString)

  println(listOfIntegers.map(new Function1[Int, Int] {
    override def apply(elem: Int): Int = elem * 2
  }).toString)

  println(listOfIntegers.map(elem => elem * 2).toString)
  println(listOfIntegers.map(_ * 2).toString)

  println(listOfIntegers.filter(new Function1[Int, Boolean] {
    override def apply(elem: Int): Boolean = elem % 2 == 0
  }).toString)

  println(listOfIntegers.filter(elem => elem % 2 == 0).toString)
  println(listOfIntegers.filter(_ % 2 == 0).toString)

  println((listOfIntegers ++ anotherListOfIntegers).toString)

  println(listOfIntegers.flatMap(new Function1[Int, MyList2[Int]] {
    override def apply(elem: Int): MyList2[Int] = new Cons2(elem, new Cons2(elem+1, Empty2))
  }).toString)

  println(listOfIntegers.flatMap(elem => new Cons2(elem, new Cons2(elem+1, Empty2))).toString)
  // 无法写成下划线形式，因为箭头右边elem出现了两次

  println(cloneListOfIntegers == listOfIntegers)

  // HOFs
  listOfIntegers.foreach(println)
  println(listOfIntegers.sort((x, y) => y - x))   // 倒序排列
  println(anotherListOfIntegers.zipWith[Int, String](listOfIntegers, _ + "-" + _))
  println(listOfIntegers.fold(0)(_ + _))

  // for-comprehensions
  val combinations = for {
    n <- listOfIntegers
    string <- listOfStrings
  } yield n + "-" + string
  println(combinations)
  // for表达式也是个expression。只要map、flatMap和filter的定义正确，就可以直接使用。编译器会自动转换。

}

