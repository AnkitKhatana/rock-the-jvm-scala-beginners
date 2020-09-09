package lectures.part3fp

object HOFsCurries extends App {


  //Higher Order Functions : A function which receives another function as an argument or returns function as result are called Higher Order Functions. Ex: map,flatmap,filter methods in MyList are higher order functions.


  // Write a function that applies another function n times over a given value.
  // nTimes(f,n,x) . Ex: nTimes(f,3,x) = f(f(f(x)))

  //nTimes(f,4,1) = f(nTimes(f,3,1)) = f(f(nTimes(f,2,1))) = f(f(f(nTimes(f,1,1)))) = f(f(f(f(nTimes(f,0,1))))) = f(f(f(f(1)))) = f(f(f(2*1))) = f(f(f(2))) = f(f(2*2)) = f(f(4)) = f(2*4) = f(8) = 2*8 = 16
  def nTimes(f: Int=>Int, n: Int, x: Int): Int =
    if(n<=0)  x
    else f(nTimes(f,n-1,x))
  println(nTimes(elem => 2*elem , 4 , 1))                                         //Prints 16

  //nTimes(f,n,x) = f(f(...f(x))) = nTimes(f, n-1, f(x))
  //nTimes(f,4,1) = nTimes(f,3,f(1)) = nTimes(f,3,2*1) = nTimes(f,3,2) = nTimes(f,2,f(2)) = nTimes(f,2,2*2) = nTimes(f,2,4) = nTimes(f,1,f(4)) = nTimes(f,1,2*4) = nTimes(f,1,8) = nTimes(f,0,f(8)) = nTimes(f,0,2*8) = nTimes(f,0,16) = 16
  def nTimes1(f: Int => Int, n: Int, x: Int): Int =                                 //nTimes1 is tail recursive , where as nTimes is not
    if(n<=0) x
    else nTimes1(f,n-1,f(x))
  println(nTimes1(elem => 2*elem, 4, 1))                                          //Prints 16

  //Instead of applying function n-times to x, we return a lambda(or function). Ex: ntb(f,x) = x => f(f(f...(x)))
  //Increment10 = ntb(plusOne, 10) = x => plusOne(plusOne(....(x)))
  def nTImesBetter(f: Int => Int, n: Int): Int => Int =
    if (n <= 0) (x: Int) => x
    else (x: Int) => nTImesBetter(f, n-1) (f(x))                                  //nTimesBetter returns a function that will be applied to f(x)

  val plusOne: Int => Int = _+1
  val plus10 = nTImesBetter(plusOne,10)
  println(plus10(1))                                                              //Prints 11


  //Curried Functions : are very useful if you want to define helper functions which you will use on various values.
  val superAdder: Int => Int => Int = (x: Int) => (y: Int) => x+y                 // "=>" is right associative i.e "Int => Int => Int" is equivalent to "Int => (Int => Int)" where parentheses are implied by compiler
  val add3 = superAdder(3)                                                        // y => 3+y . add3 is a helper function which I can use later with various values
  println(add3(10))                                                               //Prints 13
  println(superAdder(3)(10))                                                      //Prints 13

  //Functions with multiple parameter lists. You can have as many parameter lists as you want.
  def curriedFormatter(c: String)(x: Double): String = c.format(x)                //You can define a function with multiple parameter list to act like curried function. Later I can define many helper functions(or smaller functions)

  // You always have to specify type of smaller function(Helper function)
  val standardFormat: (Double => String) = curriedFormatter("%4.2f")              // standardFormat is a function from Double to String , that applies curriedFormatter with "%4.2f" format later to whatever value you pass to it.
  val preciseFormat: (Double => String) = curriedFormatter("%10.8f")

  println(standardFormat(Math.PI))                                                // Prints 3.14
  println(preciseFormat(Math.PI))                                                 // Prints 3.14159265

  /*
      EXERCISES:
      1.) Expand MyList. Add following methods:
            - foreach : for each element, it does a side effect. Receives a function(element => Unit). Apply this function to every element of MyList.
                [1,2,3].foreach(x => println(x)) prints each element on a new line.

            - sort : receives a sorting function(which compares 2 elements. (A,A) => Int ) , and returns sorted MyList.
                [1,2,3].sort( (x,y) => y-x ) => [3,2,1]

            - zipWith : (list, (A,A) => B) => MyList(B)
                [1,2,3].zipWith([4,5,6], x * y) => [1 * 4, 2 * 5, 3 * 6] = [4,10,18]

            - fold (start) (function) => a value
                [1,2,3].fold(0)(x + Y) = 6
                - you add first element to start value , and keep on adding till last element.


      2.) toCurry(f: (Int,Int) => Int) => (Int => Int => Int)) - converts a function into it's curried version.
          fromCurry(f: (Int => Int => Int) => (Int, Int) => Int)) - converts a function from curried to normal.

      3.) compose(f,g) => x => f(g(x)) . compose(f,g) will return a lambda which applies g on x then f on g(x), for every x.
          andThen(f,g) => x => g(f(x))
   */

  //2. Both solution are easy to write but not easy to think about. These are non-trivial solutions.
  def toCurry(f: (Int,Int) => Int): Int => Int => Int =
    x => y => f(x,y)                                                                                                    // Very similar to superAdder implementation earlier

  def fromCurry(f: (Int => Int => Int)): (Int,Int) => Int =
    (x, y) => f(x)(y)


  //3. "compose" , "andThen" are two very useful functions in function library. Available in FunctionX
  def compose[A,B,T](f: A => B, g: T => A): T => B =
    x => f(g(x))

  def andThen[A,B,C](f: A => B, g: B => C): A => C =
    x => g(f(x))



  def superAdder2: Int => Int => Int = toCurry(_+_)
  def add4 = superAdder2(4)
  println(add4(17))                                                                                                     //Prints 21

  val simpleAdder: (Int,Int) => Int = fromCurry(superAdder2)
  println(simpleAdder(4,17))                                                                                            //Prints 21

  val add2 = (x: Int) => x + 2
  val times3 = (x: Int) => x * 3

  val composed = compose(add2,times3)
  val ordered = andThen(add2,times3)

  println(composed(5))                                                                                                  // Prints 17. Cuz, f(g(x)) = add2(times3(5)) = 2 + (5 * 3) = 17
  println(ordered(5))                                                                                                   // Prints 21. Cuz, g(f(x)) = times3(add2(5)) = 3 * (5 + 2) = 21
  
}
