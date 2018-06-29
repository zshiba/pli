public class Token{

  public enum Type{
    OPEN_PARENTHESIS,
    CLOSE_PARENTHESIS,
    DOT,
    SYMBOL
  }

  private Type type;
  private String literal;
}
