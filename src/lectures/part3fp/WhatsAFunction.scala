package lectures.part3fp

object WhatsAFunction extends App {
  // DREAM: Use functions as first class elements i.e We would like to work with functions just like we work with values
  // PROBLEM: JVM was originally designed for java( for instances of classes). Only way you could simulate functional programming was to use classes and instances of classes.
  // So in java or OO scala , we wrote methods inside classes and then to use those methods , we would instantiate the class (either anonymously or non-anonymously) and then use the methods using those instances. AND , scala came up with a really cool trick to make it look like a functional programming language.

  //doubler is an instance of "function like class"(MyFunction)
  val doubler = new MyFunction[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }
  //Advantage of scala : doubler(2) looks like a function call although doubler's an instance.
  println(doubler(2))                                                     // Prints 4

  //Scala supports these function type out of the box. Function types supported : Function1, Function2 .... upto .... Function22. Definition: Function1[A, B]
  val StringToIntConverter = new Function[String, Int] {
    override def apply(string: String): Int = string.toInt
  }
  println(StringToIntConverter("3") + 4)                                  // Prints 7

  //By default , Scala supports these function types upto 22 parameters. Ex:
  val adder: ((Int, Int) => Int) = new Function2[Int, Int, Int] {                              // Syntactic sugar for Function2 : (Int, Int) => Int)
    override def apply(a: Int, b: Int): Int = a + b
  }

  println(adder(4,5))                                                                           // Prints 9
  //Function types "Function2[A, B, R]" === "(A,B) => R"

  //ALL SCALA FUNCTIONS ARE OBJECTS / INSTANCES OF CLASSES DERIVING FROM Function1,Function2 etc.

  /*
      EXERCISES :
        1,) Use function-type to define a function which takes 2 strings and concatenates them.
        2.) Transform MyPredicate and MyTransformer into function types.  (In , MyList.scala)
        3.) Define a function which takes an int and returns another function which takes an int and returns an int. Steps:
              1) what's the type of this function
              2) How to implement this special function
   */

  // 1.
  val concatenator: ((String,String) => String) = new Function2[String, String, String] {
    override def apply(v1: String, v2: String): String = v1+v2
  }
  println(concatenator("Hi , ", "Scala"))                                                 // Prints "Hi , Scala"

  // 3.
  val superAdder: Int => Function1[Int,Int] = new Function1[Int , Function1[Int,Int]] {             // Type of superAdder : Int => Int => Int
    override def apply(v1: Int): Function1[Int, Int] = new Function1[Int,Int] {
      override def apply(v2: Int): Int = v1 + v2
    }
  }

  val adder3 = superAdder(3)                                                              // adder3 is returned function. Type of adder3 : Int => Int
  println(adder3(4))                                                                      // Prints 7
  // superAdder is a curried function. curried function can be called with a multiple parameter list. curried function receives some kind of parameter and returns another function which receives parameters
  println(superAdder(3)(4))                                                               // Prints 7

}


//Normally
class Action {
  def execute(element: Int): String = ???
}

//To make function more abstract. Then we can define an on-the-spot implementation using anonymous classes , just like we did with "MyTransformer" trait in MyList.scala
trait Action1[A,B] {
  def execute(element: A): B
}

//Scala's Trick. We used the function name as trait name, defined an apply method. So that , I can access using name of the instance. Which looks like functions behaving as first class citizens.
trait MyFunction[A, B] {
  def apply(element: A): B
}

