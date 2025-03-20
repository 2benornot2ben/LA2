package backend;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AlbumTEST {

	@Test
	void test_01() {
		Album initAlbum = new Album("Flyers", "Ben", "Metal", "2025");
		assertEquals(initAlbum.getAlbumName(), "Flyers");
		assertEquals(initAlbum.getArtist(), "Ben");
		assertEquals(initAlbum.getGenre(), "Metal");
		assertEquals(initAlbum.getYear(), "2025");
		assertEquals(initAlbum.getPrintFormatted(), "Flyers, Ben, Metal, 2025");
	}
	
	@Test
	void test_02() {
		Album initAlbum1 = new Album("Flyers", "Ben", "Metal", "2025");
		Song initSong = new Song("Flying", "Ben", "Flyers", "Metal", "2025");
		initAlbum1.addSong(initSong);
		assertTrue(initAlbum1.getSongList().get(0).equals(initSong));
		// Actually gonna double dip while I have it
		Album initAlbum2 = new Album(initAlbum1);
		assertTrue(initAlbum2.getSongList().get(0).equals(initSong));
	}
	
	@Test
	void test_03() {
		Album initAlbum1 = new Album("1", "2", "3", "4");
		Song initSong1 = new Song("1", "2", "3", "4", "5");
		Song initSong2 = new Song("2", "3", "4", "5", "6");
		Song initSong3 = new Song("1", "2", "3", "4", "5");
		initAlbum1.addSong(initSong1);
		initAlbum1.addSong(initSong2);
		initAlbum1.addSong(initSong3); // This should fail.
		assertEquals(initAlbum1.getSongList().size(), 2);
		initAlbum1.removeSong("1", "2");
		assertEquals(initAlbum1.getSongList().size(), 1);
		initAlbum1.removeSong("1", "2"); // This should fail.
		assertEquals(initAlbum1.getSongList().size(), 1);
		initAlbum1.removeSong("2", "hi"); // Also will fail.
		assertEquals(initAlbum1.getSongList().size(), 1);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	void test_04() {
		Album initAlbum1 = new Album("1", "2", "3", "4");
		Album initAlbum2 = new Album("2", "2", "3", "4");
		Album initAlbum3 = new Album("1", "banana", "3", "4");
		Album initAlbum4 = new Album("1", "2", "", "4");
		Album initAlbum5 = new Album("1", "2", "3", "gndbfhtfynbdtyhngfdrthyjkmhngt");
		Album initAlbum6 = new Album("1", "2", "3", "4");
		Album initAlbum7 = new Album(initAlbum6);
		Song initSong = new Song("1", "2", "3", "4", "5");
		initAlbum7.addSong(initSong);
		assertTrue(initAlbum1.equals(initAlbum1));
		assertFalse(initAlbum1.equals(initAlbum2));
		assertFalse(initAlbum1.equals(initAlbum3));
		assertFalse(initAlbum1.equals(initAlbum4));
		assertFalse(initAlbum1.equals(initAlbum5));
		assertTrue(initAlbum1.equals(initAlbum6));
		assertTrue(initAlbum1.equals(initAlbum7));
		assertFalse(initAlbum1.equals(initSong));
		assertFalse(initAlbum1.equals(null));
	}
	
	@Test
	void test_05() {
		Album initAlbum1 = new Album("1", "2", "3", "4");
		Album initAlbum2 = new Album("1", "2", "3", "4");
		Album initAlbum3 = new Album("1", "banana", "3", "4");
		assertEquals((initAlbum1.hashCode() == initAlbum2.hashCode()), initAlbum1.equals(initAlbum2));
		assertEquals((initAlbum1.hashCode() == initAlbum3.hashCode()), initAlbum1.equals(initAlbum3));
		assertEquals((initAlbum2.hashCode() == initAlbum3.hashCode()), initAlbum2.equals(initAlbum3));
	}
}