public class Evaluator{

  public static class EvaluationErrorException extends Exception{

    public EvaluationErrorException(String message){
      super("eval error: " + message);
    }

  }

  public SExpression evaluate(SExpression expression, Environment environment) throws EvaluationErrorException{
    if(expression instanceof Atom){
      Atom key = (Atom)expression;
      SExpression e = environment.find(key);
      if(e != null)
        return e;
      else
        throw new EvaluationErrorException("Undefined: " + expression.toFullString());

    }else if(expression instanceof Cell){
      Cell cell = (Cell)expression;
      if(cell.isEmpty()){
        return Atom.NIL;

      }else{
        SExpression car = cell.car();
        SExpression cdr = cell.cdr();

        if(car.equals(Atom.QUOTE)){
          return this.quote(cdr);
        }else{
          return null;
        }
      }
    }else{
      throw new EvaluationErrorException("Not implemented type: " + expression.getClass().getSimpleName());
    }
  }

  //special form: (quote s-expression)
  private SExpression quote(SExpression expression) throws EvaluationErrorException{
    if(expression instanceof Cell){
      Cell cell = (Cell)expression;
      if(cell.cdr() == Atom.NIL)
        return cell.car();
      else
        throw new EvaluationErrorException("Invalid expression: " + expression.toFullString());
    }else{
      throw new EvaluationErrorException("Invalid expression: " + expression.toFullString());
    }
  }

}
