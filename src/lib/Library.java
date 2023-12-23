package lib;

import lib.exceptions.BadTargetEntityTypeException;
import lib.exceptions.EntityNotFoundException;
import lib.exceptions.InvalidRequestedActionException;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This interface describes the common behaviors of libraries.
 * <p>It makes classes reusable for different implementations of the library and makes codes easier to maintain</p>
 *
 * @see Entity
 */
public interface Library {

    /**
     * Adds a new element to the library or replaces the existing element with the same ID as "e" with "e".
     *
     * @param e The element to be saved in the library.
     */
    void save(Entity e);

    /**
     * @param e The element to be deleted from the library/
     * @throws EntityNotFoundException if "e" is not found in the library
     */
    void delete(Entity e) throws EntityNotFoundException;

    /**
     * @param e      The entity that is to be updated based on the fields inside EntityUpdate parameter
     * @param update An object containing the fields that are to be updated and their new value
     * @throws Exception when the implementing class decides to
     */
    void update(Entity e, EntityUpdate update) throws Exception;


    /**
     * @param book   the book to be borrowed.
     * @param member the user that is requesting for the book.
     * @throws InvalidRequestedActionException if the book is not available at that moment
     */
    void borrowBook(Book book, Member member) throws InvalidRequestedActionException;

    /**
     * @param book   the book to be returned.
     * @param member the user that is requesting for the book.
     * @throws InvalidRequestedActionException if the member has not borrowed this book.
     */
    void returnBook(Book book, Member member) throws InvalidRequestedActionException;


    /**
     * @param id the id of the item that is to be retrieved from the library.
     * @return the element associated with "id" with reference type of Entity.
     * @throws EntityNotFoundException if the entity doesn't exist in the library.
     */
    Entity retrieve(String id) throws EntityNotFoundException;

    /**
     * @param id   the id of the item that is to be retrieved from the library
     * @param type the type that the corresponding entity for "id" is supposed to be an instance of
     * @param <T>  the generic parameter that is used to downcast the retrieved entity to be of type "type"
     * @return the corresponding entity with reference type of "type"
     * @throws EntityNotFoundException      if the element was not in the library at all
     * @throws BadTargetEntityTypeException if the element associated with "id" is not of type "type"
     */
    <T> T retrieve(String id, Class<T> type) throws EntityNotFoundException, BadTargetEntityTypeException;

    /**
     * @param key        the search key
     * @param targetType the type of entities that are to be searched for the "key"
     * @return an array of entities that are an instance of "target type" that in some way include the search key
     */
    ArrayList<Entity> search(String key, Class<?> targetType);


    /**
     * Sets the current state of the library that is incorporating the state pattern.
     *
     * @param libState the state that the library is to go to
     */
    public void setState(LibState libState);

    /**
     * initiates the user interacting with the library
     */
    public void start();


    /**
     * @return the scanner that the library utilizes for user interactions during the library's life-time
     */
    Scanner getScanner();

    /**
     * @return a unique string ID that will be assigned to new entities of the library
     */
    String uID();

    /**
     * is used to receive user's input until the user enters an integer number inside the acceptable range
     *
     * @param from the start of the acceptable range
     * @param to   the end of the acceptable range
     * @return the user's acceptable input
     */
    int getUserSelectedItem(int from, int to);


    /**
     * It prints out all the elements of the library for the dear user to see what the library looks like.
     */
    void showLibrary();
}
