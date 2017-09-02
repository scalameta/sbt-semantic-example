Minimal template to play with the scala.meta semantic API
============================================================

To run this project, execute

```
git clone https://github.com/scalameta/sbt-semantic-example`
cd sbt-semantic-example
sbt analyzer/run
```

That should print out something like this
```
analyzeme/src/main/scala/Test.scala
-----------------------------------
Language:
Scala212

Names:
[7..11): Test <= _empty_.Test.
[20..29): printList <= _empty_.Test.printList()V.
[44..45): x <= analyzeme/src/main/scala/Test.scala@40..72
[48..52): List => _root_.scala.collection.immutable.List.
[62..65): map => _root_.scala.collection.immutable.List#map(Lscala/Function1;Lscala/collection/generic/CanBuildFrom;)Ljava/lang/Object;.
[68..69): + => _root_.scala.Int#`+`(I)I.
[77..84): println => _root_.scala.Predef.println(Ljava/lang/Object;)V.
[85..86): x => analyzeme/src/main/scala/Test.scala@40..72

Symbols:
_empty_.Test. => final object Test
_empty_.Test.printList()V. => def printList: ()Unit
_root_.scala.Int#`+`(I)I. => abstract def +: (x: Int)Int
_root_.scala.Predef.println(Ljava/lang/Object;)V. => def println: (x: Any)Unit
_root_.scala.collection.immutable.List#map(Lscala/Function1;Lscala/collection/generic/CanBuildFrom;)Ljava/lang/Object;. => final def map: [B, That](f: A => B)(implicit bf: scala.collection.generic.CanBuildFrom[List[A],B,That])That
_root_.scala.collection.immutable.List. => final object List
analyzeme/src/main/scala/Test.scala@40..72 => val x: List[Int]

Sugars:
[52..52): *.apply[Int]
  [0..1): * => _star_.
  [2..7): apply => _root_.scala.collection.immutable.List.apply(Lscala/collection/Seq;)Lscala/collection/immutable/List;.
[65..65): *[Int, List[Int]]
  [0..1): * => _star_.
[72..72): *(scala.collection.immutable.List.canBuildFrom[Int])
  [0..1): * => _star_.
  [34..46): canBuildFrom => _root_.scala.collection.immutable.List.canBuildFrom()Lscala/collection/generic/CanBuildFrom;.
```

Edit Test.scala and run again.

For more documentation about the Scalameta Semantic API, see
http://scalameta.org/tutorial/#SemanticAPI

### Project Overview

This repository provides a minimal template to play with the new approach to
semantic APIs proposed by Scalameta. Prominent parts of the project include:

* [`plugins.sbt`](/project/plugins.sbt) that enables the `sbt-buildinfo` plugin
  responsible for passing a --sourcepath and --classpath to the analyzer project.
* [`build.sbt`](/build.sbt) that shows how to install `semanticdb-scalac` and how to
  pass the --sourcepath and --classpath to `analyzer/run`.
* [`analyzeme/src/main/scala/Test.scala`](/analyzeme/src/main/scala/Test.scala)
  an example file that we want to analyze with the Semantic API.
* [`analyzer/src/main/scala/Main.scala`](/analyzer/src/main/scala/Main.scala)
  that demonstrates how to load a `scala.meta.Database`.


