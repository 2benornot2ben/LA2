/**************************************************************
 * Author: Davranbek Kadirimbetov & Benjamin Kanter
 * Description: Holds a playlist object, with it's name and songs.
 **************************************************************/

package backend;

import java.util.ArrayList;
import java.util.Collections;

public class PlayList {
	/* This class stores everything relevant
	 * about a playlist object. */
	private String playListName;
	private ArrayList<Song> songs;
	private String specialMod; // Set to "" if there's none, set to {genreName} for genre ones!
	// There is also "favorite", "topRated", "recent", and "mostPlayed"

	// Future us! I have NOT made it so the songs inside the albums are updated
	// whenever the library ones are.
	// I'll lay the plan here:
	// People can WATCH songs inside of playlists, so when you want to add a view...
	// Don't! Somehow make library get an instruction for it, and then loop
	// through every playlist. Then, have the playlists look to see if they have
	// the song. If they do, use a custom replace method to keep it in the
	// same spot WITHOUT LOSING ORDER! (You can use .equals(), it only uses
	// vars that don't change throughout runtime).
	// That second part should actually be done for everything which
	// modifies a song in general, barring straight up removal.
	// Removal will be similar, but be it's own thing (not hard to imagine
	// how it would work).
	
	public PlayList(String playListName, String specialModifier) {
		/* Initializes a playlist, with it's name
		 * and an empty list. */
		this.playListName = playListName;
		this.songs = new ArrayList<Song>();
		specialMod = specialModifier;
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
	
	public boolean isUserMade() {
		return specialMod.equals("");
	}
	
	public void songShuffle() {
		if (isUserMade()) {
			Collections.shuffle(songs);
		}
	}
	
	public void runSpecialModifier(Song song, int subInstruction) {
		// This method does NOT handle updating preexisting songs!
		// This simply handles specifically special method
		// functions!
		// Also, this assumes that it is ran EVERY TIME something might happen to the playlist!
		// If you do 2 things and then run it, errors may occur!
		if (!(specialMod.equals(""))) {
			switch (specialMod) {
			case ("favorite"):
				// Does not use subInstruction
				if (song.getFavorited() && canAddSongToList(song)) {
					addSong(song);
				} else if (!song.getFavorited() && !canAddSongToList(song)) {
					removeSong(song.getSongName(), song.getArtist());
				}
				break;
			case ("topRated"):
				if (song.getRating() >= 4 && canAddSongToList(song)) {
					addSong(song);
				} else if (song.getRating() <= 3 && !canAddSongToList(song)) {
					removeSong(song.getSongName(), song.getArtist());
				}
				break;
			case ("recent"):
				// If subInstruction == 1, then we updated the play count.
				if (subInstruction == 1) {
					// We actually just purge the song if it exists.
					if (!canAddSongToList(song)) {
						removeSong(song.getSongName(), song.getArtist());
					}
					songs.add(0, song);
					// This caps it at 10, assuming no shady interferance.
					if (songs.size() > 10) {
						songs.remove(songs.size() - 1);
					}
				}
				break;
			case ("mostPlayed"):
				// If subInstruction == 1, then we updated the view count.
				// You think it wouldn't matter, but odd removal behavior if we don't.
				if (subInstruction == 1) {
					if (!canAddSongToList(song)) {
						removeSong(song.getSongName(), song.getArtist());
					}
					songs.add(0, song);
					// Now we need to sort it.
					for (int i = 0; i < songs.size() - 1; i++) {
						if (songs.get(i).getPlayCount() < songs.get(i + 1).getPlayCount()) {
							Song songHolder = songs.get(i);
							songs.remove(i);
							songs.add(i + 1, songHolder);
						}
					}
					if (songs.size() > 10) {
						songs.remove(songs.size() - 1);
					}
				}
				break;
			default:
				if (song.getGenre().equals(specialMod)) {
					if (canAddSongToList(song)) {
						addSong(song);
					}
				}
			}
		}
	}
}
