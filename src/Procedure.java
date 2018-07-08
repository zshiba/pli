public class Procedure implements SExpression{

  private SExpression arguments;
  private SExpression body;
  private Environment environment;

  public Procedure(SExpression arguments, SExpression body, Environment environment){
    this.arguments = arguments;
    this.body = body;
    this.environment = environment;
  }

  @Override
  public boolean equals(Object object){
    return false; //ToDo
  }

  @Override
  public String toString(){
    return "(procedure)";
  }

  @Override
  public String toFullString(){
    return this.toString();
  }

}
