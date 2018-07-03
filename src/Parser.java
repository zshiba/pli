import java.util.ArrayList;

public class Parser{

  private static class ParseErrorException extends Exception{

    boolean isOfCloseParenthesis;

    ParseErrorException(String message){
      this(message, false);
    }

    ParseErrorException(String message, boolean isOfCloseParenthesis){
      super("parse error: " + message);
      this.isOfCloseParenthesis = isOfCloseParenthesis;
    }

    boolean isOfCloseParenthesis(){
      return this.isOfCloseParenthesis;
    }

  }

  private Lexer lexer;

  public Parser(){
    this(new Lexer());
  }

  public Parser(Lexer lexer){
    this.lexer = lexer;
  }

  private SExpression build() throws ParseErrorException{
    if(this.lexer.hasToken()){
      Token token = this.lexer.consumeNextToken();
      Token.Type type = token.getType();
      if(type == Token.Type.OPEN_PARENTHESIS){
        SExpression car;
        try{
          car = this.build();
        }catch(ParseErrorException e){
          if(e.isOfCloseParenthesis()) //ad-hoc
            return new Cell(); //empty list
          else
            throw e;
        }
        Cell root = new Cell(car);
        Cell leaf = root;
        while(this.lexer.hasToken()){
          SExpression cdr;
          try{
            cdr = this.build();
          }catch(ParseErrorException e){ //ad-hoc
            if(e.isOfCloseParenthesis())
              return root;
            else
              throw e;
          }
          leaf.cdr(new Cell(cdr));
          leaf = (Cell) leaf.cdr();
        }
        throw new ParseErrorException("(missing ')')");
      }else if(type == Token.Type.CLOSE_PARENTHESIS){
        throw new ParseErrorException("(unexpected ')')", true);
      }else if(type == Token.Type.SYMBOL){
        return new Atom(token.getLiteral());
      }else{
        //ToDo (Token.Type.DOT)
        throw new ParseErrorException("(unknown token)");
      }
    }else{
      throw new ParseErrorException("(no token left)");
    }
  }

  public ArrayList<SExpression> parse() throws ParseErrorException{
    ArrayList<SExpression> expressions = new ArrayList<>();
    while(this.lexer.hasToken()){
      SExpression expression = this.build();
      if(expression != null)
        expressions.add(expression);
    }
    return expressions;
  }

  //for test
  public static void main(String[] args){
    String source;
    if(args.length > 0)
      source = args[0];
    else
      source = "";

    Lexer lexer = new Lexer(source);
    Parser parser = new Parser(lexer);
    try{
      ArrayList<SExpression> expressions = parser.parse();
      for(SExpression expression : expressions)
        System.out.println(expression.toString());
    }catch(ParseErrorException e){
e.printStackTrace();
      System.err.println(e.getMessage());
    }
  }

}
