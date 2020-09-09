package exercises

import javafx.collections.transformation.SortedList

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


    ********** Exercise (Case Classes) :
    Expand MyList : Use case classes and case objects whenever you see fit.
    Solution : Made Cons class as case-class and Empty object as case-object. Functionalities added by doing so :
        1) Implemented equals and hashCode. So, can be used in collections.
        2) Made MyList serializable. Extremely powerful to use in distributed systems.

    *********** Exercise (HOF and Curried function)
    Expand MyList. Add following methods:
            - foreach : for each element, it does a side effect. Receives a function(element => Unit). Apply this function to every element of MyList.
                [1,2,3].foreach(x => println(x)) prints each element on a new line.

            - sort : receives a sorting function(which compares 2 elements. (A,A) => Int ) , and returns sorted MyList.
                [1,2,3].sort( (x,y) => y-x ) => [3,2,1]

            - zipWith : (list, (A,A) => B) => MyList(B)
                [1,2,3].zipWith([4,5,6], x * y) => [1 * 4, 2 * 5, 3 * 6] = [4,10,18]

            - fold (start) (function) => a value
                [1,2,3].fold(0)(x + Y) = 6
                - you add first element to start value , and keep on adding till last element.
   */

  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList[B]
  def printElements: String
  //POLYMORPHIC CALL
  override def toString: String = "[" + printElements + "]"                         // toString , hashcode , equals are present in anyref class. So , need to override.

  //HIGHER ORDER FUNCTIONS : Higher order functions either receive Function as parameter or return Function as result.
  def filter(predicate: A => Boolean): MyList[A]
  def map[B](transformer: A=>B): MyList[B]
  def flatmap[B](transformer: A => MyList[B]): MyList[B]
  //Concatenation function used for flatmap
  def ++[B >: A](list : MyList[B]): MyList[B]

  //hofs
  def foreach(function: A=>Unit): Unit
  def sort(compare: (A,A) => Int): MyList[A]
  def zipWith[B,C](list: MyList[B], zip: (A,B) => C): MyList[C]
  def fold[B](start: B) (operator: (B,A)=>B): B

}

/*
trait MyPredicate[-A] {                                     // Function Type : A => Boolean
  def test(arg: A): Boolean
}

trait MyTransformer[-A,B] {                                 // Function Type : A => B
  def transform(arg: A): B
}
*/


//Nothing type is proper substitute of Any type. In the same way Empty should be a substitute for MyList[Any] type. So , naturally ; Empty should extend MyList[Nothing]
case object Empty extends MyList[Nothing] {                                                       // Objects can extend
  def head: Nothing = throw new NoSuchElementException                                  // If list is empty then no head
  def tail: Nothing = throw new NoSuchElementException                               // If list is empty then no tail
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): MyList[B] = new Cons(element , Empty)
  override def printElements: String = ""

  def filter(predicate: Nothing => Boolean): MyList[Nothing] = Empty
  def map[B](transformer: Nothing => B): MyList[B] = Empty
  def flatmap[B](transformer: Nothing => MyList[B]): MyList[B] = Empty
  def ++[B >: Nothing] (list : MyList[B]): MyList[B] = list

  def foreach(function: Nothing=>Unit): Unit = ()
  def sort(function: (Nothing,Nothing) => Int) = Empty
  def zipWith[B,C](list: MyList[B], zip: (Nothing,B) => C): MyList[C] =
    if(!list.isEmpty) throw new RuntimeException("Lists do not have the same length")
    else Empty
  def fold[B](start: B) (operator: (B,Nothing)=>B): B = start
}

case class Cons[+A](h: A , t: MyList[A]) extends MyList[A]{                                       // h=head(first element) , t=tail(remainder of the list)
  def head: A = h
  def tail: MyList[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MyList[B] = new Cons(element, this)
  override def printElements: String =
    if(t.isEmpty) "" + h
    else h + " " + t.printElements

  //For filter , we need to filter the head first and then filter the tail.
  def filter(predicate: A => Boolean) =
    if(predicate(h)) new Cons(h, t.filter(predicate))                                    //Check if head satisfies the predicate. If true , then include head in the answerList and then filter the tail
    else t.filter(predicate)                                                                  //else don't include head in the answerList and filter the tail. Base condition is defined in object Empty i.e when tail=Empty , filter of object Empty will be called and it returns Empty.
  /*
      [1,2,3,4].filter(n%2 == 0)
      = [2,3,4].filter(n%2 == 0)
      = new Cons(2, [3,4].filter(n%2 == 0))
      = new Cons(2, [4].filter(n%2 == 0))
      = new Cons(2, new Cons(4, Empty.filter(n%2 == 0)))
      = new Cons(2, new Cons(4, Empty))
   */

  def map[B](transformer: A => B): MyList[B] = new Cons(transformer(h), t.map(transformer))
  /*
    [1,2,3,4].map(n*2)
      = new Cons(2, [2,3,4].map(n*2))
      = new Cons(2, new Cons(4, [3,4].map(n*2)))
      = new Cons(2, new Cons(4, new Cons(6, [4].map(n*2))))
      = new Cons(2, new Cons(4, new Cons(6, new Cons(8, Empty.map(n*2)))))
      = new Cons(2, new Cons(4, new Cons(6, new Cons(8, Empty))))

   */

  def ++[B >: A](list: MyList[B]): MyList[B] = new Cons(h, t ++ list)
  /*
      [1,2] ++ [3,4,5]
      = new Cons(1, [2] ++ [3,4,5])
      = new Cons(1, new Cons(2, Empty ++ [3,4,5]))
      = new Cons(1, new Cons(2, [3,4,5]))
      = [1,2,3,4,5]
   */

  def flatmap[B](transformer: A => MyList[B]): MyList[B] = transformer(h) ++ t.flatmap(transformer)
  /*
      [1,2].flatmap( n => [n,n+1])
      = [1,2] ++ [2].flatmap( n => [n,n+1] )
      = [1,2] ++ [2,3] ++ Empty.flatmap( n => [n,n+1] )
      = [1,2] ++ [2,3] ++ Empty
      = [1,2,2,3]
   */

  def foreach(function: A=>Unit): Unit = {
      function(h)
      t.foreach(function)
    }
  def sort(compare: (A,A) => Int): MyList[A] = {                                   // we'll implement insertion sort because it's easier to implement
    def insert(x: A, sortedList: MyList[A]):MyList[A] = {
      if (sortedList.isEmpty) new Cons(x,Empty)
      else if (compare(x,sortedList.head) <=0) new Cons(x, sortedList)               // i.e x is smallest element in the list
      else new Cons(sortedList.head, insert(x, sortedList.tail))                     // i.e By default, head is smallest element in the list. So , make a new cons with head as first element and then whatever is returned by insertion of x in sortedList.tail
    }
    val sortedTail = t.sort(compare)
    insert(h,sortedTail)
  }
  def zipWith[B,C](list: MyList[B], zip: (A,B) => C): MyList[C] =
    if(list.isEmpty) throw new RuntimeException("Lists do not have the same length")
    else new Cons (zip(head,list.head), t.zipWith(list.tail, zip))

  /*
    [1,2,3].fold(0)(+)
    = [2,3].fold(1)(+)
    = [3].fold(3)(+)
    = [].fold(6)(+)
    = 6
   */
  def fold[B](start: B) (operator: (B,A)=>B): B =
    t.fold(operator(start,h))(operator)

}

object ListTest extends App{
  val list = new Cons(1, Empty)                                                       // Empty is an object(singleton instance)
  println(list.head)                                                                  // prints 1
  println(list.toString)                                                              // This calls goes to toString method in trait which calls printElements of either Cons or Empty depending upon the instance type. Prints [1]

  val list1 = new Cons(2,list)                                                        // Linked List 2->1
  println(list1.head)                                                                 // prints 2
  println(list1.toString)                                                             // prints [2 1]
  println(list1.add(3).toString)                                                      // prints [3 2 1]

  val clonedlist2 = new Cons(1,new Cons(2, new Cons(3, new Cons(4, Empty))))
  val list2 = new Cons(1,new Cons(2, new Cons(3, new Cons(4, Empty))))                // Linked list 1->2->3->4
  println(list2.head)                                                                 // prints 1
  println(list2.toString)                                                             // prints [1 2 3 4]

  val listOfStrings: MyList[String] = new Cons("Hello",new Cons("Scala",Empty))
  println(listOfStrings.head)                                                         // prints "Hello"
  println(listOfStrings.toString)                                                     // prints [Hello Scala]

  /*
  println(list2.map(new MyTransformer[Int,Int] {                                      // prints [2 4 6 8]
    override def transform(arg: Int): Int = arg*2
  }))

  println(list2.filter(new MyPredicate[Int] {                                         // prints [2 4]
    override def test(arg: Int): Boolean = arg%2==0
  }))
  */

  /*val transform= new Function1[Int,Int] {
    override def apply(arg: Int): Int = arg*2
  }*/
  val transform: Int => Int = _*2
  println(list2.map(transform))                                                       // prints [2 4 6 8]
  println(list2.map(elem => elem*2))                                                  // prints [2 4 6 8]
  println(list2.map(_*2))                                                             // prints [2 4 6 8]

  /*val predicate = new Function1[Int,Boolean] {
    override def apply(arg: Int): Boolean = arg%2==0
  }*/
  val predicate: Int => Boolean = _%2==0
  println(list2.filter(predicate))                                                    // prints [2 4]
  println(list2.filter(elem => elem%2 ==0))                                           // prints [2 4]
  println(list2.filter(_%2==0))                                                       // prints [2 4]

  println(list1.++(list2))                                                            // prints [2 1 1 2 3 4]

  /*val transform1 = new Function1[Int, MyList[Int]] {
    override def apply(arg: Int): MyList[Int] = new Cons(arg, new Cons(arg+1, Empty))
  }*/
  val transform1: Int => MyList[Int] = { (arg: Int) =>
    new Cons(arg, new Cons(arg + 1, Empty))
  }
  println(list2.flatmap(transform1))                                                  // prints [1 2 2 3 3 4 4 5]
  println(list2.flatmap(elem => new Cons(elem,new Cons(elem+1, Empty))))              // prints [1 2 2 3 3 4 4 5]
  //Underscore notation won't work for this lambda because we're using elem twice and you can't use two underscores for one parameter because each underscore corresponds to a new parameter.

  println(clonedlist2 == list2)                                                        // prints true. Use of equals method which was implemented by making Cons a case-class. If , I hadn't used case then I would've had to define a recursive equals method.

  list2.foreach(println(_))                                                             //Prints 1,2,3,4 on new line
  println(list2.sort((x, y) => y-x))                                                    //Prints [4 3 2 1]

  println(list1.zipWith[String, String](listOfStrings, _+ "-" +_))                      //Prints [2-Hello 1-Scala]  . Zipping is very useful utility, spark and hadoop programmers use it a lot.

  println(list2.fold(0)(_+_))                                                     //Prints 10

}

