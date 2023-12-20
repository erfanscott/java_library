package lib;

import lib.exceptions.InvalidRequestedActionException;

import java.util.Date;

/**
 * This is an implementation of the Entity interface describing a book
 * <p>The Book object has fields for its name, unique ID, author's name, the last date it has been borrowed
 * and a property indicating whether the book has been borrowed at this moment or it is available for getting borrowed</p>
 *
 * @see Entity
 * @see BookAvailability
 */
public class Book implements Entity {
    private String name;
    private String author;
    private BookAvailability availability = BookAvailability.AVAILABLE;
    private Date borrowingDate;
    private final String ID;

    /**
     * The constructor for Book objects used when instantiating the Book class to initialize book fields
     *
     * @param name   the complete name of the book
     * @param author the complete name of the author
     * @param id     the unique ID assigned to the book
     */
    public Book(String name, String author, String id) {
        this.name = name;
        this.author = author;
        this.ID = id;
    }

    /**
     * This overrides the toString method from the Object class that converts book fields into one string
     *
     * @return a string containing the information about book
     */

    @Override
    public String toString() {
        return String.format("id:%s %s by %s %s", ID, name, author, availability.equals(BookAvailability.BORROWED) ? String.format("Borrowed on %s", borrowingDate.toString()) : "Available");
    }

    /**
     * @return the author's name
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return the name of the book
     */
    public String getName() {
        return name;
    }

    /**
     * @return the availability status of the book
     */
    public BookAvailability getAvailability() {
        return availability;
    }

    /**
     * sets the availability status of the book.
     *
     * @param availability The availability status that is to be assigned to the book
     */
    public void setAvailability(BookAvailability availability) {
        this.availability = availability;
    }

    /**
     * sets the date of the book being borrowed
     *
     * @param date the date that the book is being borrowed
     */
    public void setBorrowingDate(Date date) {
        this.borrowingDate = date;
    }

    /**
     * @param author the name that is to be set as the author property of the book
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @param name the name that is to be set as the name property of the book
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public void showOnConsole() {
        System.out.println(this.toString());
    }

    @Override
    public boolean hasSearchedKey(String key) {
        String searchTarget = this.getName().toLowerCase() + this.getAuthor().toLowerCase();
        return searchTarget.contains(key.toLowerCase());
    }
}
