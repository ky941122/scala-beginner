package lectures.part_1_basics

object CBVvsCBN extends App{

  def CallByValue(x: Long): Unit = {   //先evaluation后传value进去
    println("Call By Value: " + x)
    println("Call By Value: " + x)
  }

  def CallByName(x: => Long): Unit = {  //传的是expression进去，而不是value。函数每次调用expression时再进行evaluation。
    println("Call By Name: " + x)
    println("Call By Name: " + x)
  }

  CallByValue(System.nanoTime())
  CallByName(System.nanoTime())

}
