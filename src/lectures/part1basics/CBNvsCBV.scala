package lectures.part1basics

object CBNvsCBV extends App{
  def calledByValue(x: Long): Unit = {
    println("by value: "+x)
    println("by value: "+x)
  }

  def calledByName(x: => Long): Unit = {              // " => " symbol tells compiler that parameter will be called by name
      println("by name: "+x)
      println("by name: "+x)
  }


  calledByValue(System.nanoTime())                    // prints 148868509990500 twice
  /*
    Here , before execution of calledByValue() starts , the value of System.nanoTime() is computed and that value is passed to the function.
    This call is equivalent to saying calledByValue(148868509990500L).
    i.e.  println(148868509990500L)
          println(148868509990500L)
   */

  calledByName(System.nanoTime())                     // prints two different values : 148868513587000 and 148868513638700
  /*
      Here , the expression is passed as is . Function will execute these two print statements :
        println(System.nanoTime())
        println(System.nanoTime())
      So , in both println calls System.nanoTime() is called and hence the different value.
      "=>" delays the evaluation of expression passed as a parameter and it's evaluated everytime it's used in function definition.
      This is extremely useful in lazy streams and things which are going to fail.
   */


  def infinite(): Int = 1 + infinite()
  def printFirst(x: Int, y: => Int) = println(x)

  printFirst(infinite(),34)                       // Results in program crash because infinite() is to be evaluated first(because called by value) and as the infinite() is infinite recursive , it results in stackoverflow error(remember? , limited stack frames and one stack frame for each recursive call)

  printFirst(34 , infinite())                     // Prints 34. infinite() is never evaluated because "=>" delays the evaluation of an expression until it is used and value of y(i.e infinite()) is never used.
}
