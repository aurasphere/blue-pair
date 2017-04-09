/**
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
package co.aurasphere.bluepair.bluetooth;

import android.bluetooth.BluetoothDevice;

/**
 * Callback for handling Bluetooth events.
 *
 * @author Donato Rimenti
 */
public interface BluetoothDiscoveryDeviceListener {

    /**
     * Called when a new device has been found.
     *
     * @param device the device found.
     */
    void onDeviceDiscovered(BluetoothDevice device);

    /**
     * Called when device discovery starts.
     */
    void onDeviceDiscoveryStarted();

    /**
     * Called on creation to inject a {@link BluetoothController} component to handle Bluetooth.
     *
     * @param bluetooth the controller for the Bluetooth.
     */
    void setBluetoothController(BluetoothController bluetooth);

    /**
     * Called when discovery ends.
     */
    void onDeviceDiscoveryEnd();

    /**
     * Called when the Bluetooth status changes.
     */
    void onBluetoothStatusChanged();

    /**
     * Called when the Bluetooth has been enabled.
     */
    void onBluetoothTurningOn();

    /**
     * Called when a device pairing ends.
     */
    void onDevicePairingEnded();

}
