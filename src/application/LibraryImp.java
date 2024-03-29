package application;

import lib.*;
import lib.exceptions.BadTargetEntityTypeException;
import lib.exceptions.EntityNotFoundException;
import lib.exceptions.InvalidInputException;
import lib.exceptions.InvalidRequestedActionException;
import lib.LibState;
import application.libraryImpstates.Main;

import java.lang.reflect.Field;
import java.util.*;

/**
 * This is an implementation of the Library interface using the singleton and the state design patterns.
 * <p>This implementation provides with a single instance of the library as all the actions are performed on one particular library.</p>
 * <p>All the elements of the library are stored in a HashMap and all the library actions run over this map.</p>
 * <p>It also makes use of an scanner to pass through the different states of the library so they can all use the same scanner without<br/>
 * having to be concerned about any memory leaks.</p>
 * <p>A unique ID generator has also been provided to be used when creating new library entities.</p>
 *
 * @see LibState
 */

public class LibraryImp implements Library {

    private static LibraryImp libInstance;
    private long IDCounter = 5L;
    private Scanner scanner;
    private Map<String, Entity> library = new HashMap<String, Entity>();

    @Override
    public String uID() {
        return String.valueOf(IDCounter++);
    }

    private LibState state;

    @Override
    public void setState(LibState state) {
        this.state = state;
    }

    /**
     * <p>To managing the states that the library is supposed to work at based on the user's selection,
     * this method calls the action method of each state and the state itself decides what the next state should be.
     * then the library is set to be at the state that is returned by the previous state and utilizing an always-true while loop
     * the action method of this new state is called and this loop goes on until the user decides to terminate the app using the Exit menu
     * </p>
     */
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

    /**
     * The constructor of LibraryImp objects
     * <p>It initializes the scanner of the library</p>
     * <p>It also adds some default elements to the library</p>
     * <p>It has been defined as private so no instances other than the one created inside the {@link #getLibInstance() getLibInstance} method can be created</p>
     */
    private LibraryImp() {
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\\n");
        save(new Book("white nights", "dastayevski", "1"));
        save(new Book("crime and punishment", "Rahman Iraji", "2"));
        save(new Member("Erfan Mirhoseini", Gender.MALE, "3"));
        save(new Member("Mahtab Akbari", Gender.FEMALE, "4"));

    }

    /**
     * This method implements the lazy initialization of the singleton pattern's instance.
     * <p>It is a static method so the the using class can get the library instance by the LibraryImp class name as
     * it is not possible for it to instantiate the class</p>
     * <p>It has been implemented in such a way so the single instance of the library will not be created at the loading of the class and instead the library
     * will be instantiated when the using class first call this method when it actually needs the instance</p>
     *
     * @return the library instance
     */
    public static LibraryImp getLibInstance() {
        if (libInstance == null)
            libInstance = new LibraryImp();
        return libInstance;
    }

    @Override
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

    /**
     * This method replaces updates certain fields of an entity by their new values specified in an instance of EntityUpdate class
     * <p>There are probably numerous ways to implement updating of an object.
     * It was deliberately written this way so the programmer would get his hands dirty on the subject of "Reflection".
     * The generalized code makes it easy for it to be used for any type of entity without having to change anything.
     * The using method just needs to create an object from type EntityUpdate with the fields that are to be changed in the target entity.
     * </p>
     *
     * @param e      The entity that is to be updated based on the fields inside EntityUpdate parameter
     * @param update An object containing the fields that are to be updated and their new value
     * @throws Exception The author can not come up with a scenario in which an IllegalAccessException would be thrown but as better safe than sorry, this situation is semi-handled by throwing an Exception
     */
    @Override
    public void update(Entity e, EntityUpdate update) throws Exception {

        Field[] changes = update.getClass().getDeclaredFields();
        HashMap<String, Field> entityFields = getAllFields(e.getClass());


        for (Field field : changes) {
            try {

                Field toBeChangedField = entityFields.get(field.getName());
                if (toBeChangedField == null)
                    throw new NoSuchFieldException();

                toBeChangedField.setAccessible(true);
                field.setAccessible(true);

                toBeChangedField.set(e, field.get(update));

            } catch (NoSuchFieldException exception) {
                continue;
            } catch (IllegalAccessException exception) {
                throw new Exception();
            }
        }


    }

    /**
     * @param classObject the class object of the class that its fields are to be extracted
     * @return a Map containing all the fields including inherited fields of classObject's corresponding class with their name as keys
     */
    public HashMap<String, Field> getAllFields(Class classObject) {
        if (classObject == null)
            return new HashMap<>();

        HashMap<String, Field> fieldsBuffer = getAllFields(classObject.getSuperclass());

        for (Field field : classObject.getDeclaredFields())
            fieldsBuffer.put(field.getName(), field);

        return fieldsBuffer;

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
        if (!member.hasBorrowed(book))
            throw new InvalidRequestedActionException("This user has not borrowed this book");
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
