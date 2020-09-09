package lectures.part1basics

import scala.annotation.tailrec

object Recursion extends App {
  def factorial(n: Int): Int =
    if (n <= 1) 1
    else
      n * factorial(n-1)

  println(factorial(20))
  //println(factorial(5000))        --- StackOverflow while executing factorial(5000) because at least 5000 stack frames needed


  /*
  JVM uses call stack to keep partial results so that it can use that partial result later to compute desired result.
  Each call of recursive function uses a stack frame and there is only a limited amount of stack memory available (by default 1024 kb in java) at a time.
  So , factorial(20) runs fine but factorial(5000) throws StackOverflow error and since in Scala we use recursion instead of loops , does this mean i cannot iterate more than 5000 times ? - We can(else who would use it :p) , we need to use TAIL RECURSION
  */

  /* TAIL-RECURSIVE FUNCTION : A function whose very last action is a call to itself.
                      If function is tail-recursive ,  the Scala compiler can optimize the resulting JVM bytecode so that the function requires only one stack frame — as opposed to one stack frame for each level of recursion!
   */

  def anotherFactorial (n: Int): BigInt = {
    @tailrec                                                                    // @tailrec is a scala annotation to denote a tail recursive function. It can be used to check whether a function is tail recursive or not. if , method is not tail recursive , you'll get error stating that recursive call is not in tail position.
    def factorialHelper(n: Int , accumulator: BigInt): BigInt = {
      if(n == 1)  accumulator
      else factorialHelper(n-1 , n * accumulator)                                 //last expression is a call to same function as opposed to product of 'n' and 'result of factorial(n-1)' in previous factorial function.
    }
    factorialHelper(n,1)
  }

  println(anotherFactorial(20000))                          // No stackOverflow error even for 20000 because only one stack frame needed.


  //WHEN YOU NEED LOOPS , USE TAIL-RECURSION

  /*
    EXERCISES: Create tail recursive function for :
        1.) Concatenates a string n times. Takes input a string 's' and an integer 'n' returns 's' concatenated 'n' times. Ex: Input = Hello,4 . Output : HelloHelloHelloHello
        2.) Check if a number is prime.
        3.) Fibonacci function.
   */

  def concatenate(n: Int , str: String): String = {
    @tailrec
    def concatenateHelper(n: Int , accumulator: String): String = {
      if(n==0)  accumulator
      else concatenateHelper(n-1,accumulator+str)
    }
    concatenateHelper(n,"")
  }

  println(concatenate(4,"Hello"))           //prints "HelloHelloHelloHello"


  def prime(n: Int): Boolean = {
    @tailrec
    def primeHelper(divisor: Int , isStillPrime: Boolean): Boolean =                    //isStillPrime acts as accumulator
      if(!isStillPrime) false
      else if(divisor==1) isStillPrime
      else primeHelper(divisor-1 , isStillPrime && !(n%divisor==0))
    primeHelper(n/2,true)
  }

  println(prime(143))                 //prints false
  println(prime(29))                  //prints true



  def fibonacci(n: Int): Int = {
    @tailrec
    def fiboHelper(i: Int , last: Int , secondLast: Int): Int =
      if(i==n) last
      else fiboHelper(i+1 , last + secondLast , last)
    if(n<3) 1
    else fiboHelper(2 , 1, 1)
  }

  println(fibonacci(1))           //prints 1
  println(fibonacci(2))           //prints 1
  println(fibonacci(3))           //prints 2
  println(fibonacci(4))           //prints 3
  println(fibonacci(5))           //prints 5
  println(fibonacci(6))           //prints 8
  println(fibonacci(7))           //prints 13
  println(fibonacci(8))           //prints 21



  // In tail recursion , we eliminate the need to store partial results(inside stack frames) by keeping those results in accumulator variable.
  // At every recursive call , whatever we wanted to compute recursively and keep for later use , we add that to accumulator(Finding how to add to accumulator is the main challenge)
}
