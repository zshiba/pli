public class ConsCell implements SExpression{

  private SExpression car;
  private SExpression cdr;

  public ConsCell(SExpression car, SExpression cdr){
    this.car = car;
    this.cdr = cdr;
  }

  @Override
  public String toString(){
    String s = "( ";
    if(this.car != null)
      s += this.car.toString();
    else
      s += "NIL";
    s += " . ";
    if(this.cdr != null)
      s += this.cdr.toString();
    else
      s += "NIL";
    s += " )";
    return s;
  }

}
