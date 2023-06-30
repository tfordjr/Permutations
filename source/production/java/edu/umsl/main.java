package edu.umsl;

/******************************************************************************

 This program should be run with Java compiler on OnlineGDB

 CMP SCI 4500-001  HW2 ASSIGNMENT - PERMUTATIONS

 Product Description:
 This program is designed to find all permutations of a string with a length
 of 3-10 charcters given by the user. After finding string permutations the
 program will compare them to "words_alpha.txt" to determine which of these
 are english words. When matches are found, they are added to wordList
 ArrayList where they are sorted alphabetically and printed as a set.

 Data structures in Program:
 The program finds permutations with the recursive permutation method.
 This method efficiently cycles through every possible permutation of the
 given string. We transfer words_alpha.txt to an ArrayList, whicha llows the
 use of Collections.binarySearch(), A convienient and efficient means of
 matching permutations with words.

 Text Files:
 One additional text file named "words_alpha.txt" is needed to complete the
 execution of the program. In this file will be a list of the "english words"
 the program will compare the permutations to. Please note each word is
 defined by a new line.

 Source Code used:
 inputValidate() isAplha() Method.
 Source: https://www.sourcecodeexamples.net/2019/12/java-string-isalpha-utility-method.html#:~:text=Java%20String%20isAlpha%20%28%29%20Utility%20Method%20The%20following,%3Cp%3E%20%2A%20%7B%40code%20null%7D%20will%20return%20%7B%40code%20false%7D.

 Permutation Methods - This is the heart of the project. This is the main
 engine that finds different permutations to be tested, compared, and sorted later on.
 Source: https://www.netjstech.com/2016/05/how-find-all-permutations-of-given-string-java-program.html#:~:text=Java%20program%20for%20finding%20permutations%20of%20a%20String,there%20the%20steps%20are%20-%20More%20items...%20

 File Input Scanner - Assisted my understanding of File Input
 Source: https://stackoverflow.com/questions/19641794/java-text-file-input

 Information about ArrayList and .contains method, and .toString method for testing/debugging
 Source: https://docs.microsoft.com/en-us/dotnet/api/system.collections.generic.list-1.contains?view=net-6.0
 Source: https://www.javatpoint.com/java-arraylist

 *******************************************************************************/
import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.util.ArrayList;


public class main
{
    // Static variables that can be referenced in our recursive permutation function without passing by value each time.
    public static ArrayList<String> wordList = new ArrayList<String>();
    public static ArrayList<String> englishWords = new ArrayList<>();

    //driver fuction
    public static void main(String[] args) throws FileNotFoundException {
        int Y = 0;       // Counts number of words generated
        int X = 2;       // Counts word length
        int wordsPrinted = 0;

        System.out.println("Please enter a string and I will find all words possible from rearraging the given string!");
        String S = inputValidate(); //validate user input, output correct error message, and reprompt if necessary

        Scanner inFile = new Scanner(new FileReader("words_alpha.txt"));  // <-- this line Source: https://stackoverflow.com/questions/19641794/java-text-file-input
        while(inFile.hasNextLine())
            englishWords.add(inFile.nextLine().trim());
        inFile.close();

        //alphabetically sort englishwords gathered from the words_alpha.txt file
        Collections.sort(englishWords);

        for (; X <= S.length(); X++) {                  // Main engine of program
            System.out.println("\nWords of length " + X + ": ");
            Y += permutation("", S, X);  // find permutations of S with length X
            wordsPrinted = print(X, Y, wordsPrinted);  // sort and print sets of words
        }


        //Prompts user to push ENTER to Halt execution of program after all words are printed.
        System.out.println("\nProgram is finished printing permutations please push ENTER to halt the execution of the program: ");
        Scanner s = new Scanner (System.in);
        while(!s.nextLine().equals("")){
            System.out.println("Please push ENTER to halt the execution: ");
        }
        System.exit(0);
    }

    //print set of words for X size input
    public static int print(int X, int Y, int wordsPrinted) {
        for(String word: wordList) {
            if (word.length() == X) {
                wordsPrinted++;
                System.out.println("\tWord " + wordsPrinted + ": " + word);
            }
        }
        return wordsPrinted;
    }

    // recursive method to find permutations
    private static int permutation(String prefix, String str, int X)  {
        int Y = 0;

        // If there is a match, and it isn't in wordList already
        if (prefix.length() == X && !wordList.contains(prefix)
                && Collections.binarySearch(englishWords, prefix) >= 0) {
            Y++;
            wordList.add(prefix);               // add the word to the list

        } else {
            // If there isn't a match, continue searching
            for (int i  = 0;  i < str.length();  i++){
                permutation(prefix + str.charAt(i),         // Recursive function call
                        str.substring(0, i) + str.substring(i+1, str.length()), X);
            }
        }
        return Y;
    }

    /* inputValidate method captures valid user input
    //will prompt user for input again if input is not valid. */
    public static String inputValidate() {
        String input = "";
        boolean retry;
        do {
            System.out.print("Please enter a valid string: ");
            Scanner sc = new Scanner(System.in);
            retry = false;

            // try/catch block to prevent throwing an exception
            try {
                input = sc.nextLine();
            } catch (Exception ex) {
                System.out.println("Exceptions handled.");
                retry = true;
            }

            // Size checking (3-10) will prompt user again if input is invalid
            if (input.length() > 10 || input.length() < 3) {
                retry = true;
                System.out.println("String must be between 3 and 10 characters long.");
            }

            /* isAplha() method. We had great difficulty getting a simple String.isAplha()
            //method to work. This block of code is the equivalent.
            //Source: https://www.sourcecodeexamples.net/2019/12/java-string-isalpha-utility-method.html#:~:text=Java%20String%20isAlpha%20%28%29%20Utility%20Method%20The%20following,%3Cp%3E%20%2A%20%7B%40code%20null%7D%20will%20return%20%7B%40code%20false%7D. */
            for (int i = 0; i < input.length(); i++) {
                if (!Character.isLetter(input.charAt(i))) {
                    retry = true;
                    System.out.println("String must be only letters.");
                    break;
                }
            }

            //String Cleaning -converts user string to lowercase when valid.
            input = input.toLowerCase();
        } while (retry);
        return input;
    }
}
