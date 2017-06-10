Minimal template to play with the scala.meta semantic API
============================================================

To use this project
```
$ git clone https://github.com/scalameta/sbt-semantic-example
$ cd sbt-semantic-example
$ sbt app/run
```

That should print out something like this
```
library/src/main/scala/Test.scala
---------------------------------
Dialect:
Scala211

Names:
[7..11): Test => _empty_.Test.
[20..29): printList => _empty_.Test.printList()V.
[33..37): Unit => _root_.scala.Unit#
[46..53): println => _root_.scala.Predef.println(Ljava/lang/Object;)V.
[54..58): List => _root_.scala.collection.immutable.List.

Denotations:
_empty_.Test. => final object Test
_empty_.Test.printList()V. => def printList: ()Unit

Sugars:
[58..58) [Int]

_root_.scala.Predef.println(Ljava/lang/Object;)V.
```

Edit Test.scala and run again.

For more documentation about the Scalameta Semantic API, see
http://scalameta.org/tutorial/#SemanticAPI

### Project Overview

This repository provides a minimal template to play with the new approach to
semantic APIs proposed by Scalameta. Prominent parts of the project include:

* [`plugins.sbt`](/project/plugins.sbt) that enables the `scalahost` compiler plugin
  responsible for generating and persisting semantic databases.
* [`build.sbt`](/build.sbt) that shows how to use `scalahost` as a library to create a `Mirror`
  from a previously persisted semantic database and run semantic operations on the corresponding Scala code.
* [`library/src/main/scala/Test.scala`](/library/src/main/scala/Test.scala)
  an example file that we want to analyze with the Semantic API.
* [`app/src/main/scala/Main.scala`](/app/src/main/scala/Main.scala)
  that demonstrates how to load a `Mirror`, which is an implementation of the
  Scalameta Semantic API.


