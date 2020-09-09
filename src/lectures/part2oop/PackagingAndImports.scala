package lectures.part2oop // This is not an expression. This line says that whatever definitions we write in this file will be part of lectures.part2oop package

import playground.{Liverpool, ManchesterUnited => TheRedDevils}                           // Liverpool and ManchesterUnited , both belong to same package. If you want to import everything from a package , then you can use underscore(_)
//import exercises.Cons


object PackagingAndImports extends App {
  /*
    PACKAGE : A bunch of definitions grouped under the same name. 99% of the time this matches directory/folder structure.
        - Packages are in hierarchy. Hierarchy is given by dot notation. Ex: "part2oop" is sub-package of "lectures" package.
   */

  //Package members are accessible by their simple name. Ex: Writer class in OOBasics.scala can be accessed directly
  val writer = new Writer("Dan","Brown",1980)

  //if you're not in the proper package, you need to import the correct package or use fully qualified class name.
  val list = new exercises.Cons(1,exercises.Empty)                            // Here, exercises.Cons() is fully qualified class name. "exercises" is package name.

  /*
    * Package Object: Scala specific code organizing structure.
    * Need of package object : Sometimes we may want to write methods or constants outside of everything else (Just like we write traits , classes , objects). When we need universal objects , universal methods that reside outside classes , so you don't need to resort to classes to access them. That's when we use Package objects.
    * There can be only one Package object per package. Name of package object is same as that of package. Package object sits in package.scala file.
    * constants and methods are visible in whole package.
    * Very rarely used
  */

  //Accessing package objects:
  sayHello                                                                      // Method defined in package object
  println(SPEED_OF_LIGHT)                                                       // Constant defined in package object


  //Imports - Multiple imports from same package grouped in one import statement
  val manU = new TheRedDevils                                                   //Name aliasing of imports (We can refer playground.ManchesterUnited using TheRedDevils now). Useful when , we need to import more than one class with same name from different packages
  val liverpool = new Liverpool

  /*
   * Default Imports : These are the packages which are automatically imported without any intentional imports.
   * Examples are :
   * java.lang - contains String, Object, Exception etc
   * scala - contains Int, Nothing, Function and all the basic stuff in scala
   * scala.Predef - contains println, ???
  */

}
