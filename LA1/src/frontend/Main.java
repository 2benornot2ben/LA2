
package frontend;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import backend.*;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		// Apparently, this handles if the file doesn't exist too. Wow.
		PrintWriter writer = new PrintWriter("users.txt");
		writer.print("");
		writer.close();
		
		Scanner getInput = new Scanner(System.in);
		AccountStorage storage = new AccountStorage();
		boolean running = true;
		while(running) {
			System.out.println("What do you want to do?");
			System.out.println("1) Sign up (Create a new accaunt)");
			System.out.println("2) Sign in (Log in to excisting accaunt)");
			System.out.print("Enter an option you want: ");
			String option = getInput.nextLine();
			if(option.split(" ")[0].equals("1")) {
				System.out.println("You are about to create a new account.");
				System.out.println("(Case Sensitive!)");
				System.out.print("Enter a username: ");
				String username = getInput.nextLine();
				System.out.print("Enter a passport: ");
				String passport = getInput.nextLine();
				if(storage.canCreateAUser(username)) {
					storage.createAUser(username, passport);
					System.out.println("Logging in to your account.");
					storage.openLibrary(username, passport);
					//open the library
				} else {
					System.out.println("This username is already taken.");
				}
			} else if (option.split(" ")[0].equals("2")) {
				System.out.println("You are about to log into an account.");
				System.out.print("Enter a username: ");
				String username = getInput.nextLine();
				System.out.print("Enter a passport: ");
				String passport = getInput.nextLine();
				if(storage.canLogIn(username, passport)) {
					//open library
					System.out.println("Logging in to your account.");
					storage.openLibrary(username, passport);
				} else {
					System.out.println("The username/passport is incorrect");
				}
			} else {
				System.out.println("Invalid input.");
			}
		}
	}
	
}
