public class Lexer{

  private String source;

  public Lexer(){
    this("");
  }

  public Lexer(String source){
    this.source = source;
  }

  public Token getNextToken(){
    return null;
  }

  public static void main(String[] args){
    String source;
    if(args.length > 0)
      source = args[0];
    else
      source = "";

    Lexer lexer = new Lexer(source);
    Token token = lexer.getNextToken();
    while(token != null){
      System.out.println(token.toString());
      token = lexer.getNextToken();
    }
  }

}
