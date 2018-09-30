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

		User curUser;
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
		// print account summary
		theUser.printAccountsSummary();

		int choice;

		do {
			System.out.printf("Welcom %s what would you like to do?", theUser.getFirstName());
			System.out.println("  1) Show account history");
			System.out.println("  2) withdraw");
			System.out.println("  3) Deposit");
			System.out.println("  4) Transfer");
			System.out.println("  5) Quit");
			System.out.println("");
			System.out.println("Please enter 1-5 to choose.");
			choice = sc.nextInt();

			if (choice > 5 || choice < 1) {
				System.err.println("Please use the numbers 1-5.");
			}
		} while (choice < 1 || choice > 5);

		switch (choice) {
		case 1:
			ATM.showTransHistory(theUser, sc);
			break;
		case 2:
			ATM.withdrawFunds(theUser, sc);
			break;
		case 3:
			ATM.depositFunds(theUser, sc);
			break;
		case 4:
			ATM.transferFunds(theUser, sc);
			break;
		}
		if (choice != 5) {
			ATM.printUserMenu(theUser, sc);
		}
	}

	private static void transferFunds(User theUser, Scanner sc) {
		int fromAccount;
		int toAccount;
		double amount;
		double accountBalance;
		
		//get account to transfer from
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" +
		"to transfer from: ");
			fromAccount = sc.nextInt()-1;
			if(fromAccount < 0 || fromAccount >= theUser.numAccounts()) {
				System.err.println("Invalid account, Please try again.");
			}
		}while(fromAccount < 0 || fromAccount >= theUser.numAccounts());
		accountBalance = theUser.getAccountBalance(fromAccount);
		
		//get account to transfer to
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" +
		"to transfer to: ");
			toAccount = sc.nextInt()-1;
			if(toAccount < 0 || toAccount >= theUser.numAccounts()) {
				System.err.println("Invalid account, Please try again.");
			}
		}while(toAccount < 0 || toAccount >= theUser.numAccounts());
		accountBalance = theUser.getAccountBalance(toAccount);
		
		//get amount to transfer
		do {
			System.out.printf("Enter how much you want to transfer (max $%.02f): $", accountBalance);
			amount = sc.nextDouble();
			if(amount < 0 || amount > accountBalance) {
				System.err.println("Amount must be greater than zero but less than the account balance.");
			}
			
		}while(amount < 0 || amount > accountBalance);	
		
		//do the transfer
		theUser.addAccountTransaction(fromAccount, -1*amount, String.format(
				"transfer to account %s", theUser.getAccountUUID(toAccount)));
		theUser.addAccountTransaction(toAccount, amount, String.format(
				"transfer from account %s", theUser.getAccountUUID(fromAccount)));
	}

	public static void showTransHistory(User theUser, Scanner sc) {
		int theAccount;
		
		do {System.out.printf("Enter the number (1-%d) of the account\n" +
		"whose transactions you want to see: ", theUser.numAccounts());
		theAccount = sc.nextInt()-1;
		if (theAccount < 0 || theAccount >= theUser.numAccounts()) {
			System.err.println("Invalid account, Please try again.");
		}
		
		}while(theAccount < 0 || theAccount >= theUser.numAccounts());
		
		theUser.printTransactionHistory(theAccount);

	}
	
	public static void withdrawFunds(User theUser, Scanner sc) {
		int fromAccount;
		double amount;
		double accountBalance;
		String memo;
		
		do {
			System.out.printf("Enter the number (1-%d) of the account "
				+"to withdraw from: ", theUser.numAccounts());
			fromAccount = sc.nextInt()-1;
			if(fromAccount < 0 || fromAccount >= theUser.numAccounts()) {
				System.err.println("Invalid account, please try again.");
			}
		}while (fromAccount < 0 || fromAccount >= theUser.numAccounts());
		accountBalance = theUser.getAccountBalance(fromAccount);
		
		do {
			System.out.printf("Enter the amount to withdraw (max $%.02f): $",
					accountBalance);
			amount = sc.nextDouble();
			if(amount < 0) {
				System.err.println("Withdraw more than 0 dollars please.");
			}else if(amount > accountBalance) {
				System.err.println("You don't have that much money.");
			}
		}while(amount < 0 || amount > accountBalance);
		//clean scanner
		sc.nextLine();
		//get memo
		System.out.println("Enter a memo: ");
		memo = sc.nextLine();
		
		//do the withdraw
		theUser.addAccountTransaction(fromAccount, -1*amount, memo);
	}
	
	public static void depositFunds(User theUser, Scanner sc) {
		int toAccount;
		double amount;
		double accountBalance;
		String memo;
		
		do {
			System.out.printf("Enter the number (1-%d) of the account "
				+"to deposit into: ", theUser.numAccounts());
			toAccount = sc.nextInt()-1;
			if(toAccount < 0 || toAccount >= theUser.numAccounts()) {
				System.err.println("Invalid account, please try again.");
			}
		}while (toAccount < 0 || toAccount >= theUser.numAccounts());
		accountBalance = theUser.getAccountBalance(toAccount);
		
		do {
			System.out.printf("Enter the amount to deposit: $");
			amount = sc.nextDouble();
			if(amount < 0) {
				System.err.println("Deposit more than 0 dollars please.");
		}while(amount < 0);
		//clean scanner
		sc.nextLine();
		//get memo
		System.out.println("Enter a memo: ");
		memo = sc.nextLine();
		
		//do the deposit
		theUser.addAccountTransaction(toAccount, amount, memo);
	}

}