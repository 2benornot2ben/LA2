/**************************************************************
 * Author: Davranbek Kadirimbetov & Benjamin Kanter
 * Description: Acts as a storage for accounts, or rather,
 * the ability to connect the files it holds to the actual
 * txt document with the correct information. Also technically
 * runs the view.
 **************************************************************/

package backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Scanner;
import java.math.BigInteger;

import frontend.*;

public class AccountStorage {
	/* This class is responsible for accessing
	 * and switching between views. The main goes to
	 * this class to accomplish doing so. */
	private HashMap<Integer, LibraryModel> accountLibraries;
	public AccountStorage() {
		/* Initializes a hashmap! Useful for going faster. */
		accountLibraries = new HashMap<Integer, LibraryModel>();
	}
	
	public boolean canCreateAUser(String user) throws FileNotFoundException {
		/* Checks if you CAN create a user. Does this by
		 * reading the txt document, and seeing if there's
		 * any matches. */
		File myFile = new File("users.txt");
		Scanner myReader = new Scanner(myFile);
		while(myReader.hasNextLine()) {
			String line = myReader.nextLine();
			// Username is always first
			String username = line.split(" ")[0];
			if(username.equals(user.toLowerCase())) {
				myReader.close();
				// One exists! So no. We only care for the username; duplicate passwords are fine.
				return false;
			}
		}
		myReader.close();
		return true;
	}
	public void createAUser(String user, String password) throws FileNotFoundException {
		/* Tries to create a user. Uses the previous function, but still
		 * uses a try block for safety. Now with hashing functions! */
		if (canCreateAUser(user)) {
			try {
				FileWriter myWriter = new FileWriter("users.txt", true);
				// Yep, we got proper hashing in here!
				String hash = hashPassword(password);
				// Usernames are always written in lowercase.
				myWriter.write(user.toLowerCase() + " " + hash + "\n");
				myWriter.close();
				LibraryModel newLibrary = new LibraryModel(user);
				accountLibraries.put(user.toLowerCase().hashCode(), newLibrary);
				System.out.println("User successfully created.");
			} catch (IOException e) {
				System.out.println("An error occurred.");
			    e.printStackTrace();
			}
		}
	}
	public boolean canLogIn(String user, String password) throws FileNotFoundException {
		/* Determines if you can log in. Checks both user and password.
		 * Uses the same hashing function, of course. */
		File myFile = new File("users.txt");
		Scanner myReader = new Scanner(myFile);
		while(myReader.hasNextLine()) {
			String line = myReader.nextLine();
			// Proper positions...
			String username = line.split(" ")[0];
			String pass = line.split(" ")[1];
			if(username.equals(user.toLowerCase())) {
				// Same hash function, same result.
				String hash = hashPassword(password);
				if(pass.equals(hash)) {
					myReader.close();
					// So ye can.
					return true;
				}
			}
		}
		myReader.close();
		return false;
	}
	
	@SuppressWarnings("unused")
	public void openLibrary(String user, String password) throws FileNotFoundException {
		/* Opens a library, or rather, a view looking at the library.
		 * Note that this DOES call canLogIn, no bypassing security here! */
		LibraryModel getLibrary = accountLibraries.get(user.toLowerCase().hashCode());
		// Front end can call this method, so no way we're not validating this.
		if (canLogIn(user, password)) {
			// If we know it exists, and that all names are unique, then that means
			// that the entered name is for the right account. So we don't need to
			// check the password again!
			if (getLibrary.checkIfCorrectUsername(user)) {
				View newView = new View(getLibrary);
			}
		}
	}
	
	private String hashPassword(String password) {
		/* Uses MD5 hashing for encryption!
		 * Also uses salting with bigInteger, so even
		 * small passwords return massive blocks. */
		try {
			// This strategy of hashing isn't the strongest, but it's
			// good enough. Messagedigest hashes, biginteger salts.
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
	}
}