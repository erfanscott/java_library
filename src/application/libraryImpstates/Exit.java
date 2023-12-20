package application.libraryImpstates;

import lib.Book;
import lib.Library;
import lib.LibState;
import lib.Member;

import java.util.Scanner;

/**
 * This class is used to run the corresponding codes when the User wants to terminate the library.
 *
 * @see LibState
 * @see Library
 */
public class Exit implements LibState {
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
     * Constructor of the Exit State objects.
     * It initializes the values of library and scanner fields.
     *
     * @param library the instance of the library that this state is supposed to interact with
     */
    public Exit(Library library) {
        this.library = library;
        this.scanner = library.getScanner();
    }

    /**
     * It asks whether the user is sure about terminating the app and acts accordingly.
     * <p>If the user decides to terminate the app, it closes the scanner to prevent memory leaks and stops the app</p>
     * <p>If the user wants to stay in the app it returns Main state as the next state of the library</p>
     *
     * @return the Main state if the user decides to stay in the app
     */
    @Override
    public LibState action() {
        System.out.println("Are you sure you want to terminate the app? 1)Yes 2)No");

        int selectedItem = library.getUserSelectedItem(1, 2);

        if (selectedItem == 1) {
            scanner.close();
            System.exit(0);
        }
        return new Main(library);
    }
}
