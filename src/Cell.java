public class Cell implements SExpression{

  private SExpression car;
  private SExpression cdr;

  public Cell(){
    this(null, null); //to represent empty list
  }

  public Cell(SExpression car){
    this(car, Atom.NIL);
  }

  public Cell(SExpression car, SExpression cdr){
    this.car = car;
    this.cdr = cdr;
  }

  public SExpression car(){
    return this.car;
  }

  public SExpression cdr(){
    return this.cdr;
  }

  public void cdr(SExpression expression){
    this.cdr = expression;
  }

  @Override
  public String toString(){
    String s = "(";
    if(this.car != null){
      s += this.car.toString();
      s += " . ";
      s += this.cdr.toString();
    }
    s += ")";
    return s;
  }

}
