package backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Scanner;
import java.math.BigInteger;


import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

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
	public void createAUser(String user, String password) throws FileNotFoundException {
		if (canCreateAUser(user)) {
			try {
				FileWriter myWriter = new FileWriter("users.txt", true);
				String hash = hashPassword(password);
				myWriter.write(user.toLowerCase() + " " + hash + "\n");
				myWriter.close();
				LibraryModel newLibrary = new LibraryModel(user);
				accountLibraries.add(newLibrary);
				System.out.println("User successfully created.");
			} catch (IOException e) {
				System.out.println("An error occurred.");
			    e.printStackTrace();
			}
		}
	}
	public boolean canLogIn(String user, String password) throws FileNotFoundException{
		File myFile = new File("users.txt");
		Scanner myReader = new Scanner(myFile);
		while(myReader.hasNextLine()) {
			String line = myReader.nextLine();
			String username = line.split(" ")[0];
			String pass = line.split(" ")[1];
			if(username.equals(user.toLowerCase())) {
				String hash = hashPassword(password);
				if(pass.equals(hash)) {
					myReader.close();
					return true;
				}
			}
		}
		myReader.close();
		return false;
	}
	
	public void openLibrary(String user, String password) throws FileNotFoundException{
		for(int i = 0; i < accountLibraries.size(); i++) {
			//System.out.println(accountLibraries.get(i).getUsername());
			// If you want a welcome message, make it part of view.
			if (canLogIn(user, password)) {
				if(accountLibraries.get(i).checkIfCorrectUsername(user)) {
					View newView = new View(accountLibraries.get(i));
				}
			}
		}
	}
	
	private String hashPassword(String password) {
		try {
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