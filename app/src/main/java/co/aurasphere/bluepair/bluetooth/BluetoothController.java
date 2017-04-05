package co.aurasphere.bluepair.bluetooth;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.Closeable;

/**
 * Class for handling Bluetooth connection.
 *
 * @author Donato Rimenti
 */
public class BluetoothController implements Closeable {

    /**
     * Tag string used for logging.
     */
    private static final String TAG = "BluetoothManager";

    /**
     * Interface for Bluetooth OS services.
     */
    private final BluetoothAdapter bluetooth;

    /**
     * Class used to handle communication with OS about Bluetooth system events.
     */
    private final BroadcastReceiverDelegator broadcastReceiverDelegator;

    /**
     * The activity which is using this controller.
     */
    private final Activity context;

    /**
     * Instantiates a new BluetoothController.
     *
     * @param context  the activity which is using this controller.
     * @param listener a callback for handling Bluetooth events.
     */
    public BluetoothController(Activity context, BluetoothDiscoveryDeviceListener listener) {
        this.context = context;
        this.bluetooth = BluetoothAdapter.getDefaultAdapter();
        this.broadcastReceiverDelegator = new BroadcastReceiverDelegator(context, listener, this);
    }

    /**
     * Checks if the Bluetooth is already enabled on this device.
     *
     * @return true if the Bluetooth is on, false otherwise.
     */
    private boolean isBluetoothEnabled() {
        return bluetooth.isEnabled();
    }

    /**
     * Starts the discovery of new Bluetooth devices nearby.
     */
    public void startDiscovery() {
        // Enables the bluetooth (if it's not already enabled) and starts discovery.
        if (!isBluetoothEnabled()) {
            Log.d(TAG, "Bluetooth disabled. Enabling.");
            bluetooth.enable();
        }

        // This line of code is very important. In Android >= 6.0 you have to ask for the runtime permission as well in order for the
        // discovery to get the devices ids. If you don't do this, the discovery won't find any device.
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
        }

        Log.d(TAG, "Bluetooth starting discovery.");
        broadcastReceiverDelegator.onDeviceDiscoveryStarted();
        bluetooth.startDiscovery();
    }

    /**
     * Performs the device pairing.
     *
     * @param device the device to pair with.
     * @return true if the pairing was successful, false otherwise.
     */
    public boolean pair(BluetoothDevice device) {
        // Stops the discovery and then creates the pairing.
        Log.d(TAG, "Bluetooth cancelling discovery.");
        bluetooth.cancelDiscovery();
        Log.d(TAG, "Bluetooth bonding with device: " + deviceToString(device));
        boolean outcome = device.createBond();
        Log.d(TAG, "Bounding outcome : " + outcome);
        return outcome;
    }

    /**
     * Checks if a device is already paired.
     *
     * @param device the device to check.
     * @return true if it is already paired, false otherwise.
     */
    public boolean isAlreadyPaired(BluetoothDevice device) {
        return bluetooth.getBondedDevices().contains(device);
    }

    /**
     * Converts a BluetoothDevice to its String representation.
     *
     * @param device the device to convert to String.
     * @return a String representation of the device.
     */
    public static String deviceToString(BluetoothDevice device) {
        return "[Address: " + device.getAddress() + ", Name: " + device.getName() + "]";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        this.broadcastReceiverDelegator.close();
    }

}
