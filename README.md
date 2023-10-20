## Homework Questions
- Question 1: `src/main/scala/Parser.scala`
- Question 2: `src/main/scala/CoordBTree.scala`
- Entry Point: `src/main/scala/Main.scala`

## Example sbt project that compiles using Scala 3

[![Continuous Integration](https://github.com/scala/scala3-example-project/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/scala/scala3-example-project/actions/workflows/ci.yml)

## Usage

This is a normal sbt project. You can start the sbt shell using `sbt` then compile code with `compile`, run the main
method with `run` and start a REPL using `console`.

If compiling this example project fails, you probably have a global sbt plugin
that does not work with Scala 3. You might try disabling plugins in
`~/.sbt/1.0/plugins` and `~/.sbt/1.0`.

### build.sbt

Set the Scala 3 version:

```scala
scalaVersion := "3.3.1"
```

### Getting your project to compile with Scala 3

For help with porting an existing Scala 2 project to Scala 3, see the
[Scala 3 migration guide](https://docs.scala-lang.org/scala3/guides/migration/compatibility-intro.html).

## Need help?

https://www.scala-lang.org/community/ has links.
