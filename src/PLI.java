import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PLI{

  private static final String INPUT_PROMPT  = ">> ";
  private static final String OUTPUT_PROMPT = "=> ";
  private static final String ERROR_PROMPT  = "** ";

  private Environment environment;

  public PLI(){
    this.environment = this.createTopLevelEnvironment();
  }

  private Environment createTopLevelEnvironment(){
    Environment environment = new Environment();
    environment.register(Atom.NIL);
    environment.register(Atom.TRUE);
    environment.register(Atom.QUOTE);
    environment.register(Atom.DEFINE);
    environment.register(Atom.COND);
    environment.register(Atom.LAMBDA);
    environment.register(Atom.EQ);
    environment.register(Atom.ATOM);
    environment.register(Atom.CONS);
    environment.register(Atom.CAR);
    environment.register(Atom.CDR);
    return environment;
  }

  private ArrayList<SExpression> read(BufferedReader in) throws IOException, Parser.ParseErrorException{
    StringBuilder builder = new StringBuilder();
    int c;
    while((c = in.read()) != -1){
      builder.append((char) c);
    }
    String source = builder.toString();

    Lexer lexer = new Lexer(source);
    Parser parser = new Parser(lexer);
    ArrayList<SExpression> expressions = parser.parse();
    return expressions;
  }

  private SExpression eval(SExpression expression) throws Evaluator.EvaluationErrorException{
    Evaluator evaluator = new Evaluator();
    SExpression value = evaluator.evaluate(expression, this.environment);
    return value;
  }

  private void print(SExpression value){
    if(value instanceof Atom){
      System.out.print(value.toString());
    }else if(value instanceof List){
      List list = (List)value;
      if(list.isEmpty()){
        this.print(Atom.NIL);
      }else{
        System.out.print("(");
        while(true){
          this.print(list.car());
          if(list.cdr() instanceof Atom){
            if(list.cdr() != Atom.NIL){
              System.out.print(" . ");
              System.out.print(list.cdr());
            }
            break;
          }else if(list.cdr() instanceof List){
            System.out.print(" ");
            list = (List)list.cdr(); //safe cast
          }else{
            System.out.print(value.toFullString());
          }
        }
        System.out.print(")");
      }
    }else{
      System.out.print(value.toFullString());
    }
  }

  public void loop(){
    try(BufferedReader in = new BufferedReader(new InputStreamReader(System.in));){
      while(true){
        try{
          System.out.print(INPUT_PROMPT);
          ArrayList<SExpression> expressions = this.read(in);
          for(SExpression expression : expressions){
            SExpression value = this.eval(expression);
            System.out.print(OUTPUT_PROMPT);
            this.print(value);
            System.out.println();
            System.out.println();
          }
        }catch(Parser.ParseErrorException | Evaluator.EvaluationErrorException ex){
          System.out.print(ERROR_PROMPT);
          System.out.println(ex.getMessage());
          System.out.println();
        }
      }
    }catch(IOException e){
      e.printStackTrace();
    }
  }

  //entry point
  public static void main(String[] args){
    System.out.println("=== PLI start ===");
    new PLI().loop();
    System.out.println("=== PLI end ===");
  }

}
