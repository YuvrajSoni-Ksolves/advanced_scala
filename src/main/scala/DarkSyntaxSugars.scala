
object DarkSyntaxSugars {
  def aMethod(arg: Int): String = s"$arg is the arguement"

  def main(args: Array[String]): Unit = {
    println("Hello")
    //    #1 Methods with single parameter
    val aMethodOutput = aMethod {
      val b = 34;
      val c = 34;
      println(b + c)
      3
    }
    println(aMethodOutput)
    val newList = List(1, 2, 3).map {
      x => {
        x * 10
      }
    }
    println(newList)

    //    #2: single abstract method
    trait Action {
      def act(x: Int): Int
    }
    val anInstance: Action = new Action {
      override def act(x: Int): Int = x + 1
    }

    val anotherInstance: Action = (x: Int) => x + 1;
    println(anotherInstance.act(10))

    val aThread = new Thread(new Runnable {
      override def run(): Unit = println("hello,scala!")
    })

    val anotherThread = new Thread(() => println("another thread created"))

    aThread.run()
    anotherThread.run()

    abstract class AnAbstractClass {
      def implemented: Int = 12

      //      def anotherAbstractMethod(f :Int):String
      def abstractMethod(f: Int): Unit
    }

    val anAbstractInstance: AnAbstractClass = (a: Int) => println(s"$a, hii scala")

    anAbstractInstance.abstractMethod(12)

    //    #3: the :: and #:: methods are special
    val prependList = 2 :: List(3, 4)
    //    2 :: List(3,4)
    //List(3,4).::(2)
    //    ?|
    println(prependList)
    // scala specific, last char decides the associativity of method,
    //        if it is `:`, then the associativity is from right to left
    val aList = 1 :: 2 :: 3 :: List(4, 5)
    //    List(4,5).::3.::2 .::1
    println(aList)

    class MyStream[T] {
      def -->:(value: T): MyStream[T] = this // actual implementation here
    }

    val myStream = 1 -->: 2 -->: 3 -->: MyStream[Int]
    println(myStream)

    // 4# : multi-word method naming
    class TeenGirl(name: String) {
      def `and then said`(gossip: String): Unit = println(s"$name said , $gossip")
    }

    val aTeenInstance: TeenGirl = new TeenGirl("lily")
    aTeenInstance `and then said` "Scala is too sweet"

    //    5# : infix types
    class Composite[A, B]
    //    val composite : Composite[Int,String] = ???
    //    val composite : Int Composite String = ???

    class -->[A, B]
    //    val towards: Int --> String = ???

    //    6#: update() is very much special, much like apply()
    val anArray = Array(1, 2, 3)
    anArray(2) = 7;
    //gets re-written to anArray.update(2,7) --> anArray.update(index, value)
    // used in mutable collections
    //remember apply() and update()
    anArray.foreach(print)

    //    7# : setters for mutable containers
    class Mutable {
      private var internalMember = 0;

      def member = internalMember

      def member_=(value: Int): Unit =
        internalMember = value
    }
    println()
    val aMutableContainer = new Mutable
    aMutableContainer.member = 45; // rewritten as aMutableContainer._ = 45
    println(aMutableContainer.member)
  }

}