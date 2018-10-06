package com.revature.project0;

import java.sql.*;
import java.util.Scanner;

public class employee {
	Scanner sc = new Scanner(System.in);
	boolean logIN = false;
	boolean isAdmin = false;
	boolean isEMP = false;
	String scan = "";

	public employee(Scanner sc, boolean logIN, boolean isAdmin, boolean isEMP) {
		super();
		this.sc = sc;
		this.logIN = logIN;
		this.isAdmin = isAdmin;
		this.isEMP = isEMP;
	}// constructor end

	public static void menu(Scanner sc, boolean logIN, boolean isAdmin, boolean isEMP) {
		for (int i = 0; i < 20; i++)
			System.out.println(); // clear console the easy way
		System.out.println("----------------------------------------------");
		System.out.println("|1.) Deposit           A.) View all accounts |");
		System.out.println("|2.) Withdraw          B.) Set Active Status |");
		System.out.println("|3.) Transfer          C.) Process Join      |");
		System.out.println("|4.) View Balance                            |");
		System.out.println("|5.) View Account Info                       |");
		System.out.println("|6.) Request joining accounts                |");
		System.out.println("|7.) Set Username/Password                   |");
		System.out.println("|X.) Logout                                  |");
		System.out.println("----------------------------------------------");

		String scan = sc.next();
		if (isEMP) {
			if (scan.equalsIgnoreCase("A")) {
				viewAccts(sc, logIN, isAdmin, isEMP);
			} else if (scan.equalsIgnoreCase("B")) {
				setActive(sc, logIN, isAdmin, isEMP);
			} else if (scan.equalsIgnoreCase("C")) {
				processJoin(sc, logIN, isAdmin, isEMP);
			} else if (scan.equals("1")) {
				user.deposit(sc, logIN, isAdmin, isEMP);
			} else if (scan.equals("2")) {
				user.withdraw(sc, logIN, isAdmin, isEMP);
			} else if (scan.equals("3")) {
				user.transfer(sc, logIN, isAdmin, isEMP);
			} else if (scan.equals("4")) {
				user.viewBal(sc, logIN, isAdmin, isEMP);
			} else if (scan.equals("5")) {
				user.viewAcct(sc, logIN, isAdmin, isEMP);
			} else if (scan.equals("6")) {
				user.reqJoin(sc, logIN, isAdmin, isEMP);
			} else if (scan.equals("7")) {
				user.setPas(sc, logIN, isAdmin, isEMP);
			} else if (scan.equalsIgnoreCase("X")) {
				App.menu(sc);
			} else {
				System.out.println("Please enter one of the options above.");
				menu(sc, logIN, isAdmin, isEMP);
			}
		} else {
			System.out.println("You must be logged in as an employee to access this menu");
			App.menu(sc);
		}
	}// menu end

	static void processJoin(Scanner sc, boolean logIN, boolean isAdmin, boolean isEMP) {
		/*
		 * process a join request from a user. take in userID, current acctID target
		 * acctID read request from a file for processing
		 */
		String userID;
		String cAcctID;
		String tAcctID;

		for (int i = 0; i < 20; i++)
			System.out.println(); // clear console the easy way
		System.out.println("Please enter your user ID: ");
		userID = sc.next();
		System.out.println("Please enter your current account number: ");
		cAcctID = sc.next();
		System.out.println("Please enter the account number you want to join with: ");
		tAcctID = sc.next();// verify everything

		/*
		 * sql prepared statement to verify info, update the account balance then change
		 * cAcctID to tAcctID
		 */

		if (isAdmin) {
			admin.menu(sc, logIN, isAdmin, isEMP);
		} else if (isEMP) {
			employee.menu(sc, logIN, isAdmin, isEMP);
		} else if (logIN){
			menu(sc, logIN, isAdmin, isEMP);
		}else App.menu(sc);

	}// end processJoin

	static void setActive(Scanner sc, boolean logIN, boolean isAdmin, boolean isEMP) {
		/*
		 * any user may create an account but nothing should work without the active
		 * flag being true employees and admins can set this flag here
		 */
		for (int i = 0; i < 20; i++)
			System.out.println(); // clear console the easy way
		System.out.println("please enter the userID of the account you want to activate: ");
		String userID = sc.next();

		/* sql prep statement to set active flag to 1 */

		if (isAdmin) {
			admin.menu(sc, logIN, isAdmin, isEMP);
		} else if (isEMP) {
			employee.menu(sc, logIN, isAdmin, isEMP);
		} else if (logIN){
			menu(sc, logIN, isAdmin, isEMP);
		}else App.menu(sc);
	}// end setActive

	static void viewAccts(Scanner sc, boolean logIN, boolean isAdmin, boolean isEMP) {
		/* print out a formatted list of the user names, userIDs and their acctIDs */
		for (int i = 0; i < 20; i++)
			System.out.println(); // clear console the easy way
		/* sql prep statement to print out usernames and acctIDs */

		if (isAdmin) {
			admin.menu(sc, logIN, isAdmin, isEMP);
		} else if (isEMP) {
			employee.menu(sc, logIN, isAdmin, isEMP);
		} else if (logIN){
			menu(sc, logIN, isAdmin, isEMP);
		}else App.menu(sc);
	}// end viewAccts

}// end employee class
