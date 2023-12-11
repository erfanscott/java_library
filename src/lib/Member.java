package lib;

import lib.exceptions.InvalidRequestedActionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Member extends Person {
    private final String ID;
    private Map<String, Book> borrowedBooks = new HashMap<String, Book>();

    public Member(String name, Gender gender, String id) {
        super(name, gender);
        this.ID = id;
    }


    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Book> b : borrowedBooks.entrySet()) {
            Book book = b.getValue();
            builder.append(book.toString() + "/");
        }
        String borrowedBooksString = builder.toString();
        return String.format("id:%s, %s, Borrowed books: %s", ID, super.getName(), borrowedBooksString);

    }

    public void addToBorrowed(Book book) {
        borrowedBooks.put(book.getID(), book);
    }

    public void removeFromBorrowed(Book book) throws InvalidRequestedActionException {
        String bookId = book.getID();
        if (borrowedBooks.get(bookId) == null)
            throw new InvalidRequestedActionException("This member has not borrowed this book");
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
}
