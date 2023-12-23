package application.libraryImpstates;

import lib.*;
import lib.exceptions.BadTargetEntityTypeException;
import lib.exceptions.EntityNotFoundException;

import java.util.Scanner;

/**
 * This class is used to run the corresponding codes when the library is performing member-related actions
 *
 * @see LibState
 * @see Book
 * @see Member
 * @see Library
 */
public class Members implements LibState {
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
     * Constructor of the Members State objects.
     * It initializes the values of library and scanner fields.
     *
     * @param library the instance of the library that this state is supposed to interact with
     */

    public Members(Library library) {
        this.library = library;
        this.scanner = library.getScanner();
    }


    /**
     * This method displays the user interface for member actions, receives the input, reacts to it and decides on the next state
     * This method calls the corresponding method that has been defined inside an inner class to handle user's selection
     * Based on the user's action on this state the proper value will be returned as "nextState"
     *
     * @return the next state that {@link #library the library} is supposed to go to
     */

    @Override
    public LibState action() {
        LibState nextState;
        // menu selection range
        int menuFrom = 1;
        int menuTo = 5;
        System.out.println("1-Add Member");
        System.out.println("2-Delete Member");
        System.out.println("3-Edit Member");
        System.out.println("4-Show Member");
        System.out.println("5-Return");
        System.out.print("Enter a number:");
        /**
         * implements Member action methods
         * <p>There might have been better solution to fulfill this objective but for
         * the sake of practice it was written this way</p>
         */
        class Inner {

            /**
             * Gets the member information from the user and calls the proper method from the library to add the member to the library and shows the member on screen
             */
            void addMember() {
                System.out.println("Enter your name:");
                String name = scanner.next();
                System.out.println("What is your gender? 1)Male 2)Female:");
                int selectedItem = library.getUserSelectedItem(1, 2);
                Gender gender = (selectedItem == 1) ? Gender.MALE : Gender.FEMALE;
                Member member = new Member(name, gender, library.uID());
                library.save(member);
                System.out.println("The Member has been successfully added:");
                member.showOnConsole();
            }

            /**
             * Gets the ID of the member, finds the member from the library and pass it to delete method of the library
             * <p>If the received ID is not associated with a member a BadTargetEntityTypeException will be caught and dealt with and
             * if no Entity with that ID exists in the library an EntityNotFoundException will be caught and dealt with </p>
             */
            void deleteMember() {
                try {
                    System.out.println("ID of the member you want to delete:");
                    String id = scanner.next();
                    Member member = library.retrieve(id, Member.class);
                    library.delete(member);
                    System.out.println("The member has been successfully deleted");
                } catch (EntityNotFoundException e) {
                    System.out.println("The member was not found");
                } catch (BadTargetEntityTypeException e) {
                    System.out.println("The member was not found");
                }
            }

            /**
             * Gets the ID of the member and their new information and calls the methods from the library to update the user.
             * <p>If the received ID is not associated with a member a BadTargetEntityTypeException will be caught and dealt with and
             * if no Entity with that ID exists in the library an EntityNotFoundException will be caught and dealt with </p>
             */
            void editMember() {

                try {
                    System.out.print("Enter your ID:");
                    String id = scanner.next();
                    Member member = library.retrieve(id, Member.class);
                    System.out.print("Enter your new name:");
                    String newName = scanner.next();
                    System.out.println("What is your new gender? 1)Male 2)Female:");
                    int selectedItem = library.getUserSelectedItem(1, 2);
                    Gender newGender = (selectedItem == 1) ? Gender.MALE : Gender.FEMALE;

                    library.update(member, new EntityUpdate() {
                        public String name = newName;
                        public Gender gender = newGender;
                    });

                    System.out.println("The Member has been successfully updated:");
                    member.showOnConsole();
                } catch (EntityNotFoundException e) {
                    System.out.println("The member was not found");
                } catch (BadTargetEntityTypeException e) {
                    System.out.println("The member was not found");
                } catch (Exception e) {
                    System.out.println("something went wrong, try again");
                }
            }

            /**
             * Gets the ID of the member, finds the member from the library and prints their information on the screen
             * <p>If the received ID is not associated with a member a BadTargetEntityTypeException will be caught and dealt with and
             * if no Entity with that ID exists in the library an EntityNotFoundException will be caught and dealt with </p>
             */
            void showMember() {
                try {
                    System.out.println("ID of the member you want see the details of:");
                    String id = scanner.next();
                    Member member = library.retrieve(id, Member.class);
                    member.showOnConsole();
                } catch (EntityNotFoundException e) {
                    System.out.println("The member was not found");
                } catch (BadTargetEntityTypeException e) {
                    System.out.println("The member was not found");
                }
            }
        }
        Inner inner = new Inner();

        int selectedItem = library.getUserSelectedItem(1, 5);

        switch (selectedItem) {
            case 1:
                inner.addMember();
                nextState = new Members(library);
                break;
            case 2:
                inner.deleteMember();
                nextState = new Members(library);
                break;
            case 3:
                inner.editMember();
                nextState = new Members(library);
                break;
            case 4:
                inner.showMember();
                nextState = new Members(library);
                break;
            case 5:
                nextState = new Main(library);
                break;
            default:
                nextState = new Members(library);
                ;
        }
        return nextState;
    }
}
