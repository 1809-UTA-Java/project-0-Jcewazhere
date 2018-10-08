package com.revature.project0;

import java.util.Scanner;
import java.sql.*;

public class App {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		menu(sc);
	}// end main

	public static void menu(Scanner sc) {
		boolean logIN = false;
		boolean isAdmin = false;// reset flags, aka log out
		boolean isEMP = false;

		System.out.println(sc.nextLine());
		// clear scanner

		/*
		 * for (int i = 0; i < 30; i++) System.out.println(); // clear console the easy
		 * way
		 */

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

	private static void createAccount(Scanner sc) {
		/*
		 * use prepared sql statments to create an account by creating a new row, taking
		 * input for FName, LName, zip, DoB, username, and password. Use
		 * sequence/trigger to create unique userID and 2 unique acctID fields
		 */
		for (int i = 0; i < 20; i++)
			System.out.println(); // clear console the easy way
	}// end createAccount

	private static void login(Scanner sc, boolean logIN, boolean isAdmin, boolean isEMP) {
		String scan = "";
		String name = "";
		String pas = "";
		sc.nextLine();// reset scanner

		for (int i = 0; i < 20; i++)
			System.out.println(); // clear console the easy way
		System.out.println("-------------------------");
		System.out.println("|U.) Login as User.     |");
		System.out.println("|E.) Login as Employee. |");
		System.out.println("|A.) Login as Admin.    |");
		System.out.println("-------------------------");

		scan = sc.next();

		if (scan.equalsIgnoreCase("U")) {
			/* login as user with username and password from table */
			if (/* login successful set to false for now */ true) {
				logIN = true;
				user.menu(sc, logIN, isAdmin, isEMP);
			} else {
				System.out.println("Invalid username or password.");
				login(sc, logIN, isAdmin, isEMP);
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
			String adminPS = "1a2b3c4d5e";

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