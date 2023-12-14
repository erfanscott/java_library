package lib;

/**
 * This interface is used to create the states of the library UI which are going to be used when implementing the state design pattern
 */
public interface LibState {
    /**
     * This method is called on each state to run the codes that are supposed to be run when the library is at that state.
     *
     * @return the next state that the library is supposed to go to based on what happens during this state.
     */
    LibState action();
}
