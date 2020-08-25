package lectures.part2oop

object Objects extends App {
  /*
    CLASS LEVEL FUNCTIONALITY : Functionality that doesn't depend on an instance of a class. Everything we've written so far had connection to an instance of some class.
      There are cases where we shouldn't do that , ex : Universal constants or Universal functionalities that we should be able to access without relying on an instance of some class.
      In java we can do this by declaring an attribute as static and then accessing it as ClassName.attributeName

    SCALA DOES NOT HAVE CLASS-LEVEL FUNCTIONALITY (doesn't know the concept of static)
    OBJECTS IN SCALA have static like functionalities. So , to use class level functionality we use objects in scala.
    OBJECT IS NOT EQUAL TO INSTANCE OF A CLASS IN SCALA.
  */

  // Equivalent code in scala for java code involving static and then accessing using class name
  object Person {
    // "static"/"class" level functionality
    val N_EYES = 2
    def canFly: Boolean = false

    // In companion design pattern, we usually have factory methods in object that can build instances of class
    // A method which takes a mother and father and results in a new person. This is a factory method because it's sole purpose is to build persons given some parameters.
    def apply(mother: Person , father: Person) : Person = new Person("Bobby")

  }
  // Object can have var , val , object definitions. Objects can be defined in the same way as classes , with the exception that objects do not receive parameters.
  // You can access val, var and methods in the same way as you would in class level setting
  println(Person.N_EYES)
  println(Person.canFly)

  //SCALA OBJECT =  SINGLETON INSTANCE (i.e when i define a object , i define it's type and i also define it's only instance)
  val mary = Person                       // Here , Person is an instance of type Person which is the only instance this Person type can have.
  val john = Person
  println(mary == john)                   // prints "true". This is because mary and john points to same object called Person


  // We write object and classes with the same name(even in same scope) , to separate instance level functionality from class level functionality. Class and object are said to be companions here.
  // COMPANION DESIGN PATTERN : This pattern of writing objects and classes of same name is called companion design pattern.
  // So , all the code we write will be accessed from some kind of instance(either instance of class or singleton instance) , so in a way scala is more object oriented than java or other OO languages
  class Person(val name: String) {
    // Instance level functionality

  }

  val person1 = new Person("Person1")
  val person2 = new Person("Person2")
  println(person1 == person2)                         // prints false. person1 and person2 are two instances of class Person, hence are not same. But , with name Person you can only refer to a single instance (singleton instance). This is a subtle distinction.

  val bobby = Person(person1,person2)                 // This looks like a constructor but it's actually apply method defined in Person singleton object


  //SCALA APPLICATIONS is scala object with very important and particular method i.e "def main(args: Array[String]): Unit". Now , method should have exact same definition because scala code is converted into java bytecode and whose entrypoint is "public static void main(String[] args)".
  // So , void == Unit , static == singleton object. So, compiler only executes code which is inside main method. We extends App because it already has a def main method.


}
