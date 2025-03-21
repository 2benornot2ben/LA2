package backend;

import static org.junit.jupiter.api.Assertions.*;


import java.io.*;
import java.util.*;

import org.junit.jupiter.api.Test;

class AccountStorageTest {

	@Test
    public void test_01() throws IOException {
        File file = new File("users.txt");
        FileWriter myWriter = new FileWriter(file);
        myWriter.write("");
        AccountStorage storage = new AccountStorage();
        assertTrue(storage.canCreateAUser("davr"));
        file.delete();
    }
	
	@Test
    public void test_02() throws IOException {
        File file = new File("users.txt");
        try (FileWriter myWriter = new FileWriter(file)) {
            myWriter.write("davr davr");
        }
        AccountStorage storage = new AccountStorage();
        assertFalse(storage.canCreateAUser("davr"));
        assertTrue(storage.canCreateAUser("ben"));
        file.delete();
    }
	
	@Test
    public void test_03() throws IOException {
        File file = new File("users.txt");
        try (FileWriter myWriter = new FileWriter(file)) {
            myWriter.write("");
        }
        AccountStorage storage = new AccountStorage();
        storage.createAUser("davrr", "a");
        assertFalse(storage.canCreateAUser("davrr"));
        try (Scanner scanner = new Scanner(file)) {
            String line = scanner.nextLine();
            assertEquals("davrr 0cc175b9c0f1b6a831c399e269772661", line);
        }
        storage.createAUser("davrr", "a");
        file.delete();
    }
	
	@Test
    public void test_04() throws IOException {
        File file = new File("users.txt");
        AccountStorage storage = new AccountStorage();
        
        try (FileWriter myWriter = new FileWriter(file)) {
        	assertFalse(storage.canLogIn("davrr", "a"));
            myWriter.write("davrr 0cc175b9c0f1b6a831c399e269772661");
        }
        assertTrue(storage.canLogIn("davrr", "a"));
        assertFalse(storage.canLogIn("davr", "a"));
        assertFalse(storage.canLogIn("davrr", "b"));
        file.delete();
    }
	
	@Test
    public void test_05() throws IOException {
        File file = new File("users.txt");
        try (FileWriter myWriter = new FileWriter(file)) {
            myWriter.write("");
        }
        AccountStorage storage = new AccountStorage();
        storage.createAUser("davrr", "a");
        LibraryModel library = new LibraryModel("davrr");
        
        storage.openLibrary("davrr", "a"); 
        file.delete();
        
    }
	
	@Test
    public void test_06() throws IOException {
		File file = new File("users.txt");
        try (FileWriter myWriter = new FileWriter(file)) {
            myWriter.write("");
        }
        AccountStorage storage = new AccountStorage();
        storage.createAUser("davrr", "a");
        LibraryModel library = new LibraryModel("davrr");
        
        storage.openLibrary("davrr", "b"); 
        file.delete();
    }
}
