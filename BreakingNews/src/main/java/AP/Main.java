package AP;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {          //keeps showing news titles until the user exits the program
            Infrastructure infObject = new Infrastructure("b7eb49d6d3014b7e9d9977d377ad2f59");
            infObject.displayNewsList();    //displays the news titles and lets the user choose one
            System.out.println("Do you want to see another news? (y/n)");
            while (true) {
                char userInput = scanner.next().charAt(0);
                if (userInput == 'y') {
                    break; }    //if the user presses y, news list will be shown once again
                else if (userInput == 'n') {
                    System.exit(0); }   //if the user presses no, program will terminate
                else {
                    System.out.println("Invalid input. Please inter y or n.");
                }
            }
        }
    }
}