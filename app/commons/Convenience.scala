package commons

object Convenience {

  implicit class ReceiverOps[A](receiver: A) {
    def run(f: A => Unit): A = {
      f(receiver)
      receiver
    }
  }

  implicit class ApplyOps[A](target: A) {
    def let[B](f: A => B): B = f(target)
  }

}
