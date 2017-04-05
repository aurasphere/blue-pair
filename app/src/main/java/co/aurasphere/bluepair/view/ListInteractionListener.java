package co.aurasphere.bluepair.view;

/**
 * Created by Donato on 02/04/2017.
 */

public interface ListInteractionListener<T> {

    void onItemClick(T item);

    void startProgress();

    void endProgress(boolean discoveryEnded);
}
