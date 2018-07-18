public class Environment{

  private static class Pair{ //node of association list (singly linked list of key-value pair)

    private Atom key;
    private SExpression value;
    private boolean isPrimitive;
    private Pair next;

    public Pair(Atom key, SExpression value, boolean isPrimitive){
      this.key = key;
      this.value = value;
      this.isPrimitive = isPrimitive;
      this.next = null;
    }

    public Atom key(){
      return this.key;
    }

    public SExpression value(){
      return this.value;
    }

    public void setValue(SExpression value){
      this.value = value;
    }

    public boolean isPrimitive(){
      return this.isPrimitive;
    }

    public Pair next(){
      return this.next;
    }

    public void setNext(Pair node){
      this.next = node;
    }

  }

  private Environment outer;
  private Pair head;

  public Environment(){
    this(null);
  }

  public Environment(Environment outer){
    this.outer = outer;
    this.head = null;
  }

  public void register(Atom symbol){
    this.bind(symbol, symbol, true);
  }

  public boolean bind(Atom key, SExpression value){
    return this.bind(key, value, false);
  }

  private boolean bind(Atom key, SExpression value, boolean isPrimitive){
    boolean isBound = false;
    Pair newbie = new Pair(key, value, isPrimitive);

    Pair previous = null;
    Pair current = this.head;
    while(current != null){
      if(current.key().equals(key))
        break;
      previous = current;
      current = current.next();
    }

    if(previous == null && current == null){ //list was empty
      this.head = newbie;
      isBound = true;
    }else if(previous != null && current == null){ //key has not been bound yet
      previous.setNext(newbie);
      isBound = true;
    }else{
      if(!current.isPrimitive()){
        current.setValue(value); //key, which is non-primitive, already exits; overwrite value
        isBound = true;
      }
    }
    return isBound;
  }

  public SExpression find(Atom key){
    Pair node = this.head;
    while(node != null){
      if(node.key().equals(key))
        break;
      node = node.next();
    }
    if(node != null)
      return node.value();
    else if(this.outer != null)
      return this.outer.find(key);
    else
      return null;
  }

  //for tests
  public void dump(){
    System.out.println("=== start environment.dump()  ===");
    Pair node = this.head;
    while(node != null){
      System.out.println("-");
      System.out.println("key: " + node.key().toFullString());
      System.out.println("value: " + node.value().toFullString());
      System.out.println("-");
      node = node.next();
    }
    System.out.println("=== end   environment.dump() ===");
  }

}
