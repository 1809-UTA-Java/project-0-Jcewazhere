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

	}
}