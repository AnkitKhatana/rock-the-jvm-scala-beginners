package lectures.part3fp

object AnonymousFunctions extends App {
  //PROBLEM : instantiating a function is still very much tied to Object oriented way. Ex :
  val doubler = new Function1[Int , Int] {
    override def apply(v1: Int): Int = v1*2
  }
  // Above way is still OO way of defining anonymous objects(here, Function Type) and instantiating it on the spot.

  //Solution : LAMBDA
  val doubler1 = (x:Int) => x*2
  /*
      " (x:Int) => x*2 " : This is syntactic sugar for object oriented way of instantiating, and it's called lambda.
      This instantiate a new Function1 with override def apply , which takes an argument x (Type=Int) and returns x*2
      "Lambda" comes from Lambda calculus which is mathematical representation of functional programming.

      Syntax Rules:
      1) Lambda is a value , basically an instance of Function1.  "val doubler1 = (x:Int) => x*2" can also be written as "val doubler1: Int => Int = x => x*2".  Here,Compiler knows that x is of type Int since we've specified function type as Int=>Int
   */

  //Multiple parameters in a lambda
  val adder = (a: Int, b: Int) => a + b
  val adder1: (Int,Int) => Int = (a,b) => a+b                                 //adder and adder1 have same instantiation

  //No Parameters in a lambda
  val justDoSomething = () => 3                                               // "() => 3" lambda returns 3.
  val justDoSomething1: () => Int = () => 3                                   // justDoSomething and justDoSomething1 have same instantiation

  //Careful
  println(justDoSomething)                                                    // Prints "lectures.part3fp.AnonymousFunctions$$$Lambda$5/292938459@7cdbc5d3" , which is an instance i.e it prints function itself.
  println(justDoSomething())                                                  // Prints 3 . Prints what's returned by function. This is actual call. Reason : We're overriding apply method of Function1. You can call apply method using parentheses after instance name(ex: justDoSomething()) BUT if we don't use parentheses after instance name then it refers to instance only , it doesn't refer to apply method of instance.

  //Curly braces with lambdas
  val stringToInt = { (str: String) =>                                        // Write parameters here
    str.toInt                                                                 // Write implementation here
  }

  //MOAR syntactic sugar
  val incrementer: Int => Int = x => x+1
  val niceIncrementer: Int => Int = _ + 1                                     // Equivalent to incrementer AND "_ + 1" is equivalent to "x => x+1"

  val niceAdder: (Int,Int) => Int = _ + _                                     // Equivalent to adder(or adder1) AND "_ + _" is equivalent to "(a,b) => a+b". Each underscore stands for a different parameter.
  //Underscore representation is highly contextual. If, compiler doesn't know the function type then it won't know which underscore means what and it won't even know the number of parameters.



  /*
      Exercises:
      1) MyList: Replace all FunctionX calls with lambdas
      2) Rewrite superAdder(we wrote in WhatsAFunction.scala) as an anonymous function.
   */

  //Exercise-2
  val superAdder: Int => Int => Int = { (v1: Int) =>
    v1 + _
  }
  val superAdd = (x: Int) => (y:Int) => x+y                                     // Efficient way of writing superAdder

  println(superAdder(3)(4))                                                     //prints 7
  println(superAdd(3)(4))                                                       // prints 7
}
