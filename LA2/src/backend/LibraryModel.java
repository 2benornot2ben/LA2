/**************************************************************
 * Author: Davranbek Kadirimbetov & Benjamin Kanter
 * Description: Acts as a user's personal library, with the
 * ability to add and remove songs and albums from itself,
 * as well as handling most functions of a library (searching,
 * rating, making a playlist, etc).
 **************************************************************/

package backend;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class LibraryModel {
	/* This class allows everything backend-related to happen,
	 * as the view goes to the library for literally everything.
	 * Even things related to the database. */
	private String username;
	private ArrayList<Song> songList;
	private ArrayList<Album> albumList;
	private ArrayList<PlayList> playListList;
	private ArrayList<String> artistList;
	private MusicStore database;
	public LibraryModel(String username) throws FileNotFoundException {
		/* Initializes everything. Basic. */
		this.songList = new ArrayList<Song>();
		this.albumList = new ArrayList<Album>();
		this.playListList = new ArrayList<PlayList>();
		this.artistList = new ArrayList<String>();
		this.database = new MusicStore();
		this.username = username.toLowerCase();
		
		// These are the 4 special playlists. Past this point, the only
		// special playlists would be genre-based ones.
		playListList.add(new PlayList("favorites", "favorite"));
		playListList.add(new PlayList("top rated", "topRated"));
		playListList.add(new PlayList("recent", "recent"));
		playListList.add(new PlayList("most played", "mostPlayed"));
	}
	
	public ArrayList<Song> searchByIndicatorSong(String input, String category, String indicator, boolean precise) {
		/* Searches for songs based on its many, many inputs.
		 * input is the name/author, category is where to search, indicator is
		 * if its by name or author, precise is if it should return everything
		 * containing the name or just if it's equal to it.
		 * This does the job of a lot of different searching functions. */
		ArrayList<Song> songs;
		if (category.equals("musicstore")) {
			songs = database.getSongs();
		} else {
			// This is itself, basically.
			songs = new ArrayList<Song>();
		    for (Song song : this.songList) {
		        songs.add(new Song(song));
		    }
		}
		return indicatorSongAdder(input, indicator, precise, songs);
	}
	
	public ArrayList<Album> searchByIndicatorAlbum(String input, String category, String indicator, boolean precise) {
		/* Searches for albums based on its many, many inputs.
		 * input is the name/author, category is where to search, indicator is
		 * if its by name or author, precise is if it should return everything
		 * containing the name or just if it's equal to it.
		 * This does the job of a lot of different searching functions. */
		ArrayList<Album> albums;
		if (category.equals("musicstore")) {
			albums = database.getAlbums();
		} else {
			// This is itself, basically.
			albums = new ArrayList<Album>();
		    for (Album album : this.albumList) {
		        albums.add(new Album(album));
		    }
		}
		return indicatorAlbumAdder(input, indicator, precise, albums);
	}
	
	public PlayList searchByNamePlayList(String name) {
		/* Searches for playlists based on just it's name.
		 * No extra inputs here, it's all we need it for:
		 * Note that it only returns ones which are equal. */
		PlayList result = new PlayList("",  "");
		for (int i = 0; i < playListList.size(); i++) {
			if (playListList.get(i).getPlayListName().toLowerCase().equals(name.toLowerCase())) {
				// No duplicates, so we can just break out immediately.
				result = new PlayList(playListList.get(i));
				break;
			}
		}
		return result;
	}
	
	public boolean checkAlbum(String title, String artist) {
		/* Checks to see if such an album with title
		 * and artist exists. */
		for(int i = 0; i < albumList.size(); i++) {
			if(albumList.get(i).getAlbumName().toLowerCase().equals(title.toLowerCase()) && albumList.get(i).getArtist().toLowerCase().equals(artist.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean addSongToPlaylist(String playlistName, Song song) {
		/* Adds a song to a playlist, using an actual song object.
		 * Note that addSong handles encapsulation, so this is fine. */
		for (int i = 0; i < playListList.size(); i++) {
			if (playListList.get(i).getPlayListName().toLowerCase().equals(playlistName.toLowerCase())) {
				// If we've found it, then we can stop iterating. Both results from here end the program.
				if (playListList.get(i).canAddSongToList(song) && playListList.get(i).isUserMade()) {
					playListList.get(i).addSong(song);
					return true; // ("Success")
				}
				else {
					return false;
				}
			}
		}
		return false;
	}
	
	public boolean removeSongFromPlaylist(String playlistName, String title, String artist) {
		/* Adds a song to a playlist, using an song's name an artist. */
		for (int i = 0; i < playListList.size(); i ++) {
			if (playListList.get(i).getPlayListName().toLowerCase().equals(playlistName.toLowerCase())) {
				if(playListList.get(i).canRemoveSong(title, artist) && playListList.get(i).isUserMade()) {
					// If we removed a song, no reason to remove 2; we're done.
					playListList.get(i).removeSong(title, artist);
					return true; // ("Success")
				}
			}
		}
		return false;
	}
	
	public boolean canAddSongToList(Song song) {
		/* This method checks if you CAN add a song to a list; it doesn't do anything else. */
		for (int i = 0; i < songList.size(); i++) {
			if (songList.get(i).getSongName().equals(song.getSongName()) && songList.get(i).getArtist().equals(song.getArtist())) {
				// We found it in there! So uh, no.
				return false;
			}
		}
		return true;
	}
	
	public boolean canRemoveSong(String title, String artist) {
		/* This method checks if you CAN remove a song from the library; it doesn't do anything else. */
		for (int i = 0; i < songList.size(); i++) {
			if (songList.get(i).getSongName().toLowerCase().equals(title.toLowerCase()) && songList.get(i).getArtist().toLowerCase().equals(artist.toLowerCase())) {
				// We found it in there! So, true
				return true;
			}
		}
		return false;
	}
	
	public boolean canRemoveAlbum(String title, String artist) {
		/* This method checks if you CAN remove a album from the library; it doesn't do anything else. */
		for (int i = 0; i < albumList.size(); i++) {
			if (albumList.get(i).getAlbumName().toLowerCase().equals(title.toLowerCase()) && albumList.get(i).getArtist().toLowerCase().equals(artist.toLowerCase())) {
				// We found it in there! So, true
				return true;
			}
		}
		return false;
	}
	
	
	public void removeSong(String title, String artist) {
		/* Removes a song from the songlist. If it's the last
		 * song of a album/artist, it removes them too. It also
		 * removes copies of the song just about everywhere
		 * library-related. */
		for (int i = 0; i < songList.size(); i++) {
			if (songList.get(i).getSongName().toLowerCase().equals(title.toLowerCase()) && songList.get(i).getArtist().toLowerCase().equals(artist.toLowerCase())) {
				String album = songList.get(i).getAlbumName();
				Song songSave = songList.get(i);
				songList.remove(i);
				// Runs specialFunctions with it set to false, so to say it was removed.
				runSpecialFunctions(songSave, false, 0);
				// All "special" functions are private and have power over all types of playlists.
				removeSongFromPlaylistSpecial(title, artist);
				// Check all albums...
				for (int j = 0; j < albumList.size(); j++) {
					if (albumList.get(j).getAlbumName().toLowerCase().equals(album.toLowerCase()) && albumList.get(j).getArtist().toLowerCase().equals(artist.toLowerCase())) {
						albumList.get(j).removeSong(title, artist);
						// It's empty! Remove it!
						if(albumList.get(j).getSongList().size() == 0) {
							removeAlbum(album, artist);
						}
					}
				}
			}
		}
		// This does the thing with albums, but for artists.
		removeUnusedArtists();
	}
	
	public void removeAlbum(String title, String artist) {
		/* This removes a given album, along with it's songs,
		 * and maybe it's artist. */
		for (int i = 0; i < albumList.size(); i++) {
			if (albumList.get(i).getAlbumName().toLowerCase().equals(title.toLowerCase()) && albumList.get(i).getArtist().toLowerCase().equals(artist.toLowerCase())) {
				for(int j = songList.size()-1; j > -1; j--) {
					// Iteratively remove every song...
					if(songList.get(j).getAlbumName().toLowerCase().equals(title.toLowerCase())) {
						// First from all playlists...
						for(int k = 0; k < playListList.size(); k++) {
							removeSongFromPlaylistSpecial(songList.get(j).getSongName(), artist);
						}
						// And then from itself.
						Song songSave = songList.get(i);
						songList.remove(j);
						// Oh, and then from special functions, so they can keep track.
						runSpecialFunctions(songSave, false, 0);
					}
				}
				// It's empty now; goodbye.
				albumList.remove(i);
			}
		}
		// This checks to see if the artist is unused now.
		removeUnusedArtists();
	}
	
	public boolean canAddAlbumToList(Album album, int songNumber) {
		/* This method checks if you CAN add an album to a list; it doesn't do anything else. */
		for (int i = 0; i < albumList.size(); i++) {
			if (albumList.get(i).getAlbumName().equals(album.getAlbumName()) && albumList.get(i).getArtist().equals(album.getArtist())) {
				// We found it in there! So uh, no.
				if(songNumber == albumList.get(i).getSongList().size()) {
					return false;
					
				}
			}
		}
		return true;
	}
	
	public void addSongToList(Song song) {
		/* Adds a song to the library. */
		// This cannot happen under normal usage, but it would break
		// without this check if it somehow did.
		if (!songList.contains(song)) {
			songList.add(new Song(song));
			runSpecialFunctions(song, true, 0);
		}
		// Artist might already be in there
		if (!artistList.contains(song.getArtist())){
			artistList.add(song.getArtist());
		}
		
		// We need the album now, too!
		Album albumCheck = new Album(song.getAlbumName(), song.getArtist(), song.getArtist(), song.getYear());
		boolean contains = false;
		for (Album album : albumList) {
			if (album.equals(albumCheck)) {
				contains = true;
				// (If it was just added, then it wasn't in there - so add it!)
				album.addSong(song);
			}
		}
		// It doesn't exist! Time to make it exist.
		if (!contains) {
			albumCheck.addSong(song);
			albumList.add(albumCheck);
		}
    }
	
	public void addAlbumToList(Album album) {
		/* Adds an album to the library, plus all it's songs. */
		// Check if it exits already
		boolean exists = false;
		for (int i = 0; i < albumList.size(); i++) {
			if(albumList.get(i).getAlbumName().equals(album.getAlbumName()) && albumList.get(i).getArtist().equals(album.getArtist())) {
				exists = true;
				break;
			}
		}
		if (!exists) {
			// If not, add it.
			albumList.add(new Album(album));
		}
		for(int i = 0; i < albumList.size(); i++) {
			if(albumList.get(i).getAlbumName().equals(album.getAlbumName()) && albumList.get(i).getArtist().equals(album.getArtist())) {
				// If it already did exist, be sure it has everything.
				for(int j = 0; j < album.getSongList().size(); j++) {
					albumList.get(i).addSong(album.getSongList().get(j));
				}
			}
		}
		ArrayList<String> songs = new ArrayList<String>();
		for(int i = 0; i < songList.size(); i++) {
			// Add everything currently in the songlist...
			songs.add(songList.get(i).getSongName());
		}
		for(int i = 0; i < album.getSongList().size(); i++) {
			if(!songs.contains(album.getSongList().get(i).getSongName())) {
				// ... Then adds any missing ones to the song list,
				// just to make absolute sure.
				songList.add(album.getSongList().get(i));
				runSpecialFunctions(songList.get(songList.size() - 1), true, 0);
			}
		}
		
		// Artist might already be in there
		if(!artistList.contains(album.getArtist())){
			artistList.add(album.getArtist());
		}
		
	}
	
	public ArrayList<String> getLibrarySongList() {
		/* Returns a duplicate of the library song list, in name form. */
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < this.songList.size(); i++){
            list.add(songList.get(i).getSongName());
        }
		return list;
	}
	
	public ArrayList<String> getLibraryArtistList() {
		/* Returns a duplicate of the artist list. */
		return new ArrayList<String>(artistList);
	}
	
	public ArrayList<String> getLibraryAlbumList(){
		/* Returns a duplicate of the library album list, in name form. */
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < this.albumList.size(); i++){
            list.add(albumList.get(i).getAlbumName());
        }
		return list;
	}
	
	public ArrayList<String> getLibraryPlayListList(boolean getSpecial){
		/* Returns a duplicate of the library playlist list, in name form. */
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < this.playListList.size(); i++) {
			// If getspecial, we want em all. Otherwise, just usermade ones.
			if (getSpecial || playListList.get(i).isUserMade()) {
				list.add(playListList.get(i).getPlayListName());
			}
        }
		return list;
	}
	
	public ArrayList<String> getLibraryFavoriteSongs(boolean sort) {
		/* Returns a duplicate of the library song list, in name form.
		 * However, ones which are not favorited are excluded.
		 * Now with the ability to sort them, and if it does,
		 * will return all rated 1-5 instead. */
		ArrayList<Song> songListCopy = new ArrayList<Song>(songList);
		if (sort) {
			// If sort, then we need to give the below part a sorted copy of the songs.
			boolean sorted = false;
			boolean sortAttempt = true;
			// We will use insertion sort.
			while (!sorted) {
				// Continues until it goes a entire attempt without swapping.
				sortAttempt = true;
				for (int i = 0; i < songListCopy.size() - 1; i++) {
					if (songListCopy.get(i).getRating() > songListCopy.get(i + 1).getRating()) {
						Collections.swap(songListCopy, i, i+1);
						sortAttempt = false;
					}
				}
				sorted = sortAttempt;
			}
		}
        ArrayList<String> favoriteSongs = new ArrayList<String>();
		for (int i = 0; i < songListCopy.size(); i++){
			// If we sorted it earlier, then we want all rated 1-5. Else, we just want those rated 5.
            if (songListCopy.get(i).getFavorited() == true || (sort && songListCopy.get(i).getRating() > 0) ){
                favoriteSongs.add(songListCopy.get(i).getSongName());
            }
        }
        return favoriteSongs;
	}
	
	public boolean addPlayList(String name) {
		/* Adds a new playlist to the library, with a given name. */
		for (int i = 0; i < playListList.size(); i++) {
			if (playListList.get(i).getPlayListName().toLowerCase().equals(name.toLowerCase())) {
				// Make sure it doesn't already exist
				return false;
			}
		}
		// Add it.
		playListList.add(new PlayList(name, ""));
		return true; // ("Success")
	}
	
	public boolean markSongAsFavorite(Song songInst) {
		/* Marks a internal song as favorite, by finding its match */
        for (int i = 0; i < songList.size(); i++) {
            if (songList.get(i).equals(songInst)) {
            	// At this point we've found it, so either branch will end the loop.
                if (!songList.get(i).getFavorited()) {
                    songList.get(i).favorite();
                    runSpecialFunctions(songList.get(i), true, 0);
                    return true; // ("Success")
                } else return false;
            }
        }
        return false;
    }

    public void rateSong(Song songInst, int rating) {
    	/* Rates an internal song, by finding its match
		 * to the given song. */
         for (int i = 0; i < songList.size(); i++) {
        	if (songList.get(i).equals(songInst)) {
        		songList.get(i).setRating(rating);
        		if (rating == 5) songList.get(i).favorite(); // Spec
                else songList.get(i).unfavorite();
        		runSpecialFunctions(songList.get(i), true, 0);
        	}
        }
    }
    
    public boolean checkIfCorrectUsername(String username) {
    	/* Checks if it's given the right username for this library. Doesn't return the username.*/
    	return (this.username.equals(username.toLowerCase()));
    }
    
    public void shuffleLibrary() {
    	/* Shuffles the songlist of itself. */
    	Collections.shuffle(songList);
    }
    
    public boolean shufflePlayList(String name) {
    	/* Shuffles the songlist of a given playlist's name. Also returns if it worked. */
		for (int i = 0; i < playListList.size(); i++) {
			if (playListList.get(i).getPlayListName().toLowerCase().equals(name.toLowerCase()) && playListList.get(i).isUserMade()) {
				// No duplicates, so we can just break out immediately.
				playListList.get(i).songShuffle();
				return true;
			}
		}
		return false;
    }
    
    public boolean playASong(String title, String artist) {
    	/* Plays a given song, and by that, it increments its play count by 1. */
    	for (int i = 0; i < songList.size(); i++) {
			if (songList.get(i).getSongName().toLowerCase().equals(title.toLowerCase()) && songList.get(i).getArtist().toLowerCase().equals(artist.toLowerCase())) {
				songList.get(i).incrementPlay();
				// Look at the number, it's a 1. That stands for "was played", so recent & most played will
				// update themselves this time, along with everything else of course.
				this.runSpecialFunctions(songList.get(i), true, 1);
				return true;
			}
    	}
    	return false;
    }
    
    // Internal functions
    
    private ArrayList<Song> indicatorSongAdder(String input, String indicator, boolean precise, ArrayList<Song> songs) {
    	/* Adds songs to resultList based on the specifications. */
    	ArrayList<Song> resultList = new ArrayList<Song>();
    	for(int i = 0; i < songs.size(); i++) {
    		// precise means "must be the same name", as opposed to "must have the given words somewhere"
			if (precise) {
				// indicator is either title, artist, or genre.
				if (indicator.equals("title")) {
					if (songs.get(i).getSongName().toLowerCase().equals(input.toLowerCase())) {
						resultList.add(songs.get(i));
					}
				} else if (indicator.equals("artist")) {
					if (songs.get(i).getArtist().toLowerCase().equals(input.toLowerCase())) {
						resultList.add(songs.get(i));
					}
				} else {
					if (songs.get(i).getGenre().toLowerCase().equals(input.toLowerCase())) {
						resultList.add(songs.get(i));
					}
				}
			// (not precise)
			} else {
				if (indicator.equals("title")) {
					if (songs.get(i).getSongName().toLowerCase().contains(input.toLowerCase())) {
						resultList.add(songs.get(i));
					}
				} else if (indicator.equals("artist")) {
					if (songs.get(i).getArtist().toLowerCase().contains(input.toLowerCase())) {
						resultList.add(songs.get(i));
					}
				} else {
					// Incase you wanteed to search genres broadly, which like... Okay, sure.
					if (songs.get(i).getGenre().toLowerCase().contains(input.toLowerCase())) {
						resultList.add(songs.get(i));
					}
				}
			}
		}
    return resultList;
    }
    
    private ArrayList<Album> indicatorAlbumAdder(String input, String indicator, boolean precise, ArrayList<Album> albums) {
    	/* Adds albums to resultList based on the specifications. */
    	ArrayList<Album> resultList = new ArrayList<Album>();
    	for(int i = 0; i < albums.size(); i++) {
    		// precise means "must be the same name", as opposed to "must have the given words somewhere"
			if (precise) {
				// indicator is either title or artist
				if (indicator.equals("title")) {
					if (albums.get(i).getAlbumName().toLowerCase().equals(input.toLowerCase())) {
						resultList.add(albums.get(i));
					}
				} else {
					if (albums.get(i).getArtist().toLowerCase().equals(input.toLowerCase())) {
						resultList.add(albums.get(i));
					}
				}
			// (not precise)
			} else {
				if (indicator.equals("title")) {
					if (albums.get(i).getAlbumName().toLowerCase().contains(input.toLowerCase())) {
						resultList.add(albums.get(i));
					}
				} else {
					if (albums.get(i).getArtist().toLowerCase().contains(input.toLowerCase())) {
						resultList.add(albums.get(i));
					}
				}
			}
		}
    return resultList;
    }
    
    private boolean addPlayListSpecial(String name, String special) {
		/* Adds a new playlist to the library, with a given name.
		 * Exactly the same as the normal one, except it accepts
		 * a new "special" line. */
		for (int i = 0; i < playListList.size(); i++) {
			if (playListList.get(i).getPlayListName().toLowerCase().equals(name.toLowerCase())) {
				// Make sure it doesn't already exist
				return false;
			}
		}
		// Add it.
		playListList.add(new PlayList(name, special));
		// Now, we've missed a bunch of songs, so we need to add them.
		for (int i = 0; i < songList.size(); i++) {
			playListList.get(playListList.size() - 1).runSpecialModifier(songList.get(i), 0);
		}
		return true; // ("Success")
	}
    
    private void removePlayListSpecial(String name) {
		/* Removes a playlist from the library. Has no
		 * restrictions. */
		for (int i = 0; i < playListList.size(); i++) {
			if (playListList.get(i).getPlayListName().toLowerCase().equals(name.toLowerCase())) {
				playListList.remove(i);
				break;
			}
		}
	}
    
    private void removeSongFromPlaylistSpecial(String title, String artist) {
		/* Removes a song from all playlists. No exceptions. */
		for (int i = 0; i < playListList.size(); i ++) {
			if (playListList.get(i).canRemoveSong(title, artist)) {
				// If we removed a song, keep going! There's probably more!
				playListList.get(i).removeSong(title, artist);
			}
		}
	}
    
    private void removeUnusedArtists() {
    	/* Checks around for used artists. If one doesn't come up,
    	 * but is still listed as an artist, removes them! */
		ArrayList<String> artists = new ArrayList<String>();
		// First checks songs..
		for(int i = 0; i < songList.size(); i++) {
			if(!artists.contains(songList.get(i).getArtist())) {
				artists.add(songList.get(i).getArtist());
			}
		}
		// Then albums...
		for(int i = 0; i < albumList.size(); i++) {
			if(!artists.contains(albumList.get(i).getArtist())) {
				artists.add(albumList.get(i).getArtist());
			}
		}
		// Then runs to artists with the results. Not there? Goodbye.
		// (Goes top to bottom to avoid errors)
		for(int i = artistList.size()-1; i > -1; i--) {
			if(!artists.contains(artistList.get(i))) {
				artistList.remove(i);
			}
		}
	}
    
    private void runSpecialFunctions(Song song, boolean exists, int subInstruction) {
    	/* Updates every special function. Must be ran everytime something that they'd
    	 * care about updates, including multiple times when multiple things change. */
    	// Also, "exists" is basically just a way to fix up the genres w/o adding it again
    	// after removal.
    	ArrayList<String> triedGenres = new ArrayList<String>();
    	int counter = 0;
    	for (int i = 0; i < songList.size(); i++) {
    		// This part tries to add new genre playlists.
    		// ... Or remove them.
    		counter = 0;
    		if (!triedGenres.contains(songList.get(i).getGenre())) {
    			triedGenres.add(songList.get(i).getGenre());
    			// Incrmeents the counter for each genre...
    			for (int j = i; j < songList.size(); j++) {
    				if (songList.get(j).getGenre().equals(triedGenres.get(triedGenres.size() - 1))) {
    					counter++;
    				}
    			}
    			// If 10 or above, adds. Else, removes. Can handle them not being there / already being there.
    			if (counter >= 10) {
    				addPlayListSpecial(triedGenres.get(triedGenres.size() - 1).toLowerCase(), triedGenres.get(triedGenres.size() - 1).toLowerCase());
    			} else {
    				removePlayListSpecial(triedGenres.get(triedGenres.size() - 1).toLowerCase());
    			}
    		}
    	}
    	if (exists) {
	    	for (int i = 0; i < playListList.size(); i++) {
	    		playListList.get(i).runSpecialModifier(song, subInstruction);
	    	}
    	}
    }
}
