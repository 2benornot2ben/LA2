/**************************************************************
 * Author: Davranbek Kadirimbetov & Benjamin Kanter
 * Description: Acts as a public storage of all songs & albums,
 * which it gets from reading filenames. Expects a file called
 * albums.txt in order to find anything.
 **************************************************************/

package backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MusicStore {
	/* This class finds all of the songs & albums
	 * from the files, and keeps them stored so
	 * library can do things with them. */
	//instance variables
	private ArrayList<String> fileNames;
	private ArrayList<Album> albums;
	private ArrayList<Song> songs;

	//constructor
	public MusicStore() throws FileNotFoundException{
		/* Initializes the lists, and then runs the 2 functions
		 * responsible for getting the file names and then
		 * using them. */
		this.fileNames = new ArrayList<String>();
	    this.albums = new ArrayList<Album>();
	    this.songs = new ArrayList<Song>();
		creatingFileNames();
		readingAlbums();
	}
	
	public void creatingFileNames(){
		/* Creates the filenames of text files to check
		 * to find all the albums/songs. Expects albums.txt
		 * to exist to function properly. */
		try (Scanner scanLine = new Scanner(new File("albums.txt"))){
			while(scanLine.hasNextLine()){
				// format we get filenames from
				String line = scanLine.nextLine();
				String[] data = line.split(",");
				String albumFile = data[0] + "_" + data[1] + ".txt";
				fileNames.add(albumFile);
			}
			scanLine.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	public void readingAlbums() throws FileNotFoundException {
		/* This class uses the filenames, finds their real versions,
		 * and then adds all the songs & albums with them. */
		for(int i = 0; i < this.fileNames.size(); i++) {
			File myFile = new File(fileNames.get(i));
			Scanner myReader = new Scanner(myFile);

			//reads only first line which is in the following format
			//Album Title,Artist,Genre,Year
			String header = myReader.nextLine();
			String[] headerInfo = header.split(",");

			//retrieves the data
			String albumTitle = headerInfo[0];
			String artist = headerInfo[1];
			String genre = headerInfo[2];
			String year = headerInfo[3];
			
			//creates and adds new album to an arrayList
			albums.add(new Album(albumTitle, artist, genre, year));

			//reading songs
			while(myReader.hasNextLine()){
				String songName = myReader.nextLine();
				Song storeSong = new Song(songName, artist, albumTitle, genre, year);
				songs.add(storeSong);
				albums.get(i).addSong(storeSong);
			}
			myReader.close();
		}
	}
	
	public ArrayList<Song> getSongs(){
		/* Gives a copy of list of all the songs.
		 * Everything inside is also a copy. */
		ArrayList<Song> copy = new ArrayList<Song>();
	    for (Song song : songs) {
	        copy.add(new Song(song));
	    }
	    return copy;
	}
	
	public ArrayList<Album> getAlbums(){
		/* Gives a copy of list of all the albums.
		 * Everything inside is also a copy. */
		ArrayList<Album> copy = new ArrayList<>();
	    for (Album album: albums) {
	        copy.add(new Album(album));
	    }
	    return copy;
	}
}