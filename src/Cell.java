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

  public boolean isEmpty(){
    if(this.car == null && this.cdr == null)
      return true;
    else
      return false;
  }

  @Override
  public boolean equals(Object object){
    if(object instanceof Cell){
      Cell other = (Cell)object;
      if(this.isEmpty() && other.isEmpty()){
        return true;
      }else{
        if((this.car.equals(other.car())) && (this.cdr.equals(other.cdr())))
          return true;
        else
          return false;
      }
    }else if(object instanceof Atom){
      if(this.isEmpty() && object.equals(Atom.NIL))
        return true;
      else
        return false;
    }else{
      return false;
    }
  }

  @Override
  public String toString(){
    String s;
    if(this.isEmpty()){
      s = "()";
    }else{
      if(this.cdr == Atom.NIL){
        s = this.car.toString();
      }else{
        s = "(";
        s += this.car.toString();
        s += " ";
        s += this.cdr.toString();
        s += ")";
      }
    }
    return s;
  }

  @Override
  public String toFullString(){
    String s;
    if(this.isEmpty()){
      s = "()";
    }else{
      s = "(";
      s += this.car.toFullString();
      s += " . ";
      s += this.cdr.toFullString();
      s += ")";
    }
    return s;
  }

}
