public class Cell implements SExpression{

  private SExpression car;
  private SExpression cdr;

  public Cell(){
    this(null, null);
  }

  public Cell(SExpression car){
    this(car, Atom.NIL);
  }

  public Cell(SExpression car, SExpression cdr){
    this.car = car;
    this.cdr = cdr;
  }

  public void cdr(SExpression expression){
    this.cdr = expression;
  }

  public SExpression cdr(){
    return this.cdr;
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
