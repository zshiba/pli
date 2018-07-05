public class Environment{

  private Environment outer;

  public Environment(){
    this(null);
  }

  public Environment(Environment outer){
    this.outer = outer;
  }

}
