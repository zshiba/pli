public class Evaluator{

  public static class EvaluationErrorException extends Exception{

    public EvaluationErrorException(String message){
      super("eval error: " + message);
    }

  }

  public SExpression evaluate(SExpression expression, Environment environment) throws EvaluationErrorException{
    if(expression instanceof Atom){
      SExpression e = environment.find((Atom)expression);
      if(e == null)
        throw new EvaluationErrorException("null");
      return e;
    }else{
      return null; //ToDo
    }
  }

}
