/**************************************************************
 * Author: Davranbek Kadirimbetov & Benjamin Kanter
 * Description: Holds a song object, with a lot of it's details.
 * Only it's rating & if it's favorited can be changed after
 * initialization.
 **************************************************************/

package backend;

public class Song {
	/* This class stores everything relevant
	 * about a song object. */
	private String songName;
	private String artist;
	private String albumName;
	private String genre;
    //added year just in case we may need it, if not we can delete.
    private String year;
	private int rating;
	private boolean favorited;
	
	public Song(String songName, String artist, String albumName, String genre, String year) {
		/* Initializes everything to a song's specifications.
		 * Favorited is always false. */
		this.songName = songName;
		this.albumName = albumName;
		this.artist = artist;
		this.genre = genre;
        this.year = year;
        this.favorited = false;
	}
	
	public Song(Song incomingSong) {
		/* Copies a song object. Useful for encapsulation. */
		this.songName = incomingSong.getSongName();
		this.albumName = incomingSong.getAlbumName();
		this.artist = incomingSong.getArtist();
		this.genre = incomingSong.getGenre();
        this.year = incomingSong.getYear();
        this.favorited = incomingSong.getFavorited();
        this.rating = incomingSong.getRating();
	}
	
	public String getPrintFormatted() {
		/* Gives a simple output of 3 of a song's most important details. */
		return getSongName() + ", " + getArtist() + ", " + getAlbumName();
	}
	
	public String getSongName() {
		// No need for docstrings on basic getters.
		return songName;
	}
	
	public String getAlbumName() {
		return albumName;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public String getYear() {
		return year;
	}
	
	public int getRating() {
		return rating;
	}
	
	public boolean getFavorited() {
		return favorited;
	}
	
	public void setRating(int n) {
		/* Sets the rating of the song object.
		 * Note that in normal usage, n should never be outside of 1-5 bounds. */
		if (n < 1) n = 1; // But just incase.
		if (n > 5) n = 5;
		if (n == 5) favorited = true;
		this.rating = n;
	}
	
	public void favorite() {
		/* Sets favorite to true. */
		favorited = true;
	}
	
	public void unfavorite() {
		/* Sets favorite to false. */
		favorited = false;
	}
	
	@Override
	public boolean equals(Object obj) {
		/* Compares 2 song objects. Returns true if
		 * they are the same, either in pointer or in
		 * all relevant details (a few are omitted to
		 * prevent strange errors) */
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Song song = (Song) obj;
	    return songName.equals(song.songName) &&
	           artist.equals(song.artist) &&
	           albumName.equals(song.albumName) &&
	           genre.equals(song.genre) &&
	           year.equals(song.year);
	}
	
	@Override
	public int hashCode() {
	    return songName.hashCode() + artist.hashCode() + albumName.hashCode() + genre.hashCode() + year.hashCode();
	}
}