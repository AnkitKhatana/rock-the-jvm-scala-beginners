package exercises

abstract class MyList[+A]  {

  /*
    Exercise 1 : Implement your own collection.
    A single linked list which holds any type(Integer, String, Float etc) and have following following methods:
    1) head : returns first element of the list.
    2) tail : remainder of the list (kind of pointer which holds remainder of the list)
    3) isEmpty : returns true if empty , false otherwise
    4) add(int) : returns a new list with this element added
    5) override toString() : which returns a string representation of the list

    Implement above functionalities using as much subclasses as you want of this MyList abstract class

    Exercise-2 :
    1) Create Generic Trait MyPredicate[-T]. It has a method which checks if T fulfills that condition. Every class which implements this trait will have a different implementation for this method.
        method : test(T) => Boolean
    2) Create Generic Trait MyTransformer[-A,B].
        method( transform(A) => B ) : converts a value of type A into value of type B.Every subclass will have a different implementation.
    3) Implement following functions on MyList :
       - map(takes MyTransformer) => returns MyList of different type.
       - filter(takes MyPredicate) => returns MyList
       - flatMap(takes transformer from A to MyList[B]) => returns MyList[B]

    Ex :
        class EvenPredicate extends MyPredicate[Int] : method test will check whether int is ecen or not
        class StringToIntTransformer extends MyTransformer[String , Int] : method transform will convert string into Int

        Map will work like this. if transformer : n*2 ;then; [1,2,3].map(transformer) => [2,4,6]
        Filter will work like this. if test : (n%2) ;then; [1,2,3,4].filter(test) => [2,4]
        Flatmap will work like this. if transformer : (n=> [n,n+1]) ;then; [1,2,3,4].flatmap(transformer) => [1,2,2,3,3,4,4,5]      i.e flatmap concatenates lists outputted by transformer for each n.
   */

  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList[B]
  def printElements: String
  //POLYMORPHIC CALL
  override def toString: String = "[" + printElements + "]"                         // toString , hashcode , equals are present in anyref class. So , need to override.
}

//Nothing type is proper substitute of Any type. In the same way Empty should be a substitute for MyList[Any] type. So , naturally ; Empty should extend MyList[Nothing]
object Empty extends MyList[Nothing] {                                                       // Objects can extend methods
  def head: Nothing = throw new NoSuchElementException                                  // If list is empty then no head
  def tail: Nothing = throw new NoSuchElementException                               // If list is empty then no tail
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): MyList[B] = new Cons(element , Empty)
  override def printElements: String = ""
}

class Cons[+A](h: A , t: MyList[A]) extends MyList[A]{                                       // h=head(first element) , t=tail(remainder of the list)
  def head: A = h
  def tail: MyList[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MyList[B] = new Cons(element, this)
  override def printElements: String =
    if(t.isEmpty) "" + h
    else h + " " + t.printElements
}

object ListTest extends App{
  val list = new Cons(1, Empty)                                                       // Empty is an object(singleton instance)
  println(list.head)                                                                  // prints 1
  println(list.toString)                                                              // This calls goes to toString method in trait which calls printElements of either Cons or Empty depending upon the instance type. Prints [1]

  val list1 = new Cons(2,list)                                                        // Linked List 2->1
  println(list1.head)                                                                 // prints 2
  println(list1.toString)                                                             // prints [2 1]
  println(list1.add(3).toString)                                                      // prints [3 2 1]

  val list2 = new Cons(1,new Cons(2, new Cons(3, new Cons(4, Empty))))                // Linked list 1->2->3->4
  println(list2.head)                                                                 // prints 1
  println(list2.toString)                                                             // prints [1 2 3 4]

  val listOfStrings: MyList[String] = new Cons("Hello",new Cons("Scala",Empty))
  println(listOfStrings.head)                                                         // prints "Hello"
  println(listOfStrings.toString)                                                     // prints [Hello Scala]

}

