/**************************************************************
 * Author: Davranbek Kadirimbetov & Benjamin Kanter
 * Description: Actually what runs the program. Calls upon
 * AccountStorage for most things. Only guides logging in
 * and signing in though, aswell as initializing the file
 * used for saving login info.
 **************************************************************/

package frontend;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import backend.*;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		/* This function actually runs the program now. It doesn't actually
		 * do the main file manipulation, but it does let the user get there.
		 * Note that it doesn't know anything apart from user input and
		 * knowing where to ask about logging in. */
		PrintWriter writer = new PrintWriter("users.txt");
		writer.print("");
		writer.close();
		
		Scanner getInput = new Scanner(System.in);
		AccountStorage storage = new AccountStorage();
		boolean running = true;
		while(running) {
			System.out.println("What do you want to do?");
			System.out.println("1) Sign up (Create a new account)");
			System.out.println("2) Sign in (Log in to excisting account)");
			// This makes no sense irl, but I get if you don't want a leak. So here.
			// In real life this would be akin to wiping the entire storage.
			System.out.println("3) Exit completely (Prevents a resource leak)");
			System.out.print("Enter an option you want: ");
			String option = getInput.nextLine();
			if(option.split(" ")[0].equals("1")) {
				// This is specifically for CREATING an ACCOUNT.
				System.out.println("You are about to create a new account.");
				System.out.print("Enter a username: ");
				String username = getInput.nextLine();
				System.out.print("Enter a password: ");
				String password = getInput.nextLine();
				// Doesn't care for capitalization on your username.
				if(storage.canCreateAUser(username)) {
					storage.createAUser(username, password);
					System.out.println("Logging in to your account.");
					// Auto log-in, because every site does that.
					storage.openLibrary(username, password);
				} else {
					System.out.println("This username is already taken.");
				}
			} else if (option.split(" ")[0].equals("2")) {
				// This is specifically for LOGGING IN.
				System.out.println("You are about to log into an account.");
				System.out.print("Enter a username: ");
				String username = getInput.nextLine();
				System.out.print("Enter a password: ");
				String password = getInput.nextLine();
				// Still doesn't care for username capitalization.
				if(storage.canLogIn(username, password)) {
					System.out.println("Logging in to your account.");
					// Note that the program here doesn't progress past this line until
					// AFTER you use exit on your library. Same for the one above.
					storage.openLibrary(username, password);
				} else {
					System.out.println("The username/password is incorrect");
				}
			} else if (option.split(" ")[0].equals("3")) {
				// This would NOT EXIST in real life. This is just so you
				// can avoid a resource leak!
				running = false;
				getInput.close();
			} else {
				System.out.println("Invalid input.");
			}
		}
	}
	
}