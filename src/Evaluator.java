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
        }else if(car.equals(Atom.DEFINE)){
          return this.define(cdr, environment);
        }else if(car.equals(Atom.LAMBDA)){
          return this.lambda(cdr, environment);
        }else{
          return null; //ToDo
        }
      }
    }else{
      throw new EvaluationErrorException("Not implemented type: " + expression.getClass().getSimpleName());
    }
  }

  //special form: (quote value::s-expression)
  private SExpression quote(SExpression expression) throws EvaluationErrorException{
    if(expression instanceof Cell){
      Cell cell = (Cell)expression;
      if(cell.cdr() == Atom.NIL)
        return cell.car();
      else
        throw new EvaluationErrorException("Invalid expression: " + cell.cdr().toFullString());
    }else{
      throw new EvaluationErrorException("Invalid expression: " + expression.toFullString());
    }
  }

  //special form: (define key::atom value::s-expression)
  private SExpression define(SExpression expression, Environment environment) throws EvaluationErrorException{
    if(expression instanceof Cell){
      Cell cell = (Cell)expression;
      SExpression car = cell.car();
      SExpression cdr = cell.cdr();
      if(car instanceof Atom){
        Atom key = (Atom)car;
        SExpression value = this.evaluate(((Cell)cdr).car(), environment);
        boolean isBound = environment.bind(key, value);
        if(isBound)
          return car;
        else
          throw new EvaluationErrorException("Binding failure: " + key.toFullString());
      }else{
        throw new EvaluationErrorException("Invalid expression: " + car.toFullString());
      }
    }else{
      throw new EvaluationErrorException("Invalid expression: " + expression.toFullString());
    }
  }

  //special form: (lambda (argument::atom...) body::s-expression)
  private SExpression lambda(SExpression expression, Environment environment) throws EvaluationErrorException{
    if(expression instanceof Cell){
      Cell cell = (Cell)expression;
      SExpression car = cell.car();
      SExpression cdr = cell.cdr();
      if(car instanceof Cell){
        Cell argument = (Cell)car;
        while(true){
          if((argument.car() instanceof Atom) && (argument.cdr() == Atom.NIL))
            break;
          else if(!(argument.car() instanceof Atom))
            throw new EvaluationErrorException("Invalid expression: " + argument.car().toFullString());
          else if(!(argument.cdr() instanceof Cell))
            throw new EvaluationErrorException("Invalid expression: " + argument.cdr().toFullString());
          else
            argument = (Cell)argument.cdr();
        }
        return new Procedure(car, cdr, environment);
      }else{
        throw new EvaluationErrorException("Invalid expression: " + car.toFullString());
      }
    }else{
      throw new EvaluationErrorException("Invalid expression: " + expression.toFullString());
    }
  }

}
