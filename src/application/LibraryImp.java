package application;

import lib.*;
import lib.exceptions.BadTargetEntityTypeException;
import lib.exceptions.EntityNotFoundException;
import lib.exceptions.InvalidInputException;
import lib.exceptions.InvalidRequestedActionException;
import lib.statepattern.LibState;
import lib.statepattern.states.Main;

import java.util.*;

public class LibraryImp implements Library {
    /**
     * Singleton implementation of the one object used in the application
     */
    private static LibraryImp libInstance;
    private long IDCounter = 5L;
    private Scanner scanner;
    private Map<String, Entity> library = new HashMap<String, Entity>();

    @Override
    public String uID() {
        return String.valueOf(IDCounter++);
    }

    /**
     * implementing state pattern for the UI part of the code
     */
    private LibState state;

    @Override
    public void setState(LibState state) {
        this.state = state;
    }

    @Override
    public void start() {
        System.out.println("\033[0;32m");
        System.out.println("Welcome to My Library\n.............................................");
        System.out.println("\033[0m");
        setState(new Main(this));
        while (true) {
            setState(state.action());
        }
    }

    private LibraryImp() {
        scanner = new Scanner(System.in);

        save(new Book("white nights", "dastayevski", "1"));
        save(new Book("crime and punishment", "Rahman Iraji", "2"));
        save(new Member("Erfan Mirhoseini", Gender.MALE, "3"));
        save(new Member("Mahtab Akbari", Gender.FEMALE, "4"));

    }

    public static LibraryImp getLibInstance() {
        if (libInstance == null)
            libInstance = new LibraryImp();
        return libInstance;
    }

    public Scanner getScanner() {
        return this.scanner;
    }

    @Override
    public Entity retrieve(String id) throws EntityNotFoundException {
        Entity result = library.get(id);
        if (result == null)
            throw new EntityNotFoundException();
        else return result;
    }

    @Override
    public <T> T retrieve(String id, Class<T> type) throws EntityNotFoundException, BadTargetEntityTypeException {
        Entity entity = this.retrieve(id);
        if (type.equals(entity.getClass()))
            return (T) entity;
        else throw new BadTargetEntityTypeException(type);
    }

    @Override
    public void save(Entity e) {
        library.put(e.getID(), e);
    }

    @Override
    public void delete(Entity e) throws EntityNotFoundException {
        if (library.remove(e.getID()) == null)
            throw new EntityNotFoundException();
    }

    @Override
    public void borrowBook(Book book, Member member) throws InvalidRequestedActionException {
        if (book.getAvailability().equals(BookAvailability.BORROWED))
            throw new InvalidRequestedActionException("Sorry The book has already been borrowed");
        book.setAvailability(BookAvailability.BORROWED);
        book.setBorrowingDate(new Date());
        member.addToBorrowed(book);
    }

    @Override
    public void returnBook(Book book, Member member) throws InvalidRequestedActionException {
        if (book.getAvailability().equals(BookAvailability.AVAILABLE))
            throw new InvalidRequestedActionException("The book has not even been borrowed. It can't be returned");
        book.setAvailability(BookAvailability.AVAILABLE);
        member.removeFromBorrowed(book);
    }

    @Override
    public ArrayList<Entity> search(String key, Class<?> targetType) {
        ArrayList<Entity> results = new ArrayList<Entity>();

        for (Map.Entry<String, Entity> l : library.entrySet()) {
            Entity entity = l.getValue();
            if (entity.getClass().equals(targetType)) {
                if (entity.hasSearchedKey(key))
                    results.add(entity);
            } else continue;
        }
        return results;
    }

    @Override
    public int getUserSelectedItem(int from, int to) {
        int selectedItem;
        do {
            try {
                selectedItem = scanner.nextInt();
                if (selectedItem > to || selectedItem < from)
                    throw new InvalidInputException();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please Enter a number between " + from + " and " + to + " I have exceptions handled");
            } catch (InvalidInputException e) {
                System.out.println("Please Enter a number between " + from + " and " + to + " I have exceptions handled");
            }
            scanner.nextLine();
        } while (true);
        return selectedItem;
    }

    @Override
    public void showLibrary() {
        library.forEach((id, entity) -> entity.showOnConsole());
    }
}
