package lectures.part3fp

import scala.util.Random

object Sequences extends App {

  /*
    Sequences(denoted by seq) : is very general interface for data structures that have following 2 properties:
                                a) Have a well defined order
                                b) Can be Indexed

    Sequences supports various operations :
      - apply, iterator, length, reverse for indexing and iterating
      - concatenation, appending, prepending to construct new collections
      - a lot of others(besides map, flatmap, filter): grouping, sorting, zipping, searching, slicing
   */

  //Seq
  val aSequence = Seq(1,3,2,4)                                                                                 // Seq() - apply() method from seq companion object
  println(aSequence)                                                                                           // Prints "List(1, 3, 2, 4)". This is a list , seq companion object actually has an apply factory method that can construct subclasses of sequence. But , the declared type of aSequence is "Seq[Int]"
  // Utility methods
  println(aSequence.reverse)                                                                                   // Prints "List(4, 2, 3, 1)"
  println(aSequence(2))                                                                                        // Prints 3 . aSequence(2) means aSequence.get(2) i.e get value from aSequence which is present at index number 2.
  println(aSequence ++ Seq(7,6,5))                                                                             // Prints "List(1, 3, 2, 4, 7, 6, 5)". "++" means concatenation
  println(aSequence.sorted)                                                                                    // Prints  "List(1, 2, 3, 4)".  sorted-method works if type is by default ordered.


  //Ranges : Ranges are represented in constant space, because they can be defined by just three numbers: their start, their end, and the stepping value. Because of this representation, most operations on ranges are extremely fast.
  val aRange: Seq[Int] = 1 to 10
  println(aRange)                                                                                               // Prints "Range 1 to 10"
  aRange.foreach(println)                                                                                       // Prints 1,2,3,4,5,6,7,8,9,10 on separate lines.

  //Print "Hello" 10 times without using recursive function
  (1 to 10).foreach(x => println("Hello"))

  //List
  val aList = List(1,2,3)
  // To prepend something to the list ( :: or +: )
  val prepended = 42 :: aList                                                                                   // "42 :: aList" is equivalent to "::.apply(42,aList)". "::" is the name of the case class which implements List. It's definition is  "case class ::[A](val hd: A, val tl: List[A]) extends List[A]"
  println(prepended)                                                                                            // Prints "List(42, 1, 2, 3)"
  // To append something to the list ( :+ )
  val appended = aList :+ 89
  println(appended)                                                                                             // Prints "List(1, 2, 3, 89)"
  val apple5 = List.fill(5)("apple")                                                                            // "fill" is a curried function that takes in a number and a value, constructs a list with n times that value.
  println(apple5)                                                                                               // Prints "List(apple, apple, apple, apple, apple)"
  println(aList.mkString("-"))                                                                                  // Prints "1-2-3" . "mkstring" concatenates all the values and place a "-" in between


  //Arrays
  val numbers = Array(1,2,3,4)
  val threeElements = Array.ofDim[Int](3)                                                                  // Creates int array of size 3
  println(threeElements)                                                                                        // Prints "[I@66d33a" i.e a garbage value
  threeElements.foreach(println)                                                                                // Prints three 0's on separate lines. This is because values are initialised with a default value. For , primitive types like Int , float , boolean - default value is 0 or false ;whereas; for reference types like String , person etc - default value is null
  val threeStrings = Array.ofDim[String](3)
  threeStrings.foreach(println)                                                                                 // Prints 3 null on separate lines
  // Arrays can be mutated in-place
  numbers(2) = 0                                                                                                // Syntax sugar for numbers.update(2,0) . This updates value at index 2 with value = 0 . "update" method is also quite special in scala , it is rarely used in practice and it's only used for mutable collections. But , this is a syntactic sugar which is very similar to apply.
  println(numbers.mkString(" "))                                                                                // Prints 1 2 0 4

  //Arrays and Seq
  val numberSeq: Seq[Int] = numbers                                                                             // "numbers" is an array (which is direct mapping over java's native array) is totally unrelated to type "Seq" , despite this conversion can be applied from array to Seq. "=" sign denotes implicit conversion.
  println(numberSeq)                                                                                            // Prints "ArraySeq(1, 2, 0, 4)"


  //VECTORS : Default implementation of Immutable sequences because very fast read and write(log(n) base 32) . They offer all the same functionalities that are offered by other collections
  val vector: Vector[Int] = Vector(1,2,3)
  println(vector)                                                                                               // Prints "Vector(1, 2, 3)"


  //Vectors vs List

  val maxRuns = 1000
  val maxCapacity = 1000000

  def getWriteTime(collection: Seq[Int]): Double = {
    val r = new Random
    val times = for {                                                                                             // for comprehension , later will be converted into map. flatmap function calls
      it <- 1 to maxRuns                                                                                          // runs 1000 times
    } yield {
      val currentTime = System.nanoTime()
      collection.updated(r.nextInt(maxCapacity), r.nextInt())                                                                // We're updating the value at random index(can be from 0 to maxCapacity) to some random number
      System.nanoTime() - currentTime
    }

    times.sum * 1.0 / maxRuns                                                                                     // times is a collection of numbers. It stores time taken for writing 0 at random index. It has 1000 values for 1000 update operations. We sum all the values and divide it with totalRuns to get average time taken.
  }

  val numbersList = (1 to maxCapacity).toList
  val numberVector = (1 to maxCapacity).toVector

  //List: Saves the reference to the tail . Hence , if update at first element , then list is incredibly efficient(Advantage). But, not efficient if element is present in the middle of the list(DisAdvantage).
  println(getWriteTime(numbersList))                                                                              // Prints 5040314.3
  //Vector: Needs to traverse the whole 32 branch tree and then replace that entire chunk(DisAdvantage). But, depth of the tree is very small(Advantage).
  println(getWriteTime(numberVector))                                                                             // Prints 1861.6

  // Difference between average write of list and vector is huge(order of 1000x). That's why vector is default representation of sequence.
}
