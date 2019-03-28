package lectures.part_1_basics

import scala.annotation.tailrec

object Recursions extends App{


  def Concat(s: String, n: Int): String = {
    @tailrec
    def ConcatHelper(s1: String, s2: String, t: Int): String =
      if (t < 1) s2
      else ConcatHelper(s1, s2 + s1, t - 1)

    ConcatHelper(s, "", n)
  }
  println(Concat("Hello! ", 10))

//  def IsPrime(n: Int): Boolean = {
//    @tailrec
//    def IsPrimeHelper(t: Int): Boolean =
//      if (t <= 1) true
//      else (n % t != 0) && IsPrimeHelper(t - 1)
//
//    IsPrimeHelper(n / 2)
//  }

  def IsPrime(n: Int): Boolean = {
    @tailrec
    def IsPrimeHelper(t: Int, IsStillPrime: Boolean): Boolean =
      if (!IsStillPrime) false
      else if (t <= 1) true
      else IsPrimeHelper(t - 1, IsStillPrime && (n % t != 0))

    IsPrimeHelper(n / 2, true)
  }


  println(IsPrime(37))
  println(IsPrime(37 * 7))

  def Fibonacci(n: Int): Int = {
    @tailrec
    def FibonacciHelper(t: Int, n1: Int, n2: Int): Int =    //n2里存的是f(n-1)，n1里存的是f(n-2)
      if (t <= 2) n2
      else FibonacciHelper(t - 1, n2, n1 + n2)

    if (n <= 2) 1
    else FibonacciHelper(n, 1, 1)
  }
  println(Fibonacci(8))
  println(Fibonacci(4))

}
