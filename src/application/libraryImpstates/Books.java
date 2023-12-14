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

public class Books implements LibState {
    private final Library library;
    private final Scanner scanner;

    public Books(Library library) {
        this.library = library;
        this.scanner = library.getScanner();
    }

    @Override
    public LibState action() {
        LibState nextState;
        System.out.println("1-Add Book");
        System.out.println("2-Delete Book");
        System.out.println("3-Search Book");
        System.out.println("4-Return Book");
        System.out.println("5-Borrow Book");
        System.out.println("6-Return");
        System.out.print("Enter a number:");
        int selectedItem = library.getUserSelectedItem(1, 6);
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

    private void addBook() {
        System.out.println("Enter name of the book:");
        String name = scanner.next();
        System.out.println("Enter name of the author:");
        String author = scanner.next();
        library.save(new Book(name, author, library.uID()));
        System.out.println("The book has been successfully added");

    }

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

    private void searchBook() {
        System.out.println("Enter search key word:");
        ArrayList<Entity> bookResults = library.search(scanner.next(), Book.class);
        for (Entity b : bookResults) {
            b.showOnConsole();
        }
    }

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
