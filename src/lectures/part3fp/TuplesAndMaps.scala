package lectures.part3fp

import scala.:+

object TuplesAndMaps extends App {

  // Tuples : Finite ordered "lists". Basically, tuples group things together. Tuples can group at most 22 different types together. Why 22 ? - Because, they are used in conjunction with function types.

  val aTuple = Tuple2(2, "Hello Scala")                                                 // Type = Tuple2[Int, String] . "(Int,String)" is syntactic sugar for Tuple2[Int, String]
  val aTuple_ = (2, "Hello Scala")                                                      // Can also create tuple using just parentheses.

  // You can access elements of tuple using _1 , _2 ... _22
  println(aTuple._1)                                                                      // Prints 2
  // We can create copies , just like we did with case classes
  println(aTuple.copy(_2 = "GoodBye Java"))                                               // Prints "(2,GoodBye Java)"
  // Swap elements in place
  println(aTuple.swap)                                                                    // Prints "(Hello Scala,2)"



  // Maps : collection used to associate keys to values. Keys are the elements by which we index these maps and values are data corresponding to those keys
  val aMap: Map[String,Int] = Map()                                                       // Instantiates an empty map

  val phonebook = Map(("Jim", 555), ("Daniel", 789)).withDefaultValue(-1)                // To populate a map , specify tuples or pairings
  // "a -> b" is syntactic sugar for (a,b)
  val phonebook_ = Map("Jim" -> 555, "Daniel" -> 789)
  println(phonebook)                                                                      // Prints "Map(Jim -> 555, Daniel -> 789)"


  // Map Operations
  println(phonebook.contains("Jim"))                                                      // Prints true . Querying a key. "contains" method takes key and returns boolean
  println(phonebook("Jim"))                                                               // Prints 555 . "apply" method returns the value associated with key.
  println(phonebook("Mary"))                                                              // Prints -1 . If you call map.apply() with a key that doesn't exist in map , then you get a "No such element" exception if you haven't used "withDefaultValues" while instantiating.

  // Add a pairing
  val newPairing =  "Mary" -> 678
  val newPhonebook = phonebook + newPairing                                               // "+" is the add method. This will create a new phonebook because maps are immutable
  println(newPhonebook)                                                                   // Prints "Map(Jim -> 555, Daniel -> 789, Mary -> 678)"

  //Functions on Maps
  // Map, FlatMap, Filter
  println(phonebook.map(pair => pair._1.toLowerCase() -> pair._2))                        // Prints "Map(jim -> 555, daniel -> 789)" . We've transformed the map by lowercasing the keys.

  //filterKeys - applied to keys
  println(phonebook.view.filterKeys(x => x.startsWith("J")).toMap)                        // Prints "Map(Jim -> 555)". It filters the keys based on the expression inside.
  //map-values - applied to values
  println(phonebook.view.mapValues(number => "0245-" + number).toMap)                     // Prints "Map(Jim -> 0245-555, Daniel -> 0245-789)". To update all the values inside the map.

  //Conversion to other collections
  println(phonebook.toList)                                                               // Prints "List((Jim,555), (Daniel,789))". Converts map into list of pairings.
  println(List(("Daniel",555)).toMap)                                                     // Prints "Map(Daniel -> 555)". Converts List of tuples/pairings to Map.

  //GroupBy
  val names = List("Bob","James","Angela","Mary","Daniel","Jim")
  println(names.groupBy(name => name.charAt(0)))                                          // Prints "HashMap(J -> List(James, Jim), A -> List(Angela), M -> List(Mary), B -> List(Bob), D -> List(Daniel))". Groups all those names together who have same first character

  /*
      EXERCISES:
      1) What would happen if i had two original entries "Jim" -> 555 and "JIM" -> 900 and we ran "println(phonebook.map(pair => pair._1.toLowerCase() -> pair._2))".
      2) Overly simplified social network based on maps
          Each person has a name. Person = String
          Network will keep an association(i.e a map) between each name and list of friends.
          - add a person to the network
          - remove a person
          - friend() which means add one person as a friend , friendship is mutual.
          - unfriend()

          //Some stat methods
          - number of friends of a given person
          - person with most friends
          - how many people have NO friends
          - If there is a social connection between two people(direct or indirect- if A knows B and B knows C , then a social connection between A and C)
   */

  //Exercise-1
  val temp = Map("Jim"->555 , "JIM"-> 900)
  println(temp)                                                                       // Prints "Map(Jim -> 555, JIM -> 900)"
  println(temp.map(pair => pair._1.toLowerCase() -> pair._2))                         // Prints "Map(jim -> 900)" . This is because both the keys are same when in lowercase and hence second pair overwrites the first one.

  //Exercise-2
  def add(network: Map[String,Set[String]], person: String): Map[String,Set[String]] =                         // Using set guarantees that our list of friends will only contain unique elements
    network + (person -> Set())

  def friend(network: Map[String,Set[String]], a: String, b: String): Map[String, Set[String]] = {
    val friendsA = network(a)
    val friendsB = network(b)

    network + (a -> (friendsA + b)) + (b -> (friendsB + a))                           // + is an operator which returns a new set with new element added into the set
  }

  def unfriend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {
    val friendsA = network(a)
    val friendsB = network(b)

    network + (a -> (friendsA - b)) + (b -> (friendsB - a))                           // - is an operator which returns a new set with specific element removed form the set. '+' operator between pairings and set returns a new map with the old pairing(with same key) replaced from map
  }

  def remove(network: Map[String, Set[String]], person: String) = {
    def removeAux(friends: Set[String], networkAcc: Map[String, Set[String]]): Map[String, Set[String]] =               //"friends" is list of person's friend
      if(friends.isEmpty) networkAcc
      else removeAux(friends.tail, unfriend(networkAcc,person,friends.head))                                            //Unfriend first person in the set and then call removeAux on rest of elements

    val unfriended = removeAux(network(person), network)                                                                // removeAux method removes person from everybody's friend-list
    unfriended - person                                                                                                 // Finally , remove person key from the map
  }

  val empty: Map[String, Set[String]] = Map()
  val network = add(add(empty,"Bob"), "Mary")
  println(network)                                                                                                      // Prints "Map(Bob -> Set(), Mary -> Set())"
  println(friend(network,"Bob","Mary"))                                                                                 // Prints "Map(Bob -> Set(Mary), Mary -> Set(Bob))"
  println(unfriend(friend(network,"Bob","Mary"),"Bob","Mary"))                                                          // Prints "Map(Bob -> Set(), Mary -> Set())"
  println(remove(friend(network,"Bob","Mary"),"Bob"))                                                           // Prints "Map(Mary -> Set())"

  // Jim, Bob, Mary
  val people = add(add(add(empty,"Bob"),"Mary"),"Jim")
  val jimBob = friend(people, "Jim", "Bob")
  val testNet = friend(jimBob,"Bob","Mary")

  println(testNet)                                                                                                      // Prints "Map(Bob -> Set(Jim, Mary), Mary -> Set(Bob), Jim -> Set(Bob))"

  def nFriends(netowrk: Map[String, Set[String]], person: String): Int =
    if(!network.contains(person)) 0
    else netowrk(person).size

  println(nFriends(testNet, "Bob"))                                                                              // Prints 2

  def mostFriends(network: Map[String, Set[String]]): String =
    network.maxBy(pair => pair._2.size)._1                                                                               // "maxBy" is a method which is specific to Map. This receives a lambda from a pairing to a value and value has to be comparable. maxBy returns a pairing which has maximum value amongst the pairings returned by lambda. We return _1(which is key or first element) of that pairing.

  println(mostFriends(testNet))                                                                                          // Prints "Bob"

  def nPeopleWithNoFriends(network: Map[String, Set[String]]): Int =
    network.count(pair => pair._2.isEmpty)                                                                               // Only those pairings will be counted whose 2 element's(i.e Set[String]) size is more than 0

  println(nPeopleWithNoFriends(testNet))                                                                                 // Prints 0

  def socialConnection(network: Map[String, Set[String]], a: String, b: String): Boolean ={                              // We'll solve this by running a breadth first search in our network graph
    def bfs(target: String, consideredPeople: Set[String], discoveredPeople: Set[String]): Boolean = {                   // Significance of this function is going to be "Can I find target in discovered people, having already considered consideredPeople "
      if (discoveredPeople.isEmpty) false                                                                                // If discoveredPeople is empty then I have no set of people to search. So , I haven't found the target and I won't find the target.
      else {
        val person = discoveredPeople.head                                                                               // Consider a person out of discovered people
        if (person == target)  true                                                                                      // Found the target, return true
        else if(consideredPeople.contains(person)) bfs(target, consideredPeople, discoveredPeople.tail)                  // If this person has already been considered , then call bfs on rest of the discovered people
        else bfs(target, consideredPeople + person, discoveredPeople.tail ++ network(person))                            // Since , we've considered this person(i.e looked at it) in above 2 ifs and it's not equal to my target , so add this person to considered people. Append this person's direct friend to discovered people.
      }
    }

    bfs(b, Set(), network(a) + a)                                                                                        // consideredPeople is an empty set at the start because I haven't considered anyone yet. We also add 'a' to discovered people set because i can check if a,a are connected(i.e reflexive relationship). bfs is tail recursive
  }

  println(socialConnection(testNet,"Mary","Jim"))                                                                       // Prints true
  println(socialConnection(network,"Mary", "Bob"))                                                                      // Prints false
}