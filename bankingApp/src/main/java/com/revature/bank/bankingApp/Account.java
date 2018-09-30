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
}
