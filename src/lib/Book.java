package lib;

import lib.exceptions.InvalidRequestedActionException;

import java.util.Date;

public class Book implements Entity {
    private String name;
    private String author;
    private BookAvailability availability = BookAvailability.AVAILABLE;
    private Date borrowingDate;
    private final String ID;

    public Book(String name, String author, String id) {
        this.name = name;
        this.author = author;
        this.ID = id;
    }


    public String toString() {
        return String.format("id:%s %s by %s %s", ID, name, author, availability.equals(BookAvailability.BORROWED) ? String.format("Borrowed on %s", borrowingDate.toString()) : "Available");
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public BookAvailability getAvailability() {
        return availability;
    }

    public void setAvailability(BookAvailability availability) {
        this.availability = availability;
    }

    public void setBorrowingDate(Date date) {
        this.borrowingDate = date;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

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
