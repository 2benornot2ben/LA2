package backend;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.FileNotFoundException;

class AccountStorageTEST {

	private AccountStorage holdStorage;
	
	@Before
	void setUp() throws FileNotFoundException {
		holdStorage = new AccountStorage();
	}

	@Test
	void test_01() throws FileNotFoundException {
		assertTrue(holdStorage.canCreateAUser("Ben"));
		holdStorage.createAUser("Ben", "hi");
		assertFalse(holdStorage.canCreateAUser("Ben"));
	}

}
