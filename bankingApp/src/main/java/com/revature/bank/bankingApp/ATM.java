package com.revature.bank.bankingApp;

import java.util.Scanner;

public class ATM {
	public static void main(String[] args) {
		// start scanner
		Scanner sc = new Scanner(System.in);

		// initialize bank
		Bank theBank = new Bank("Edwards Bank");

		// add user, which also creates savings account
		User aUser = theBank.addUser("John", "Doe", "1234");

		// add checking account
		Account newAccount = new Account("Checking", aUser, theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);

		user curUser;
		while (true) {

			// stay in login until logged in
			curUser = ATM.mainMenuPrompt(theBank, sc);

			// stay in main menu until user quits
			ATM.printUserMenu(curUser, sc);
		}

	}

	public static User mainMenuPrompt(Bank theBank, Scanner sc) {
		String userID;
		String pin;
		User authUser;
		do {

			System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
			System.out.print("Enter the user ID: ");
			userID = sc.nextLine();
			System.out.printf("enter pin: ");
			pin = sc.nextLine();

			authUser = theBank.userLogin(userID, pin);
			if (authUser == null) {
				System.out.println("Invalid userID or pin.");
			}

		} while (authUser == null);// loop until login success
		return authUser;
	}

	public static void printUserMenu(User theUser, Scanner sc) {
		//print account summary
		theUser.printAccountsSummary();
		
		int choice;
		
		do {
			System.out.printf("Welcom %s what would you like to do?",
					theUser.getFirstName());
			System.out.println("  1) Show account history");
			System.out.println("  2) Withdrawal");
			System.out.println("  3) Deposit");
			System.out.println("  4) Transfer");
			System.out.println("  5) Quit");
			System.out.println("");
			System.out.println("Please enter 1-5 to choose.");
			choice = sc.nextInt();
			
			if(choice > 5 || choice < 1) {
				System.err.println("Please use the numbers 1-5.");
			}
		}while choice < 1 || choice > 5);
		
		switch (choice) {
		case 1: ATM.showTransHistory(theUser, sc);
		break;
		case 2: ATM.withdrawalFunds(theUser, sc);
		break;
		case 3: ATM.depositFunds(theUser, sc);
		break;
		case 4: ATM.transferFunds(theUser, sc);
		break;
		}
		if (choice != 5) {ATM.printUserMenu(theUser, sc);}
	}
}