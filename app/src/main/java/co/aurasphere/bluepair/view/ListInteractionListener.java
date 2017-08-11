/*
 * MIT License
 * <p>
 * Copyright (c) 2017 Donato Rimenti
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
    void startLoading();

    /**
     * Called when one or all the list elements have been fetched.
     *
     * @param partialResults true if the results are partial and
     *                       the fetching is still going, false otherwise.
     */
    void endLoading(boolean partialResults);

    /**
     * Called to dismiss a loading dialog.
     *
     * @param error   true if an error has occurred, false otherwise.
     * @param element the list element processed.
     */
    void endLoadingWithDialog(boolean error, T element);

}
