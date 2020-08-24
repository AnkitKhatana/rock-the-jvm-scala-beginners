package lectures.part1basics

object DefaultArgs extends App{

  def tailRecFact(n: Int , accumulator: Long): Long =
    if( n==1 ) accumulator
    else tailRecFact(n-1 , n * accumulator)

  /*  In above function , value of accumulator that is passed to the function is always 1.
      This redundancy distorts the function definition or we need to define this function as a helper function inside another factorial function to keep the function signature normal.
      To avoid this , we can provide accumulator with a default value i.e 1. These are called default arguments. Same function using default arguments is :
   */

  def tailRecFactDefArgs(n: Int , accumulator: Long = 1): Long =                        // Specify the default value using = sign
    if( n==1 ) accumulator
    else tailRecFact(n-1 , n * accumulator)

  //Difference in calling both functions
  println(tailRecFact(10,1))                                                 //Prints 3628800
  println(tailRecFactDefArgs(10))                                                       //Prints 3628800

  //I can overwrite the default value(for a call) by passing a different value for default parameter. Ex:
  println(tailRecFactDefArgs(10,2))                                         // Prints 7257600 because accumulator's value is used as 2 not 1.

  //Problems with default arguments:
  def savePicture(format: String , height: Int , width: Int): Unit = println("saving picture")

  //If format is jpg most of the times then we can define format as default parameter but we encounter a problem
  def savePicturedef1(format: String = "jpg" , height: Int , width: Int): Unit = println("saving picture")

  //savePicturedef1(1920,1080)          // Compiler throws type mismatch error i.e "Required String but found Int" that's because compiler is confused. It doesn't know that you're omitting default parameter(because it's a choice)

  //If we make all the arguments default , then also compiler will be confused id you pass only one or two values.
  def savePicturedef2(format: String = "jpg" , height: Int= 1920 , width: Int= 1080): Unit = println("saving picture")

  //savePicturedef2(1000)                       // Again type mismatch error

  /*
    SOLUTION:
    1.) Pass in every leading argument, then other two considered as defaults. Ex : savePicturedef2("bmp").
    2.) Name the arguments. Ex : savePicturedef2(height= 1000)
   */

  savePicturedef2(height = 1420)                          // No error


  //By naming the arguments , you can also pass arguments in a different order.
  savePicture(height = 1920 , format = "jpg" , width = 1080)

}
