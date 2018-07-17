public class Procedure implements SExpression{

  private SExpression parameters;
  private SExpression body;
  private Environment environment;

  public Procedure(SExpression parameters, SExpression body, Environment environment){
    this.parameters = parameters;
    this.body = body;
    this.environment = environment;
  }

  public SExpression getParameters(){
    return this.parameters;
  }

  public SExpression getBody(){
    return this.body;
  }

  public Environment getEnvironment(){
    return this.environment;
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
