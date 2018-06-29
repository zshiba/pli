public class Token{

  public static enum Type{
    OPEN_PARENTHESIS,
    CLOSE_PARENTHESIS,
    DOT,
    SYMBOL
  }

  private Type type;
  private String literal;

  public Token(Type type, String literal){
    this.type = type;
    this.literal = literal;
  }

  @Override
  public String toString(){
    return "{type: " + this.type.name() + ", literal: \"" + this.literal + "\"}";
  }

}
