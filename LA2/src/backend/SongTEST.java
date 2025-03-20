package backend;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SongTEST {

	@Test
	void test_01() {
		Song initSong = new Song("Songname", "Artist", "Albumname", "Genre", "Year");
		assertEquals(initSong.getSongName(), "Songname");
		assertEquals(initSong.getArtist(), "Artist");
		assertEquals(initSong.getAlbumName(), "Albumname");
		assertEquals(initSong.getGenre(), "Genre");
		assertEquals(initSong.getYear(), "Year");
	}
	
	@Test
	void test_02() {
		Song initSong = new Song("Songname", "Artist", "Albumname", "Genre", "Year");
		initSong.favorite();
		assertEquals(initSong.getRating(), 0);
		assertTrue(initSong.getFavorited());
		initSong.unfavorite();
		assertFalse(initSong.getFavorited());
	}
	
	@Test
	void test_03() {
		Song initSong = new Song("Songname", "Artist", "Albumname", "Genre", "Year");
		assertEquals(initSong.getRating(), 0);
		initSong.setRating(4);
		assertEquals(initSong.getRating(), 4);
		assertFalse(initSong.getFavorited());
		initSong.setRating(5);
		assertEquals(initSong.getRating(), 5);
		assertTrue(initSong.getFavorited());
	}
	
	@Test
	void test_04() {
		Song initSong = new Song("1", "2", "3", "4", "");
		assertEquals(initSong.getArtist(), "2");
		assertEquals(initSong.getYear(), "");
		initSong.setRating(-420);
		assertEquals(initSong.getRating(), 1);
		assertFalse(initSong.getFavorited());
		initSong.setRating(72157195);
		assertEquals(initSong.getRating(), 5);
		assertTrue(initSong.getFavorited());
	}
	
	@Test
	void test_05() {
		Song initSong = new Song("Hello", "there you", "lovely people!", "Don't print this", "or this!");
		assertEquals(initSong.getPrintFormatted(), "Hello, there you, lovely people!");
	}
	
	@Test
	void test_06() {
		Song initSong = new Song("Songname", "Artist", "Albumname", "Genre", "Year");
		initSong.setRating(3);
		Song duplicatedSong = new Song(initSong);
		assertEquals(duplicatedSong.getSongName(), "Songname");
		assertEquals(duplicatedSong.getArtist(), "Artist");
		assertEquals(duplicatedSong.getAlbumName(), "Albumname");
		assertEquals(duplicatedSong.getGenre(), "Genre");
		assertEquals(duplicatedSong.getYear(), "Year");
		assertEquals(duplicatedSong.getRating(), 3);
		initSong.setRating(5);
		assertEquals(duplicatedSong.getRating(), 3);
		assertFalse(duplicatedSong.getFavorited());
		assertTrue(initSong.getFavorited());
	}
	
	@Test
	void test_07() {
		Song initSong1 = new Song("Rock", "Paper", "Scissors", "Black Hole", "Shoot");
		Song initSong2 = new Song("Rock", "Paper", "Scissors", "Black Hole", "Shoot");
		Song initSong3 = new Song("Rock", "Paper", "Scissors", "Black Hole", "Shoet");
		Song initSong4 = new Song(initSong2);
		Song initSong5 = new Song(initSong2);
		assertTrue(initSong1.equals(initSong1));
		assertTrue(initSong1.equals(initSong2));
		assertFalse(initSong1.equals(initSong3));
		assertTrue(initSong1.equals(initSong4));
		assertTrue(initSong1.equals(initSong5));
		assertFalse(initSong1.equals(null));
	}
	
	@SuppressWarnings("unlikely-arg-type") // lmao i love that it tells you that
	@Test
	void test_08() {
		Song initSong1 = new Song("1", "2", "3", "4", "5");
		Song initSong2 = new Song("2", "2", "3", "4", "5");
		Song initSong3 = new Song("1", "banana", "3", "4", "5");
		Song initSong4 = new Song("1", "2", "", "4", "5");
		Song initSong5 = new Song("1", "2", "3", "gndbfhtfynbdtyhngfdrthyjkmhngt", "5");
		Song initSong6 = new Song("1", "2", "3", "4", "five");
		Album initAlbum = new Album("1", "2", "3", "4");
		assertFalse(initSong1.equals(initSong2));
		assertFalse(initSong1.equals(initSong3));
		assertFalse(initSong1.equals(initSong4));
		assertFalse(initSong1.equals(initSong5));
		assertFalse(initSong1.equals(initSong6));
		assertFalse(initSong1.equals(initAlbum));
	}

}