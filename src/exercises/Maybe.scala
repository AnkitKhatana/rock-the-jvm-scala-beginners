package exercises

abstract class Maybe[+T] {

  def map[B] (f: T => B): Maybe[B]
  def filter (p: T => Boolean): Maybe[T]
  def flatMap[B] (f: T => Maybe[B]): Maybe[B]
}

//Empty "collection"
case object MaybeNot extends Maybe[Nothing] {
  def map[B] (f: Nothing => B): Maybe[B] = MaybeNot
  def filter (p: Nothing => Boolean): Maybe[Nothing] = MaybeNot
  def flatMap[B] (f: Nothing => Maybe[B]): Maybe[B] = MaybeNot
}

//Full "collection"
case class Just[+T] (value: T) extends Maybe[T]{
  def map[B] (f: T => B): Maybe[B]  = Just[B](f(value))
  def filter (p: T => Boolean): Maybe[T] =
    if(p(value)) this
    else MaybeNot
  def flatMap[B] (f: T => Maybe[B]): Maybe[B] = f(value)
}

object MaybeTest extends App{
  val just3 = Just(3)
  println(just3)                                                                                // Prints "Just(3)"
  println(just3.map(_ * 2))                                                                     // Prints "Just(6)"
  println(just3.flatMap(x  => Just(x % 2 == 0)))                                                // Prints "Just(false)" because we're creating a just object with value = false
  println(just3.filter(_ % 2 == 0))                                                             // Prints "MaybeNot" because value doesn't return true for predicate and hence no value, that's why MaybeNot object


  // Collection with only one element in it(i.e. Maybe class) may look like stupidity. It is stupid if you use it as collection but it has a very special use.
  // Philosophy of Maybe class is "To denote the possible absence of a value". This is extremely important.
}