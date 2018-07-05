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
    System.out.println(source);

    Lexer lexer = new Lexer(source);
    Parser parser = new Parser(lexer);
    ArrayList<SExpression> expressions = parser.parse();
    return expressions;
  }

  private String eval(SExpression expression){
    return ""; //ToDo
  }

  private void print(String prompt, String value){
    System.out.print(prompt);
    System.out.println(value);
  }

  public void loop(){
    try(BufferedReader in = new BufferedReader(new InputStreamReader(System.in));){
      while(true){
        try{
          System.out.print(INPUT_PROMPT);
          ArrayList<SExpression> expressions = this.read(in);
          for(SExpression expression : expressions){
            String value = this.eval(expression);
            this.print(OUTPUT_PROMPT, value);
          }
        }catch(Parser.ParseErrorException pe){
          this.print(ERROR_PROMPT, pe.getMessage());
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
