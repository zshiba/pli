import java.util.ArrayList;

public class Parser{

  private Lexer lexer;

  public Parser(){
    this(new Lexer());
  }

  public Parser(Lexer lexer){
    this.lexer = lexer;
  }

  private SExpression build(){
    return null; //ToDo
  }

  public ArrayList<SExpression> parse(){
    ArrayList<SExpression> expressions = new ArrayList<>();
    while(this.lexer.hasToken()){
      SExpression e = this.build();
      if(e != null)
        expressions.add(e);
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
    ArrayList<SExpression> expressions = parser.parse();
    for(SExpression e : expressions)
      System.out.println(e.toString());
  }

}
