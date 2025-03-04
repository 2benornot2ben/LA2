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
		songs.add(new Song(songInst));
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
}