package application.libraryImpstates;

import lib.Book;
import lib.Library;
import lib.LibState;
import lib.Member;

import java.util.Scanner;

/**
 * This class is used to run the corresponding codes when the library is to show the user the main menu
 *
 * @see LibState
 * @see Library
 */
public class Main implements LibState {
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
     * Constructor of the Main State objects.
     * It initializes the values of library and scanner fields.
     *
     * @param library the instance of the library that this state is supposed to interact with
     */
    public Main(Library library) {
        this.library = library;
        this.scanner = library.getScanner();
    }


    /**
     * This method displays the user interface for the main menu, receives the input, and decides on the next state
     *
     * @return the next state that {@link #library the library} is supposed to go to
     */
    @Override
    public LibState action() {
        LibState nextState;
        // menu selection range
        int menuFrom = 1;
        int menuTo = 4;
        System.out.println("1-Add/Delete/Edit/Show members");
        System.out.println("2-Add/Delete/Edit/Borrow/Return/Search books");
        System.out.println("3-Exit");
        System.out.println("4-see the library");
        System.out.print("Enter a number:");

        int selectedItem = library.getUserSelectedItem(menuFrom, menuTo);

        switch (selectedItem) {
            case 1:
                nextState = new Members(library);
                break;
            case 2:
                nextState = new Books(library);
                break;
            case 3:
                nextState = new Exit(library);
                break;
            case 4:
                library.showLibrary();
                nextState = new Main(library);
                break;
            default:
                nextState = new Main(library);
        }
        return nextState;
    }


}
