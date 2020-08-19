package lectures.part1basics

object Expressions extends App {
  //MATHEMATICAL EXPRESSIONS
  val x = 1+2       //RHS = Expression . Expression has a value and type in scala. So , compiler can assign the same type to x.
  println(x)

  println(2 + 3 * 4)      //Mathematical Operators : + - * / & | ^ << >> >>>(right shift with zero extension)



  //BOOLEAN EXPRESSIONS
  println(1 == x)         // prints false(x=3). Here , "1==x" is boolean expression. Relational Operators : == != > >= < <=

  println(!(1 == x))      // Logical Operators : ! && ||



  //INSTRUCTIONS (DO) VS EXPRESSIONS (VALUE)
  //An instruction is something that we tells a computer to do. Ex: print something , change a variable etc. Languages like Java , Python , C , C++ works with instructions
  //An expression is something that has a value and/or a type. In scala or functional programming language , we think in terms of expressions i.e every statement should return a value.

  //IF EXPRESSION
  val aCondition = true
  val aConditionedValue = if(aCondition) 5 else 3       // In other languages we could've set aConditionedValue=5 else aConditionedValue=3. But , In scala if is an expression so it returns a value. It will either return 5 or 3.
  println(aConditionedValue)
  println(if(aCondition) 5 else 3)                      // This prints 5. It acts the same way as println(1+3)


  //LOOPS - Discouraged in scala because they don't return anything meaningful and only executes side-effects.
  var i = 0
  while(i<10) {                 // While loop is a side effect. It returns Unit.
    println(i)
    i+=1
  }
  //NEVER WRITE THIS AGAIN

  //Everything in scala is an expression
  //UNIT TYPE
  val aWeirdValue = (i=3)     // aWeirdValue's type = Unit(equivalent to void of c,c++). If return value is Unit then it means that don't return anything meaningful. (i=3) is a side effect. Side Effects in scala are expressions returning unit.
  println (aWeirdValue)         // prints (). "()" is the only value a unit type can hold.


  // Ex of side Effects : println(), whiles , reassigning. Side effects are reminiscent of Imperative programming i.e they're like instructions but in scala they're still expressions which returns Unit.


  //Code Blocks : surrounded by curly braces and inside we can write code. Code block is an expression . Value of the block is value of its last expression.
  val aCodeBlock = {
    val y=2           //A code block can have its own definition of values , variables etc inside the code block, which is visible only inside the code block.
    val z=y+1         //z won't be visible outside this code block.
    if(z>2) "hello" else "goodbye"
  }
  println(aCodeBlock)     //prints "hello"


  //Difference between "Hello world" and println("Hello World")?
  //ANS : "Hello World" is a value of type String. println("Hello World") is an expression which is a side effect , hence it's type is Unit . So , types of these two are different.

  //Output questions:

  val someValue = {
    2 < 3
  }

  val someOtherValue = {
    if(someValue) 235 else 678
    45
  }

  println(someValue)              //prints true
  println(someOtherValue)         //prints 45
}
