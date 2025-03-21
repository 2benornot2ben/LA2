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
	private String specialMod; // Set to "" if there's none, set to {genrename} for genre ones!
	// There is also "favorite", "toprated", "recent", and "mostplayed"

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
		// "" stands for "usermade". We're banking on a genre not being called "".
		// ... Pretty safe bet to make.
		return specialMod.equals("");
	}
	
	public void songShuffle() {
		/* Shuffles songs inside the playlist. Also, requires it to be usermade - 
		 * most specials have their own sorting mechanisms. */
		if (isUserMade()) {
			Collections.shuffle(songs);
		}
	}
	
	protected void runSpecialModifier(Song song, int subInstruction) {
		/* Updates itself according to what the special functions would logically
		 * want to do - favorite needs favorites, toprated needs 5 stars,
		 * recent needs sorted by recently played, and mostplayed needs
		 * sorting by most played songs.
		 * Also handles genre specials with the default.
		 * Must be ran EVERY TIME a song is modified in a way which matters. */
		if (!(specialMod.equals(""))) {
			// Switch statement, because fancy.
			switch (specialMod) {
			case ("favorite"):
				// Does not use subInstruction
				// If favorite, add it. If not favorite, remove it. Might already be done, which is ok.
				if (song.getFavorited() && canAddSongToList(song)) {
					addSong(song);
				} else if (!song.getFavorited() && !canAddSongToList(song)) {
					removeSong(song.getSongName(), song.getArtist());
				}
				break;
			case ("topRated"):
				// Does not use subInstruction
				// If 4+, add it. If not, remove it. Might already be done, which still works fine.
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
				// You think it wouldn't matter, but odd behavior if we don't.
				if (subInstruction == 1) {
					if (!canAddSongToList(song)) {
						removeSong(song.getSongName(), song.getArtist());
					}
					songs.add(0, song);
					// Now we need to sort it.
					for (int i = 0; i < songs.size() - 1; i++) {
						// This is bubble sort, but it only runs once.
						// This is why it must be ran every time.
						if (songs.get(i).getPlayCount() < songs.get(i + 1).getPlayCount()) {
							Song songHolder = songs.get(i);
							songs.remove(i);
							songs.add(i + 1, songHolder);
						}
					}
					// Hard limit of 10
					if (songs.size() > 10) {
						songs.remove(songs.size() - 1);
					}
				}
				break;
			default:
				// Genre doesn't care about order, and already has
				// removals dealt with for it, so that's fine.
				if (song.getGenre().toLowerCase().equals(specialMod)) {
					if (canAddSongToList(song)) {
						addSong(song);
					}
				}
			}
		}
	}
}
