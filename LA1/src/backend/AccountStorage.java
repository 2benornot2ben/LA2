package backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import frontend.*;

public class AccountStorage {
	private ArrayList<LibraryModel> accountLibraries;
	public AccountStorage() {
		accountLibraries = new ArrayList<LibraryModel>();
	}
	
	public boolean canCreateAUser(String user) throws FileNotFoundException{
		File myFile = new File("users.txt");
		Scanner myReader = new Scanner(myFile);
		while(myReader.hasNextLine()) {
			String line = myReader.nextLine();
			String username = line.split(" ")[0];
			if(username.equals(user.toLowerCase())) {
				myReader.close();
				return false;
			}
		}
		myReader.close();
		return true;
	}
	public void createAUser(String user, String passport) {
		try {
			FileWriter myWriter = new FileWriter("users.txt");
			myWriter.write(user.toLowerCase() + " " + passport);
			myWriter.close();
			LibraryModel newLibrary = new LibraryModel(user);
			accountLibraries.add(newLibrary);
			System.out.println("User successfully created.");
		} catch (IOException e) {
			System.out.println("An error occurred.");
		    e.printStackTrace();
		}
	}
	public boolean canLogIn(String user, String passport) throws FileNotFoundException{
		File myFile = new File("users.txt");
		Scanner myReader = new Scanner(myFile);
		while(myReader.hasNextLine()) {
			String line = myReader.nextLine();
			String username = line.split(" ")[0];
			String pass = line.split(" ")[1];
			if(username.equals(user.toLowerCase())) {
				if(pass.equals(passport)) {
					myReader.close();
					return true;
				}
			}
		}
		myReader.close();
		return false;
	}
	
	public void openLibrary(String user, String passport) throws FileNotFoundException{
		for(int i = 0; i < accountLibraries.size(); i++) {
			//System.out.println(accountLibraries.get(i).getUsername());
			// If you want a welcome message, make it part of view.
			if (canLogIn(user, passport)) {
				if(accountLibraries.get(i).checkIfCorrectUsername(user)) {
					View newView = new View(accountLibraries.get(i));
				}
			}
		}
	}
}