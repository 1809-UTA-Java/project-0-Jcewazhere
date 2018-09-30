package com.revature.bank.bankingApp;

import java.util.ArrayList;

public class Account {

	private String name;// account type
	private String uuid;
	private User holder;// user of this account
	private ArrayList<Transaction> transactions;// this account's transactions

	public Account(String name, User holder, Bank theBank) {

		// set account type and holder
		this.name = name;
		this.holder = holder;
		// get new account ID
		this.uuid = theBank.getNewAccountUUID();
		// initialize transactions
		this.transactions = new ArrayList<Transaction>();

	}

	public String getUUID() {
		return this.getUUID();
	}
	
	public String getSummaryLine() {
		//get the account's balance
		double balance = this.getBalance();
		
		//format the summary line to remove - signs
		if (balance >= 0) {
			return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
		}else {
			return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);
		}
	}
	
	public double getBalance() {
		double balance = 0;
		for (Transaction t : this.transactions) {
			balance += t.getAmount();
		}
		return balance;
	}

	public void printTransactionHistory() {
		System.out.printf("\nTransaction history for account %s\n", this.uuid);
		for(int t = this.transactions.size()-1; t >= 0; t--) {
			System.out.printf(this.transactions.get(t).getSummaryLine());
		}
		
	}

	public void addTransaction(double amount, String memo) {
		//create new transaction object and add it to the list
		Transaction newTrans = new Transaction(amount, memo, this);
		this.transactions.add(newTrans);
		
	}
}
