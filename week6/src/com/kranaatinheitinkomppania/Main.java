package com.kranaatinheitinkomppania;
import java.util.Scanner;


public class Main {

    private static int cli() {
        Scanner sc = new Scanner(System.in);
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

        choice = sc.nextInt();
        return choice;
    }

    public static void main(String[] args) {
        int choice;
        Scanner sc = new Scanner(System.in);
        String accNumber;
        double depositAmount;
        double withdrawAmount;

        while (true) {
            switch (choice=cli()) {
                case 1:
                    System.out.println("Give an account number: ");
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        }
    }
}
