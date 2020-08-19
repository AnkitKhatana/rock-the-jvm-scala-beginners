package lectures.part1basics

object Functions extends App{

  //SYNTAX OF A FUNCTION : def <function_name> (<parameter1: Type , parameter2: Type ...>): return_type of function = Implementation of a function(i.e expression , A code block is also an expression)

  def aFunction(a: String , b: Int): String =             //def is the keyword
    a + " " + b                                           //expression = concatenation of two arguments separated by a space

  println(aFunction("Hello",5))                           //prints "Hello 5" . Calling a function is also an expression.


  //PARAMETER LESS FUNCTION
  def aParameterLessFunction(): Int = 42                  //returns 42
  println(aParameterLessFunction())                       //prints 42
  println(aParameterLessFunction)                         //prints 42 . Parameter less function can also be called just by their name i.e without parenthesis


  //Repeat a String n times. ***WHEN YOU NEED LOOPS , USE RECURSION.***
  def aRepeatedFunction(aString: String , n: Int): String = {
    if (n==1) aString
    else aString + aRepeatedFunction(aString,n-1)               //Recursion
  }

  println(aRepeatedFunction("hello" , 3))               //Prints "hellohellohello"

  //Compiler can figure out return type of a normal function on its own by seeing the value of function expression BUT it can't figure out return type of recursive function. So, you can omit return type in normal case but not in recursive function.

  //UNIT can be return type of a function , when you only want to execute side effect. Side effects are useful when we want to print , write to a file , audit etc.
  def aFunctionWithSideEffects(aString: String): Unit = println(aString)


  //We can have code blocks as implementation of a function and inside a code block we can have auxiliary definitions. So, we can define a function inside a function.
  def aBigFunction(n: Int): Int = {
    def aSmallerFunction(a: Int , b: Int): Int = a + b

    aSmallerFunction(n,n-1)                                               //A function defined inside a code block can be used inside the code block.
  }

  println(aBigFunction(5))                                                // prints 9



  /*
  EXERCISE:
  1.) A greeting function, which takes input (name,age) and returns "Hi , My name is $name and I am $age years old."
  2.) Factorial Function. Input = n , Output = Factorial of n
  3.) A Fibonacci Function. Input = n , Output = nth fibonacci number
      f(1)=1
      f(2)=1
      f(n) = f(n-1)+f(n-2)
  4.) Function which tests if a number is prime.
   */


  def aGeneratingFunction(name: String,age: Int): String = "Hi , My name is " +name+ " and I am " +age+ " years old."
  println(aGeneratingFunction("Thor",1000))

  def aFactorialFunction(n: Int): Int = {
    if(n <= 1) 1
    else n * aFactorialFunction(n-1)
  }
  println(aFactorialFunction(5))

  def aFibonacciFunction(n: Int): Int = {
    if(n <= 2) 1
    else aFibonacciFunction(n-1) + aFibonacciFunction(n-2)
  }
  println(aFibonacciFunction(6))

  def isPrime1(n: Int): Boolean = {
    def isPrimeInside(x: Int): Boolean = {
      if (x > n/2) true
      else isPrimeInside(x + 1) && !(n%x==0)
    }
    isPrimeInside(2)
  }
  println(isPrime1(2003))
  println(isPrime1(37*17))

  def isPrime(n: Int): Boolean = {
    def isPrimeUntil(t: Int): Boolean =
      if(t<=1) true
      else n%t != 0 && isPrimeUntil(t-1)
    isPrimeUntil(n/2)
  }
  println(isPrime(2003))
  println(isPrime(37*17))
}
