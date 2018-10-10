package com.revature.project0;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

		System.out.println("Please enter your user ID: ");
		userID = sc.next();// compare to user table to validate
		System.out.println("Please enter your current account number: ");
		cAcctID = sc.next();// validate
		System.out.println("Please enter the account number you want to join with: ");
		tAcctID = sc.next();// verify everything

		/*
		 * sql prepared statement to verify info, update the account balance then change
		 * cAcctID to tAcctID
		 */
		try {
			Connection conn = ConnectionUtil.getConnection();
			PreparedStatement ps = null;
			String sql = null;
			ResultSet rs = null;

			sql = "UPDATE ACCOUNT ACCTID ? WHERE USERID = ? AND WHERE ACCTID = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, tAcctID);
			ps.setString(2, userID);//aim the account pointer at another account
			ps.setString(3, cAcctID);

			ps.executeQuery();
			
			sql = "SELECT MAX (TRANSID) FROM TRANSACTIONS WHERE USERID = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, userID);
			ps.executeQuery();
			
			String amount = rs.getString("TRANSID");
			
			
			
			sql = "INSERT INTO TRANSACTIONS (TYPE, AMOUNT, TIMESTAMP) VALUE (?,?,?) WHERE "

		} catch (SQLException | IOException e) {
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
	}// end reqJoin

	static void viewAcct(Scanner sc, boolean logIN, boolean isAdmin, boolean isEMP) {
		/* print out a specific account's info */

		System.out.println("Please enter the acctID of the account you wish to view: ");
		String userID = sc.next();
		System.out.println();

		/* sql prepared statement to get account info */
		try {
			Connection con = ConnectionUtil.getConnection();
			CallableStatement cs = null;
			ResultSet rs = null;

			cs = con.prepareCall("{call GETUSERNAME(?)}");
			cs.setString(1, userID);
			rs = cs.executeQuery();

			System.out.println("The account's first name is: " + rs.next());
			System.out.println("The account's last name is: " + rs.next());
			System.out.println("The account's username is: " + rs.next());
			System.out.println("The account's password is: " + rs.next());

			rs.close();
			cs.close();

		} catch (SQLException | IOException e) {
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
	}// end viewAcct

	static void viewBal(Scanner sc, boolean logIN, boolean isAdmin, boolean isEMP) {

		System.out.println("Please enter your userID: ");
		String userID = sc.next();
		System.out.println("Please enter the ID of the account you wish to see the balance of: ");
		String acctID = sc.next();

		// SQL Prep statment to return balance of acctID

		try {
			PreparedStatement ps = null;
			Connection conn = ConnectionUtil.getConnection();
			String sql = "SELECT BALANCE FROM TRANSACTION WHERE ACCTID = ?";
			ResultSet rs = null;
			ps = conn.prepareStatement(sql);

			ps.setString(1, "ACCTID");

			// sequence and trigger keep track of balance
			rs = ps.executeQuery();
			String bal = rs.getString("balance");
			System.out.println("Your balance is: " + bal + ".");
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
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
	}// end viewBal

	static void transfer(Scanner sc, boolean logIN, boolean isAdmin, boolean isEMP) {
		/* take in acctID to transfer from, acctID to transfer to and amount */
		System.out.println("Please enter the ID of the account you want to transfer FROM: ");
		String fAcctID = sc.next();
		System.out.println("Please enter the ID of the account you want to transfer TO: ");
		String tAcctID = sc.next();
		System.out.println("Please enter the amount you want to transfer: ");
		int amt = (int) (sc.nextDouble() * 100);
		amt = java.lang.Math.abs(amt);
		String TOamount = String.valueOf(amt);
		String FROMamount = String.valueOf(-1 * amt);

		/*
		 * sql prep statement to ADD amt to tAcctID and SUBTRACT it from fAcctID then
		 * print new balances
		 */
		PreparedStatement ps = null;
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO TRANSACTION (TYPE, ACCTID, AMOUNT, TIMESTAMP) VALUES(?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, "Transfer");
			ps.setString(2, tAcctID);
			ps.setString(3, TOamount);
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, ''yy");
			String dateForm = dateFormat.format(new Date());
			ps.setString(4, dateForm);
			ps.executeQuery();

			sql = "INSERT INTO TRANSACTIONS (TYPE, ACCTID, AMOUNT, TIMESTAMP) VALUES (?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, "Transfer");
			ps.setString(2, fAcctID);
			ps.setString(3, FROMamount);
			ps.setString(4, dateForm);
			ps.executeQuery();

			// TRIGGER AND SEQUENCE CALCULATE BALANCE
		} catch (SQLException e) {
			System.out.println("SQL Derped.");

			if (isAdmin) {
				admin.menu(sc, logIN, isAdmin, isEMP);
			} else if (isEMP) {
				employee.menu(sc, logIN, isAdmin, isEMP);
			} else if (logIN) {
				menu(sc, logIN, isAdmin, isEMP);
			} else
				App.menu(sc);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}// end transfer

	static void withdraw(Scanner sc, boolean logIN, boolean isAdmin, boolean isEMP) {
		System.out.println("Please enter the ID of the account you want to withdraw from: ");
		String acctID = sc.next();
		System.out.println("Please enter the amount you want to withdraw: ");
		int amt = (int) (sc.nextDouble() * 100);
		amt = java.lang.Math.abs(amt);
		amt *= -1;// this is a withdrawal
		String amount = String.valueOf(amt);

		/* sql prep statement to SUBTRACT amt to acctID */

		PreparedStatement ps = null;
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO TRANSACTION (AMOUNT, TIMESTAMP) VALUES(?,?) WHERE ACCTID = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, amount);
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, ''yy");
			String dateForm = dateFormat.format(new Date());
			ps.setString(2, dateForm);

			// all the above to get a pretty timestamp and to not have to deal with doubles
			// eating fractions of pennies
//TRIGGER AND SEQUENCE CALCULATE BALANCE		

			if (isAdmin) {
				admin.menu(sc, logIN, isAdmin, isEMP);
			} else if (isEMP) {
				employee.menu(sc, logIN, isAdmin, isEMP);
			} else if (logIN) {
				menu(sc, logIN, isAdmin, isEMP);
			} else
				App.menu(sc);

		} catch (SQLException e) {
			System.out.println("SQL Exception");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

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
		System.out.println("Please enter the ID of the account you want to deposit into: ");
		String acctID = sc.next();
		System.out.println("Please enter the amount you want to deposit: ");
		int amt = (int) (sc.nextDouble() * 100);
		amt = java.lang.Math.abs(amt);
		String amount = String.valueOf(amt);

		/* sql prep statement to ADD amt to acctID then print new balance */

		PreparedStatement ps = null;
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO TRANSACTION (AMOUNT, TIMESTAMP) VALUES(?,?) WHERE ACCTID = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, amount);
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, ''yy");
			String dateForm = dateFormat.format(new Date());
			ps.setString(2, dateForm);

			// all the above to get a pretty timestamp and to not have to deal with doubles
			// eating fractions of pennies

			if (isAdmin) {
				admin.menu(sc, logIN, isAdmin, isEMP);
			} else if (isEMP) {
				employee.menu(sc, logIN, isAdmin, isEMP);
			} else if (logIN) {
				menu(sc, logIN, isAdmin, isEMP);
			} else
				App.menu(sc);

		} catch (SQLException e) {
			System.out.println("SQL Exception");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}// end deposit

}// end user class
