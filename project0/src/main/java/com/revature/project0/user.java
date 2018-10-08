package com.revature.project0;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class user {
	static Scanner sc = new Scanner(System.in);
	boolean logIN = false;
	boolean isAdmin = false;
	boolean isEMP = false;
	static String scan = "";

	public user(Scanner sc, boolean logIN, boolean isAdmin, boolean isEMP) {
		this.sc = sc;
		this.logIN = logIN;
		this.isAdmin = isAdmin;
		this.isEMP = isEMP;
	}// end constructor

	public static void menu(Scanner sc, boolean logIN, boolean isAdmin, boolean isEMP) {
		for (int i = 0; i < 20; i++)
			System.out.println(); // clear console the easy way
		System.out.println("-------------------------------");
		System.out.println("|1.) Deposit                  |");
		System.out.println("|2.) Withdraw                 |");
		System.out.println("|3.) Transfer                 |");
		System.out.println("|4.) View Balance             |");
		System.out.println("|5.) View Account Info        |");
		System.out.println("|6.) Request Joining accounts |");
		System.out.println("|7.) Set Username/Password    |");
		System.out.println("|X.) Logout                   |");
		System.out.println("-------------------------------");

		scan = sc.next();

		if (logIN) {
			switch (scan) {
			case "1":
				deposit(sc, logIN, isAdmin, isEMP);
				break;
			case "2":
				withdraw(sc, logIN, isAdmin, isEMP);
				break;
			case "3":
				transfer(sc, logIN, isAdmin, isEMP);
				break;
			case "4":
				viewBal(sc, logIN, isAdmin, isEMP);
				break;
			case "5":
				viewAcct(sc, logIN, isAdmin, isEMP);
				break;
			case "6":
				reqJoin(sc, logIN, isAdmin, isEMP);
				break;
			case "7":
				setPas(sc, logIN, isAdmin, isEMP);
				break;
			case "X":
				App.menu(sc);
				break;
			case "x":
				App.menu(sc);
				break;
			default:
				System.out.println("Please enter one of the choices from above.");
				sc.nextLine();
				menu(sc, logIN, isAdmin, isEMP);
			}
		} else {
			System.out.println("You must be logged in to use this menu.");
			App.menu(sc);
		}
	}// end menu

	static void setPas(Scanner sc, boolean logIN, boolean isAdmin, boolean isEMP) {
		/*
		 * set the user's password taking in acctID, oldPas and newPas then using
		 * prepared statement to verify ID and set new password
		 */
		sc.nextLine();// clear scanner
		String acctID;
		String oldPas;
		String newPas;
		for (int i = 0; i < 20; i++)
			System.out.println(); // clear console the easy way
		System.out.println("Please enter your account ID: ");
		acctID = sc.next();
		System.out.println("Please enter your old password: ");
		oldPas = sc.next();
		System.out.println("Please enter your new password: ");
		newPas = sc.next();

		/*
		 * prepared statements to verify acctID and password, then update the password
		 * to the new one
		 */
		if (isAdmin) {
			admin.menu(sc, logIN, isAdmin, isEMP);
		} else if (isEMP) {
			employee.menu(sc, logIN, isAdmin, isEMP);
		} else if (logIN) {
			menu(sc, logIN, isAdmin, isEMP);
		} else
			App.menu(sc);
	}// end setPas

	static void reqJoin(Scanner sc, boolean logIN, boolean isAdmin, boolean isEMP) {
		/*
		 * request a joint account from an admin. take in userID, current acctID target
		 * acctID save request to a file for an employee to process later
		 */
		String userID;
		String cAcctID;
		String tAcctID;

		for (int i = 0; i < 20; i++)
			System.out.println(); // clear console the easy way
		try {
			System.out.println("Please enter your user ID: ");
			userID = sc.next();// compare to user table to validate
			System.out.println("Please enter your current account number: ");
			cAcctID = sc.next();// validate
			System.out.println("Please enter the account number you want to join with: ");
			tAcctID = sc.next();// verify everything
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * sql prepared statement to verify info, update the account balance then change
		 * cAcctID to tAcctID
		 */

		if (isAdmin) {
			admin.menu(sc, logIN, isAdmin, isEMP);
		} else if (isEMP) {
			employee.menu(sc, logIN, isAdmin, isEMP);
		} else if (logIN) {
			menu(sc, logIN, isAdmin, isEMP);
		} else
			App.menu(sc);
	}// end reqJoin

	static void viewAcct(Scanner sc, boolean logIN, boolean isAdmin, boolean isEMP) {
		/* print out a specific account's info */
		for (int i = 0; i < 20; i++)
			System.out.println(); // clear console the easy way
		System.out.println("Please enter the acctID of the account you wish to view: ");
		String userID = sc.next();
		System.out.println();

		/* sql prepared statement to get account info */

		/* print formatted output */

		if (isAdmin) {
			admin.menu(sc, logIN, isAdmin, isEMP);
		} else if (isEMP) {
			employee.menu(sc, logIN, isAdmin, isEMP);
		} else if (logIN) {
			menu(sc, logIN, isAdmin, isEMP);
		} else
			App.menu(sc);
	}// end viewAcct

	static void viewBal(Scanner sc, boolean logIN, boolean isAdmin, boolean isEMP) {
		for (int i = 0; i < 20; i++)
			System.out.println(); // clear console the easy way
		System.out.println("Please enter your userID: ");
		String userID = sc.next();
		System.out.println("Please enter the ID of the account you wish to see the balance of: ");
		String acctID = sc.next();

		/*
		 * sql prepared statement to return the derived balance field. remember the
		 * balance is in cents for accuracy, so divid by 100 when printing
		 */

		if (isAdmin) {
			admin.menu(sc, logIN, isAdmin, isEMP);
		} else if (isEMP) {
			employee.menu(sc, logIN, isAdmin, isEMP);
		} else if (logIN) {
			menu(sc, logIN, isAdmin, isEMP);
		} else
			App.menu(sc);
	}// end viewBal

	static void transfer(Scanner sc, boolean logIN, boolean isAdmin, boolean isEMP) {
		/* take in acctID to transfer from, acctID to transfer to and amount */
		for (int i = 0; i < 20; i++)
			System.out.println(); // clear console the easy way
		System.out.println("Please enter the ID of the account you want to transfer FROM: ");
		String fAcctID = sc.next();
		System.out.println("Please enter the ID of the account you want to transfer TO: ");
		String tAcctID = sc.next();
		System.out.println("Please enter the amount you want to transfer: ");
		int amt = (int) (sc.nextDouble() * 100);
		amt = java.lang.Math.abs(amt);

		/*
		 * sql prep statement to ADD amt to tAcctID and SUBTRACT it from fAcctID then
		 * print new balances
		 */

		if (isAdmin) {
			admin.menu(sc, logIN, isAdmin, isEMP);
		} else if (isEMP) {
			employee.menu(sc, logIN, isAdmin, isEMP);
		} else if (logIN) {
			menu(sc, logIN, isAdmin, isEMP);
		} else
			App.menu(sc);

	}// end transfer

	static void withdraw(Scanner sc, boolean logIN, boolean isAdmin, boolean isEMP) {
		for (int i = 0; i < 20; i++)
			System.out.println(); // clear console the easy way
		/* deposit a negative amount */
		System.out.println("Please enter the ID of the account you want to withdraw from: ");
		String acctID = sc.next();
		System.out.println("Please enter the amount you want to withdraw: ");
		int amt = (int) (sc.nextDouble() * 100);
		amt = java.lang.Math.abs(amt);

		/* sql prep statement to SUBTRACT amt from tAcctID then print new balances */

		if (isAdmin) {
			admin.menu(sc, logIN, isAdmin, isEMP);
		} else if (isEMP) {
			employee.menu(sc, logIN, isAdmin, isEMP);
		} else if (logIN) {
			menu(sc, logIN, isAdmin, isEMP);
		} else
			App.menu(sc);
	}// end withdraw

	static void deposit(Scanner sc, boolean logIN, boolean isAdmin, boolean isEMP) {
		for (int i = 0; i < 20; i++)
			System.out.println(); // clear console the easy way
		System.out.println("Please enter the ID of the account you want to deposit into: ");
		String acctID = sc.next();
		System.out.println("Please enter the amount you want to deposit: ");
		int amt = (int) (sc.nextDouble() * 100);
		amt = java.lang.Math.abs(amt);

		/* sql prep statement to ADD amt to acctID then print new balance */
		try {
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

			Connection con = DriverManager.getConnection("192.168.56.105:1521:xe", "SYSTEM", "revature");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (isAdmin) {
			admin.menu(sc, logIN, isAdmin, isEMP);
		} else if (isEMP) {
			employee.menu(sc, logIN, isAdmin, isEMP);
		} else if (logIN) {
			menu(sc, logIN, isAdmin, isEMP);
		} else
			App.menu(sc);

	}// end deposit

}// end user class
