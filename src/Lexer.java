public class Lexer{

  private String source;
  private int position;

  public Lexer(){
    this("");
  }

  public Lexer(String source){
    this.source = source;
    this.position = 0;
  }

  public Token getNextToken(){
    this.skip();
    Token token = null;
    if(this.position < this.source.length()){
      char character = this.source.charAt(this.position);
      switch(character){
        case '(':
          token = new Token(Token.Type.OPEN_PARENTHESIS, "(");
          ++this.position;
          break;
        case ')':
          token = new Token(Token.Type.CLOSE_PARENTHESIS, ")");
          ++this.position;
          break;
        case '.':
          token = new Token(Token.Type.DOT, ".");
          ++this.position;
          break;
        default:
          String symbol = this.readSymbol();
          token = new Token(Token.Type.SYMBOL, symbol);
          break;
      }
      return token;
    }else{
      return token;
    }
  }

  private void skip(){
    while(this.position < this.source.length()){
      char c = this.source.charAt(this.position);
      if(c == ' ' || c == '\t' || c == '\n')
        ++this.position;
      else
        break;
    }
  }

  private String readSymbol(){
    StringBuilder builder = new StringBuilder();
    for(int peek = this.position; peek < this.source.length(); peek++){
      char c = this.source.charAt(peek);
      if(c == ' ' || c == '\t' || c == '\n' ||
         c == '(' || c == ')' || c == '.'){
        break;
      }else{
        builder.append(c);
        ++this.position;
      }
    }
    return builder.toString();
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
