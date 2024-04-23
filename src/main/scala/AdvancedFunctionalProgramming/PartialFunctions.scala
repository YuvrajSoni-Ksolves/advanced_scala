package AdvancedFunctionalProgramming

object PartialFunctions {
  // partial functions works on pattern matching
  def main(args: Array[String]): Unit = {
    val aPartialFunction: PartialFunction[Int, Int] = {
      case 1 => 1
      case 2 => 2
      case 5 => 5
    }

    println(aPartialFunction(1))
    println(aPartialFunction(2))
    println(aPartialFunction(5))
    //    println(aPartialFunction(10))
    println(aPartialFunction.isDefinedAt(1)) // to check whether a partial function is defined for a certain case or not
    if (aPartialFunction.isDefinedAt(1)) {
      println(aPartialFunction(1))
    } else {
      println("Function is not defined for the given input")
    }

    //lifted to total functions by using the .lift method

    val liftedPartialFunction = aPartialFunction.lift // [Int,Int] ===> Option[Int]
    println(liftedPartialFunction(1))
    println(liftedPartialFunction(10).getOrElse("Not defined for this case"))

    // partial function chain
    val pfChain = aPartialFunction.orElse[Int, Int] {
      case 10 => 10
    }
    println(pfChain(2))
    println(pfChain(10))

    //partial functions extend normal functions
    val aTotalFunction: Int => Int = {
      case 1 => 99
    }

    //HOFs accept partial functions as well
    val aMappedList = List(1, 2, 3).map {
      case 1 => 32
      case 2 => 4
      case 3 => 5
    }
    println(aMappedList)
    //Note unlike normal functions, a partial function can only have a SINGLE PARAMETER

    val aManualFussyFunction = new PartialFunction[Int, Int] {
      override def apply(x: Int): Int = x match {
        case 1 => 1
        case 2 => 2
        case 5 => 5
      }

      override def isDefinedAt(x: Int): Boolean = x == 1 || x == 2 || x == 5
    }

    // dumb chatbot
    val chatbot: PartialFunction[String, String] = {
      case "hello" => "Hii, my name is CHATBOT"
      case "bye" => "once you start talking to me, there is no returning"
      case "call mom" => "unable to perform the action, without your phone"
    }
    scala.io.Source.stdin.getLines().foreach(line => {
      if (chatbot.isDefinedAt(line)) {
        println(chatbot(line))
      } else {
        println("I didn't get that")
      }
    })
  }
}
