package lectures.part1basics

object StringOps extends App {

  val str: String = "Hello , I am learning scala"

  println(str.charAt(2))
  println(str.substring(7,11))                    // Here , 7 is inclusive and 11 is exclusive. Prints " I a"
  println(str.split(" ").toList)           //Prints "List(Hello, ,, I, am, learning, scala)". Split method splits string based on a character and it includes that character in splitted string.
  println(str.startsWith("Hello"))
  println(str.replace(" ","-"))
  println(str.toLowerCase())
  println(str.length)
  //All the above functions are java functions and as scala runs on jvm , it has access to java string class.

  //Scala has its own additional functionalities . These are scala specific
  val aNumberString = "45"
  val aNumber = aNumberString.toInt                   //Converts a number to a string

  // +: is prepending operation , :+ is appending operation
  println('a' +: aNumberString :+ 'z')                //prints "a45z"

  println(str.reverse)                                // Reverses
  println(str.take(2))                                // prints "He". Takes two characters from the front


  // Scala-specific : String Interpolator

  // S-interpolator   = A very good way to inject names , values and complex expressions into strings without needing to decompose the string into proper constituents and then concatenating them again at the end.
  val name = "David"
  val age = 20
  val greeting = s"Hello , My name is $name and I am $age years old"                          // s at the beginning denotes s-interpolated string. $name suggests that value of variable name will be injected here.
  val anotherGreeting = s"Hello , My name is $name and I am turning ${age+1}  years old"      // s-interpolated strings can also evaluate expression inside curly braces. You can write any complex expression inside curly braces.
  println(anotherGreeting)                                                                    // prints "Hello , My name is David and I am turning 21  years old".


  // F-interpolator   = They can do whatever S-interpolator can do and additionally they can also receive printf like formats
  val speed = 1.2f
  val myth = f"$name%s can eat $speed%2.2f burgers per minute"            // notice "%2.2f". It is a format specification , specifies that print at-most 2 chars before decimal and at-least 2 chars after decimal.
  println(myth)                                                         // prints "David can eat 1.20 burgers per minute"

  //Raw Interpolator = Works same way as s-interpolator although it has a property that it can print literals literally.
  println(raw"This is a \n newline")                                 // prints "This is a \n newline" . Doesn't escape \n, prints it as is.
  val escaped = "This is a \n newline"
  println(raw"$escaped")                                             // prints "This is a
                                                                      //          newline" . \n is escaped. raw-interpolator only prints raw characters as is ,Injected variables do get escaped.

}
