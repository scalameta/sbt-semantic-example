Minimal template to play with the scala.meta semantic API
============================================================

Since 1.6.0, scala.meta supports a minimalistic semantic API.
Our general idea for this module is to start small, and then progressively support
more and more functionality until we've covered most, if not all, use cases of scala.reflect.

### Mirrors

The semantic API is based on the concept of mirrors. In scala.meta parlance, a mirror
is an entity that encapsulates a compilation context, providing a classpath and a sourcepath
to perform semantic operations such as name resolution, type inference and others.
Most semantic APIs take an implicit parameter of type [`Mirror`](https://github.com/scalameta/scalameta/blob/master/scalameta/semantic/src/main/scala/scala/meta/semantic/v1/Mirror.scala).

Unlike the syntactic API, which is implemented completely in house, scala.meta delegates
implementations of `Mirror` to external projects. In Scala, performing even the simplest semantic operations
requires a full-blown typechecker, so implementing even a simple `Mirror` in house would require us
to reinvent a Scala typechecker, which is a multi-man-year effort. Currently, we provide a `Mirror` implementation
that is backed by the Scala compiler, and there are plans to implement mirrors for Dotty and IntelliJ.

### Low-level API: semantic databases

Underlying our implementations of `Mirror` is the notion of [semantic databases](https://github.com/scalameta/scalameta/issues/605).
A semantic database is a storage for platform-independent semantic information about Scala code.
For instance, here's how a semantic database looks like for a simple Scala program:

```scala
object Test {
  def printList(): Unit = {
    println(List(1, 2, 3))
  }
}
```

```
file:/Users/xeno_by/Projects/sbt-semantic-example/library/src/main/scala/Test.scala
[7..11): Test => _empty_.Test.
[20..29): printList => _empty_.Test.printList()V.
[33..37): Unit => _root_.scala.Unit#
[46..53): println => _root_.scala.Predef.println(Ljava/lang/Object;)V.
[54..58): List => _root_.scala.collection.immutable.List.apply(Lscala/collection/Seq;)Lscala/collection/immutable/List;.
```

One of the properties of semantic databases is their **portability**.
Unlike typical representations of semantic information in Scala, semantic databases are not tied
to a particular implementation of the Scala typechecker.
This makes it possible for metaprograms written against the scala.meta semantic API to run on multiple platforms.

Another important property is **persistence**. Since semantic databases are portable,
they can be created and consumed in separate environments. This is a key insight
that we promote in scala.meta, and we are confident that it will revolutionize
the developer tool ecosystem in Scala.

Currently, a typical approach to semantic developer tools in Scala is implementing
them as compiler plugins and then using them inside builds. Apart from being a hassle to configure,
this approach is also quite slow, because it needs to run a Scala typechecker
every time when a tool is invoked.

Scala.meta enables a much more convenient workflow. First, we use our `scalahost` compiler plugin
to typecheck a given codebase and generate a semantic database for it. This is done only once
per unique snapshot of the codebase. Afterwards, using the persisted semantic database, we can launch
any number of developer tools any number of times without having to typecheck the codebase again.

**Among the pioneers in this area are code analysis tools developed at Twitter**.
At ScalaDays 2017, [@xeno-by](http://github.com/xeno-by) and [@stuhood](http://github.com/stuhood)
will present their vision and hands-on experience with running this workflow
on millions of lines of Scala code in Twitter's codebase.

**Important research in semantic databases is also done at Scala Center**.
The [scalafix](https://github.com/scalacenter/scalafix) project developed by [@olafurpg](http://github.com/olafurpg)
is currently in the middle of transitioning to the new workflow.
Scalafix has already been able to perform [non-trivial semantic rewrites](https://github.com/scalacenter/scalafix/blob/f61136fad79afcdbb03528ce78c7928afc6eafd6/core/src/main/scala/scalafix/rewrite/Xor2Either.scala) using scala.meta semantic API,
and we are anxiously awaiting for further practical results!

### High-level API: Ref.symbol and friends

On top of the low-level, flat concept of semantic databases, we have implemented high-level, tree-based semantic APIs.
At the moment, we only support a single API called `Ref.symbol` that resolves references and returns symbols, i.e.
unique identifiers of definitions. In the future, we're planning to expand the semantic API
to [include more operations on symbols](https://github.com/scalameta/scalameta/issues/609) and
to [introduce operations on types](https://github.com/scalameta/scalameta/issues/612).

### Demonstration

The associated sbt project in this repository provides a minimal template to play with
the new approach to semantic APIs proposed by scala.meta. Prominent parts of the project include:
  * [`plugins.sbt`](/project/plugins.sbt) that enables the `scalahost` compiler plugin
    responsible for generating and persisting semantic databases.
  * [`build.sbt`](/build.sbt) that shows how to use `scalahost` as a library to create a `Mirror`
    from a previously persisted semantic database and run semantic operations on the corresponding Scala code.
  * [`app/src/main/scala/Main.scala`](/app/src/main/scala/Main.scala)
    that demonstrates usage of both low-level and high-level semantic APIs.


### Call for contributors

The scala.meta semantic API is very young, so we need your help to get it mature as fast as possible.

The main area for contributions is ensuring that we can generate semantic databases
for even the trickiest snippets of Scala code. We would greatly appreciate if you could add
the `sbt-scalahost` plugin to your sbt build, generate a semantic database of your project
and report back at [https://gitter.im/scalameta/scalameta](https://gitter.im/scalameta/scalameta).
