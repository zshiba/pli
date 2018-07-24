import java.util.ArrayList;

public class Parser{

  public static class ParseErrorException extends Exception{

    public static enum Type{
      NORMAL,
      CLOSE_PARENTHESIS,
      DOT
    }

    private Type type;

    public ParseErrorException(String message){
      this(message, Type.NORMAL);
    }

    public ParseErrorException(String message, Type type){
      super("parse error: " + message);
      this.type = type;
    }

    public Type getType(){
      return this.type;
    }

  }

  private Lexer lexer;

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
          if(e.getType() == ParseErrorException.Type.CLOSE_PARENTHESIS)
            return new List(); //empty list
          else
            throw e;
        }
        List root = new List(car);
        List leaf = root;
        while(this.lexer.hasToken()){
          SExpression cdr;
          try{
            cdr = this.build();
          }catch(ParseErrorException e){
            if(e.getType() == ParseErrorException.Type.CLOSE_PARENTHESIS){
              return root;
            }else if(e.getType() == ParseErrorException.Type.DOT){
              cdr = this.build();
              leaf.cdr(cdr);
              if(this.lexer.hasToken() && this.lexer.consumeNextToken().getType() == Token.Type.CLOSE_PARENTHESIS)
                return root;
              else
                break;
            }else{
              throw e;
            }
          }
          leaf.cdr(new List(cdr));
          leaf = (List)leaf.cdr(); //ad-hoc (safe cast only within this context)
        }
        throw new ParseErrorException("(missing ')')");
      }else if(type == Token.Type.CLOSE_PARENTHESIS){
        throw new ParseErrorException("(unexpected ')')", ParseErrorException.Type.CLOSE_PARENTHESIS);
      }else if(type == Token.Type.SYMBOL){
        return new Atom(token.getLiteral());
      }else{
        throw new ParseErrorException("(unexpected '.')", ParseErrorException.Type.DOT);
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
        System.out.println(expression.toFullString());
    }catch(ParseErrorException e){
      System.err.println(e.getMessage());
    }
  }

}
