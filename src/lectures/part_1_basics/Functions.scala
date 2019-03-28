package lectures.part_1_basics

object Functions extends App{
  def greetingChild(name: String, age: Int): String =
    "Hi, my name is " + name + " I'm " + age + " years old."

  println(greetingChild("Bob", 12))

  def Fibonacci(n: Int): Int =
    if (n <= 2) 1
    else Fibonacci(n - 1) + Fibonacci(n - 2)

  println(Fibonacci(8))

  def IsPrime(n: Int): Boolean = {
    def IsPrimeUntil(t: Int): Boolean =
      if (t <= 1) true
      else (n % t != 0) && IsPrimeUntil(t - 1)

    IsPrimeUntil(n / 2)
  }

  println(IsPrime(37))
  println(IsPrime(37 * 7))

  def aParameterlessFunction(): String = "This is a parameterless functions!"

  println(aParameterlessFunction())
  println(aParameterlessFunction)

}
