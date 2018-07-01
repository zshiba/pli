public class Parser{

  private Lexer lexer;

  public Parser(){
    this(new Lexer());
  }

  public Parser(Lexer lexer){
    this.lexer = lexer;
  }

  public static void main(String[] args){
    String source;
    if(args.length > 0)
      source = args[0];
    else
      source = "";

    Lexer lexer = new Lexer(source);
    Parser parser = new Parser(lexer);
  }
}
