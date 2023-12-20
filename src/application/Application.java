package application;

import lib.Library;

/**
 * This is the skeleton of the library project
 * <p>
 * It initiates an instance of the library
 * </p>
 * <p>This version does not follow the separation of concerns principle in the UI department.<br/>
 * Patterns like MVC and MVP were found to be potential solutions but It was time to move on.
 * </p>
 *
 * @author Erfan Mirhoseini
 * @see LibraryImp
 * @see Library
 */

public class Application {
    public static void main(String[] args) {
        Library lib = LibraryImp.getLibInstance();
        lib.start();
    }
}
