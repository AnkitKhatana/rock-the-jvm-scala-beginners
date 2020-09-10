package lectures.part3fp

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


  //Ranges
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
  println(aList.mkString("-"))                                                                                 // Prints "1-2-3" . "mkstring" concatenates all the values and place a "-" in between



}
