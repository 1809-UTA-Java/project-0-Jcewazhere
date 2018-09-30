package com.revature.bank.bankingApp;

import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {

	private String firstName;
	private String lastName;
	private String uuid;
	private byte pinHash[];// MD5 of user's pin
	private ArrayList<Account> accounts;// list of this user's accounts

	public User(String firstName, String lastName, String pin, Bank theBank) {

		// set user's name
		this.firstName = firstName;
		this.lastName = lastName;

		// MD5 hash for the pin
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}

		// create unique userID
		this.uuid = theBank.getNewUserUUID();

		// create empty account list
		this.accounts = new ArrayList<Account>();

		// print log
		System.out.printf("New user %s, %s with id %s created.\n", lastName, firstName, this.userID);

	}

	public void addAccount(Account anAccount) {
		this.accounts.add(anAccount);
	}

	public String getuuid() {
		return this.uuid;
	}

	// validate the pin
	public boolean validatePin(String aPin) {

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		return false;
	}

	public String getFirstName() {
		return this.firstName;
	}

	// print summary of the user's accounts
	public void printAccountsSummary() {
		System.out.printf("\n\n%s's accounts summary", this.firstName);
		for (int a = 0; a < this.accounts.size(); a++) {
			System.out.printf("%d) %s\n", a + 1, this.accounts.get(a).getSummaryLine());
		}
		System.out.println();
	}

	public int numAccounts() {
		return this.accounts.size();
	}

	public void printTransactionHistory(int theAccount) {
		this.accounts.get(theAccount).printTransactionHistory();
	}

	public double getAccountBalance(int fromAccount) {
		return this.accounts.get(fromAccount).getBalance();
	}

	public String getAccountUUID(int fromAccount) {
		return this.accounts.get(fromAccount).getUUID();

	}

	public void addAccountTransaction(int toAccount, double amount, String memo) {
		this.accounts.get(toAccount).addTransaction(amount, memo);
	}

}
