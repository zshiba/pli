public class Lexer{

  private String source;
  private int position;
  private Token nextToken;

  public Lexer(String source){
    this.source = source;
    this.position = 0;
    this.nextToken = this.readToken();
  }

  public Token consumeNextToken(){
    Token token = this.nextToken;
    this.nextToken = this.readToken();
    return token;
  }

  public Token peekNextToken(){
    return this.nextToken;
  }

  public boolean hasToken(){
    if(this.nextToken != null)
      return true;
    else
      return false;
  }

  private Token readToken(){
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
    while(this.position < this.source.length()){
      char c = this.source.charAt(this.position);
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

  //for test
  public static void main(String[] args){
    String source;
    if(args.length > 0)
      source = args[0];
    else
      source = "";

    Lexer lexer = new Lexer(source);

    while(lexer.hasToken()){
      Token token = lexer.consumeNextToken();
      System.out.println(token.toString());
    }
  }

}
