package myapp

import scala.meta._

object Main {
  def main(args: Array[String]): Unit = {
    val database = Database.load(Classpath(BuildInfo.classpath), Sourcepath(BuildInfo.sourcepath))
    println(database)
    println(s"Size: ${database.documents.length}")
  }
}
