object AdvacedPatternMatching {
  def main(args: Array[String]): Unit = {
    val numbers = List(1)
    val description: Unit = numbers match {
      case head :: Nil => println(s"the only element is $head")
      case _ => println("any other case")
    }
    /*
    * constants
    * wildcards
    * case classes
    * tuples
    * something special, like above
    * */

    class Person(val name: String, val age: Int)
    object Person {
      def unapply(person: Person): Option[(String, Int)] = {
        if (person.age < 21) None
        else Some((person.name, person.age))
      }

      def unapply(age: Int): Option[String] = Some(if age < 25 then "minor" else "major")
    }
    val bob = new Person("Bob", 24)

    val greeting = bob match {
      case Person(n, a) => s"Hi, my name is $n and I am $a yo."
    }
    println(greeting)

    val agePattern = bob.age match {
      case Person(status) => s"my legal status is $status"
    }
    println(agePattern)

    //    val n : Int = 18;
    //    val matchProperty = n match {
    //      case x if x < 10 => "Single Digit"
    //      case x if x % 2 ==0 => "Even Number"
    //      case _ => "No property"
    //    }

    object singleDigit{
      def unapply(arg : Int ): Option[Boolean] = {
        if(arg > -10 && arg < 10)Some(true)
        else None
      }
    }
    object even{
      def unapply(arg : Int) : Option[Boolean] ={
        if(arg %2==0 && arg < 20) Some(true)
        else None
      }
    }
    val n = 58
    val matchProperty = n match {
      case singleDigit(f) => s"single digit, $f"
      case even(n) => s"this is even $n"
      case _ => s"No property"
    }

    println(matchProperty)
  }
}
