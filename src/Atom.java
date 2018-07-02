public class Atom implements SExpression{

  private String symbol;

  public Atom(String symbol){
    this.symbol = symbol;
  }

  @Override
  public String toString(){
    return this.symbol;
  }

}
