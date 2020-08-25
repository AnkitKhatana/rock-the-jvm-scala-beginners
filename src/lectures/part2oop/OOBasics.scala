package lectures.part2oop

object OOBasics extends App {

  val person = new Person                   // instantiation of class person i.e reserving the memory for data in class
  println(person)

  val person1 = new Person1("John",26)        //instantiation
  //println(person1.age)                                    //Compiler error:"Cannot resolve symbol age". We can't access class parameters using dot because they're not class members

  val person2 = new Person2("John",26)
  println(person2.age)                                      //prints 26. Class fields can be accessed using dot.

  val person3 = new Person3
  println(person3.x)                                        //prints 4 , 3  on two different lines. This is because at every instantiation of class Person3 , all the expressions inside the body(code block) are also evaluated and println( 1+3 ) prints 4 before println(person3.x) is executed.

  val person4 = new Person4("John")
  person4.greet("Paul")                              //prints "Paul says : Hi, Paul."
  person4.correctGreet("paul")                       //prints "John says : Hi, paul"
  person4.greet()                                           //prints "Hi , I am John"


  //Testing of writer and novel class functionalities
  val writer = new Writer("Paulo", "Coelho" , 1962)
  val imposter = new Writer("Paulo", "Coelho" , 1962)
  println(writer.fullName)                            //prints "Paulo Coelho"

  val novel_alchemist = new Novel("The Alchemist" , 2001 , writer)

  println(novel_alchemist.authorAge)                        //prints 39
  println(novel_alchemist.isWrittenBy(writer))              //prints true
  println(novel_alchemist.isWrittenBy(imposter))            //prints false. Although the values are same but since instances of the class are different we got false.

  val new_alchemist = novel_alchemist.copy(2005)
  println(new_alchemist.authorAge)                          //prints 43


  //Testing of counter
  val counter = new Counter(0)
  counter.increment.print                                   //prints "Incrementing" and then count value which is 1
  counter.decrement.print                                   //prints "Decrementing" and then count value which is -1
  counter.increment(5).print                        //prints "Incrementing" 5 times and then count value which is 5
  counter.decrement(3).print                        //prints "Decrementing" 3 times and then count value which is -3

  //Notice that increment method returns a Counter class instance but since we're not catching it , we're not able to track the progress across the lines
}


//CLASSES are blueprint that describes the way things look like(data) and the way things work(functionalities).

class Person                                // creation of class Person

class Person1(name: String , age: Int)      // constructor for class Person1. name and age are class parameters and this is how they're passed.

//Class parameters are not class fields. To convert a class parameter into class field , add var/val ahead.

class Person2(val name: String , val age: Int)    // Here , name and age are class fields.

class Person3 {
  //body - is not an expression per se but defines the implementation of this class. Body can have anything including var and val definitions , methods definitions , packages , other classes definitions etc.
  // value of the body(body is a block expression) is ignored because this is just implementation not expression but you can do anything inside the body that you can do in a block.
  // val,var definitions inside body are actually fields of the class.

  val x =3

  println( 1+3 )
}


//Defining Functions inside Class. As , these functions are defined inside the class , they're called methods.
class Person4 (name: String , val age: Int){

  def greet(name: String): Unit = println(s"$name says : Hi, $name.")                           // Here , compiler thinks that we're accessing method's name , because method's name has local scope.

  def correctGreet(name: String): Unit = println(s"${this.name} says : Hi, $name")              // using this.name we can access class's name. Notice that name is not a class field but class parameter and still we can access it using this.name because it is available in class definition.

  //Overloading i.e methods of same name but different signatures (different signatures means different number of arguments or different datatypes of arguments coupled with return type of function , return type alone is not sufficient for overloading)
  def greet(): Unit = println(s"Hi , I am $name")                                               // Here , $name is equivalent to "this.name" i.e here "this." is implied because no local variable called name.

  //Multiple constructors(for a class) or overloading constructors (Constructors = which allows construction of instances of class)

  //Auxiliary constructor = whose implementation is another constructor
  def this(name: String) = this(name,0)                   // Here , we've define an auxiliary constructor for Person4 using this keyword. It's calling "class Person4 (name: String , val age: Int)" constructor using values (name , 0). So , it's setting age = 0 everytime. This can be achieved using default parameters , so no need of auxiliary constructors.

  def this() = this("John")                               // Auxiliary constructor whose implementation is calling above auxiliary constructor.

  //Auxiliary constructor can only have one implementation i.e to call another constructor(primary or secondary). This limitation makes auxiliary constructors impractical because they're only doing the job of default parameters.
  //  Ex : We declared our first auxiliary constructor just to emit the age parameter but this can be more easily solved by supplying default parameter to actual class definition i.e  class Person4 (name: String , val age: Int = 0)

}


/*
      ----------- EXERCISE 1 -----------
      Implement novel and writer class.
      Writer : first name , sur name , year of birth
        - Method : fullname() - which returns concatenation of first name and sur name.

      Novel : name , year of release , author
        - Method : authorAge() - which returns age of author at the time of release.
                   isWrittenBy(author) - tests if it's written by the author whose name is provided as method parameter
                   copy(new year of release) - new instance of novel with new year of release
   */

class Writer(firstName: String ,surName: String ,val yearOfBirth: Int){
  def fullName: String = firstName+" "+surName
}

class Novel(val name: String, val yearOfRelease: Int, val author: Writer ){
  def authorAge: Int = yearOfRelease - author.yearOfBirth
  def isWrittenBy(author : Writer): Boolean = author == this.author
  def copy(newYear: Int): Novel = new Novel(name, newYear, author)

}

/*
    ----------- EXERCISE 2 ------------
    Implement Counter class
      - recieves an int.
      - method which returns current count.
      - methods to increment/decrement the counter by 1 and return the new counter , also log(print on console) when you're incrementing/decrementing.
      - overload inc/dec to receive an amount by which you increment or decrement the counter and returns the new counter , also log while incrementing and decrementing.

 */

class Counter(val count : Int ) {
  // No need to define the method to return the current count because we can access current count using dot operator because count is field not parameter(cuz , we have used val in front of the name in constructor definition)
  def increment: Counter = {                             // Here we're returning the new counter class instance rather than modifying current count. We cannot modify count as it is val not var. This is principle of immutability.
    println("Incrementing")
    new Counter(count+1)
  }
  //IMMUTABILITY : This says that instances are fixed , they cannot be modified. Whenever you need to modify the contents of an instance, oyu need to return a new instance with updated contents. This is very important concept in functional programming.

  def decrement: Counter = {
    println("Decrementing")
    new Counter(count-1)
  }

  def increment(amount: Int): Counter = {
    if(amount<=0) this
    else increment.increment(amount-1)                    //increment method without parameters return a new Counter class instance which is then used to call increment function with parameters. processing(inc) is done by increment function without parameters but we need to decrement the amount to keep track of remaining increments, so we call increment(n-1).

  }

  def decrement(amount: Int): Counter =
    if(amount<=0) this
    else decrement.decrement(amount-1)

  def print = println(count)
}