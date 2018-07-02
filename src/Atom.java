public class Atom implements SExpression{

  public static final Atom NIL = new Atom("NIL");

  private String symbol;

  public Atom(String symbol){
    this.symbol = symbol;
  }

  @Override
  public String toString(){
    return this.symbol;
  }

}
