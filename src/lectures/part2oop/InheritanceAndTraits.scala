package lectures.part2oop

object InheritanceAndTraits extends App{

  //Single class inheritance(i.e can extend only one class at a time). Inheriting a class means inheriting all the non-private fields and methods.
  //Access modifiers that are allowed in scala : private(Can only be accessed inside the class) , protected(can be accessed inside the class or by sub-class) , no modifier(which means public)
  class Animal{
    val creatureType = "wild"
    protected def eat = println("nomnom")
  }
  class Cat extends Animal{
    def crunch = {
      eat
      println("crunch crunch")
    }
  }
  val cat = new Cat
  //cat.eat                                 // eat can't be accessed from here because access-modifier used is protected
  cat.crunch

  //CONSTRUCTORS
  class Person(name: String, age: Int)
  //class adult(name: String, age: Int, idCard: String) extends person                    // Gives an error "Unspecified value parameters: name,age.
  // Defining class like we did above with class signature also defines it's constructor. This code doesn't compile because when you instantiate a derived class(in our case adult) , JVM will need to call a constructor from parent class first(in our case person). This happens in internals of JVM.
  // Therefore scala compiler forces us to guarantee that there's a correct super constructed code when using derived class. Since , we don't have a constructor for person with zero arguments , we must pass correct parameters like name and age. So, correct adult definition is:
  class Adult(name: String, age: Int, idCard: String) extends Person(name,age)



  //OVER-RIDING
  class Dog extends Animal {
    override val creatureType: String = "domestic"
    override def eat = println("crunch,crunch")                               //Over-ride method using "override" keyword. Over riding works for val,var and methods
  }
  val dog = new Dog
  dog.eat                                                                           //prints "crunch,crunch"
  println(dog.creatureType)                                                         //prints "domestic"

  //Fields as opposed to methods can also be directly overridden in constructors.Ex:
  class Dog1(override val creatureType: String) extends Animal {
    override def eat = println("crunch,crunch")
  }
  val dog1 = new Dog1("K9")
  println(dog1.creatureType)                                                        //prints "K9"


  //TYPE SUBSTITUTION (In broad sense , it is called polymorphism)
  val unknownAnimal: Animal = new Dog1("K9")                             //If i call eat using unknownAnimal it'll call eat method of Dog1 not Animal

  //OVERRIDING : supplying different implementations in derived classes.
  //OVERLOADING : supplying multiple methods with different signatures but with same name in same class.


  //SUPER : is used when you want to reference a method or field of parent class from sub class.Ex:
  class Dog2(override val creatureType: String) extends Animal {
    override def eat = {
      super.eat
      println("crunch,crunch")
    }
  }
  (new Dog2("K8")).eat                                                    // prints "nomnom <newline> crunch,crunch"


  //PREVENTING OVERRIDES : 3 ways to do this
  //1st way: Use final keyword on method = It prevents derived classes from overriding the method. If we use final in front of eat method of Animal , then Dog1 or Dog2 will not be able to override it.
  //2nd way: Use final on entire class = It prevents the class form being extended. If i declare Animal class as final then extension by Cat and Dog becomes illegal. Numerical classes , String class are final in scala.
  //3rd way: Seal the class = can extend classes in THIS FILE , but prevents extension in other files. To seal a class use keyword "sealed". Used when you want to be exhaustive in type hierarchy ,
  // Ex: if only two possible animals were cats and dogs , then you would normally use a sealed class Animal and extend Cat and Dog in this file and prevent Animal being extended from other files.

}
