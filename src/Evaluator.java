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
        }else if(car.equals(Atom.COND)){
          return this.cond(cdr, environment);
        }else if(car.equals(Atom.EQ)){
          return this.eq(cdr, environment);
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

  //special form: (cond ((predicate::s-expression then-body::s-expression)...))
  private SExpression cond(SExpression expression, Environment environment) throws EvaluationErrorException{
    if(expression instanceof Cell){
      Cell cell = (Cell)((Cell)expression).car(); //ToDo need check for safe cast
      while(true){
        SExpression clause = cell.car();
        if(clause instanceof Cell){
          SExpression predicate = ((Cell)clause).car();
          SExpression body = ((Cell)((Cell)clause).cdr()).car(); //ToDo need check for safe cast
          SExpression result = this.evaluate(predicate, environment);
          if(result == Atom.TRUE){
            return this.evaluate(body, environment);
          }else{
            if(cell.cdr() instanceof Cell)
              cell = (Cell)cell.cdr();
            else
              throw new EvaluationErrorException("Invalid expression: " + cell.cdr().toFullString());
          }
        }else{
          throw new EvaluationErrorException("Invalid expression: " + clause.toFullString());
        }
      }
    }else{
      throw new EvaluationErrorException("Invalid expression: " + expression.toFullString());
    }
  }

  //primitive: (eq symbol1::atom symbol2::atom)
  private SExpression eq(SExpression expression, Environment environment) throws EvaluationErrorException{
    if(expression instanceof Cell){
      Cell cell = (Cell)expression;
      SExpression expression1 = cell.car();
      SExpression right = cell.cdr();
      if(right instanceof Cell){
        if(((Cell)right).cdr() == Atom.NIL){
          SExpression expression2 = ((Cell)right).car();
          if(expression1 instanceof Atom){
            if(environment.find((Atom)expression1) != null){
              if(expression2 instanceof Atom){
                if(environment.find((Atom)expression2) != null){
                  if(expression1.equals(expression2))
                    return Atom.TRUE;
                  else
                    return Atom.NIL;
                }else{
                  throw new EvaluationErrorException("Invalid expression: " + expression2.toFullString());
                }
              }else{
                throw new EvaluationErrorException("Invalid expression: " + expression2.toFullString());
              }
            }else{
              throw new EvaluationErrorException("Invalid expression: " + expression1.toFullString());
            }
          }else{
            throw new EvaluationErrorException("Invalid expression: " + expression1.toFullString());
          }
        }else{
          throw new EvaluationErrorException("Invalid expression: " + ((Cell)right).cdr().toFullString());
        }
      }else{
        throw new EvaluationErrorException("Invalid expression: " + right.toFullString());
      }
    }else{
      throw new EvaluationErrorException("Invalid expression: " + expression.toFullString());
    }
  }

}
