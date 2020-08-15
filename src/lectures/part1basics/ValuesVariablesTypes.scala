package lectures.part1basics

object ValuesVariablesTypes extends App {

  //VALS are Immutable : Similar to constant or final BUT In scala(functional programming) used to store intermediate results not constants.
  val x : Int = 42
  println(x)
  //x = 24  IS NOT ALLOWED


  //DATATYPE CAN BE INFERRED BY COMPILER using RHS value
  val y = 42
  println(y)

  //SEMICOLONS ARE OPTIONAL if you're writing only single expression on a single line BUT NECESSARY otherwise
  val aString:String= "hello"; val anotherString = "goodbye"   //Here , ';' mandatory to separate two expressions. But , this is discouraged. Better to put one expression in one line.

  //DIFFERENT TYPES IN SCALA
  val aBoolean : Boolean = true
  val aChar : Char = 'a'
  val anInt : Int = x
  val aShort : Short = 300                  //SHORTS are integers with half the representation size.
  val aLong : Long = 5273456892146L         //LONGS  are integers with double the size. If , value is larger than what Int can hold then you'll have to specify L at the last to specify that this number is long not int
  val aFloat : Float = 2.0f                 // If you don't write f at the end , then compiler considers this number ad Double
  val aDouble : Double = 3.14


  //VARIABLES : used in functional programming for side effects . Example of side effects : changing value of a variable , printing something onto the console. Advantages of side effects : allow us to see what our program is doing.
  //Programs without side effects are easier to understand because there are no variables and nothing to keep track of for logic. But , we cannot completely eliminate side effects because we want our program to do something , print some output , change some value etc.

  var aVariable : Int = 4

  aVariable = 10      //side effect




}
