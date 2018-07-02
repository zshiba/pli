public class ConsCell implements SExpression{

  private SExpression car;
  private SExpression cdr;

  public ConsCell(SExpression car){
    this(car, Atom.NIL);
  }

  public ConsCell(SExpression car, SExpression cdr){
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
