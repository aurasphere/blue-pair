package co.aurasphere.bluepair.view;

/**
 * Interface that defines how to handle interaction with a RecyclerView list or one of its elements.
 * This class has a generic argument which should evaluate to the list's elements class.
 *
 * @author Donato Rimenti
 */
public interface ListInteractionListener<T> {

    /**
     * Called when a list element is clicked.
     *
     * @param item the clicked item.
     */
    void onItemClick(T item);

    /**
     * Called when the list elements are being fetched.
     */
    void onLoadingStarted();

    /**
     * Called when one or all the list elements have been fetched.
     *
     * @param fetchingEnded true if all the elements have been retrieved, false if the results are
     *                      partial and the fetching is still going.
     */
    void onLoadingEnded(boolean fetchingEnded);
}
