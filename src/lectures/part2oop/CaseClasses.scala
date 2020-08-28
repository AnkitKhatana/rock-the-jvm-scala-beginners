package lectures.part2oop

object CaseClasses extends App {
  /*
    Problem that we're trying to solve using Case Class :

    Often when using lightweight data structures in programming, it is necessary to re-implement all sorts of boilerplate code.
    For example we might add companion objects. Or methods for serializing and then pretty printing - methods like equals, hashCode, toString etc.

    Case classes in scala are exceptionally useful shorthand for defining both a class and a companion object. They come with a lot of sensible defaults.
    Perfect for lightweight data holding classes with a minimum of hassle.
   */

  class NormalPerson(name: String, age: Int)

  case class CasePerson(name: String, age: Int)
  // Case classes provides following functionalities:

  // 1. Class parameters are promoted to fields.
  val caseJim: CasePerson = new CasePerson("Jim",34)
  println(caseJim.age)                                                        // In a non-case class , we won't be able to access age like this
  val normalJim: NormalPerson = new NormalPerson("Jim",34)
  //println(normalJim.age)                                                      // Error: "Cannot resolve symbol age" because age is parameter not field

  //2. Sensible toString
  println(caseJim.toString)                                                   // prints "CasePerson(Jim,34)"
  println(normalJim.toString)                                                 // prints "lectures.part2oop.CaseClasses$NormalPerson@2eee9593"

  // " println(instance) = println(instance.toString) " - This is another syntactic sugar
  println(caseJim)                                                            //prints "CasePerson(Jim,34)"

  //3. equals and hashcode implemented out of the box, which makes case classes especially important to be used in collection
  val caseJim2 = new CasePerson("Jim",34)
  println( caseJim == caseJim2)                                                // prints true
  val normalJim2 = new NormalPerson("Jim", 34)
  println( normalJim == normalJim2)                                             // prints false ;because; equals method was not implemented, so , default equals from anyref was picked which by default returns false for different instances of the class.

  //4. Case classes have handy copy methods. you can also pass named parameters into copy method
  val caseJim3 = caseJim.copy(age = 45)                                         // Creates new instance of this case class
  println(caseJim3)                                                             // prints "CasePerson(Jim,45)"

  //5. Case classes have companion objects and companion objects also have some handy factory methods
  val thePerson = CasePerson                                                    // valid definition because CasePerson is companion object of CasePeron class
  val mary = CasePerson("Mary",23)                                              // We've used object's apply method , which let's use object as a function. Object's apply method works the same way as constructor of the class. So, we usually do not use "new" keyword for creating a new instance

  //6. Case classes are serializable which makes classes useful when dealing with distributed systems , instances of case classes can be sent through the network and in between JVMs
      // Ex : Important in akka framework , akka deals with sending serializable messages through the network and messages in general are case-classes


  //7. Case Classes have extractor patterns i.e case classes can be used in PATTERN MATCHING(one of the most powerful scala feature)


  //CASE OBJECTS : acts like a case class except they are object. So , they don't get companion objects.
  case object UnitedKingdom {
    def name: String = "The UK of GB and NI"
  }

  /*
    Exercise : Expand MyList
    Use case classes and case objects whenever you see fit.
   */
}
