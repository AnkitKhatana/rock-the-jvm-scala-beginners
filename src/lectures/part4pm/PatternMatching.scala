package lectures.part4pm

import java.util.Random

object PatternMatching extends App {

  //Switch on steroids
  val random = new Random
  val x = random.nextInt(10)                                                                                    // upper bound = 10 i.e any number from 0 to 10

  val description = x match {                                                                                           // This whole block is called pattern matching. Pattern matching tries to match value against multiple patterns. Each pattern is written in a case statement. " case pattern => expression "
    case 1 => "the one"
    case 2 => "double or nothing"
    case 3 => "third time is the charm"
    case _ => "something else"                                                                                          // "_" is called a wildcard and it will match anything
  }

  println(x)                                                                                                            // Prints 7
  println(description)                                                                                                  // Prints "something else"

  // above PATTERN MATCH expression looks like switch from other languages(like java , c ) ,BUT, pattern matching is much more powerful. Like:

  //1. PATTERN MATCHING CAN DECOMPOSE VALUES AND WE CAN ALSO ADD A GUARD. Especially used in conjunction of case classes, case classes have the ability to be deconstructed or extracted in pattern matching.
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 20)

  val greeting = bob match {
    case Person(n,a)  if a < 21 => s"Hi, my name is $n and I can't drink in India"                                      //"if a < 21" is called a guard. AND case statement will read : If bob is a person with name n , age a and if the age extracted  is less than 21 then print following expression.
    case Person(n,a) => s"Hi, my name is $n and I am $a years old"                                                      //So , if bob is a person , this patten match expression is able to deconstruct bob into it's constituent parts(i.e n,a ) even though pattern match expression doesn't know them beforehand. It can extract them and use them in returned expression.
    case _ => "I don't know who I am"
  }
  println(greeting)                                                                                                     // Prints "Hi, my name is Bob and I can't drink in India"
  // So , we cannot only pattern match against any kind of value we want , we can pattern match against a case class pattern and extract the values out of an instance of a case class

  /*
    1. Cases are matched in order.
    2. what if no cases match - We get a match error.
    3. Type of the pattern match expression - It's unification(i.e lowest common ancestor) of return type of all the expressions returned by all the cases in pattern match expression. Ex: Type of greeting : String.
    4. Pattern matching works extremely well with case classes. Because case classes comes with extractor patterns out of the box whereas normal classes do not come with these extractor patterns.
   */

  //2. Pattern matching on sealed hierarchies
  sealed class Animal
  case class Dog(breed: String) extends Animal
  case class Parrot(greeting: String) extends Animal

  val animal: Animal = Dog("Terra Nova")
  animal match {
    case Dog(someBreed) => println(s"Matched a dog of the $someBreed breed")                                            // "Dog(someBreed)" is extractor pattern for case class
    case Parrot(someGreeting) => println(s"Parrot with greeting = $someGreeting")                                       // For , sealed hierarchies , you have to write pattern match for every subclass who extends sealed class otherwise code won't compile
  }

  // Match everything - Rookies tries to implement pattern matching everywhere because it's so powerful but not necessary
  val isEven = x match {                                                                                                // Here , we can write "val isEven = n%2 == 0". No need to use pattern matching here
    case n if n % 2 == 0 => true
    case _ => false
  }

  /*
      Exercise :
      Write a simple function that uses Pattern matching.
         - takes an Expr
         - returns human readable form

     Ex: Sum(Number(2), Number(3))  =>  2 + 3
         Sum(Sum(Number(2), Number(3)), Number(4)) => 2 + 3 + 4
         Prod(Sum(Number(2), Number(1)), Number(3)) => (2 + 1) * 3
         Sum(Prod(Number(2), Number(1)), Number(3)) => 2 * 1 + 3
   */

  // Class hierarchy for Exercise
  trait Expr
  case class Number(n: Int)  extends Expr
  case class Sum(e1: Expr, e2: Expr) extends Expr
  case class Prod(e1: Expr, e2: Expr) extends Expr

  val number_2 = Number(2)
  val number_3 = Number(3)
  val number_1 = Number(1)
  val number_4 = Number(4)

  def conversion(expr: Expr): String = expr match {
      case Number(n) => n.toString
      case Sum(e1,e2) => conversion(e1) + " + " + conversion(e2)
      case Prod(e1,e2) => {                                                                                             // In case of Prod, things get a little complicated because I might have to show parentheses because either e1 or e2 might be expressions of lower rank(might be sum)
        def maybeShowParentheses(expr: Expr) = expr match {
          case Prod(_, _) => conversion(expr)                                                                           // No need to wrap expr is parentheses if expr is Prod
          case Number(_) => conversion(expr)                                                                            // If number , then also no need to wrap
          case _ => "(" + conversion(expr) + ")"                                                                        // In any other case, wrap the expression in parentheses
        }

        maybeShowParentheses(e1) + " * " + maybeShowParentheses(e2)                                                     // conversion to human readable Prod form
      }
    }

  println(conversion(Sum(Number(2), Number(3))))                                                                        // Prints 2 + 3
  println(conversion(Sum(Sum(Number(2), Number(3)), Number(4))))                                                        // Prints 2 + 3 + 4
  println(conversion(Prod(Sum(Number(2), Number(1)), Sum(Number(4), Number(3)))))                                       // Prints (2 + 1) * (4 + 3)
  println(conversion(Sum(Prod(Number(2), Number(1)), Number(3))))                                                       // Prints 2 * 1 + 3
}
