package lectures.part4pm

object PatternsEverywhere extends App {

  // Big Idea #1 : catches are actually MATCHES
  try{
    // code
  } catch {
    case e: RuntimeException => "runtime"
    case npe: NullPointerException => "npe"
    case _ => "something else"
  }

  /*
      try {
        // code
      } catch (e) {
        e match {
          case e: RuntimeException => "runtime"
          case npe: NullPointerException => "npe"
          case _ => "something else"
        }
      }
   */


  // Big Idea #2 : generators are also based on PATTERN MATCHING
  val list = List(1,2,3,4)
  val evenOnes = for {
    x <- list if x % 2 == 0                                                                                             // This is a generator
  } yield (10 * x)

  // another example
  val tuples = List((1,2),(3,4))
  val filterTuples = for {
    (first, second) <- tuples                                                                                           // Notice, how (first, second) was decomposed in the same fashion as pattern matching decompose tuples. That's because all the generators are based on pattern matching
  } yield first * second

  //similarly for case classes, :: operators, ---


  // Big Idea #3 : multiple value definition based on PATTERN MATCHING
  val tuple = (1,2,3)
  val (a, b, c) = tuple                                                                                                 // i.e I can assign multiple values by exploiting the name binding property of pattern matching
  println(b)                                                                                                            // Prints 2

  val head :: tail = list                                                                                               // equivalent to list match pattern head : tail
  println(head)                                                                                                         // Prints 1
  println(tail)                                                                                                         // Prints List(2, 3, 4)


  // Big Idea #4
  //partial function literal based on PATTERN MATCHING
  val mappedList = list.map {                                                                                           // Instead of using some lambda inside curly braces , we use case statements.
    case v if v % 2 == 0 => v + " is even"
    case 1 => "the one"
    case _ => "something else"
  }
  // Above syntax is equivalent to saying:
  val mappedList2 = list.map { x =>
    x match {
      case v if v % 2 == 0 => v + "is even"
      case 1 => "the one"
      case _ => "something else"
    }
  }

  println(mappedList)                                                                                                   // Prints "List(the one, 2 is even, something else, 4 is even)" . i.e every element of list was put through all of these cases.


}
