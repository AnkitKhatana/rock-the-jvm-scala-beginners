package lectures.part3fp

import java.util.Random

object Options extends App {
  /*
      OPTIONS: Option is a wrapper for a value that might be absent. Option type's implementation is very similar to MayBe type.
               BUT, You should approach Option with a different philosophy.
               INSTEAD OF , considering option to be collection of at most one element; OPTION means possible absence of a value.
    -> Options are present throughout scala library. Ex:
       1) In Map : when we want to get value associated with a key, instead of calling apply method (which might throw an exception if key doesn't exist) ;
                   we use get method which returns a value(if present) or None(denoting absence, if no value).
       2) In List : Trying to access head of an empty list usually throws an exception ; or ; finding something by passing a predicate also , might or might not return a value.
                    So , these functions are designed to return an option.
   */

  val myFirstOption: Option[Int] = Some(4)                                                                              // We have an option of int , this denotes that it can hold int or none. To build option, Use some(if there's a value) or use none(if no value)
  val noOption:  Option[Int] = None

  println(myFirstOption)                                                                                                // Prints Some(4)

  //Options were invented to deal with unsafe APIs. Whole point of having option is that we shouldn't do null checks ourselves, option will do this for us.
  def unsafeMethod(): String = null                                                                                     // unsafeMethod written by someone else and may return null for some cases
  //WRONG APPROACH
  val wrongResult = Some(unsafeMethod())                                                                                // Wrong approach. Because you might get some(null) which breaks the whole point of option
  //INSTEAD, use Option companion's apply method
  val result = Option(unsafeMethod())                                                                                   // apply method takes care to build a some or none depending on whether returned value is null or not

  println(result)                                                                                                       // Prints "None"
  println(wrongResult)                                                                                                  // Prints "Some(null)"

  //Use options in chained methods
  def backupMethod(): String = "A valid result"
  //unsafeMethod is preferred API but if it returns null , then fall back to backupMethod
  val chainedResult = Option(unsafeMethod()).orElse(Option(backupMethod()))                                             // If unsafeMethod returns null, then Option(unsafeMethod()) will be None. "orElse(Option(backupMethod()))" will only execute if previous option was none.
  println(chainedResult)                                                                                                // Prints "Some(A valid result)"

  //We were using already designed unsafe API but If you're designing unsafe API, then make your method return option of something instead of returning nulls
  def betterUnsafeMethod(): Option[String] = None
  def betterBackupMethod(): Option[String] = Some("A valid result")

  val betterChainedResult = betterUnsafeMethod() orElse betterBackupMethod()


  // FUnctions on Options
  println(myFirstOption.isEmpty)                                                                                        // Prints false
  println(noOption.isEmpty)                                                                                             // Prints true

  println(myFirstOption.get)                                                                                            // UNSAFE (DO NOT use this) because option maybe none and getting a value where there isn't a value , will result in nullPointerException.

  //map,flatmap,filter
  println(myFirstOption.map(_ * 2))                                                                                     // Prints "Some(8)"
  println(myFirstOption.filter(x => x > 10))                                                                            // Prints "None" . filter can convert an option of some into option of none if predicate returns false.
  println(myFirstOption.flatMap(x => Option(x * 10)))                                                                   // Prints "Some(40)"

  //for-comprehension - since we have map,flatmap,filter. We also have for-comprehension

  /*
      EXERCISE :
        1) Given below is an API(by some other programmer) , try to establish a connection.
   */

  //Data for Exercise-1
  val config: Map[String, String] = Map(
    //fetched from elsewhere like configuration file or another connection. So , we don't have certainty that keys host and port have values inside this map.
    "host" -> "176.45.36.1",
    "port" -> "80"
  )

  class Connection {
    def connect = "Connected"                                                                                           // In reality, would connect to server
  }
  object Connection {
    val random = new Random(System.nanoTime())
    def apply(host: String, port: String): Option[Connection] =                                                         // It returns a connection or no connection depending on random choice. This basically simulates possibility of a connection or a faulty connection.
      if (random.nextBoolean()) Some(new Connection)
      else None
  }

  // One possible solution to Exercise-1. TRY to establish a connection , if successful in connecting then print the connect method. So, you have a map which might or might not have values against some keys , and , you have an API which might or might not return a connection.

  val host = config.get("host")
  val port = config.get("port")

  /*
    **********IMPERATIVE EQUIVALENT OF FUNCTIONAL CODE***********
      if(h != null)
        if(p!=null)
          return Connection.apply(h, p)
      return null
   */
  val connection = host.flatMap(h => port.flatMap(p => Connection.apply(h,p)))                                          // flatMap on option handles none value. lambda inside flatMap only executed if there's a value , it won't be executed if there's none. If None , then return None. Type of connection = Option[Connection]

  /*
      if(c != null)
        return c.connect
      return null
   */
  val connectionStatus = connection.map(c => c.connect)                                                                 // map also handles none the same way. Type of connectionStatus = Option[String]

  // if(connectionStatus == null) println(None) `else println(Some(ConnectionStatus.get))
  println(connectionStatus)                                                                                             // Sometimes Prints "Some(Connected)" , sometimes prints None

  /*
      if(status != null)
         println(status)
  */
  connectionStatus.foreach(println)                                                                                     // Prints Connected when Some(Connected) , blank when None


  //Chained calls - THIS IS SHORTHAND SOLUTION
    config.get("host")
      .flatMap(host => config.get("port")
        .flatMap(port => Connection.apply(host, port))
        .map(connection => connection.connect))
      .foreach(println)


  //For comprehension
  val forConnectionStatus = for {                                                              // HOW IS THIS FOR-COMPREHENSION READ? GIVEN-BELOW
    host <- config.get("host")                                                                 // Given a host which is obtained from config.get("host")
    port <- config.get("port")                                                                 // Given a port which is obtained from config.get("port")
    connection <- Connection.apply(host, port)                                                 // Given a connection from Connection.apply(host, port)
  } yield connection.connect                                                                   // i.e assuming host, port, connection are not null. Then, give me connection.connect ;Otherwise; give me none

  forConnectionStatus.foreach(println)
}
