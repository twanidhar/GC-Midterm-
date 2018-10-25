package gc_midterm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookTextFile {

	
	// displays list of file
    public static List<Book> readFile(Path filePath) {
        List<Book> books = new ArrayList<>();
        // ** Example of reading a file into a list
        try {
            // reads each line back as a a single string
            List<String> lines = Files.readAllLines(filePath);
            // convert the list of strings back to a string and an int
            for (String line : lines) {
                Book book1 = convertLineToItem(line);
                books.add(book1);
            }
            return books;
        } catch (NoSuchFileException ex) {
            return new ArrayList<>();
        } catch (IOException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }// TODO be sure to update the converting line portion with variables
// Modify this method as necessary to convert a line of text from the file to a
    // new item instance
    public static Book convertLineToItem(String line) {
        String[] parts = line.split(", ");
        String countryName = parts[0];
        int population = Integer.parseInt(parts[1]);
        Country country = new Country(countryName, population);
        return country;
    }
// makes changes to the list (adds)
    public static void appendToFile(String item, Path filePath) throws IOException {
        if (Files.notExists(filePath)) {
            Files.createFile(filePath);
        }

        // Create a list with the user's food in it
        List<String> linesToAdd = Arrays.asList(item);
        // Write those lines to the end of the file
        Files.write(filePath, linesToAdd, StandardOpenOption.APPEND);
    }
	
	
	
}