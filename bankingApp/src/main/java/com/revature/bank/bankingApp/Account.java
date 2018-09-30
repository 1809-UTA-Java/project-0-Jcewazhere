package com.revature.bank.bankingApp;

import java.util.ArrayList;

public class Account {

	private String type;// account type
	private String accountID;
	private User holder;// user of this account
	private ArrayList<Transaction> transactions;// this account's transactions

	public Account(String type, User holder, Bank theBank) {

		// set account type and holder
		this.type = type;
		this.holder = holder;
		// get new account ID
		this.accountID = theBank.getNewUUID();
		// initialize transactions
		this.transactions = new ArrayList<Transaction>();

	}

	public String getUUID() {
		return this.getUUID();
	}
}
