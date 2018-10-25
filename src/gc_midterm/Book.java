package gc_midterm;

import java.util.Date;

public class Book {

	String title;
	String author;
	BookStatus availability;
	Date dueDate;
	String genre;

	public Book() {
	}

	public Book(String title, String author, BookStatus availability, Date dueDate, String genre) {
		this.title = title;
		this.author = author;
		this.availability = availability;
		this.dueDate = dueDate;
		this.genre = genre;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public BookStatus isCheckedOut() {
		return availability;
	}

	public BookStatus getAvailability() {
		return availability;
	}

	public void setAvailability(BookStatus availability) {
		this.availability = availability;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	// TODO toString

}
