package lectures.part2oop

object AbstractDataTypes extends App {
  //ABSTRACT MEMBERS: Methods which are not implemented , fields which are left blanked are called abstract members.
  //ABSTRACT CLASSES : Classes which contains abstract fields or methods are called abstract classes. Keyword : abstract. Abstract classes cannot be instantiated. They are meant to be extended later.
  abstract class Animal{
    val creatureType: String                          // As I am only defining it but not supplying any value to this val , it is abstract.
    def eat: Unit                                     // This method is also unimplemented , so abstract.
  }

  class Dog extends Animal {                          // Dog must provide implementation for creatureType and eat OR must be declared abstract.
    val creatureType: String = "Canine"
    def eat = println("Crunch Crunch")          // Although this qualifies as overriding, but override keyword not required here because compiler knows that we're extending abstract class and hence overriding method(by default).
  }


  // TRAITS - Ultimate abstract datatype in scala.
  trait Carnivore{
    def eat(animal: Animal): Unit

  }

  trait ColdBlooded

  // Traits by default(just like abstract classes) have abstract fields and methods but unlike Abstract classes , TRAITS can be inherited along classes i.e You can extend more than one trait at a time. Ex:
  class Crocodile extends Animal with Carnivore with ColdBlooded {                                        // Crocodile inherits members from Animal, Carnivore, ColdBlooded
    val creatureType: String = "Croc"
    def eat:Unit = println("nomnomnom")
    def eat(animal: Animal): Unit = println(s"I'm a croc and I'm eating ${animal.creatureType}")
  }
  val croc: Crocodile = new Crocodile
  val dog: Dog = new Dog
  croc.eat(dog)

  /*
     Both abstract classes and traits can have abstract as well as non-abstract members.
    --------------TRAITS vs ABSTRACT CLASSES---------------
    1) Traits do not have constructor parameters. Ex : I cannot define a trait like trait Carnivore(name: String). Here, name is constructor parameter.
    2) Multiple traits may be inherited by same class. Scala has single class inheritance but multiple trait inheritance.
    3) Abstract classes describe what they are BUT Traits describe what they do (Generally , this is how you chose which one to use where)
        TRAITS = BEHAVIOR , ABSTRACT CLASSES = THING
  */

}
