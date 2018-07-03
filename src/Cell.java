public class Cell implements SExpression{

  private SExpression car;
  private SExpression cdr;

  public Cell(SExpression car){
    this(car, Atom.NIL);
  }

  public Cell(SExpression car, SExpression cdr){
    this.car = car;
    this.cdr = cdr;
  }

  @Override
  public String toString(){
    String s = "( ";
    s += this.car.toString();
    s += " . ";
    s += this.cdr.toString();
    s += " )";
    return s;
  }

}
