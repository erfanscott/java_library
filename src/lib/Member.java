package lib;

import lib.exceptions.InvalidRequestedActionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class defines a subclass of {@link Person Person} abstract class describing a library member
 * <p>It defines a unique id field for the member along with a map
 * containing the books that hav been borrowed by this member and have not been returned yet</p>
 *
 * @see Person
 * @see Gender
 * @see Book
 * @see HashMap
 */

public class Member extends Person {
    private final String ID;
    private Map<String, Book> borrowedBooks = new HashMap<String, Book>();


    /**
     * The constructor for member object.
     * It calls the constructor of the upper-class {@link Person Person} to set the name and gender values
     * It also assigns an ID to the object
     *
     * @param name   name of the member
     * @param gender gender of the member
     * @param id     unique ID of the member
     */
    public Member(String name, Gender gender, String id) {
        super(name, gender);
        this.ID = id;
    }

    /**
     * This overrides the toString method from the Object class that converts member fields into one string
     *
     * @return a string containing the information about the member
     */

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Book> b : borrowedBooks.entrySet()) {
            Book book = b.getValue();
            builder.append(book.toString() + "/");
        }
        String borrowedBooksString = builder.toString();
        return String.format("id:%s, Name: %s, Gender: %s, Borrowed books: %s", ID, super.getName(), super.getGender().toString(), borrowedBooksString);

    }

    /**
     * adds a book to {@link #borrowedBooks the borrowed books list}
     *
     * @param book the book that is to be added to {@link #borrowedBooks the borrowed books list}
     */
    public void addToBorrowed(Book book) {
        borrowedBooks.put(book.getID(), book);
    }

    /**
     * removes a book from {@link #borrowedBooks the borrowed books list}
     *
     * @param book the book that is to be removed from {@link #borrowedBooks the borrowed books list}
     */
    public void removeFromBorrowed(Book book) {
        
        if (!this.hasBorrowed(book))
            return;
        borrowedBooks.remove(book.getID());

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
        String searchTarget = this.getName().toLowerCase();
        return searchTarget.contains(key.toLowerCase());
    }

    public boolean hasBorrowed(Book book) {
        return (borrowedBooks.get(book.getID()) == null) ? false : true;
    }

}
