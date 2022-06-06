/*
Main - this is a runner class that runs the Game class

Author - Marc Shepard
*/

class Main {
  public static void main(String[] args) {
    /*
    while (true) {
      State s = State.restore();
      if (s != null) {
        System.out.println ("Welcome back!\n");
      } else {
        s = new State();
        s.wins = 0;
        s.losses = 0;
      }
      s.save();
    }
    */
    Hangman g = new Hangman();
    g.play();
  }

  // Unit tests for Utils.java. Call this from main if you want to
  // see examples of the Utils methods
  public static void testUtils () {
    String s = Utils.inputStr ("What's your name? ");
    System.out.println ("Hi " + s);
    
    int n = Utils.inputNum ("What's your age? ", 150);
    System.out.println ("You typed " + n);

    int r = Utils.randInt (1, 100);
    System.out.println ("A random number between 1-100 is " + r);

    String[] strs = {"chocolate", "vanilla", "strawberry"};
    s = Utils.randChoice(strs);
    System.out.println ("A random flavor is: " + s);
  }
}