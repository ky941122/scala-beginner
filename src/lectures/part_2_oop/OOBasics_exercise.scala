package lectures.part_2_oop

object OOBasics_exercise extends App{
  val writer1 = new Writer("John", "Snow", 1981)
  println(writer1.fullname())
  val writer2 = new Writer("Bob", "Snow", 1926)

  val novel1 = new Novel("ABC", 2018, writer1)
  println(novel1.authorAge())
  println(novel1.isWrittenBy(writer1))
  val novel2 = novel1.copy(2020)
  println(novel2.authorAge())

//  val counter1 = new Counter(1926)
//  println(counter1.currentCount())
//  val counter2 = counter1.increase()
//  println(counter2.currentCount())
//  val counter3 = counter2.decrease(1000)
//  println(counter3.currentCount())

  val counter1 = new Counter
  println(counter1.count)
  counter1.print
  counter1.inc.print
  counter1.inc(10).print

}

class Writer(firstname: String, surname: String, val year: Int){
  def fullname(): String = s"$firstname $surname"
}

class Novel(name: String, val releaseYear: Int, author: Writer){
  def authorAge(): Int = releaseYear - author.year
  def isWrittenBy(author: Writer): Boolean = if (author == this.author) true else false
  def copy(newReleaseYear: Int): Novel = new Novel(name, newReleaseYear, author)
}

//class Counter(n: Int){
//  def currentCount(): Int = n
//  def increase(): Counter = new Counter(n + 1)
//  def increase(k: Int): Counter = new Counter(n + k)
//  def decrease(): Counter = new Counter(n - 1)
//  def decrease(k: Int): Counter = new Counter(n - k)

class Counter(val count: Int = 0) {
  def inc = {
    println("increamenting!")
    new Counter(count + 1)
  }

  def inc(n: Int): Counter = {
    if (n <= 0) this
    else inc.inc(n - 1)
  }

  def dec = {
    println("decreamenting!")
    new Counter(count - 1)
  }

  def dec(n: Int): Counter = {
    if (n <= 0) this
    else dec.dec(n - 1)
  }

  def print = println(count)
}
