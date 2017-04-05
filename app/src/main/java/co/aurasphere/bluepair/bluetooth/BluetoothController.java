package co.aurasphere.bluepair.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

import java.io.Closeable;

/**
 * Created by Donato on 02/04/2017.
 */
public class BluetoothController implements Closeable {

    private static final String TAG = "BluetoothManager";

    private final BluetoothAdapter bluetooth;

    private final BroadcastReceiverDelegator broadcastReceiverDelegator;

    public BluetoothController(Activity context, BluetoothDiscoveryDeviceListener listener) {
        this.bluetooth = BluetoothAdapter.getDefaultAdapter();
        this.broadcastReceiverDelegator = new BroadcastReceiverDelegator(context, listener, this);
    }

    public boolean isBluetoothEnabled() {
        return bluetooth.isEnabled();
    }

    public void startDiscovery() {
        if (!isBluetoothEnabled()) {
            // Enables bluetooth and starts discovery.
            Log.d(TAG, "Bluetooth disabled. Enabling.");
            bluetooth.enable();

            // Waits for the bluetooth to fully start.
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Log.e(TAG, "Error while waiting for bluetooth enabling.", e);
            }
        }

        Log.d(TAG, "Bluetooth starting discovery.");
        broadcastReceiverDelegator.onDeviceDiscoveryStarted();
        bluetooth.startDiscovery();
    }

    public void pair(BluetoothDevice device) {
        // Stops the discovery and then creates the pairing.
        Log.d(TAG, "Bluetooth cancelling discovery.");
        bluetooth.cancelDiscovery();
        Log.d(TAG, "Bluetooth bonding with device: " + deviceToString(device));
        device.createBond();
    }

    public boolean isAlreadyPaired(BluetoothDevice device) {
        return bluetooth.getBondedDevices().contains(device);
    }

    public static String deviceToString(BluetoothDevice device) {
        return "[Address: " + device.getAddress() + ", Name: " + device.getName() + "]";
    }

    @Override
    public void close() {
        this.broadcastReceiverDelegator.close();
    }

}
