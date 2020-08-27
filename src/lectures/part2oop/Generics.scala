package lectures.part2oop

object Generics extends App {
  // When you want to implement a functionality for more than one type without writing the code for each type. Ex: Linked List for each type
  class MyList[A] {                   // type A in between the square brackets denotes the generic type. Can use generic type in trait type parametrization.
    //use the Type A
  }

  class MyMap[Key,Value]                            // Here , key and value are two generic types. You can have as many generic types as you want.

  // Instantiate the above class with different datatype
  val integerList = new MyList[Int]                 //Now A will act as Int in class definition
  val stringList = new MyList[String]


  // Generic Methods
  object MyList {                                   // objects cannot be type parametrized , object MyL comist =panion to class MyList
    //Method for : Given a type parameter , construct an empty MyList parametrized with that type
    def empty[A]: MyList[A] = ???                   // Method empty type-parametrized with A. ??? denotes that no implementation now and returns nothing
  }
  //Use of empty method
  val emptyListOfIntegers = MyList.empty[Int]


  // VARIANCE PROBLEM
  class Animal
  class Cat extends Animal
  class Dog extends Animal

  /*
      Question : If Cat extends Animal , does List of Cats also extends List of Animals.
      Answer : 3 options=
        1) Yes : List[Cat] extends List[Animal] . This behavior is called CO-VARIANCE.
        2) No : List[Cat] and List[Animal] are two separate things. Called IN-VARIANCE.
        3) Hell , No !! : Relationship is exactly opposite. Called CONTRA-VARIANCE.
   */

  //Covariant List
  class CovariantList[+A]                               //"+A" denotes that this is covariant list.
  val animal: Animal = new Cat                          //Polymorphic type substitution
  val animalList: CovariantList[Animal] = new CovariantList[Cat]              //Instantiation of CovariantList done in same way as polymorphic type substitution
  //Hard question on this approach : Can I add a Dog to animalList? i.e animalList.add(new Dog)  =>  We return a list of animals.
  //Since animalList is list of animals and Dog is animal ,In theory ,we should be able to add it ; BUT ; if we add Dog it'll pollute the list since it is list of Cats


  //InVariant List - Easy Option
  class invariantList[A]                                                                            //MyList defined at the top is an invariant list.
  val invaiantAnimalList: invariantList[Animal] = new invariantList[Animal]
  // val invaiantAnimalList: invariantList[Animal] = new invariantList[Cat]                        // Not Allowed


  //ContraVariant List
  class ContraVariantList[-A]                                                                       //"-A" denotes contra-variant list
  val contraVariantList: ContraVariantList[Cat] = new ContraVariantList[Animal]                     // We're replacing List of Cats with List of animals. This doesn't make sense because an animal can be a dog ,horse etc
  //A better use case for contravariant list
  class Trainer[-A]
  val trainer: Trainer[Cat] = new Trainer[Animal]                                                   // Here , it makes sense semantically. Because , a trainer of animal can train a cat and all other animals.



  //BOUNDED TYPES   -     allow you to use generic classes only for certain types that are either a subclass of different type or superclass
  //Upper-bounded (<:)
  class Cage[A <: Animal](animal : A)                                                                            // Here , "<:" is an operator. which says that A can only be substituted by subclasses of Animal. So , cage can only hold types which are subclasses of Animals. Class receives a parameter animal
  val cage = new Cage(new Dog)                                                                                   // Dog extends Animal. Hence , is accepted.

  //Lower-bouded (>:)
  class Cage1[A >: Animal](animal : A)                                                                             // This says , Cage1 only accepts something which is supertype of animal.


  //BOUNDED TYPE SOLVES A VERY IMPORTANT PROBLEM WHICH ARISES WHEN WE WRITE CO-VARIANT COLLECTION
  class MyCovariantList[+A] {
    /*
    def add(element: A): MyCovariantList[A] = ???                                                                 // This method signature doesn't work. Compiler error : "Covariant type A occurs in contravariant position in type A of value element". This error is technical version of the hard question we saw in covariantList section.

    So , whenever we want to define a covariant list , we need to answer this question : "If i have a list of animals which in fact is a list of cats , what happens if i add a new dog to it?" . Answer is : The list is turned into List[Animal] (i.e.becomes more generic)  because now it have both cats and a dog. Technical implementation of this for add method is given below:
    */
    def add[B >: A] (element: B): MyCovariantList[B] = ???
    /*
      Above Definition of function says : "If i have a list of A , and I call add(B) on this list (where B is superClass of A) ; then ; method returns MyCovariantList of B not A.
      In terms of cats and dogs :
      A = Cat
      B = Dog = Animal
      Then , we'll return list of Animals.
     */
  }

}
