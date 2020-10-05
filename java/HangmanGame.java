
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class HangmanGame {

 public static final String[] WORDS = {
  "ABSTRACT","BOOLEAN", "BREAK", "BYTE", "CASE", "CATCH", "CHAR", "CLASS", "CONST",
  "CONTINUE", "DEFAULT", "DOUBLE", "DO", "ELSE", "EXTENDS", "FALSE", "FINAL", "FINALLY",
  "FLOAT", "FOR",  "IF", "IMPLEMENTS", "IMPORT", "INT", "INTERFACE", 
  "LONG", "NEW", "NULL", "PACKAGE", "PRIVATE", "PROTECTED", "PUBLIC", "RETURN", 
  "SHORT", "STATIC", "SUPER", "SWITCH", "THIS", "THROW", "THROWS", "TRUE", "TRY", "VOID","WHILE"
 };

 public static final Random RANDOM = new Random();
 
 public static final int MAX_ERRORS = 8;

 private String wordToFind;
 private char[] wordFound;
 private int nbErrors;

 // letters already entered by user
 private ArrayList < String > letters = new ArrayList < > ();

 
 private String nextWordToFind() {
  return WORDS[RANDOM.nextInt(WORDS.length)];
 }

 
 public void newGame() {
  nbErrors = 0;
  letters.clear();
  wordToFind = nextWordToFind();

  
  wordFound = new char[wordToFind.length()];

  for (int i = 0; i < wordFound.length; i++) {
   wordFound[i] = '_';
  }
 }

 // if word is found by user
 public boolean wordFound() {
  return wordToFind.contentEquals(new String(wordFound));
 }

 private void enter(String c) {
  //  update if c has not already been entered
  if (!letters.contains(c)) {
   if (wordToFind.contains(c)) {
    int index = wordToFind.indexOf(c);

    while (index >= 0) {
     wordFound[index] = c.charAt(0);
     index = wordToFind.indexOf(c, index + 1);
    }
   } else {
    // c not in the word => error
    nbErrors++;
   }

   // c is now a letter entered
   letters.add(c);
  }
 }

 private String wordFoundContent() {
  StringBuilder builder = new StringBuilder();

  for (int i = 0; i < wordFound.length; i++) {
   builder.append(wordFound[i]);

   if (i < wordFound.length - 1) {
    builder.append(" ");
   }
  }

  return builder.toString();
 }

 // Play method for our Hangman Game
 public void play() {
  try (Scanner input = new Scanner(System.in)) {
   // we play while nbErrors is lower than max errors or user has found the word
   while (nbErrors < MAX_ERRORS) {
    System.out.println("\nEnter a letter : ");
    // get next input from user
    String str = input.next();

    // we keep just first letter
    if (str.length() > 1) {
     str = str.substring(0, 1);
    }

    // update word found
    enter(str);

    // display current state
    System.out.println("\n" + wordFoundContent());

    // check if word is found
    if (wordFound()) {
     System.out.println("\nYou win!");
     break;
    } else {
     System.out.println("\n=> Nb tries remaining : " + (MAX_ERRORS - nbErrors));
    }
   }

   if (nbErrors == MAX_ERRORS) {
    System.out.println("\nYou lose!");
    System.out.println("=> Word to find was : " + wordToFind);
   }
  }
 }

 public static void main(String[] args) {
  System.out.println("Hangman Game with Java Keywords :)");
  HangmanGame hangmanGame = new HangmanGame();
  hangmanGame.newGame();
  hangmanGame.play();
 }

}
