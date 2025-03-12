/**************************************************************
 * Author: Davranbek Kadirimbetov & Benjamin Kanter
 * Description: Acts as a text-based interface for interacting
 * with the back end, allowing for manipulation of a library,
 * playlists, rating of songs, as well as searching & getting
 * songs, albums, authors, etc from their respective sources.
 **************************************************************/

package frontend;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import backend.*;

public class View {
	/* This class allows everything frontend-related to happen,
	 * and is the only place where input is gotten and
	 * output is printed. */
	private LibraryModel myLibrary;
	boolean minimize = false;
	Scanner getInput = new Scanner(System.in);
	String holdInput = "";
	String holdInputLower = "";
	public View() throws FileNotFoundException{
		/* This function lets the user interact with the back end
		 * and preform all of it's functions simply by typing
		 * commands into the console in their respective orders. */
		myLibrary = new LibraryModel();
		boolean running = true;
		while (running) {
			// Basically, print this small line of text when the program cycles, UNLESS it was by immediate error / first beginning.
			if (minimize) {
				System.out.println("You can enter a new command now, or type anything else for the list again.");
			} else {
				System.out.println("Enter: \"Search\" \"MusicStore OR Library\" to begin SEARCHING (Library has Playlists)");
				System.out.println("Enter: \"Add\" \"Album OR Song\" to add to Library or Playlists");
				System.out.println("Enter: \"Remove\" to remove song/album from a library.");
				System.out.println("Enter: \"Create\" \"{PlaylistName}\" to create a new playlist. No spaces in the name."); 
				System.out.println("Enter: \"Rate\" to rate a song.");
				System.out.println("Enter: \"Get\" to get a list from the library."); 
				System.out.println("Enter: \"AddP\" to add a song to a playlist");
				System.out.println("Enter: \"RemoveP\" to remove a song from a playlist");
				System.out.println("Enter: \"Markf\" to mark a song favorite");
				System.out.println("Enter: \"Exit\" to kill the program. You will need to rerun it after.");
				System.out.println("This is not case sensitive, but is spelling sensitive.");
				minimize = true;
			}
			holdInput = getInput.nextLine();
			// You could argue this is a temporary field, but with how much it's used... Yeah it's for everyone's own good.
			holdInputLower = holdInput.toLowerCase();
			
			// This handles SEARCHING, for both the MusicStore and the Library.
			if (holdInputLower.split(" ")[0].equals("search") && holdInputLower.split(" ").length > 1 &&
					(holdInputLower.split(" ")[1].equals("musicstore") || holdInputLower.split(" ")[1].equals("library"))) {
				String locationHolder = holdInputLower.split(" ")[1];
				System.out.println("");
				System.out.println("YOU CAN SEARCH FOR THE ...");
				System.out.println("1. Song by title");
				System.out.println("2. Song by artist");
				System.out.println("3. Song by genre");
				System.out.println("4. Album by title");
				System.out.println("5. Album by artist");
				// MusicStore does not have a playlist.
				if (locationHolder.equals("library")) System.out.println("6. Playlist by name");
				
				System.out.print("Enter the number of an option you want: ");
				String searching = getInput.nextLine();
				
				// 1 and 2 are both Song type, hence we can do this without masking.
				if (searching.equals("1") || searching.equals("2") || searching.equals("3")) {
					searchSong_Function(searching, locationHolder);
				}
				
				// 3 and 4 are both Album type, same idea.
				else if (searching.equals("4") || searching.equals("5")) {
					searchAlbum_Function(searching, locationHolder);
				}
				
				// 5 is only for searching the library, and is PlayList type.
				else if(searching.equals("6") && locationHolder.equals("library")) {
					searchPlayList_Function();
				}
				else {
					System.out.println("Invalid number");
				}
			
			
			// This handles REMOVING, for both Albums and Songs.
			} 
			else if (holdInputLower.split(" ")[0].equals("remove")) {
				System.out.println("");
				System.out.println("What do you want to remove?");
				System.out.println("1. Song from a library");
				System.out.println("2. Album from a library");
				System.out.print("Choose an option you want: ");
				String option = getInput.nextLine();
				ArrayList<String> songs = myLibrary.getLibrarySongList();
				if(songs.size() == 0) {
					System.out.println("Library is empty!");
				} else {
					if(option.split(" ")[0].equals("1")) {
						// removes a song from an album as well.
						System.out.print("Enter a title of the song you want to remove: ");
						String title = getInput.nextLine();
						System.out.print("Enter an artist of the song you want to remove: ");
						String artist = getInput.nextLine();
						removeSong_Function(title, artist);
					} else if(option.split(" ")[0].equals("2")) {
						System.out.print("Enter a title of the album you want to remove: ");
						String title = getInput.nextLine();
						System.out.print("Enter an artist of the album you want to remove: ");
						String artist = getInput.nextLine();
						removeAlbum_Function(title, artist);
					} else {
						System.out.println("Invalid input!");
					}
				}
				
				
				// This handles ADDING, for both Albums and Songs.
			}else if (holdInputLower.split(" ")[0].equals("add") && holdInputLower.split(" ").length > 1 &&
					(holdInputLower.split(" ")[1].equals("album") || holdInputLower.split(" ")[1].equals("song"))) {
				System.out.println("");
				System.out.print("Enter a title of the " + holdInputLower.split(" ")[1] + " you want to add: ");
				String title = getInput.nextLine();
				System.out.print("Enter an artist of the " + holdInputLower.split(" ")[1] + " you want to add: ");
				String artist = getInput.nextLine();
				// Once again, the type difference splits these two functions.
				if (holdInputLower.split(" ")[1].equals("song")) {
					addSong_Function(title, artist);
				} else if (holdInputLower.split(" ")[1].equals("album")){
					addAlbum_Function(title, artist);
				}
				
			// This handles GETTING, for everything from the Library (specifically).
			} else if (holdInputLower.split(" ")[0].equals("get")) {
				System.out.println("");
				System.out.println("YOU CAN GET A LIST OF ...");
				System.out.println("1. Song titles (s)");
				System.out.println("2. Artists (s)");
				System.out.println("3. Albums");
				System.out.println("4. Playlists");
				System.out.println("5. Favorite Songs");
				System.out.println("6. By rating (sorted)");
				System.out.println("Type \"(num) s\" if you want it sorted (must have a \"(s)\" symbol in the list)");
				System.out.print("Enter the number of an option you want: ");
				String option = getInput.nextLine();
				System.out.println("");
				// Handling the 5 numbers the users could've input, sadly one at a time.
				if (option.split(" ")[0].equals("1")) {
					ArrayList<String> songNames = myLibrary.getLibrarySongList();
					if (option.split(" ").length > 1 && option.split(" ")[1].equals("s")) Collections.sort(songNames);
					if(songNames.size() == 0) System.out.println("No songs in the library");
					else getPrintText(songNames);
				} else if (option.split(" ")[0].equals("2")) {
					ArrayList<String> artists = myLibrary.getLibraryArtistList();
					if (option.split(" ").length > 1 && option.split(" ")[1].equals("s")) Collections.sort(artists);
					if(artists.size() == 0) System.out.println("No artists in the library");
					else getPrintText(artists);
				} else if (option.equals("3")) {
					ArrayList<String> albums = myLibrary.getLibraryAlbumList();
					if (albums.size() == 0) System.out.println("No albums in the library");
					else getPrintText(albums);
				} else if (option.equals("4")) {
					ArrayList<String> playLists = myLibrary.getLibraryPlayListList();
					if(playLists.size() == 0) System.out.println("No playlists in the library");
					else getPrintText(playLists);
				} else if (option.split(" ")[0].equals("5")) {
					ArrayList<String> favorite = myLibrary.getLibraryFavoriteSongs(false);
					if(favorite.size() == 0) System.out.println("No favorite songs in the library");
					else getPrintText(favorite);
				} else if (option.split(" ")[0].equals("6")){
					ArrayList<String> songs = myLibrary.getLibraryFavoriteSongs(true);
					if(songs.size() == 0) System.out.println("No favorite songs in the library");
					else getPrintText(songs);
			    }else {
					System.out.println("Wrong input");
				}
				
			// This handles CREATING PLAYLISTS. This doesn't need any more info past what you initially give it.
			} else if (holdInputLower.split(" ")[0].equals("create") && holdInputLower.length() > 7) {
				String name = holdInputLower.split(" ", 2)[1];
				boolean added = myLibrary.addPlayList(name);
				System.out.println("");
				// added is a variable used a lot later on, and it's as the name implies: A boolean that says if
				// whatever it is was successfully added or not.
				if (added) System.out.println("The playlist " + name + " has been added to the library.");
				else System.out.println("The playlist " + name + " is already in the library.");
				
			// This handles RATING SONGS. Not favoriting, just rating (unless you rate 5 stars).
			} else if (holdInputLower.split(" ")[0].equals("rate")) {
				System.out.println("");
				System.out.print("Enter a title of the song you want to rate: ");
        		String title = getInput.nextLine();
				System.out.print("Enter an artist of the song you want to rate: ");
				String artist = getInput.nextLine();
				// This search's boolean is true, so it will only return exact matches.
				ArrayList<Song> songs = myLibrary.searchByIndicatorSong(title, "library", "title", true);
				if(songs.size() == 0) {
					System.out.println("");
					System.out.println(title + " is not found in the library");
				} else {
					// This is asked after; no reason to have them rate before we know it exists.
					System.out.print("Enter a rating (between 1 and 5): ");
					String number = getInput.nextLine();
					System.out.println("");
					rateSong_Function(songs, title, artist, number);
				}	
				
			// This handles FAVORITING SONGS. Not rating, just favoriting.
			} else if (holdInputLower.split(" ")[0].equals("markf")) {
				System.out.println("");
				System.out.print("Enter a title of the song you want to mark as favorite: ");
        		String title = getInput.nextLine();
				System.out.print("Enter an artist of the song you want to mark as favorite: ");
				String artist = getInput.nextLine();
				System.out.println("");
				// This will only return exact matches.
				ArrayList<Song> songs = myLibrary.searchByIndicatorSong(title, "library", "title", true);
				if(songs.size() == 0) {
					System.out.println(title + " is not found in the library");
				} else {
					markFavorite_Function(songs, title, artist);
				}
				
			// This handles ADDING to PLAYLISTS. One must already exist, of course.
			} else if (holdInputLower.split(" ")[0].equals("addp")) {
				System.out.println("");
				ArrayList<String> playlists = myLibrary.getLibraryPlayListList();
				if(playlists.size() == 0) {
					// Immediate rejection if you don't have any.
					System.out.println("No playlists in the library");
				} else {
					System.out.println("SELECT A PLAYLIST WHERE YOU WANT TO ADD A SONG...");
					// This code prints each playlist with their index in the ArrayList (+1).
					for(int i = 0; i < playlists.size(); i++) {
						System.out.println((i+1) + ". " + playlists.get(i));
					}
					System.out.println("Enter a number of a playlist where you want to add: ");
					String number = getInput.nextLine();
					System.out.println("");
					// Of course, they had to of given a number, so let's make sure they did.
			        if(isNumeric(number)) {
			        	int num = Integer.parseInt(number);
			        	// A number within range...
			        	if (!(num > 0 && num <= playlists.size())) {
			        		System.out.println("Your input is invalid");
			        	}
			        	else {
			        		System.out.print("Enter a title of the song you want to add to a playlist: ");
			        		String title = getInput.nextLine();
							System.out.print("Enter an artist of the song you want to add to a playlist: ");
							String artist = getInput.nextLine();
							// They entered a number 1 above, so subtract 1 to get the right index.
							String playListName = playlists.get(num-1);
							System.out.println("");
							// This will only return exact matches.
							ArrayList<Song> songs = myLibrary.searchByIndicatorSong(title, "library", "title", true);
							if(songs.size() == 0) {
								System.out.println(title + " is not found in the library");
							} else {
								addToPlayList_Function(songs, title, artist, playListName);
							}
							
							
						}
			        } else {
			        	System.out.println("Your input is invalid");
			        }
				}	
				
			// This handles REMOVING from PLAYLISTS. One must already exist, of course.
			} else if (holdInputLower.split(" ")[0].equals("removep")) {
				System.out.println("");
				ArrayList<String> playlists = myLibrary.getLibraryPlayListList();
				if(playlists.size() == 0) {
					// Immediate rejection if you don't have any.
					System.out.println("No playlists in the library");
				} else {
					System.out.println("SELECT A PLAYLIST FROM WHERE YOU WANT TO REMOVE A SONG...");
					// This code prints each playlist with their index in the ArrayList (+1).
					for(int i = 0; i < playlists.size(); i++) {
						System.out.println((i+1) + ". " + playlists.get(i));
					}
					System.out.println("Enter a number of a playlist where you want to remove: ");
					String number = getInput.nextLine();
					// Of course, they had to of given a number, so let's make sure they did.
					if(isNumeric(number)) {
			        	int num = Integer.parseInt(number);
			        	// A number within range...
			        	if (!(num > 0 && num <= playlists.size())) {
			        		System.out.println("Your input is invalid");
			        	}
			        	else {
			        		System.out.print("Enter a title of the song you want to remove from a playlist: ");
			        		String title = getInput.nextLine();
							System.out.print("Enter an artist of the song you want to remove to a playlist: ");
							String artist = getInput.nextLine();
							// They entered a number 1 above, so subtract 1 to get the right index.
							String playListName = playlists.get(num-1);
							System.out.println("");
							removeFromPlayList_Function(title, artist, playListName);
						}
			        }
					else {
			        	System.out.println("Your input is invalid");
			        }
				}
			// This EXITS the program. This one's probably self explanitory.
			} else if (holdInputLower.split(" ")[0].equals("exit")) {
				// Kills the program
				running = false;
				System.out.println("Program exited.");
			} else {
				System.out.println("Invalid instruction! Did you spell everything right? Resetting.");
				// This makes all the instructions reprint.
				minimize = false;
			}
			System.out.println(""); // Extra whitespace
			
		} 
	}
	
	// General Functions.
	// By "General", we mean these are used more than once,
	// or arent directly used by the constructor.
	
	private boolean isNumeric(String num) {
		/* This functions as an easy way to see if what the user gave
		 * is in fact an integer without crashing the program.
		 * Useful for having them pick an option via number. */
		boolean numeric = true;
        try {
            Integer.parseInt(num);
        } catch (NumberFormatException e) {
            numeric = false;
        }
        return numeric;
	}
	
	public static void main(String[] args) throws FileNotFoundException{
		/* This function starts the program. That's it. */
		new View();
	}
	
	private void printAdditionText(boolean added, boolean exists, String title, String artist) {
		/* This function is used to print out if something
		 * was added to the library, if it was already in
		 * the library, or if it doesn't exist. Convenient. */
		if(!added) {
			if(exists) {
				System.out.println(title + " by " + artist + " is already in the library");
			} else {
				System.out.println(title + " by " + artist + " is not found in the store");
			}
		}
		else {
			System.out.println(title + " by " + artist + " has been added to the library");
		}
	}
	
	
	private void getPrintText(ArrayList<String> listToRepeat) {
		/* This function prints out things according to
		 * the get order's instructions. Saves a good amount
		 * of space. */
		for(int i = 0; i < listToRepeat.size(); i++) {
			System.out.println((i+1) + ". " + listToRepeat.get(i));
		}
	}
	
	// Specific Functions
	// By "Specific", we mean these were taken out of the constructor
	// and put here for the sake of the constructor not being a complete
	// spaghetti monster able to crush a coder's hopes and dreams when
	// they try to read it.
	
	// Note, all of these end with _Function, so they're easy to tell apart.
	
	private void searchSong_Function(String searching, String locationHolder) {
		/* Handles searching for songs (specifically). Can be either from
		 * the library or musicstore. Does nearly everything by itself. */
		String indicator = "";
		// There's 2 ways of searching, so we need to account for it.
		// indicator is often used as the way of "accounting" for these.
		if (searching.equals("1")) indicator = "title";
		else if (searching.equals("2")) indicator = "artist";
		else indicator = "genre";
		System.out.print("Please enter the " +  indicator + " of the song: ");
		holdInput = getInput.nextLine();
		System.out.println("");
		// Now more interesting: We need contains if it's name or artist, but equals if genre.
		ArrayList<Song> resultList = new ArrayList<Song>();
		if (indicator.equals("genre")) resultList = myLibrary.searchByIndicatorSong(holdInput, locationHolder, indicator, true);
		else resultList = myLibrary.searchByIndicatorSong(holdInput, locationHolder, indicator, false);
		if (resultList.size() == 0) {
			System.out.println("Sorry " + holdInput + " is not in the " + locationHolder + ".");
		} else {
			System.out.println("Search result: ");
			// Print what we found.
			for(int i = 0; i < resultList.size(); i++) {
				System.out.println((i+1) + ": " + resultList.get(i).getPrintFormatted());
			}
		}
		// It appears we are not done.
		System.out.println("Please enter the number of any song you want album information of.");
		System.out.println("Alternatively, enter anything else to go back to the start.");
		String getNum = getInput.nextLine();
		if (isNumeric(getNum)) {
			int indexPos = Integer.parseInt(getNum) - 1;
			Song holdWant = resultList.get(indexPos);
			ArrayList<Album> secondaryResultList = myLibrary.searchByIndicatorAlbum(holdWant.getAlbumName(), "library", "title", true);
			if (secondaryResultList.size() == 0) {
				System.out.println("Sorry " + holdInput + " is not in the " + locationHolder + ".");
			} else {
				System.out.println("Search result: ");
				// This double loop basically prints the album name, and then everything in the album,
				// before moving onto the next one.
				for(int i = 0; i < secondaryResultList.size(); i++) {
					System.out.println("   ~ " + secondaryResultList.get(i).getPrintFormatted());
					for(int j = 0; j < secondaryResultList.get(i).getSongList().size(); j++) {
						System.out.println((j+1) + ": " + secondaryResultList.get(i).getSongList().get(j).getSongName());
					}
					System.out.println("");
				}
			}
		} else {
			// This functions like minimize's role, so no reason to have it.
			minimize = false;
		}
	}
	
	private void searchAlbum_Function(String searching, String locationHolder) {
		/* Handles searching for albums (specifically). Can be either from
		 * the library or musicstore. Does nearly everything by itself. */
		String indicator = "";
		// Indicator tells apart how it's searching, as like before.
		if (searching.equals("4")) indicator = "title";
		else indicator = "artist";
		System.out.print("Please enter the " +  indicator + " of the album: ");
		holdInput = getInput.nextLine();
		// This will return anything that contains the given input.
		ArrayList<Album> resultList = myLibrary.searchByIndicatorAlbum(holdInput, locationHolder, indicator, false);
		System.out.println("");
		if (resultList.size() == 0) {
			System.out.println("Sorry " + holdInput + " is not in the " + locationHolder + ".");
		} else {
			System.out.println("Search result: ");
			// This double loop basically prints the album name, and then everything in the album,
			// before moving onto the next one.
			for(int i = 0; i < resultList.size(); i++) {
				System.out.println("   ~ " + resultList.get(i).getPrintFormatted());
				for(int j = 0; j < resultList.get(i).getSongList().size(); j++) {
					System.out.println((j+1) + ": " + resultList.get(i).getSongList().get(j).getSongName());
				}
				System.out.println("");
			}
		}
	}
	
	private void searchPlayList_Function() {
		/* Handles searching for playlists (specifically).
		 * Can only be from the library.
		 * Does everything by itself. */
		System.out.print("Please enter the name of the playlist: ");
		String nameOfPlayList = getInput.nextLine();
		// This will only return exact matches. Different method this time, too.
		PlayList result = myLibrary.searchByNamePlayList(nameOfPlayList);
		System.out.println("");
		if (result.getPlayListName().equals("")) {
			System.out.println("Sorry " + nameOfPlayList + " is not in the library.");
		} else {
			System.out.println("Search result: ");
			// Library enforces no duplicate playlist names, so we're fine.
			System.out.println("   ~ " + result.getPlayListName());
			for (int i = 0; i < result.getSongList().size(); i++) {
				// And print everything from it, of course.
				System.out.println((i+1) + ": " + result.getSongList().get(i).getSongName() 
						+ ", " + result.getSongList().get(i).getArtist());
			}
		}
	}
	
	private void removeSong_Function(String title, String artist) {
		if(myLibrary.canRemoveSong(title, artist)) {
			myLibrary.removeSong(title, artist);
			System.out.println("A song " + title + " by " + artist + " has been removed from library.");
		}else {
			System.out.println("A song " + title + " by " + artist + " is not found in the library");
		}
		
	}
	
	private void removeAlbum_Function(String title, String artist) {
		if(myLibrary.canRemoveAlbum(title, artist)) {
			myLibrary.removeAlbum(title, artist);
			System.out.println("An album " + title + " by " + artist + " has been removed from library.");
		}else {
			System.out.println("An album " + title + " by " + artist + " is not found in the library");
		}
		
	}
	
	private void addSong_Function(String title, String artist) {
		/* Handles adding a song to the library.
		 * Demands both it's title and artist.
		 * Does everything else by itself. */
		// This will only return exact matches.
		ArrayList<Song> songs = myLibrary.searchByIndicatorSong(title, "musicstore", "title", true);
		System.out.println("");
		if(songs.size() == 0) {
			System.out.println(title + " is not found in the store");
		}else {
			// added is back with the same concept (if it's already there or not),
			// but now there's exists. exists just states... If it exists.
			// In this case, it's if the song exists in the music store.
			boolean added = false;
			boolean exists = false;
			for (int i = 0; i < songs.size(); i++) {
				if (songs.get(i).getArtist().toLowerCase().equals(artist.toLowerCase())) {
					// If we got here, then it certainly exists.
					if (myLibrary.canAddSongToList(songs.get(i))) {
						// And if we get here, then we're done.
						myLibrary.addSongToList(songs.get(i));
						added = true; // Why not set exists? Because added basically acts like it.
						break;
					} else {
						exists = true;
					}
				}
			}
			printAdditionText(added, exists, title, artist);
		}
	}
	
	private void addAlbum_Function(String title, String artist) {
		/* Handles adding a album to the library, and all it's songs.
		 * Demands both it's title and artist. Does everything else by itself. */
		// This will only return exact matches.
		ArrayList<Album> albums = myLibrary.searchByIndicatorAlbum(title, "musicstore", "title", true);
		System.out.println("");
		if(albums.size() == 0) {
			System.out.println(title + " is not found in the store");
		}else {
			boolean added = false;
			boolean exists = false;
			for(int i = 0; i < albums.size(); i++) {
				if(albums.get(i).getArtist().toLowerCase().equals(artist.toLowerCase())) {
					// If we got here, then it certainly exists.
					if(myLibrary.canAddAlbumToList(albums.get(i))) {
						// And if we get here, then we're done.
						myLibrary.addAlbumToList(albums.get(i));
						added = true; // Why not set exists? Because added basically acts like it.
						break;
					} else {
						exists = true;
					}
				}
			}
			printAdditionText(added, exists, title, artist);
		}
	}
	
	private void rateSong_Function(ArrayList<Song> songs, String title, String artist, String number) {
		/* Handles rating a song with a bunch of inputs. Does not handle favoriting.
		 * Has some code before being called. */
        if(isNumeric(number)) {
        	int num = Integer.parseInt(number);
        	// It's 1-5, so... Yeah.
        	if(num > 0 && num < 6) {
        		// This basically keeps track of if it was found or not.
        		boolean rated = false;
				for(int i = 0; i < songs.size(); i++) {
					if(songs.get(i).getArtist().toLowerCase().equals(artist.toLowerCase())) {
						myLibrary.rateSong(songs.get(i), num);
						rated = true;
					}
				}
				if (rated) System.out.println(title + " by " + artist + " has been rated.");
				else System.out.println(title + " by " + artist + " is not in the library"); // Else for rated = false
        	} else System.out.println("Rating should be between 1 and 5!"); // Else for num out of bounds
        } else System.out.println("Invalid input. Enter a number between 1 and 5!"); // Else for isNumeric
	}
	
	private void markFavorite_Function(ArrayList<Song> songs, String title, String artist) {
		/* Handles favoriting a song with a bunch of inputs. Does not handle rating.
		 * Has some code before being called. */
		// Marked handles if it's already favorited or not. If it is, then it will be false.
		boolean marked = false;
		boolean exist = false;
		for(int i = 0; i < songs.size(); i++) {
			if(songs.get(i).getArtist().toLowerCase().equals(artist.toLowerCase())) {
				marked = myLibrary.markSongAsFavorite(songs.get(i));
				exist = true;
			}
		}
		if (exist) {
			if (marked) System.out.println(title + " by " + artist + " has been marked as favorite");
			else System.out.println(title + " by " + artist + " is already marked as favorite"); // Else for it already being favorited
		} else System.out.println(title + " by " + artist + " is not in the library"); // Else for it not existing
	}
	
	private void addToPlayList_Function(ArrayList<Song> songs, String title, String artist, String playListName) {
		/* Handles the tail end of adding to playlists. By this point most details are already figured out,
		 * so now it's just the adding part & output. */
		boolean added = false;
		boolean exist = false;
		for (int i = 0; i < songs.size(); i++) {
			if (songs.get(i).getArtist().toLowerCase().equals(artist.toLowerCase())) {
				// added = false if it's already there.
				added = myLibrary.addSongToPlaylist(playListName, songs.get(i));
				exist = true;
			}
		}
		if (exist) {
			if (added) System.out.println(title + " by " + artist + " has been added to " + playListName);
			else System.out.println(title + " by " + artist + " is already in " + playListName); // Else for it already being there
		} else System.out.println(title + " by " + artist + " is not in the library"); // Else for it not existing
	}
	
	private void removeFromPlayList_Function(String title, String artist, String playListName) {
		/* Handles the tail end of removing from a playlist. By this point most details are already figured out,
		 * so now it's just the removing part & output. */
		boolean removed = false;
		boolean exist = false;
		// This will return anything that equals the given input.
		ArrayList<Song> songs = myLibrary.searchByIndicatorSong(title, "musicstore", "title", true);
		for (int i = 0; i < songs.size(); i++) {
			if (songs.get(i).getArtist().toLowerCase().equals(artist.toLowerCase())) {
				exist = true;
			}
		}
		// We don't need to know if it exists to try.
		removed = myLibrary.removeSongFromPlaylist(playListName, title, artist);
		if (exist) {
			if (removed) System.out.println(title + " by " + artist + " has been removed from " + playListName);
			else System.out.println(title + " by " + artist + " is not in " + playListName); // Else for it not being there
		} else System.out.println(title + " by " + artist + " is not in the store"); // Else for it not existing
	}
}
