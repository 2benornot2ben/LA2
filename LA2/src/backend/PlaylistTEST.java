package backend;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class PlayListTEST {

	@Test
	void test_01() {
		PlayList playlist = new PlayList("hello","");
		assertEquals(playlist.getPlayListName(), "hello");
	}
	
	@Test
	void test_02() {
		PlayList playlist = new PlayList("hello","");
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
		PlayList playlist = new PlayList("hello","");
		PlayList copy = new PlayList(playlist);
		assertEquals(copy.getPlayListName(), "hello");
	}
	
	@Test
	void test_04() {
		PlayList playlist = new PlayList("hello", "");
		Song song1 = new Song("Dav", "Davran", "dav1", "pop", "2005");
		playlist.addSong(song1);
		assertTrue(playlist.canRemoveSong("Dav", "Davran"));
		assertFalse(playlist.canRemoveSong("Davf", "Davranbek"));
		assertFalse(playlist.canRemoveSong("Dav", "Davranbek"));
		assertFalse(playlist.canRemoveSong("Davf", "Davran"));
	}
	
	@Test
	void test_05() {
		PlayList playlist = new PlayList("Test Playlist", "");
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
	
	@Test
	void test_06() {
		PlayList playlist = new PlayList("playlist", "");
		PlayList playlist2 = new PlayList("playlist", "a");
		assertTrue(playlist.isUserMade());
		assertFalse(playlist2.isUserMade());
		
	}
	
	@Test
	void test_07() {
		PlayList playlist = new PlayList("playlist", "");
        Song song1 = new Song("1", "1", "1","1", "1");
        Song song2 = new Song("2", "2", "2","2", "2");
        playlist.addSong(song1);
        playlist.addSong(song2);
        ArrayList<Song> prev = playlist.getSongList();
        playlist.songShuffle();
        ArrayList<Song> after = playlist.getSongList();
        assertEquals(prev.size(), after.size());
        assertTrue(after.contains(song1));
        assertTrue(after.contains(song2));
	}
	
	@Test
	void test_08() {
		PlayList playlist = new PlayList("playlist", "a");
        Song song1 = new Song("1", "1", "1","1", "1");
        Song song2 = new Song("2", "2", "2","2", "2");
        playlist.addSong(song1);
        playlist.addSong(song2);
        ArrayList<Song> prev = playlist.getSongList();
        playlist.songShuffle();
        ArrayList<Song> after = playlist.getSongList();
        assertEquals(prev, after);
	}
	
	@Test
    public void test_09() {
        PlayList favorite = new PlayList("Favorites", "favorite");
        Song song1 = new Song("1", "1", "1","1", "1");
        song1.setRating(5);
        Song song2 = new Song("2", "2", "2","2", "2");
        song2.setRating(4);
        favorite.runSpecialModifier(song1, 0);
        assertTrue(favorite.getSongList().contains(song1));       
        favorite.runSpecialModifier(song2, 0);
        assertFalse(favorite.getSongList().contains(song2));
    }
	
	@Test
    public void test_10() {
        PlayList topRated = new PlayList("Top Rated", "topRated");
        Song song1 = new Song("1", "1", "1","1", "1");
        song1.setRating(5);
        Song song2 = new Song("2", "2", "2","2", "2");
        song2.setRating(2);
        topRated.runSpecialModifier(song1, 0);
        assertTrue(topRated.getSongList().contains(song1));
        topRated.runSpecialModifier(song2, 0);
        assertFalse(topRated.getSongList().contains(song2));
    }
	
	@Test
    public void test_11(){
        PlayList recent = new PlayList("Recent", "recent");
        Song song1 = new Song("1", "1", "1","1", "1");
        for (int i = 0; i < 12; i++) {
        	Song temp = new Song("2"+i, "2", "2","2", "2");
            recent.runSpecialModifier(temp, 1);
        }
        assertEquals(10, recent.getSongList().size());
        recent.runSpecialModifier(song1, 1);
        assertEquals(song1, recent.getSongList().get(0));
    }
	
	@Test
    public void test_12() {
        PlayList mostPlayed = new PlayList("Most Played", "mostPlayed");
        Song song1 = new Song("1", "1", "1","1", "1");
        song1.incrementPlay();
        song1.incrementPlay();
        Song song2 = new Song("2", "2", "2","2", "2");
        song2.incrementPlay();
        mostPlayed.runSpecialModifier(song1, 1);
        mostPlayed.runSpecialModifier(song2, 1); 
        ArrayList<Song> songList = mostPlayed.getSongList();
        assertEquals(song2, songList.get(1));
        assertEquals(song1, songList.get(0));
        for (int i = 0; i < 10; i++) {
        	Song temp = new Song("2"+i, "2", "2","2", "2");
            temp.incrementPlay();
            mostPlayed.runSpecialModifier(temp, 1);
        }
        assertEquals(10, mostPlayed.getSongList().size());
    }
	
	@Test
    public void test13() {
        PlayList genre = new PlayList("Playlist", "1");
        Song song1 = new Song("1", "1", "1","1", "1");
        Song song2 = new Song("2", "2", "2","2", "2");
        genre.runSpecialModifier(song1, 0);
        assertTrue(genre.getSongList().contains(song1));
        genre.runSpecialModifier(song2, 0);
        assertFalse(genre.getSongList().contains(song2));
    }
	
	@Test
    public void test14() {
        PlayList genre = new PlayList("Playlist", "");
        Song song1 = new Song("1", "1", "1","1", "1");
        genre.runSpecialModifier(song1, 0);
    }
}