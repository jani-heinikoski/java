/*
Author: Jani Heinikoski | 0541122
Date: 26.2.2020
Header: CT60A2411_07.01.2020 | Olio-ohjelmointi | WEEK 5
Version: 6.1.0
 */
package com.kranaatinheitinkomppania;
import java.util.Scanner;


public class Main {

    private static int strToI(String buffer) { // converts buffer to int
        int i = 0;
        Scanner sc = new Scanner(buffer.trim());

        while (sc.hasNext()) {
            if (sc.hasNextInt()) {
                i = sc.nextInt();
                break;
            } else {
                sc.next();
            }
        }

        return i;
    }

    private static int cli(Scanner sc) { // Commandline interface
        int choice;

        System.out.print("\n*** BANK SYSTEM ***\n" +
                "1) Add a regular account\n" +
                "2) Add a credit account\n" +
                "3) Deposit money\n" +
                "4) Withdraw money\n" +
                "5) Remove an account\n" +
                "6) Print account information\n" +
                "7) Print all accounts\n" +
                "0) Quit\n" +
                "Your choice: ");

        choice = strToI(sc.nextLine());
        return choice;
    }

    public static void main(String[] args) {
        int choice;
        Scanner sc = new Scanner(System.in);
        String accNumber;
        int depositAmount;
        int withdrawAmount;
        int creditLimit;

        while (true) {
            switch (choice=cli(sc)) {
                case 1:
                    System.out.print("Give an account number: ");
                    accNumber = sc.nextLine().trim();
                    System.out.print("Amount of money to deposit: ");
                    depositAmount = strToI(sc.nextLine());
                    System.out.println("Account number: " + accNumber);
                    System.out.println("Amount of money: " + depositAmount);
                    break;
                case 2:
                    System.out.print("Give an account number: ");
                    accNumber = sc.nextLine().trim();
                    System.out.print("Amount of money to deposit: ");
                    depositAmount = strToI(sc.nextLine());
                    System.out.print("Give a credit limit: ");
                    creditLimit = strToI(sc.nextLine());
                    System.out.println("Account number: " + accNumber);
                    System.out.println("Amount of money: " + depositAmount);
                    System.out.println("Credit: " + creditLimit);
                    break;
                case 3:
                    System.out.print("Give an account number: ");
                    accNumber = sc.nextLine().trim();
                    System.out.print("Amount of money to deposit: ");
                    depositAmount = strToI(sc.nextLine());
                    System.out.println("Account number: " + accNumber);
                    System.out.println("Amount of money: " + depositAmount);
                    break;
                case 4:
                    System.out.print("Give an account number: ");
                    accNumber = sc.nextLine().trim();
                    System.out.print("Amount of money to withdraw: ");
                    withdrawAmount = strToI(sc.nextLine());
                    System.out.println("Account number: " + accNumber);
                    System.out.println("Amount of money: " + withdrawAmount);
                    break;
                case 5:
                    System.out.print("Give the account number of the account to delete: ");
                    accNumber = sc.nextLine().trim();
                    System.out.println("Account number: " + accNumber);
                    break;
                case 6:
                    System.out.print("Give the account number of the account to print information from: ");
                    accNumber = sc.nextLine().trim();
                    System.out.println("Account number: " + accNumber);
                    break;
                case 7:
                    System.out.println("Prints every account");
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
