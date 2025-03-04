package backend;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlaylistTEST {

	@Test
	void test_01() {
		PlayList playlist = new PlayList("hello");
		assertEquals(playlist.getPlayListName(), "hello");
	}
	
	@Test
	void test_02() {
		PlayList playlist = new PlayList("hello");
		Song song1 = new Song("Dav", "Davran", "dav1", "pop", "2005");
		Song song2 = new Song("Davf", "Davranfa", "davasf1", "faspop", "20205");
		Song song3 = new Song("Dav", "Davran", "dav1", "pop", "2005");
		Song song4 = new Song("Dav", "Davranbek", "dav1", "pop", "2005");
	    Song song5 = new Song("Mm", "Davran", "another1", "pop", "2005");
		playlist.addSong(song1);
		assertFalse(playlist.canAddSongToList(song3));
		assertTrue(playlist.canAddSongToList(song2));
		assertTrue(playlist.canAddSongToList(song4));
	    assertTrue(playlist.canAddSongToList(song5));
	}
	
	@Test
	void test_03() {
		PlayList playlist = new PlayList("hello");
		PlayList copy = new PlayList(playlist);
		assertEquals(copy.getPlayListName(), "hello");
	}
	
	@Test
	void test_04() {
		PlayList playlist = new PlayList("hello");
		Song song1 = new Song("Dav", "Davran", "dav1", "pop", "2005");
		playlist.addSong(song1);
		assertTrue(playlist.canRemoveSong("Dav", "Davran"));
		assertFalse(playlist.canRemoveSong("Davf", "Davranbek"));
		assertFalse(playlist.canRemoveSong("Dav", "Davranbek"));
		assertFalse(playlist.canRemoveSong("Davf", "Davran"));
	}
	
	@Test
	void test_05() {
		PlayList playlist = new PlayList("Test Playlist");
	    Song song1 = new Song("Dav", "Davran", "dav1", "Pop", "2005");
	    Song song2 = new Song("Be", "Ben", "eve1", "Rock", "2010");
	    Song song3 = new Song("Mark", "Markson", "mark1", "Jazz", "2015");
	    playlist.addSong(song1);
	    playlist.addSong(song2);
	    playlist.addSong(song3);
	    assertTrue(playlist.canRemoveSong("Dav", "Davran"));
	    playlist.removeSong("Dav", "Davran");
	    assertFalse(playlist.canRemoveSong("Dav", "Davran"));
	    assertEquals(2, playlist.getSongList().size());
	    assertTrue(playlist.canRemoveSong("Mark", "Markson"));
	    playlist.removeSong("Mark", "Markson");
	    assertFalse(playlist.canRemoveSong("Mark", "Markson"));
	    assertEquals(1, playlist.getSongList().size());
	    assertTrue(playlist.canRemoveSong("Be", "Ben"));
	    playlist.removeSong("X", "Y");
	    assertEquals(1, playlist.getSongList().size());
	    playlist.removeSong("Be", "WrongArtist");
	    assertEquals(1, playlist.getSongList().size());
	    playlist.removeSong("Wrong", "Ben");
	    assertEquals(1, playlist.getSongList().size());
	    playlist.removeSong("BE", "BEN");
	    assertFalse(playlist.canRemoveSong("Be", "Ben"));
	    assertEquals(0, playlist.getSongList().size());
        
	}
	

}