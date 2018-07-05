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
    environment.register(Atom.NIL, Atom.NIL);
    environment.register(Atom.TRUE, Atom.TRUE);
    //ToDo
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

  private SExpression eval(SExpression expression){
    Evaluator evaluator = new Evaluator();
    SExpression value = evaluator.evaluate(expression, this.environment);
    return value;
  }

  private void print(SExpression value){
    System.out.println(value.toString());
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
          }
        }catch(Parser.ParseErrorException pe){
          System.out.print(ERROR_PROMPT);
          System.out.println(pe.getMessage());
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
