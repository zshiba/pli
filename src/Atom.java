public class Atom implements SExpression{

  //constant literal
  public static final Atom NIL    = new Atom("nil");
  public static final Atom TRUE   = new Atom("t");

  //literal for special form
  public static final Atom QUOTE  = new Atom("quote");
  public static final Atom DEFINE = new Atom("define");
  public static final Atom COND   = new Atom("cond");
  public static final Atom LAMBDA = new Atom("lambda");

  //literal for primitive function
  public static final Atom EQ     = new Atom("eq");
  public static final Atom ATOM   = new Atom("atom");
  public static final Atom CONS   = new Atom("cons");
  public static final Atom CAR    = new Atom("car");
  public static final Atom CDR    = new Atom("cdr");

  private String symbol;

  public Atom(String symbol){
    this.symbol = symbol;
  }

  @Override
  public boolean equals(Object object){
    if(object instanceof Atom){
      return this.toString().equals(object.toString());
    }else if(object instanceof Cell){
      if(this.equals(Atom.NIL) && ((Cell)object).isEmpty())
        return true;
      else
        return false;
    }else{
      return false;
    }
  }

  @Override
  public String toString(){
    return this.symbol;
  }

  @Override
  public String toFullString(){
    return this.toString();
  }

}
