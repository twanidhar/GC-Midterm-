package gc_midterm;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class LibraryApp {

	private static BookTextFile btf = new BookTextFile();
	private static Scanner userInput = new Scanner(System.in);
	private static final LocalDate PRINCE = LocalDate.of(1999, Month.JANUARY, 1);
	private static boolean userContinue = false;

	public static void main(String[] args) throws IOException, ParseException {

		List<Book> completeList = btf.showBooks();
		System.out.println("Welcome to Grand Chirpus library!");
		System.out.println("How can we help you today?");

		int userResponse;

		do {

			System.out.println("");
			System.out.println(" 1. See a list of books.");
			System.out.println(" 2. Select a genre.");
			System.out.println(" 3. Search by author.");
			System.out.println(" 4. Search by keyword.");
			System.out.println(" 5. Select a book to checkout.");
			System.out.println(" 6. Return a book.");
			System.out.println(" 7. Exit Grand Chirpus Directory.");
			System.out.println();

			System.out.print("Please select the number that you would like explore: ");
			userResponse = userInput.nextInt();
			System.out.println();
			userInput.nextLine();

			switch (userResponse) {// the value being checked for
			case 1:
				// method to list books
				System.out.print("Grand Chirpus Inventory: ");
				System.out.println("==============================");
				printOutBooks(completeList);
				System.out.println();
				break;

			case 2:
				do {
					// method for searching books by genre
					System.out.println("What genre would you like to search by: ");
					genreList(completeList);
					String userSearch = userInput.nextLine();
					// TODO
					System.out.print("Books in " /* + genreVariable */ + ": ");
					searchBooks(completeList, userSearch);

					userContinue = userContinueLoop(
							"Would you like to search by another genre? (Please enter yes to continue and any other key to go back to the main menu) : ");
				} while (userContinue);
				break;

			case 3:
				do {
					// calls method to search books by author
					System.out.print("Enter author's name: ");
					String authorName = userInput.nextLine();
					System.out.println("Books by " + authorName + ": ");
					searchByAuthor(completeList, authorName);

					userContinue = userContinueLoop(
							"Would you like to search by a different author? (Please enter yes to continue and any other key to go back to the main menu) : ");
				} while (userContinue);
				break;

			case 4:
				// calls method to find keywords
				keywordInBooklist(completeList);
				break;

			case 5:
				do {
					// TODO possibly just make a list of books that are currently onshelf??
					// calls method to check out book
					printOutBooks(completeList);
					System.out.print("Which book would you like to check out? ");
					int choice = userInput.nextInt();
					completeList = checkOutBook(completeList, choice);

					userContinue = userContinueLoop(
							"Would you like to check out another book? (Please enter yes to continue and any other key to go back to the main menu) : ");
				} while (userContinue);
				break;

			case 6:
				// TODO possibly just make a list of books that are currently check out??
				// allows user to return a book
				do {
					printOutBooks(completeList);
					System.out.print("Which book would you like to return? ");
					int choice = userInput.nextInt();
					completeList = returnBook(completeList, choice);

					// calls method to check if user would like to return another book
					userContinue = userContinueLoop(
							"Would you like to return another book? (Please enter yes to continue and any other key to go back to the main menu) : ");
				} while (userContinue);

				break;

			case 7:
				System.out.println("Enjoy reading! We hope to see you soon.");
				btf.rewriteFile(completeList);
				System.exit(0);
				break;
			}

		} while (userResponse != 7);

		userInput.close();
	}/*
		 * if (Arrays.equals(array1, array2)) { System.out.println("EQUAL"); } else {
		 * System.out.println("NOT EQUAL");
		 */// layout we can use for book availability

	public static void printOutBooks(List<Book> completeList) throws IOException, ParseException {
		int i = 1;

		for (Book book : completeList) {
			System.out.println((i++) + ". " + book.getTitle() + " by " + book.getAuthor());
		}

	}

	public static void genreList(List<Book> completeList) {
		// TODO create a validator in user input that user may only enter 5 genres if
		// adding a book
		String[] genresIncluded = new String[5];
		String singleGenre = null;
		Set<String> listOfGenres = new HashSet<>(); // hashset to get rid of duplicate genres (and to account for user
													// entry genre that may not be included in original list)
		int i = 1; // variable for number options on genre menu

		// creates a string array for all possible genres (up to 5) for each instance of
		// book
		for (Book book : completeList) {
			singleGenre = book.getGenre();
			genresIncluded = singleGenre.split(","); // allows for 5 possible genres per book

			// sorts through the string array to add possible genres (unless they're null)
			// to a hashset to account for duplicates
			for (String genre : genresIncluded) {
				if (genre != null) {
					listOfGenres.add(genre);
				}
			}
		}

		// prints out list itself
		for (String genre : listOfGenres) {
			System.out.println(i + ". " + genre);
		}
	}

	public static void keywordInBooklist(List<Book> completeList) {
		List<Book> keywordIncluded = new ArrayList<>();

		boolean isValid = false;
		do {
			try {

				userInput.nextLine();
				System.out.print("Please enter a keyword to search: ");
				String userKeyword = userInput.nextLine();

				// creates a list to cycle through
				completeList = btf.showBooks();

				// create an option to sort through authors or books with keyword
				System.out.print("Would you like to search through authors or titles? ");
				String userResponse = userInput.nextLine();

				// cycles through the list to add to a new list of just books with that key word
				for (Book book : completeList) {
					// sorts either authors or titles
					if (userResponse.toLowerCase().contains("author")) {
						if (book.getAuthor().toLowerCase().contains(userKeyword)) {
							keywordIncluded.add(book);
							System.out.println(book.getTitle() + book.getAuthor());
						}

					} else if (userResponse.toLowerCase().contains("title")) {
						if (book.getTitle().toLowerCase().contains(userKeyword)) {
							keywordIncluded.add(book);
							System.out.println(book.getTitle() + book.getAuthor());
						}
					}

					// if no books were added to the list, it lets the user know
					if (keywordIncluded == null) {
						System.out.println("Sorry, we don't have any inventory containing that keyword.");
					}

				}
				// allows user to quit loop
				isValid = true;
			} catch (IOException | ParseException e) {
				// prints error and has user try again
				e.printStackTrace();
				// allows user to repeat loop in order to enter valid input
				isValid = false;
			}
		} while (!isValid);
	}

	public static void searchBooks(List<Book> completeList, String search) {
		List<Book> searchedBookList = new ArrayList<>();
		// TODO do we want to keep getAuthor and getTitle as equals or change them to
		// "contains"

		for (Book sortBook : completeList) {
			if (sortBook.getGenre().equals(search) || sortBook.getAuthor().equals(search)
					|| sortBook.getTitle().equals(search)) {
				searchedBookList.add(sortBook);
			}
		}

		int i = 1;
		for (Book book : searchedBookList) {
			System.out.println((i++) + ". " + book.getTitle() + " by " + book.getAuthor());
		}

	}

	public static void searchByAuthor(List<Book> completeList, String search) {
		List<Book> searchedBookList = new ArrayList<>();

		for (Book sortBook : completeList) {
			if (sortBook.getAuthor().equals(search)) {
				searchedBookList.add(sortBook);
			}
		}

		int i = 1;
		for (Book book : searchedBookList) {
			System.out.println((i++) + ". " + book.getTitle() + " by " + book.getAuthor());
		}

	}

	public static List<Book> checkOutBook(List<Book> completeList, int choice) throws IOException, ParseException {
		// sorts through list of all books to find the book requested by user and sets a
		// due date if book is on shelf, if book is already checked out, it lets the
		// user know
		Book ourBook = completeList.get(choice - 1);

		if (ourBook.getAvailability().equals(BookStatus.ONSHELF)) {
			ourBook.setAvailability(BookStatus.CHECKEDOUT);
			LocalDate today = LocalDate.now();
			LocalDate dueDate = today.plus(14, ChronoUnit.DAYS);
			ourBook.setDueDate(dueDate);
		} else if (completeList.get(choice - 1).getAvailability().equals(BookStatus.CHECKEDOUT)) {
			System.out.println("Sorry, that book has already been checked out. It is due back to the library "
					+ ourBook.getDueDate() + ".");
		}
		// TODO: figure out a prettier format
		System.out.println(completeList.get(choice - 1).toString());
		// System.out.println(completeList);
		return completeList;
	}

	public static List<Book> returnBook(List<Book> completeList, int choice) throws IOException, ParseException {
		// checks book at index and sets to onshelf if it was checked out
		Book ourBook = completeList.get(choice - 1);

		if (ourBook.getAvailability().equals(BookStatus.CHECKEDOUT)) {
			ourBook.setAvailability(BookStatus.ONSHELF);
			ourBook.setDueDate(PRINCE);
		} else if (completeList.get(choice - 1).getAvailability().equals(BookStatus.ONSHELF)) {
			System.out.println("That book is already available!");
		}
		// TODO figure out a prettier format
		System.out.println(completeList.get(choice - 1).toString());
		System.out.println(completeList);
		return completeList;
	}

	public static boolean userContinueLoop(String prompt) {
		System.out.print(prompt);
		String userReturn = userInput.nextLine();

		if (userReturn.toLowerCase().equals("y")) {
			userContinue = true;
		} else {
			userContinue = false;
		}
		return userContinue;
	}

	// method to update book i/o file when user quits the program
	public static void updatedBooks(List<Book> completeList) throws IOException {
		List<Book> updatedBooks = new ArrayList<>();

		for (Book b : completeList) {
			updatedBooks.add(b);
		}

		btf.rewriteFile(updatedBooks);
	}
}