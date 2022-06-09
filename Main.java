/*
Main - this is a runner class that runs the Game class

Author - Marc Shepard
*/

//Author - Kira Buehler
class Main {
  public static void main(String[] args) {

    State st = State.restore();
    if (st != null) {
      System.out.println("Welcome back!");
      if (Utils.inputNum(
          "Would you like to continue with your prior history(1) or start over(2)? Please type the associated number",
          2) == 2) {
        st.reset();
      }
    } else {
      st = new State();
      st.wins = 0;
      st.losses = 0;
    }
    st.save();

    Hangman g = new Hangman();
    g.play();
  }

  // Unit tests for Utils.java. Call this from main if you want to
  // see examples of the Utils methods
  public static void testUtils() {
    String s = Utils.inputStr("What's your name? ");
    System.out.println("Hi " + s);

    int n = Utils.inputNum("What's your age? ", 150);
    System.out.println("You typed " + n);

    int r = Utils.randInt(1, 100);
    System.out.println("A random number between 1-100 is " + r);

    String[] strs = { "chocolate", "vanilla", "strawberry" };
    s = Utils.randChoice(strs);
    System.out.println("A random flavor is: " + s);
  }
}