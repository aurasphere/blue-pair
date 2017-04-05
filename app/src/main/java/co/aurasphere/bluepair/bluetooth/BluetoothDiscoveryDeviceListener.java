package co.aurasphere.bluepair.bluetooth;

import android.bluetooth.BluetoothDevice;

/**
 * Callback for handling Bluetooth events.
 *s
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
    void onDiscoveryEnd();
}
