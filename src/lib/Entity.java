package lib;

/**
 * All the library elements are subclasses of this interface
 * <p> * This architecture makes the library easier to maintain. It provides loose coupling between classes and
 * it also makes it possible to write generalized codes to avoid rewriting logics for different components</p>
 */
public interface Entity {
    /**
     * @return the id assigned to the entity when it was created.
     */
    String getID();

    /**
     * This method is used to find out whether a string can be found in the entity's fields
     *
     * @param key This is the key word that the entity is to be searched for
     * @return true if the entity contains the key word in a way and false if not
     */
    boolean hasSearchedKey(String key);

    /**
     * This method is used to print out the entity's information on the screen.
     */
    void showOnConsole();
}