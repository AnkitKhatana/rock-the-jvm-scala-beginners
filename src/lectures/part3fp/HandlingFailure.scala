package lectures.part3fp

import java.util.Random

import scala.util.{Failure, Success, Try}

object HandlingFailure extends App {
  /*
      Exceptions are are handled using try-catch-finally expression. But the problem with this is :
        1) Multiple try/catch expression make the code incredibly hard to follow.
        2) We can't chain multiple try/catch expression or operations that are pron to failure.

     TRY : is a wrapper over a computation that might either succeed with a value or fail with some kind of throwable.
           Try abstract class is extended by two case classes : Failure and Success.

        -Failure holds the throwable that would've been thrown if you had run the computation by yourself.
        -Success wraps the value obtained by that computation.
   */

  //Create Success and Failure explicitly
  val aSuccess = Success(3)                                                                                             // Success and Failure are case classes
  val aFailure = Failure(new RuntimeException("SUPER FAILURE"))

  println(aSuccess)                                                                                                     // Prints "Success(3)"
  println(aFailure)                                                                                                     // Prints "Failure(java.lang.RuntimeException: SUPER FAILURE)"

  //You don't have to create successor failure yourself because Try's companion object's apply method takes care of it
  def unsafeMethod(): String = throw new RuntimeException("NO STRING FOR YOU")
  //Try objects via apply method
  val potentialFailure = Try(unsafeMethod())
  println(potentialFailure)                                                                                             // Prints "Failure(java.lang.RuntimeException: NO STRING FOR YOU)". Program doesn't crash even though I had a run time exception thrown, because Try caught the exception and wrap it up in Failure class instance

  //Syntax sugar
  val anotherPotentialFailure = Try {                                                                                   // We are calling Try's apply method with whatever is passed inside curly braces
    // Code that might throw
  }

  //Utilities
  println(potentialFailure.isSuccess)                                                                                   // Prints false. isSuccess method tells whether whatever code that you wrote inside Try has succeeded or not
  println(potentialFailure.isFailure)                                                                                   // Prints true

  // orElse
  def backupMethod(): String = "A valid result"
  val fallbackTry = Try(unsafeMethod()).orElse(Try(backupMethod()))                                                     // Try unsafeMethod and if it fails then try backupMethod
  println(fallbackTry)                                                                                                  // Prints "Success(A valid result)"

  //If you design the API - analogous to Options
  def betterUnsafeMethod(): Try[String] = Failure(new RuntimeException)                                                 // If you know that your code might throw an exception, wrap your computation in a try. If you know that one of your code paths will throw an exception then wrap that in Failure
  def betterBackupMethod(): Try[String] = Success("A valid result")
  def betterFallBack = betterUnsafeMethod() orElse betterBackupMethod()

  // map, flatMap, filter
  println(aSuccess.map(_ * 2))                                                                                          // Prints "Success(6)"
  println(aSuccess.flatMap(x => Success(x + 10)))                                                                       // Prints "Success(13)"
  //filtering is the only thing that might turn a success into a failure
  println(aSuccess.filter(_ > 10))                                                                                      // Prints "Failure(java.util.NoSuchElementException: Predicate does not hold for 3)"

  //for-comprehension

  /*
      Exercise :
        Data given below. Try to obtain connection with server and given the connection we will try to obtain an HTML page from serve and given the page we will try to print it to the console
   */

  //Data for Exercise
  val hostname = "localhost"
  val port = "8080"
  def renderHTML(page: String) = println(page)

  class Connection {
    def get(url: String): String = {                                                                                    // simulates fetching a HTML page from server
      val random = new Random(System.nanoTime())
      if (random.nextBoolean()) "<html>....</html>"                                                                     // i.e success in fetching the page
      else throw new RuntimeException("Connection interrupted")                                                         // simulates flaky connection or connection interrupted
    }

    // Change for solution
    def getSafe(url: String): Try[String] = Try(get(url))
  }

  object HttpService {
    val random = new Random(System.nanoTime())

    def getConnection(host: String, port: String): Connection = {
      if(random.nextBoolean()) new Connection
      else throw new RuntimeException("Someone else took the port")
    }

    //Change for solution
    def safeGetConnection(host: String, port: String): Try[Connection] = Try(getConnection(host, port))
  }

  //Solution (If you get HTML page from connection , print it to the console i.e call renderHTML)

  //Naive way
  val connection = Try(HttpService.getConnection(hostname,port))
  val html = Try(connection.map(x => x.get("url")))
  html.map(x => renderHTML(x.toString))


  //Chaining naive way
  Try(HttpService.getConnection(hostname, port))
    .flatMap(x => Try(x.get("url"))
      .map(x => renderHTML(x)))

  //for-comprehension naive way
  for {
    connection <- Try(HttpService.getConnection(hostname, port))
    html <- Try(connection.get("url"))
  } renderHTML(html)


  // Using safe methods that we defined for solution
  val possibleConnection = HttpService.getConnection(hostname, port)
  val possibleHTML = possibleConnection.getSafe("url")
  possibleHTML.foreach(renderHTML)

  //Chain safe methods
  HttpService.safeGetConnection(hostname, port)
    .flatMap(connection => connection.getSafe("url"))
    .foreach(renderHTML)

  //for-comprehension safe methods
  for{
    connection <- HttpService.safeGetConnection(hostname, port)
    html <- connection.getSafe("url")
  } renderHTML(html)

  /*
      *************SAME LOGIC IN IMPERATIVE LANGUAGE**************

      try{
        connection = HttpService.getConnection(hostname, port)
        try{
          page = connection.get("url")
          renderHTML(page)
        } catch (some other exception)
      } catch (exception)

   */
}
