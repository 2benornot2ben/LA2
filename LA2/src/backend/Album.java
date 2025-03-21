/**************************************************************
 * Author: Davranbek Kadirimbetov & Benjamin Kanter
 * Description: Holds a album object, with a lot of it's details.
 * Also holds the list of songs it should have.
 **************************************************************/

package backend;

import java.util.ArrayList;

public class Album {
	/* This class stores everything relevant
	 * about a album object. */
	private String albumName;
	private String artist;
	private String genre;
	private String year;
	private ArrayList<Song> songs;
	
	public Album(String albumName, String artist, String genre, String year) {
		/* Initializes a album object. Only songs is changable after. */
		this.albumName = albumName;
		this.artist = artist;
		this.genre = genre;
		this.year = year;
		this.songs = new ArrayList<Song>();
	}
	
	public Album(Album album) {
		/* Initializes everything to a song's specifications.
		 * Favorited is always false. */
		this.albumName = album.getAlbumName();
		this.artist = album.getArtist();
		this.genre = album.getGenre();
		this.year = album.getYear();
		this.songs = album.getSongList();
	}
	
	protected void addSong(Song songInst) {
		/* Adds a song via copying it. */
		boolean exists = false;
		for (Song song : songs) {
			if (song.equals(songInst)) {
				exists = true;
				break;
			}
		}
		if (!exists) songs.add(new Song(songInst));
	}
	
	protected void removeSong(String title, String artist) {
		/* Removes a song from itself, if it's there. */
		for (int i = 0; i < songs.size(); i++) {
			if (songs.get(i).getSongName().toLowerCase().equals(title.toLowerCase()) && songs.get(i).getArtist().toLowerCase().equals(artist.toLowerCase())) {
				songs.remove(i);
			}
		}
	}
	
	public String getPrintFormatted() {
		/* Returns a formatted string of multiple returns */
		return getAlbumName() + ", " + getArtist() + ", " + getGenre() + ", " + getYear();
	}
	
	public String getAlbumName() {
		// No need for docstrings on basic getters.
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
	
	public ArrayList<Song> getSongList() {
		/* Returns a copy of the list of songs,
		 * with each song also being a copy. */
		ArrayList<Song> copy = new ArrayList<>();
	    for (Song song : this.songs) {
	        copy.add(new Song(song));
	    }
	    return copy;
	}
	
	@Override
	public boolean equals(Object obj) {
		/* Compares 2 song objects. Returns true if
		 * they are the same, either in pointer or in
		 * all relevant details (a few are omitted to
		 * prevent strange errors) */
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Album album = (Album) obj;
	    // This does NOT check the list of songs.
	    return albumName.equals(album.albumName) &&
	           artist.equals(album.artist) &&
	           genre.equals(album.genre) &&
	           year.equals(album.year);
	}
	
	@Override
	public int hashCode() {
		/* Hashcode override. Compares what you'd expect. */
	    return albumName.hashCode() + artist.hashCode() + genre.hashCode() + year.hashCode();
	}
}