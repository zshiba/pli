import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PLI{

  private static final String PROMPT = "> ";

  public PLI(){
  }

  private void read(BufferedReader in) throws IOException{
    System.out.print(PROMPT);
    StringBuilder builder = new StringBuilder();
    int c;
    while((c = in.read()) != -1){
      builder.append((char) c);
    }
    String source = builder.toString();
    System.out.println(source);
  }

  public void loop(){
    try(BufferedReader in = new BufferedReader(new InputStreamReader(System.in));){
      while(true){
        this.read(in);
      }
    }catch(IOException e){
      e.printStackTrace();
    }
  }

  public static void main(String[] args){
    System.out.println("=== PLI start ===");
    new PLI().loop();
    System.out.println("=== PLI end ===");
  }
}
