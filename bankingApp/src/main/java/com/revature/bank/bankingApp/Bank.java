package com.revature.bank.bankingApp;

import java.util.ArrayList;
import java.util.Random;

public class Bank {

	private String name;
	private ArrayList<User> users;
	private ArrayList<Account> accounts;

	public Bank(String name) {
		this.name = name;
		this.users = new ArrayList<User>();
		this.accounts = newArrayList<Account>();
	}

	public String getNewUserUUID() {
		String uuid;
		Random rng = new Random();
		int length = 5;
		boolean isUnique;

		do {// creating unique UUID
			uuid = "";
			for (int i = 0; i < length; i++) {
				uuid += ((Integer) rng.nextInt(10)).toString();
			}
			// check for unique
			isUnique = false;
			for (User u : this.users) {
				if (uuid.compareTo(u.getuuid()) == 0) {
					isUnique = true;
					break;
				}
			}
		} while (isUnique);

		return uuid;
	}

	public String getNewAccountUUID() {

		String uuid;
		Random rng = new Random();
		int length = 15;
		boolean isUnique;

		do {// creating unique UUID
			uuid = "";
			for (int i = 0; i < length; i++) {
				uuid += ((Integer) rng.nextInt(10)).toString();
			}
			// check for unique
			isUnique = false;
			for (Account a : this.accounts) {
				if (uuid.compareTo(a.getUUID()) == 0) {
					isUnique = true;
					break;
				}
			}
		} while (isUnique);

		return uuid;
	}

	public void addAccount(Account anAccount) {
		this.accounts.add(anAccount);
	}

	public User addUser(String firstName, String lastName, String pin) {
		// create a new user
		User newUser = new User(firstName, lastName, pin, this);
		this.user.add(newUser);

		// create savings account
		Account newAccount = new Account("Savings", newUser, this);
		
		// add to holder and bank lists
		newUser.addAccount(newAccount);
		this.addAccount(newAccount);

		return newUser;

	}

	public User userLogin(String userID, String pin) {
		// search through user list
		for (User u : this.user) {
			if (u.getuuid().compareTo(userID) == 0 && u.validatePin(pin)) {
				return u;
			}
		}
		// if pin or UUID incorrect return null
		return null;
	}
	
	public String getName() {
		return this.name;
	}

}
