package commons

sealed trait Result[+Err, +A] extends Product
case class Success[A](value: A) extends Result[Nothing, A]
case class Failure[Err](err: Err) extends Result[Err, Nothing]

object Result {

  implicit class OnReceiver[A](value: A) {
    def asSuccess: Success[A] = Success(value)
  }

  implicit class ResultOps[Err, A](result: Result[Err, A]) {

    def printHello(): Unit = println("Hello")

    def map[B](f: A => B): Result[Err, B] =
      result match {
        case Success(value) => Success(f(value))
        case Failure(err)   => Failure(err)
      }

    def fold[T](onSuccess: A => T, onFailure: Err => T): T =
      result match {
        case Success(value) => onSuccess(value)
        case Failure(err)   => onFailure(err)
      }

  }

}
