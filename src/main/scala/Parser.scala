object Parser {
  import scala.util.parsing.combinator._

  object SimpleParser extends RegexParsers {

    // Define the data structures for the language
    case class Program(id: String, statements: List[Statement])
    sealed trait Statement
    case class Assignment(id: String, expr: Expression) extends Statement
    case class IfElse(comp: Comparison, ifStmt: Statement, elseStmt: Statement) extends Statement
    case class WhileDo(comp: Comparison, stmt: Statement) extends Statement
    case class Read(id: String) extends Statement
    case class Write(expr: Expression) extends Statement

    sealed trait Expression
    case class BinaryOp(left: Expression, op: String, right: Expression) extends Expression
    case class Integer(value: Int) extends Expression
    case class Identifier(name: String) extends Expression

    sealed trait Comparison
    case class ComparisonOp(left: Expression, op: String, right: Expression) extends Comparison

    // Define the parsers for the language
    def program: Parser[Program] = "program" ~> ident ~ ";" ~ rep1(statement) <~ "end" ^^ {
      case id ~ _ ~ stmts => Program(id.name, stmts)
    }

    def statement: Parser[Statement] = assignment | ifElse | whileDo | read | write

    def assignment: Parser[Assignment] = ident ~ ":=" ~ expr ^^ {
      case id ~ _ ~ e => Assignment(id.name, e)
    }

    def ifElse: Parser[IfElse] = "if" ~> comp ~ "then" ~ statement ~ "else" ~ statement ^^ {
      case c ~ _ ~ ifS ~ _ ~ elseS => IfElse(c, ifS, elseS)
    }

    def whileDo: Parser[WhileDo] = "while" ~> comp ~ "do" ~ statement ^^ {
      case c ~ _ ~ stmt => WhileDo(c, stmt)
    }

    def read: Parser[Read] = "read" ~> ident ^^ (id => Read(id.name))

    def write: Parser[Write] = "write" ~> expr ^^ (e => Write(e))

    def expr: Parser[Expression] = term | (term ~ expressionOperator ~ term) ^^ {
      case left ~ op ~ right => BinaryOp(left, op, right) // Cast to Expression
    }

    def term: Parser[Expression] =  fact | fact ~ termOperator ~ fact ^^ {
      case left ~ op ~ right => BinaryOp(left, op, right) // Cast to Expression
    }

    def fact: Parser[Expression] = integer | ident

    def integer: Parser[Integer] = """\d+""".r ^^ (s => Integer(s.toInt))

    def ident: Parser[Identifier] = """[a-zA-Z_]\w*""".r ^^ (id => Identifier(id))

    def comparisonOperator: Parser[String] = "==" | "!=" | "<=" | ">=" | ">" | "<"

    def expressionOperator: Parser[String] = "+" | "-"

    def termOperator: Parser[String] = "*" | "/"

    def comp: Parser[Comparison] = expr ~ comparisonOperator ~ expr ^^ {
      case left ~ op ~ right => ComparisonOp(left, op, right)
    }

    // Define a function to parse input strings
    def parseInput(input: String): ParseResult[Program] = parseAll(program, input)
  }

  object Run {
    def main(args: Array[String]): Unit = {
      val input =
        """
          |program MyProgram;
          |  y := 5
          |  x := 5
          |  if y >= x then
          |    write y
          |  else
          |    write x
          |end
      """.stripMargin

      val result = SimpleParser.parseInput(input)
      result match {
        case SimpleParser.Success(program, _) =>
          println(program)
        case SimpleParser.NoSuccess(msg, next) =>
          println(s"Parser error: $msg at line ${next.pos.line} column ${next.pos.column}")
      }
    }
  }

}
