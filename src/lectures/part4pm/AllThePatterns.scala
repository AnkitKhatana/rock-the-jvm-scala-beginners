package lectures.part4pm

import exercises.{Cons, MyList, Empty}

object AllThePatterns extends App {


  // 1 - Constants
  val x: Any = "Scala"                                                                                                  // A literal can be number, string, boolean, singleton object
  val constants = x match {
    case 1 => "a number"
    case "Scala" => "THE Scala"
    case true => "The Truth"
    case AllThePatterns => "A singleton object"
  }

  // 2 - Match Anything

  // 2.1 Wildcard
  val matchAnything = x match {
    case _ =>                                                                                                           // Return expression is blank because it's not relevant(can be anything). Emphasis is on case statements.
  }

  // 2.2 Variable
  val matchAVariable = x match {
    case something => s"I've foung $something"                                                                          // This something matches any value and I can use this value in return expression.
  }

  // 3. Tuples
  val aTuple = (1,2)
  val matchATuple = aTuple match {
    case (1,1) =>                                                                                                       // Can pass a literal tuple. (1,1) is literal tuple
    case (something,2) => s"I've foung $something"                                                                      // Can pass tuple composed of nested patterns. This case statement tries to extract something out of the tuple if the rest of pattern matches and I can use something in return expression
  }

  val nestedTuple = (1, (2, 3))
  val matchANestedTuple = nestedTuple match {
    case (_, (2,v)) =>                                                                                                  // We can pass a nested tuple composed of nested patterns in case expression. I can match 'v' even though it is inside a nested tuple inside whole pattern(i.e (_, (2, v)))
  }
  //Pattern Matches can be nested


  // 4 - Case classes - constructor pattern
  val aList: MyList[Int] = Cons(1, Cons(2, Empty))
  val matchAList = aList match {
    case Empty =>
    case Cons(head, tail) =>                                                                                            // head, tail will bind to values extracted from aList. So, In case of aList, head = 1 and tail = Cons(2, Empty)
    case Cons(head, Cons(subhead, subtail)) =>                                                                          // Case class patterns can also be nested. In case of aList, head = 1, subhead = 2 and subtail = Empty
  }

  // 5 - List Patterns
  val aStandardList = List(1, 2, 3, 42)
  val standardListMatching = aStandardList match {
    case List(1, _, _, _) =>                                                                                            // This is called extractor for list. Even though List itself is not a case class, list constructor extractor exists in the scala standard library
    case List(1, _*) =>                                                                                                 // This is a var arg pattern which is a list of arbitrary length
    case 1 :: List(_) =>                                                                                                // Infix pattern
    case List(1,2,3) :+ 42 =>                                                                                           // Infix pattern
  }

  // 6 - Type specifiers
  val unknown: Any = 2
  val unknownMatch = unknown match {
    case list: List[Int] =>                                                                                             // EXPLICIT TYPE SPECIFIER. ": List[Int]" is a type specifier. success if pattern matches including type
    case _ =>
  }

  // 7 - Name binding. This is what happens with variable. When you match anything with variable, you bind that anything to a name. BUT name binding here means something else, this is an explicit name binding
  val nameBindingMatch = aList match {
    case nonEmptyList @ Cons(_,_) =>                                                                                    // "nonEmptyList @" will name a pattern. This is a name binding. So, you can use the name later in the return expression. This is similar to name binding in variable pattern matching but this is more powerful since you can
    case Cons(1, rest @ Cons(2, _)) =>                                                                                  // Name binding inside nested patterns
  }

  // 8 - Multi-Patterns : are multiple patterns chained by pipe operator
  val multipattern = aList match {
    case Empty | Cons(0, _) =>                                                                                          // Compound pattern(multi-pattern). You can chain as many patterns as you want with '|' when you want to return
  }

  // 9 - if guards
  val secondElementSpecial = aList match {
    case Cons(_, Cons(specialElement, _)) if specialElement % 2 == 0 =>                                                 // if guard will filter out the pattern using the predicate(specialElement % 2 == 0)
  }



  /*
    Question.
   */

  val numbers = List(1, 2, 3)
  val numbersMatch = numbers match {
    case listOfStrings: List[String] => "A list of strings"
    case listOfNumbers: List[Int] => "A list of numbera"
    case _ => ""
  }
  //Question : What would below expression print ?
  println(numbersMatch)                                                                                                 // Prints "A list of strings"

  /*
     Above expression prints "A list of strings" even though numbers is a list of integers. This is not scala's fault, it is JVM trick question. JVM was designed for java language and Java language was designed with backward compatibility in mind. Java-1 did not have generic parameters, generics was introduced in java-5.
     So, to make JVM compatible with Java-1, Java-5 compiler erased all generic types after type checking, which makes JVM absolutely oblivious to generic types. Scala also suffers from this fault, because JVM was designed in this way. So , basically after Type-Checking, our pattern match expressions look like below:

      val numbersMatch = numbers match {
        case listOfStrings: List => "A list of strings"
        case listOfNumbers: List => "A list of numbera"
        case _ => ""
      }

     i.e type parameters are erased just like that. So , first pattern is matched because a list matches a list. Hence, prints "A list of strings".
     This is called TYPE ERASER. Frameworks like scalaZ and shadeless also suffer from eraser problem.
     IDE also warns about this : "fruitless type test: a value of type List[Int] cannot also be a List[String](but still might match its erasure)"
 */

}

