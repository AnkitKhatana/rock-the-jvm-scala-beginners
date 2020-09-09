package lectures.part2oop

object Exceptions extends App {

  val x: String = null
  //println(x.length)                                           // program crashes with "NullPointerException" error. length method throws the exception here and we've nobody to catch

  //1. How to throw Exceptions
  //val aWeirdValue = throw new NullPointerException              // This will crash the program just like above. Exceptions are instances of classes , hence the keyword new. So , NullPointerException is a class , I'm instantiating it and throwing that instance.
  // "throw new NullPointerException" is an expression and it's value = "Nothing". So , aWeirdValue can have any datatype and still it can hold Nothing because Nothing is a valid substitute of all other types.

  //To throw an instance, a class need to extend Throwable class. Exception and Error are major throwable subtypes.
  //Both exceptions and errors will crash the JVM but they're different in their semantics. "Exceptions" denotes something that went wrong with the program(Ex: NullPointerException) ;whereas; Errors denotes something that went wrong with the system(Ex: StackOverflow error denotes that you've exceeded the memory of JVM stack)
  //Exceptions comes from java language , they're a JVM specific ting not a Java specific thing.


  //2. How to catch exceptions
  def getInt(withExcpetion: Boolean): Int =
    if(withExcpetion) throw new RuntimeException("No int for you")            // Runtime Exception gets a message as a class parameter
    else 42

  val potentialFail = try {
    //Code that might fail
    getInt(true)
  }
  catch {
    //Match all the exceptions that you might get
    case e: RuntimeException => println("caught a RunTimeException")
  } finally {
    //Code that will get executed NO MATTER WHAT.
    //This block is optional.
    //Doesn't influence return type of try-catch-finally expression.
    //So , use finally only for side-effects. Ex: logging something to a file.
    println("Finally")
  }
  /*
        Above try-catch block prints :
        "caught a RunTimeException
         Finally"

         Like everything else in scala , try-catch-finally is an expression.
         For above try-catch-finally , type is AnyVal because try returns an "Int" , catch returns "Unit"(cuz, println returns Unit). So , unifying Int and Unit results in AnyVal.
   */


  //3. How to define your own exceptions
  class MyException extends Exception                           // Custom exceptions are regular classes , hence , they can have class parameters , fields , methods and anything that you expect from a regular class.
  val exception = new MyException

  //throw exception                                               // Crashes the program with MyException. Message : "Exception in thread "main" lectures.part2oop.Exceptions$MyException"


  /*
     EXERCISES :
      1. Crash your program with OutOfMemoryError(these happen when you try to allocate more memory than the JVM have)
      2, Crash with a StackOverflowError.
      3. Define a class PocketCalculator with 4 methods for Int:
          -add(x,y)
          -multiply(x,y)
          -divide(x,y)
          -subtract(x,y)
        They throw custom exceptions if anything wrong happens. Exceptions are:
          -overflow Exception - if add(x,y) exceeds Int.MAX_VALUE
          -Underflow Exception - if subtract(x,y) < Int.MIN_VALUE
          -MathCalculation Exception - for division by 0
   */

  //1. OOM
  //val array = Array.ofDim(Int.MaxValue)                                               // Crashes the program with "Exception in thread "main" java.lang.OutOfMemoryError: Requested array size exceeds VM limit"

  //2. SO
  def infiniteRecursion(i: Int): Int =
    i * infiniteRecursion(i+1)

  //infiniteRecursion(1)                                                                  // Crashes with "Exception in thread "main" java.lang.StackOverflowError"

  //3.
  class OverflowExcpetion extends RuntimeException
  class UnderflowException  extends RuntimeException
  class MathCalculationException extends RuntimeException("Division by 0")
  /*
      * We cannot compare x+y with Int.MaxValue because Int.MaxValue is the largest value Int can hold and result of x+y(also being Int), if greater than Int.MaxValue will result in a negative result due to overflow.
      * So , if x and y are both positive and result(of x+y or x*y) is negative then there's overflow.
      * Similarly , if x and y are both negative and result(of x+y) is positive then there's underflow.
      * AND
      * If, x is negative and y is positive , "x-y" is positive , then there's underflow.
      * Similarly , if x is positive and y is negative , "x-y" is negative , then there's overflow
     */

  object PocketCalculator {
    def add(x: Int,y: Int): Int =
      if(x>0 && y>0 && x+y<0)  throw new OverflowExcpetion
      else if (x<0 && y<0 && x+y>0)  throw new UnderflowException
      else x+y
    def multiply(x: Int, y: Int) =
      if(x>0 && y>0 && x*y<0)  throw new OverflowExcpetion
      else if (x<0 && y>0 && x*y>0) throw new UnderflowException
      else if (x>0 && y<0 && x*y>0) throw new UnderflowException
      else x*y
    def divide(x: Int, y: Int) =
      if(y == 0)  throw new MathCalculationException
      else x/y
    def subtract(x: Int, y: Int) =
      if(x<0 && y>0 && x-y>0)  throw new OverflowExcpetion
      else if(x>0 && y<0 && x-y<0)  throw new UnderflowException
      else x-y
  }

  //PocketCalculator.add(Int.MaxValue,10)                                               // Results in "Exception in thread "main" lectures.part2oop.Exceptions$OverflowExcpetion"
  //PocketCalculator.add(Int.MinValue,-10)                                              // Results in "Exception in thread "main" lectures.part2oop.Exceptions$UnderflowException"
  //PocketCalculator.divide(10,0)                                                         // Results in "Exception in thread "main" lectures.part2oop.Exceptions$MathCalculationException: Division by 0"

}
