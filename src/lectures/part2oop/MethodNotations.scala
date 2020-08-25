package lectures.part2oop

import scala.language.postfixOps

object MethodNotations extends App {

  class Person(val name: String , favouriteMovie: String ,val age : Int = 0){
    //DEFINITIONS USED IN POSTFIX NOTATION SECTION
    def likes(movie: String): Boolean = movie ==favouriteMovie
    def hangOutWith(person: Person): String = s"${this.name} is hanging out with ${person.name}"

    //+ and hangOutWith do the same thing
    def +(person: Person): String = s"${this.name} is hanging out with ${person.name}"

    //DEFINITIONS USED IN PREFIX NOTATION SECTION
    def unary_! : String = s"$name , what the heck?!"                                   // space between unary_! and : is important otherwise compiler thinks that method name is unary_!:

    //DEFINITIONS USED IN PREFIX NOTATION SECTION
    def isAlive: Boolean = true
    def apply(): String = s"Hi , my name is $name and I like $favouriteMovie"           // Method signature is very important here. parentheses are important here.

    //DEFINITIONS USED IN EXERCISE SECTION
    //exercise-1
    def +(nickName: String): Person = new Person(name + " (" + nickName + ")" , favouriteMovie)
    //exercise-2
    def unary_+ : Person = new Person(name , favouriteMovie , age+1)
    //exercise-3
    def learns(subject: String): String = s"$name learns $subject"
    def scalaLearn: String = this learns "Scala"                                        // We've accessed learns method in an infix notation
    //exercise-4
    def apply(times: Int): String = s"$name watched $favouriteMovie $times times"
   }


  //INFIX NOTATION or OPERATOR NOTATION
  val mary = new Person("Mary" , "Inception")
  println(mary.likes("Inception"))        //expression 1
  println(mary likes "Inception")                 //expression 2
  /*
  expression 1 and expression 2 are equivalent and expression 2 is in a natural language, so much easier to read and understand. This is called "infix notation" or "operator notation" (One form of Syntactic Sugar).
  We call it operator notation because method(likes) acts like operator between two objects(mary and inception) , returns boolean ; just like mathematical operators +,-,*,/.
  This only works with methods having one parameter. So , "object.method(with single parameter)" can be replaced with "object method single parameter".
  */

  // SYNTACTIC SUGAR : Nicer ways of writing code that are equivalent to more complex or more cumbersome ways of writing code. Syntactic sugar is more resemblance of natural language.


  //"Operators" in Scala
  val tom = new Person("Tom" , "Fight Club")
  println(mary hangOutWith tom)                   // Valid Expression(Infix style). Prints "Mary is hanging out with Tom". "hangOutWith" acts like operator between two objects mary and tom , returns a string.

  // Scala has extremely permissive method naming scheme. So , I can even name a method as '+' or '-' or just about anything. This makes scala extremely enjoyable to work with.
  //Ex: Akka actors have method names like ! or ?
  println(mary + tom)                             // expression 3
  println(mary.+(tom))                            // equivalent to expression 3

  // "+" operator between numbers is actually a method as well. i.e:
  println( 1 + 2 )                    //expression 4
  println( 1.+(2) )                   //equivalent to expression 4

  //ALL OPERATORS ARE METHOD


  // PREFIX NOTATION ( It is all about unary operator)
  val x = -1                            // Expression 1 (Here , - is a unary operator)
  val y = 1.unary_-                     // Expression 2 = Expression 1
  // Unary operators are also methods. "unary_" prefix only works with these 4 operators : - + ~ !

  println(!mary)                         // Prints "Mary , what the heck?!"
  println(mary.unary_!)                  // equivalent to println(!mary)


  //POSTFIX NOTATION (Functions which do not receive a parameter can be used with postfix notation)
  println(mary.isAlive)                  // prints true
  println(mary isAlive)                  // equivalent to println(mary.isAlive)


  // Special method : apply

  println(mary.apply())                 // prints "Hi , my name is Mary and I like Inception"
  println(mary())                       // equivalent to println(mary.apply())

  /*
      Whenever compiler sees an object being called like a function , it looks for definition of apply in that particular class.
      Apply method can be defined with parameters or without parameters , but parentheses are compulsory. This is very important as it breaks the barrier between object oriented programming and functional programming.
   */


  /*
    EXERCISE :
    1. Overload the + operator defined at line 14. Receives a string and returns a new person with a nickname.
        ex : mary + "the rockstar" => returns new person with name "Mary (the rockstar)" and same favourite movie

    2. Add an age to person class (with default value = 0)
        Add a unary + operator which increments the value of age and returns a new person with age plus one. It resembles prefix ++ operator in java or c++.
          ex : +mary => mary with age incremented.

    3. Add a "learns" method in the person class. Receives a string parameter and returns " Mary learns <string you passed>".
       Add a learnScala method , which doesn't receive any parameter and calls the learns method with scala as parameter. Use it in postfix notation.

   4. Overload the apply method. New apply method receives an int and returns a string.
      Ex : mary.apply(2) => "Mary watched inception 2 times"

   */

  //exercise-1
  println((mary + "the rockstar").name)                                   // Prints "Mary (the rockstar)"
  println((mary+ "the rockstar")())                                       // Prints "Hi , my name is Mary (the rockstar) and I like Inception". We've used apply() method here.

  //exercise-2
  println((+mary).age)                                                    // Prints 1

  //exercise-3
  println(mary scalaLearn)                                                // Prints "Mary learns Scala" . Called scalaLearn in postfix notation

  //exercise-4
  println(mary(4))                                                        // Prints "Mary watched Inception 4 times"

}
