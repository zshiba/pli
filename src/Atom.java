public class Atom implements SExpression{

  public static final Atom NIL  = new Atom("nil");
  public static final Atom TRUE = new Atom("t");

  private String symbol;

  public Atom(String symbol){
    this.symbol = symbol;
  }

  @Override
  public boolean equals(Object object){
    if(object instanceof Atom)
      return this.toString().equals(object.toString());
    else
      return false;
  }

  @Override
  public String toString(){
    return this.symbol;
  }

}
