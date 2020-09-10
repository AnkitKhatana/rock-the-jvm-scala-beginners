package lectures.part3fp

object MapFlatMapFilterFor extends App {

  val list = List(1,2,3)                                                                      // Standard Library List. We are creating a list using apply method on list companion object.
  println(list)                                                                               // Prints "List(1, 2, 3)"

  // Standard list implementation also has kind of methods that we implemented in MyList
  println(list.head)                                                                          // Prints 1
  println(list.tail)                                                                          // Prints "List(2, 3)"

  //To see how these methods are implemented in standard library , search "List" on scala.lang.org and search for the respective members
  // MAP
  println(list.map(_ + 1))                                                                     // Prints "List(2, 3, 4)"
  println(list.map(_ + " is a number"))                                                        // Prints "List(1 is a number, 2 is a number, 3 is a number)"

  // FILTER
  println(list.filter(_ % 2 == 0))                                                             // Prints "List(2)"

  // FLATMAP
  val toPair = (x: Int) => List(x,x+1)
  println(list.flatMap(toPair))                                                                // Prints "List(1, 2, 2, 3, 3, 4)"

  /*
      EXERCISE :
      1.) Print all combinations between two lists.
          Ex: Numbers = (1,2,3,4)  , Chars = ('a', 'b', 'c', 'd')
              All Combinations = (1a, 1b, 1c, 1d ...... 4c, 4d)
   */

  val numbers = List(1,2,3,4)
  val chars = List('a', 'b', 'c', 'd')

  //First approach
  def combinator(x: Int, list: List[Char]) = list.map(x.toString + _)
  val comb = numbers.flatMap(combinator(_,chars))
  println(comb)                                                                                 // Prints "List(1a, 1b, 1c, 1d, 2a, 2b, 2c, 2d, 3a, 3b, 3c, 3d, 4a, 4b, 4c, 4d)"

  //Better approach
  val comb1 = numbers.flatMap( n => chars.map(c => "" + n + c))
  println(comb1)                                                                                // Prints "List(1a, 1b, 1c, 1d, 2a, 2b, 2c, 2d, 3a, 3b, 3c, 3d, 4a, 4b, 4c, 4d)"
  //In an imperative style , we could've used two loops and be done with it. But, In functional programming we use recursive approach. So , whenever you have two loops you can implement that logic using map and flatmap.

  //if 3 loops , then
  val colors = List("black", "white")
  //ALl possible combinations of numbers,chars,colors
  val comb2 = numbers.flatMap(n => chars.flatMap(c => colors.map(color => "" + c + n + color)))
  println(comb2)                                                                                // Prints "List(a1black, a1white, b1black, b1white, c1black, c1white, d1black, d1white, a2black, a2white, b2black, b2white, c2black, c2white, d2black, d2white, a3black, a3white, b3black, b3white, c3black, c3white, d3black, d3white, a4black, a4white, b4black, b4white, c4black, c4white, d4black, d4white)"

  //for-each - Implementation is exactly how we implemented in MyList
  list.foreach(println)                                                                         // Prints 1,2,3 on separate lines

  //FOR-COMPREHENSION : When there are 2-3 loops and you write chains of flatmaps and maps , it becomes hard for another person to understand. So , there's a shorthand for all this called for-comprehension
  val forCombinations = for {
    n <- numbers
    c <- chars
    color <- colors
  } yield ""+ c + n + color                                                                     // yield is a keyword here and this for comprehension is equivalent to "numbers.flatMap(n => chars.flatMap(c => colors.map(color => "" + c + n + color)))" we wrote earlier. So , comb2 = forCombinations.
  println(forCombinations)                                                                      // Prints "List(a1black, a1white, b1black, b1white, c1black, c1white, d1black, d1white, a2black, a2white, b2black, b2white, c2black, c2white, d2black, d2white, a3black, a3white, b3black, b3white, c3black, c3white, d3black, d3white, a4black, a4white, b4black, b4white, c4black, c4white, d4black, d4white)"

  //To filter out some of the numbers , we can put in a guard. Ex: Take only even numbers from numbers list
  val forCombinationsEven = for {
    n <- numbers if n%2 == 0                                                                    // "if n%2 == 0" is a guard. These are translated into filter call for numbers
    c <- chars
    color <- colors
  } yield ""+ c + n + color                                                                     // Equivalent to "numbers.filter(_ % 2 == 0).flatMap(n => chars.flatMap(c => colors.map(color => "" + c + n + color)))"

  // You can also do side effects using for comprehension
  for {
    n <- numbers
  } println(n)                                                                                  // Prints 1,2,3,4 on separate lines. This is identical to for-each method because compiler rewrites this for-comprehension into actual functional call.


  //Syntax Overload
  //Alternative syntax for multiplying each element of list by 2
  list.map { x =>
    x * 2
  }

  /*
      Exercises:
      1) See if MyList supports for-comprehension. Since compiler converts for comprehensions into map,flatmap,filter,for-each.
      2) Implement small collection of at most one element -  Maybe[+T] , only subtypes of Maybe are an Empty collection or an element containing one element of type T
          - map,flatmap,filter
   */

  /*
      ANSWER(Exercise-1) : for-comprehension is internally converted into map, flatmap, filter. But , they should have specific signatures:
        map(f: A => B) => MyList[B]
        filter(p: A => Boolean) => MyList[A]
        flatMap(f: A => MyList[B]) => MyList[B]
      Functions in MyList have these signatures only.
   */

  //Maybe collection : Maybe is often used in functional programming to denote optional values. Answer to Exercise 2 is in Maybe.scala

}
