package com.revature.bank.bankingApp;

import java.util.Date;

public class Transaction {

	private double amount;// transaction amount
	private Date timestamp;// when the transaction happened
	private String memo;// optional note on the transaction
	private Account inAccount;// which account did the transaction

	public Transaction(double amount, Account inAccount) {

		this.amount = amount;
		this.inAccount = inAccount;
		this.timestamp = new Date();
		this.memo = "";
	}

	public Transaction(double amount, String memo, Account inAccount) {
		// call 2 arg constructor first
		this(amount, inAccount);
		this.memo = memo;

	}

}
