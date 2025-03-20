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

}