package lectures.part_2_oop

object Exceptions extends App {
  val x: String = null
//  println(x.length)
  // 这行命令会出现NPE (NullPointerException)

  // 1. throwing exceptions
//  val aWeirdValue: String = throw new NullPointerException
  // throw 语句也是expression，跟其他scala命令一样，返回值是Nothing。

  // throwable classes extend the Throwable class.
  // Exception 和 Error 是Throwable的主要子类

  // 2. catching exceptions
  def getInt(withExceptions: Boolean): Int =
    if (withExceptions) throw new RuntimeException("No int for you!!")
    else 42

  // try catch finally 也是expression。返回类型是try语句块和catch语句块的返回值的公共父类
  // 如下面这个try catch，返回值就是AnyVal类型。因为try返回Int，但是catch返回Unit
  val potentialFail = try {
    // code that might throw
    getInt(true)
  } catch {
    case e: RuntimeException => println("caught a Runtime exception")
  } finally {
    // 不管有没有异常，不管有没有catch住，都会执行的代码
    println("finally")
  }

  // 如果把catch语句块改成这样，那下面的try catch的返回值就是Int类型的。
  val potentialFail2 = try {
    // code that might throw
    getInt(true)
  } catch {
    case e: RuntimeException => 43
  } finally {
    // 不管有没有异常，不管有没有catch住，都会执行的代码
    println("finally")
  }
  // 可以看出，finally语句块是可选的，并且不影响try catch语句块的返回类型。
  // 使用finally语句块来进行side effect。比如打印log等

  println(potentialFail2)

  // 3. 定义自己的exceptions
  class MyException extends Exception
//  val exception = new MyException

//  throw exception

  // 如何得到OOM错误和SO错误：
  // OOM:
//  val array = Array.ofDim(Int.MaxValue)

  // SO:
//  def infinite: Int = 1 + infinite
//  val noLimit = infinite

  class OverflowException extends RuntimeException
  class UnderflowException extends RuntimeException
  class MathCalculationException extends RuntimeException("Division by 0")

  // 定义成object而不是class，这样每次调用其中方法时就不用实例化了。
  object PocketCalculator {
    // 不能直接判断result > Int.MaxValue。因为这个永远会是false。只能利用正数超过上限后会变负数来判断。下限也是如此。
    def add(x:Int, y: Int) = {
      val result = x + y
      if (x > 0 && y > 0 && result < 0) throw new OverflowException
      else if (x < 0 && y < 0 && result > 0) throw new UnderflowException
      else result
    }

    def subtract(x: Int, y: Int) = {
      val result = x - y
      if (x > 0 && y < 0 && result < 0) throw new OverflowException
      else if (x < 0 && y > 0 && result > 0) throw new UnderflowException
      else result
    }

    def multiply(x: Int, y: Int) = {
      val result = x * y
      if (x > 0 && y > 0 && result < 0) throw new OverflowException
      else if (x < 0 && y < 0 && result < 0) throw new OverflowException
      else if (x > 0 && y < 0 && result > 0) throw new UnderflowException
      else if (x < 0 && y > 0 && result > 0) throw new UnderflowException
      else result
    }

    def divide(x: Int, y: Int) = {
      if (y == 0) throw new MathCalculationException
      else x / y
    }
  }

//  println(PocketCalculator.add(Int.MaxValue, 10))
  println(PocketCalculator.divide(2, 0))

}
