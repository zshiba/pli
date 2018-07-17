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
        }else if(car.equals(Atom.ATOM)){
          return this.atom(cdr, environment);
        }else if(car.equals(Atom.CONS)){
          return this.cons(cdr, environment);
        }else if(car.equals(Atom.CAR)){
          return this.car(cdr, environment);
        }else if(car.equals(Atom.CDR)){
          return this.cdr(cdr, environment);
        }else{
          return this.apply(expression, environment);
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

  //primitive: (eq symbol1::s-expression symbol2::s-expression)
  private SExpression eq(SExpression expression, Environment environment) throws EvaluationErrorException{
    if(expression instanceof Cell){
      Cell cell = (Cell)expression;
      SExpression expression1 = cell.car();
      SExpression right = cell.cdr();
      if(right instanceof Cell){
        if(((Cell)right).cdr() == Atom.NIL){
          SExpression expression2 = ((Cell)right).car();
          expression1 = this.evaluate(expression1, environment);
          expression2 = this.evaluate(expression2, environment);
          if(expression1.equals(expression2))
            return Atom.TRUE;
          else
            return Atom.NIL;
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

  //primitive: (atom symbol::s-expression)
  private SExpression atom(SExpression expression, Environment environment) throws EvaluationErrorException{
    if(expression instanceof Cell){
      Cell cell = (Cell)expression;
      SExpression car = cell.car();
      SExpression cdr = cell.cdr();
      if(cdr == Atom.NIL){
        if(car instanceof Atom){
          if(environment.find((Atom)car) != null)
            return Atom.TRUE;
          else
            throw new EvaluationErrorException("Invalid expression: " + car.toFullString());
        }else{
          return Atom.NIL;
        }
      }else{
        throw new EvaluationErrorException("Invalid expression: " + cdr.toFullString());
      }
    }else{
      throw new EvaluationErrorException("Invalid expression: " + expression.toFullString());
    }
  }


  //primitive: (cons expression1::s-expression expression2::s-expression)
  private SExpression cons(SExpression expression, Environment environment) throws EvaluationErrorException{
    if(expression instanceof Cell){
      Cell cell = (Cell)expression;
      SExpression expression1 = cell.car();
      SExpression right = cell.cdr();
      if(right instanceof Cell){
        if(((Cell)right).cdr() == Atom.NIL){
          SExpression expression2 = ((Cell)right).car();
          return new Cell(this.evaluate(expression1, environment), this.evaluate(expression2, environment));
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

  //primitive: (car list::s-expression)
  private SExpression car(SExpression expression, Environment environment) throws EvaluationErrorException{
    if(expression instanceof Cell){
      Cell cell = (Cell)expression;
      SExpression car = cell.car();
      SExpression cdr = cell.cdr();
      if(cdr == Atom.NIL){
        SExpression e = this.evaluate(car, environment);
        if(e instanceof Cell){
          Cell list = (Cell)e;
          if(list.isEmpty())
            return Atom.NIL;
          else
            return list.car();
        }else{
          throw new EvaluationErrorException("Invalid expression: " + car.toFullString());
        }
      }else{
        throw new EvaluationErrorException("Invalid expression: " + cdr.toFullString());
      }
    }else{
      throw new EvaluationErrorException("Invalid expression: " + expression.toFullString());
    }
  }

  //primitive: (cdr list::s-expression)
  private SExpression cdr(SExpression expression, Environment environment) throws EvaluationErrorException{
    if(expression instanceof Cell){
      Cell cell = (Cell)expression;
      SExpression car = cell.car();
      SExpression cdr = cell.cdr();
      if(cdr == Atom.NIL){
        SExpression e = this.evaluate(car, environment);
        if(e instanceof Cell){
          Cell list = (Cell)e;
          if(list.isEmpty())
            return Atom.NIL;
          else
            return list.cdr();
        }else{
          throw new EvaluationErrorException("Invalid expression: " + car.toFullString());
        }
      }else{
        throw new EvaluationErrorException("Invalid expression: " + cdr.toFullString());
      }
    }else{
      throw new EvaluationErrorException("Invalid expression: " + expression.toFullString());
    }
  }

  private SExpression apply(SExpression expression, Environment environment) throws EvaluationErrorException{
    if(expression instanceof Cell){
      Cell cell = (Cell)expression;
      SExpression car = cell.car();
      SExpression cdr = cell.cdr();
      SExpression procedure = this.evaluate(car, environment);
      if(procedure instanceof Procedure){
        SExpression parameters = ((Procedure)procedure).getParameters();
        SExpression body = ((Procedure)procedure).getBody();
        Environment env = ((Procedure)procedure).getEnvironment();

        //evaluate arguments with environment
        SExpression arguments = null;
        SExpression argument = cdr;
        while(true){
          if(argument == Atom.NIL){
            break;
          }else if(!(argument instanceof Cell)){
            throw new EvaluationErrorException("Invalid expression: " + argument.toFullString());
          }else{
            Cell newbie = new Cell(this.evaluate(((Cell)argument).car(), environment));
            if(arguments == null)
              arguments = newbie;
            else
              ((Cell)argument).cdr(newbie); //ad-hoc safe cast only within this context
            argument = ((Cell)argument).cdr();
          }
        }
        //create new local environment; bind parameters and evaluated_arguments
        Environment local = new Environment(env);
        SExpression parameter = ((Cell)parameters).car();    //ToDo
        SExpression arg = ((Cell)arguments).car();           //ToDo
        while(parameter != Atom.NIL && arg != Atom.NIL){     //ToDo
          local.bind((Atom)parameter, arg);                  //ToDo
          if(parameter instanceof Atom || arg instanceof Atom)
            break;
          parameter = ((Cell)((Cell)parameter).cdr()).car(); //ToDo
          arg = ((Cell)((Cell)arg).cdr()).car();             //ToDo
        }
        //evaluate body with the new local environment
        SExpression value = null;
        SExpression b = ((Cell)body);
        while(b != Atom.NIL){
          value = this.evaluate(((Cell)b).car(), local);
          if(b instanceof Atom)
            break;
          b = ((Cell)b).cdr();
        }
        return value;
      }else{
        System.out.println(procedure);
        throw new EvaluationErrorException("aaa Invalid expression: " + procedure.toFullString());
      }
    }else{
      throw new EvaluationErrorException("bbb Invalid expression: " + expression.toFullString());
    }
  }

}
