/**************************************************************
 * Author: Davranbek Kadirimbetov & Benjamin Kanter
 * Description: Holds a playlist object, with it's name and songs.
 **************************************************************/

package backend;

import java.util.ArrayList;

public class PlayList {
	/* This class stores everything relevant
	 * about a playlist object. */
	private String playListName;
	private ArrayList<Song> songs;
	
	public PlayList(String playListName) {
		/* Initializes a playlist, with it's name
		 * and an empty list. */
		this.playListName = playListName;
		this.songs = new ArrayList<Song>();
	}
	
	public PlayList(PlayList playlist) {
		/* Copies a playlist object. Useful for encapsulation. */
		this.playListName = playlist.getPlayListName();
		this.songs = playlist.getSongList();
	}
	
	public void addSong(Song songInst) {
		/* Adds a song to the playlist via copying. */
		songs.add(new Song(songInst));
	}

	public boolean canAddSongToList(Song song) {
		/* Determines if a song is already inside it's list. */
		for(int i = 0; i < songs.size(); i++) {
			//if(songs.get(i).getSongName().toLowerCase().equals(song.getSongName()) && songs.get(i).getArtist().toLowerCase().equals(song.getArtist())) {
			if (songs.get(i).equals(song)) {
				// No need to iterate more; it is.
				return false;
			}
		}
		return true;
	}
    
	public boolean canRemoveSong(String title, String artist) {
		/* Determines if a song is already inside it's list... But opposite. */
		for(int i = 0; i < songs.size(); i++){
            if(songs.get(i).getSongName().toLowerCase().equals(title.toLowerCase()) && songs.get(i).getArtist().toLowerCase().equals(artist.toLowerCase())){
            	// No need to iterate more; it is.
                return true;
            }
        }
		return false;
	}
	
	public void removeSong(String title, String artist){
		/* Removes a song from it's list via comparing them with their custom equals. */
        for(int i = 0; i < songs.size(); i++){
            if(songs.get(i).getSongName().toLowerCase().equals(title.toLowerCase()) && songs.get(i).getArtist().toLowerCase().equals(artist.toLowerCase())){
                songs.remove(i);
                // Song is gone, so we're done.
                break;
            }
        }
    }

	public String getPlayListName() {
		return this.playListName;
	}

	public ArrayList<Song> getSongList() {
		/* Returns a copy of the song list,
		 * with the songs also being copies. */
		ArrayList<Song> copy = new ArrayList<Song>();
	    for (Song song : songs) {
	        copy.add(new Song(song));
	    }
	    return copy;
	}
}