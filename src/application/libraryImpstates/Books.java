package application.libraryImpstates;

import lib.Book;
import lib.Entity;
import lib.Library;
import lib.Member;
import lib.exceptions.BadTargetEntityTypeException;
import lib.exceptions.EntityNotFoundException;
import lib.exceptions.InvalidRequestedActionException;
import lib.LibState;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is used to run the corresponding codes when the library is performing book-related actions
 *
 * @see LibState
 * @see Book
 * @see Member
 * @see Library
 */
public class Books implements LibState {
    /**
     * This is the instance of the library that wants to use this state.
     */
    private final Library library;

    /**
     * This is an instance of the scanner that is received from the library to perform user interactions.
     * <p>It is supposed to come from the library that is using this state to assure that at any given moment only one scanner is being used to avoid potential memory leaks</p>
     */
    private final Scanner scanner;

    /**
     * Constructor of the Books State objects.
     * It initializes the values of library and scanner fields.
     *
     * @param library the instance of the library that this state is supposed to interact with
     */
    public Books(Library library) {
        this.library = library;
        this.scanner = library.getScanner();
    }

    /**
     * This method displays the user interface for book actions, receives the input, reacts to it and decides on the next state
     * This method calls the corresponding method to handle user's selection
     * Based on the user's action on this state the proper value will be returned as "nextState"
     *
     * @return the next state that {@link #library the library} is supposed to go to
     */
    @Override
    public LibState action() {
        LibState nextState;
        // menu selection range
        int menuFrom = 1;
        int menuTo = 6;
        System.out.println("1-Add Book");
        System.out.println("2-Delete Book");
        System.out.println("3-Search Book");
        System.out.println("4-Return Book");
        System.out.println("5-Borrow Book");
        System.out.println("6-Return");
        System.out.print("Enter a number:");
        int selectedItem = library.getUserSelectedItem(menuFrom, menuTo);
        switch (selectedItem) {
            case 1:
                addBook();
                nextState = new Books(library);
                break;
            case 2:
                deleteBook();
                nextState = new Books(library);
                break;
            case 3:
                searchBook();
                nextState = new Books(library);
                break;
            case 4:
                returnBook();
                nextState = new Books(library);
                break;
            case 5:
                borrowBook();
                nextState = new Books(library);
                break;
            case 6:
                nextState = new Main(library);
                break;
            default:
                nextState = new Books(library);
                ;
        }
        return nextState;
    }

    /**
     * Gets the book information from the user and calls the proper method from the library to add the book to the library
     */
    private void addBook() {
        System.out.println("Enter name of the book:");
        String name = scanner.next();
        System.out.println("Enter name of the author:");
        String author = scanner.next();
        library.save(new Book(name, author, library.uID()));
        System.out.println("The book has been successfully added");

    }

    /**
     * Gets the ID of the book, finds the book from the library and pass it to delete method of the library
     * <p>If the received ID is not associated with a book a BadTargetEntityTypeException will be caught and dealt with and
     * if no Entity with that ID exists in the library an EntityNotFoundException will be caught and dealt with </p>
     */
    private void deleteBook() {
        try {
            System.out.println("ID of the book you want to delete:");
            String id = scanner.next();
            Book book = library.retrieve(id, Book.class);
            library.delete(book);
            System.out.println("The book has been successfully deleted");
        } catch (EntityNotFoundException e) {
            System.out.println("The book was not found");
        } catch (BadTargetEntityTypeException e) {
            System.out.println("The book was not found");
        }

    }

    /**
     * Receives the search key word, finds books containing that key and displays them.
     */
    private void searchBook() {
        System.out.println("Enter search key word:");
        ArrayList<Entity> bookResults = library.search(scanner.next(), Book.class);
        for (Entity b : bookResults) {
            b.showOnConsole();
        }
    }

    /**
     * Receives the ID of the user that wants to return a book and the ID of the Book They want to return and calls the return action of the library on them.
     * <p>In case the book or the member can not be found or the book can not be returned, proper exceptions will be thrown
     * from the library methods and get caught in their respective handlers</p>
     */
    private void returnBook() {
        try {
            System.out.println("Enter your ID:");
            String memberId = scanner.next();
            Member member = library.retrieve(memberId, Member.class);
            System.out.println("ID of the book you want to return:");
            String bookId = scanner.next();
            Book book = library.retrieve(bookId, Book.class);
            library.returnBook(book, member);
            System.out.println("The book was successfully deleted");
        } catch (EntityNotFoundException e) {
            System.out.println("Nothing associated with this ID was found in the library");
        } catch (InvalidRequestedActionException e) {
            System.out.println(e.getMessage());
        } catch (BadTargetEntityTypeException e) {
            if (e.getBadType().equals(Book.class)) {
                System.out.println("No Book associated with this ID was found in the library");
            } else if (e.getBadType().equals(Member.class))
                System.out.println("No Member associated with this ID was found in the library");
        }
    }

    /**
     * Receives the ID of the user that wants to borrow book and the ID of the Book They want to borrow and calls the borrow action of the library on them.
     * <p>In case the book or the member can not be found or the book can not be borrowed, proper exceptions will be thrown
     * from the library methods and get caught in their respective handlers</p>
     */
    private void borrowBook() {
        try {
            System.out.println("Enter your ID:");
            String memberId = scanner.next();
            Member member = library.retrieve(memberId, Member.class);
            System.out.println("ID of the book you want to borrow:");
            String bookId = scanner.next();
            Book book = library.retrieve(bookId, Book.class);
            library.borrowBook(book, member);
        } catch (EntityNotFoundException e) {
            System.out.println("Nothing associated with this ID was found in the library");
        } catch (InvalidRequestedActionException e) {
            System.out.println(e.getMessage());
        } catch (BadTargetEntityTypeException e) {
            if (e.getBadType().equals(Book.class)) {
                System.out.println("No Book associated with this ID was found in the library");
            } else if (e.getBadType().equals(Member.class))
                System.out.println("No Member associated with this ID was found in the library");
        }


    }
}
