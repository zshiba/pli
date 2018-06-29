import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PLI{


  public PLI(){
  }

  public void loop(){
    try(BufferedReader in = new BufferedReader(new InputStreamReader(System.in));){
      StringBuilder builder = new StringBuilder();
      int c;
      while((c = in.read()) != -1){
        builder.append((char)c);
      }
      String input = builder.toString();
      System.out.println(input);
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
