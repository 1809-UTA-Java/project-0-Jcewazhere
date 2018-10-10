package com.revature.project0;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

public class App {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		menu(sc);
	}// end main

	public static void menu(Scanner sc) {
		boolean logIN = false;
		boolean isAdmin = false;// reset flags, aka log out
		boolean isEMP = false;

		System.out.println("-----------------------------------");
		System.out.println("| Please choose:                  |");
		System.out.println("| 1.) Create Account    2.) Login |");
		System.out.println("-----------------------------------");

		String scan = sc.next();

		switch (scan) {
		case "1":
			createAccount(sc);
			break;
		case "2":
			login(sc, logIN, isAdmin, isEMP);
			break;
		default:
			System.out.println("Please enter either a 1 or a 2.");
			sc.nextLine();// clear scanner
			menu(sc);
		}
	}// end menu

	static void createAccount(Scanner sc) {

		/*
		 * use prepared sql statments to create an account by creating a new row, taking
		 * input for FName, LName, zip, DoB, username, and password. Use
		 * sequence/trigger to create unique userID and 2 unique acctID fields
		 */
		String userID = null;

		try {
			Connection con = ConnectionUtil.getConnection();
			CallableStatement cs = null;
			sc.nextLine();// clear scanner
			// FNAME, LNAME, ZIP, DOB, USERNAME, PASS, STATUS
			cs = con.prepareCall("{call MAKEUSER(?,?,?,?,?,?,?)}");
			System.out.println("Please enter your first name: ");
			String FName = sc.nextLine();
			cs.setString(1, FName);
			System.out.println("Please enter your last name: ");
			cs.setString(2, sc.nextLine());
			System.out.println("Please enter your zip code: ");
			cs.setString(3, sc.nextLine());
			System.out.println("Please enter your date of birth:");
			cs.setString(4, sc.nextLine());
			System.out.println("Please create a username:");
			cs.setString(5, sc.nextLine());
			System.out.println("Please create a password");
			cs.setString(6, sc.nextLine());
			cs.setString(7, "inactive");
			cs.execute();

			// SETUP ACCOUNTS table
			PreparedStatement ps = null;
			try (Connection conn = ConnectionUtil.getConnection()) {
				String sql = "SELECT USERID FROM USERS WHERE FNAME = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, FName);

				ResultSet rs = ps.executeQuery();
				userID = rs.getString("USERID");
				rs.next();//ResultSet.next is called right here :(

				sql = "INSERT INTO ACCOUNT (USERID, TYPE) VALUES (?, CHECKING)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, userID);
				ps.executeQuery();
				sql = "INSERT INTO ACCOUNT (USERID, TYPE) VALUES (?, SAVING)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, userID);
				ps.executeQuery();

				rs.close();
				ps.close();

				cs.close();
				con.close();
				sc.nextLine();
				menu(sc);
			} catch (SQLException e) {
				System.err.println("SQLException: " + e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			System.out.println();
		}

	}// end createAccount

	private static void login(Scanner sc, boolean logIN, boolean isAdmin, boolean isEMP) {
		String scan = "";
		String name = "";
		String pas = "";
		String passTest = null;
		String usernameTest = null;
		sc.nextLine();// reset scanner

		System.out.println("-------------------------");
		System.out.println("|U.) Login as User.     |");
		System.out.println("|E.) Login as Employee. |");
		System.out.println("|A.) Login as Admin.    |");
		System.out.println("-------------------------");

		scan = sc.nextLine();

		PreparedStatement ps = null;
		if (scan.equalsIgnoreCase("U")) {
			/* login as user with username and password from table */
			System.out.println("Please enter your username:");
			name = sc.nextLine();
			System.out.println("Please enter your password:");
			pas = sc.nextLine();
			try (Connection conn = ConnectionUtil.getConnection()) {
				String sql = "SELECT USERNAME, PASS FROM USERS WHERE USERNAME = ?";

				ps = conn.prepareStatement(sql);
				ps.setString(1, name);
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					usernameTest = rs.getString("USERNAME");
					passTest = rs.getString("PASS");
				}

				rs.close();
				ps.close();

				if (pas == passTest) {
					logIN = true;
					user.menu(sc, logIN, isAdmin, isEMP);
				} else {
					System.out.println("Invalid username or password.");
					login(sc, logIN, isAdmin, isEMP);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else if (scan.equalsIgnoreCase("E")) {
			/* Login as employee with pre-set username and password */
			String empUN = "employee";
			String empPS = "12345";

			System.out.println("Please enter your username: ");
			name = sc.next();
			System.out.println("Please enter your password: ");
			pas = sc.next();

			if (name.equals(empUN) && pas.equals(empPS)) {
				logIN = true;
				isEMP = true;
				employee.menu(sc, logIN, isAdmin, isEMP);
			} else {
				System.out.println("Invalid username or password.");
				login(sc, logIN, isAdmin, isEMP);
			}

		} else if (scan.equalsIgnoreCase("A")) {
			/* login as admin with pre-set username and password */
			String adminUN = "admin";
			String adminPS = "12345";

			System.out.println("Please enter your username: ");
			name = sc.next();
			System.out.println("Please enter your password: ");
			pas = sc.next();

			if (name.equals(adminUN) && pas.equals(adminPS)) {
				logIN = true;
				isEMP = true;// set flags
				isAdmin = true;

				admin.menu(sc, logIN, isAdmin, isEMP);
			} else {
				System.out.println("Invalid username or password.");
				login(sc, logIN, isAdmin, isEMP);
			}

		} else {
			System.out.println("Please enter U, E, or A to choose.");
			login(sc, logIN, isAdmin, isEMP);
		}
	}// end login

}// end app class