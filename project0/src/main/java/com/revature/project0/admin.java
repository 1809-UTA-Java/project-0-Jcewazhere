package com.revature.project0;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class admin {
	Scanner sc = new Scanner(System.in);
	boolean logIN;
	boolean isAdmin;
	boolean isEMP;
	String scan;

	public admin(Scanner sc, boolean logIN, boolean isAdmin, boolean isEMP) {
		this.sc = sc;
		this.logIN = logIN;
		this.isAdmin = isAdmin;
		this.isEMP = isEMP;
	}// end constructor

	static void menu(Scanner sc, boolean logIN, boolean isAdmin, boolean isEMP) {
		sc.nextLine();// clear scanner
		for (int i = 0; i < 20; i++)
			System.out.println(); // clear console the easy way
		System.out.println("---------------------------------------------------------------------");
		System.out.println("|1.) Deposit           A.) View all accounts     Z.) Delete account |");
		System.out.println("|2.) Withdraw          B.) Set Active Status                        |");
		System.out.println("|3.) Transfer          C.) Process Join                             |");
		System.out.println("|4.) View Balance                                                   |");
		System.out.println("|5.) View Account Info                                              |");
		System.out.println("|6.) Request joining accounts                                       |");
		System.out.println("|7.) Set Username/Password                                          |");
		System.out.println("|X.) Logout                                                         |");
		System.out.println("---------------------------------------------------------------------");

		String scan = sc.next();
		if (isAdmin) {
			if (scan.equalsIgnoreCase("Z")) {
				deleteAcct(sc, logIN, isAdmin, isEMP);
			} else if (scan.equalsIgnoreCase("A")) {
				employee.viewAccts(sc, logIN, isAdmin, isEMP);
			} else if (scan.equalsIgnoreCase("B")) {
				employee.setActive(sc, logIN, isAdmin, isEMP);
			} else if (scan.equalsIgnoreCase("C")) {
				employee.processJoin(sc, logIN, isAdmin, isEMP);
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
			System.out.println("You must be logged in as an adminstrator to use this menu");
			App.menu(sc);
		}
	}// end menu

	private static void deleteAcct(Scanner sc, boolean logIN, boolean isAdmin, boolean isEMP) {
		/* delete an account */
		for (int i = 0; i < 20; i++)
			System.out.println(); // clear console the easy way
		System.out.println("Please enter the acctID of the account you wish to DELETE: ");
		String userID = sc.next();
		System.out.println("Are you sure you want to delete this account? Y/N");
		String yn = sc.next();
		if (yn.equalsIgnoreCase("Y")) {
			/* sql prep statement to cascade delete the selected account */

			try {
				Connection conn = ConnectionUtil.getConnection();
				PreparedStatement ps = null;
				String sql = null;
				sql = "DELETE FROM USERS WHERE USERID = ? CASCADE";
				ps = conn.prepareStatement(sql);
				ps.setString(1, userID);

			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			menu(sc, logIN, isAdmin, isEMP);
		}
	}// end deleteAcct
}// end class admin
