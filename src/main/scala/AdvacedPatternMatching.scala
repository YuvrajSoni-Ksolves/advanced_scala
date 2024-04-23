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

    object singleDigit {
      def unapply(arg: Int): Option[Boolean] = {
        if (arg > -10 && arg < 10) Some(true)
        else None
      }
    }
    object even {
      def unapply(arg: Int): Option[Boolean] = {
        if (arg % 2 == 0 && arg < 20) Some(true)
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
    // infix patterns
    case class Or[A, B](a: A, b: B)
    val either = Or(2, "two")
    val humanDescription = either match {
      case number Or string => s"$number is written as $string"
    }
    println(humanDescription)

    //decomposing sequences
    abstract class MyList[+A] {
      def head: A = ???

      def tail: MyList[A] = ???
    }
    case object Empty extends MyList[Nothing]
    case class Cons[+A](override val head: A, override val tail: MyList[A]) extends MyList[A]
    object MyList {
      def unapplySeq[A](list: MyList[A]): Option[Seq[A]] =
        if (list == Empty) Some(Seq.empty)
        else unapplySeq(list.tail).map(list.head +: _)
    }
    val myList: MyList[Int] = Cons(1, Cons(2, Cons(3, Empty)))
    val decomposed = myList match {
      case MyList(1, 2, _*) => "starting with 1 and 2"
      case _ => "something else"
    }
    println(decomposed)

    //custom return type for unapply
    // isEmpty -> Boolean
    // get -> Something

    abstract class Wrapper[T] {
      def isEmpty: Boolean
      def get: T
    }
    object PersonWrapper {
      def unapply(person: Person): Wrapper[String] = new Wrapper[String] {
        def isEmpty: Boolean = false
        def get: String = person.name
      }
    }
    println(bob match
      case PersonWrapper(n) => s"This person's name is ${n}"
      case _ => "any other case "
    )
  }
}
