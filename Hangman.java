/*
Hangman Class - simple hangman word guessing game

In this game, the player guesses letters in a randomly chosen word, trying to find all the letters before the number of failures reaches the max. 

Author - Kira Buehler
*/

/*
Design Comment Block:
  public class Hangman{
    private static HashMap<String, ArrayList<String>> words;
    private static ArrayList<String> easyWords;
    private static ArrayList<String> midWords;
    private static ArrayList<String> hardWords;
    private String curWord;
    private ArrayList<String> prevGuesses;
    private ArrayList<String> failedGuesses;
    private int numGuesses;
    private int maxGuesses;
    private String curGuess;
    private String hint;

    public Hangman(){
      Sets up words lists
    }

    public void play{
      Introduces the name and rules of game
      Asks for difficulty of word, then chooses word, sets up game
      Loops this until game ends{
        Prints numGuesses + numGuesses left
        Prints the previously failedGuesses
        Prints the current hint
        Asks for a letter, assigns to curGuess
        If valid letter, checks how that plays out
        If numGuesses reaches max guesses, game ends
        If all letters have been found, game ends
      }
    }

    public String createHint(String word){
      Creates a hint with * replacing all letters
    }

    public String updateHint(String letter){
      Sets substrings in hint which align with where letter is in curWord with the letter
    }

    public void printState(){
      Prints all relevant information for a new turn
    }

    public void chooseWord(String difficulty){
      Assigns a random word from chosen difficulty list, assigns it to curWord
    Sets maxGuesses based on difficulty (Switch Statements)
    }

    public boolean validLetter(String letter){
      Checks if letter is one letter
      Checks if letter is in prevGuesses
      If valid letter, is added to prevGuesses, numGuesses increased by 1
      Checks if letter is in curWord
        If yes, update hint is run
        If no, letter is added to failedGuesses
    }

    public boolean checkEnd(){
      Checks if the hint is equal to the curWord
      Checks if the maxGuesses == numGuesses
    }

  }
*/

import java.util.HashMap;
import java.util.ArrayList;

public class Hangman {
  private static HashMap<String, String[]> words;
  private static String[] easyWords;
  private static String[] midWords;
  private static String[] hardWords;
  private String curWord;
  private ArrayList<String> prevGuesses;
  private ArrayList<String> failedGuesses;
  private int numWrongGuesses;
  private int maxWrongGuesses;
  private String curGuess;
  private String hint;
  private int recWin;
  private int recLos;
  private static String[] visuals;
  private boolean images;
  State stat = State.restore();

  // Sets up the games variables
  public Hangman() {
    words = new HashMap<String, String[]>();
    hardWords = new String[] { "dog", "boy", "hat", "bog", "ion", "red", "sir", "won" };
    midWords = new String[] { "table", "sugar", "array", "still", "cough", "sweat", "floor", "plate" };
    easyWords = new String[] { "computer", "calculate", "pavement", "regenerate", "graduation", "difficulty", "automobile", "gasoline" };
    words.put("easy", easyWords);
    words.put("medium", midWords);
    words.put("hard", hardWords);
    numWrongGuesses = 0;
    prevGuesses = new ArrayList<String>();
    failedGuesses = new ArrayList<String>();
    recWin = 0;
    recLos = 0;
    
    //Visuals created by Chris Horton
    //Visuals adapted from Python for Java by Kira Buehler
    visuals = new String[] {
      "  +---+\n  |   |\n      |\n      |\n      |\n      |\n =========",
      "  +---+\n  |   |\n  O   |\n      |\n      |\n      |\n =========",
      "  +---+\n  |   |\n  O   |\n  |   |\n      |\n      |\n =========",
      "  +---+\n  |   |\n  O   |\n /|   |\n      |\n      |\n =========",
      "  +---+\n  |   |\n  O   |\n /|\\  |\n      |\n      |\n =========",
      "  +---+\n  |   |\n  O   |\n /|\\  |\n /    |\n      |\n =========",
      "  +---+\n  |   |\n  O   |\n /|\\  |\n / \\  |\n      |\n========="
    };
  }

  // Runs the Game
  public void play() {
    System.out.println(
        "\nWelcome to Hangman! \nIn this game, you will try to guess all the letters in a secret word before you reach the maximum amount of wrong guesses. \nIf you guess every letter in the word, you win! \nIf you reach the maximum number of wrong guesses and haven't found every letter in the word, you lose!");
    setUp(stat);
    System.out.println(stat);
    System.out.println();
    switch(Utils.inputNum("Would you like to play simple(1) or advanced(2) mode? Simple mode has a visual hangman and advanced mode allows you to select your difficulty. Type the corresponding number", 2)){
      case 1:
        maxWrongGuesses = 6;
        curWord = Utils.randChoice(words.get("medium"));
        images = true;
        break;
      case 2:
        images = false;
        switch (Utils.inputNum(
        "Please select your chosen difficulty by typing the corresponding number (easy(1), medium(2), or hard(3))", 3)) {
          case 1:
            maxWrongGuesses = 10;
            curWord = Utils.randChoice(words.get("easy"));
            break;
          case 2:
            maxWrongGuesses = 6;
            curWord = Utils.randChoice(words.get("medium"));
            break;
          case 3:
            maxWrongGuesses = 4;
            curWord = Utils.randChoice(words.get("hard"));
            break;
        }
    }
    
    hint = createHint(curWord);
    while (!checkEnd()) {
      printState();
      curGuess = Utils.inputStr("Please type a valid letter");
      while (!validLetter(curGuess)) {
        curGuess = Utils.inputStr("Please type one letter/one that has not been guessed");
      }
      prevGuesses.add(curGuess);
      if (inWord(curGuess)) {
        updateHint(curGuess);
      } else {
        failedGuesses.add(curGuess);
        numWrongGuesses += 1;
      }
    }
    stat.save();
    System.out.println("The word was: " + curWord);
    System.out.println("Number of Wins: " + recWin + "\nNumber of Losses: " + recLos);
    switch (Utils.inputNum(
        "Would you like to play again? Type the corresponding number: Yes(1), No(2), Maybe(3)", 3)){
      case 1:
        reset();
        break;
      case 2:
        System.out.println("Thanks for playing!");
        break;
      case 3:
        if (Utils.randInt(0, 1) == 1){
          reset();
        } else {
          System.out.println("Thanks for playing!");
        }
        break;
    }
  }

  // Creates the initial hint with all "*"
  public String createHint(String word) {
    String h = "";
    for (int i = 0; i < word.length(); i++) {
      h += "*";
    }
    return h;
  }

  // Checks if the game has ended, prints if it has
  public boolean checkEnd() {
    if (hint.equals(curWord)) {
      System.out.println("");
      System.out.println("You Won!");
      recWin += 1;
      stat.addWin();
      return true;
    } else if (numWrongGuesses == maxWrongGuesses) {
      System.out.println(visuals[6]);
      System.out.println("You Lost!");
      recLos += 1;
      stat.addLos();
      return true;
    } else {
      return false;
    }
  }

  // Prints the current state of the game
  public void printState() {
    if (images){
      System.out.println(visuals[numWrongGuesses]);
    }
    System.out.println("\nThe current hint is: " + hint);
    System.out.println("You have " + (maxWrongGuesses - numWrongGuesses) + " chances to guess left");
    String letters = "";
    for (String l : failedGuesses) {
      letters += (l + "  ");
    }
    System.out.println("Failed Guesses: " + letters);
  }

  //Checks if the letter is a valid input
  public boolean validLetter(String l) {
    if (l.length() != 1) {
      return false;
    }
    for (int i = 0; i < prevGuesses.size(); i++) {
      if (l.equals(prevGuesses.get(i))) {
        return false;
      }
    }
    return true;
  }

  // Checks if a given letter is in curWord
  public boolean inWord(String l) {
    if (curWord.indexOf(l) >= 0) {
      System.out.println("\nThis letter IS in the word!");
      return true;
    }
    System.out.println("\nThis letter is NOT in the word!");
    return false;
  }

  // Adds the correct letter into the hint
  public void updateHint(String l) {
    for (int i = 0; i < curWord.length(); i++) {
      if (curWord.substring(i, i + 1).equals(l)) {
        hint = hint.substring(0, i) + l + hint.substring(i + 1);
      }
    }
  }

  //Resets the game
  public void reset(){
    curWord = "";
    prevGuesses.clear();
    failedGuesses.clear();
    numWrongGuesses = 0;
    hint = "";
    play();
  }

  public void setUp(State sta){
    recWin = sta.getWins();
    recLos = sta.getLos();
  }
}