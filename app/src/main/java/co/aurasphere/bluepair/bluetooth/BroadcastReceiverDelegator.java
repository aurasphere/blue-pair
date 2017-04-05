package co.aurasphere.bluepair.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.io.Closeable;

/**
 * Class used to handle communication with the OS about Bluetooth system events.
 * Created by Donato on 02/04/2017.
 *
 * @author Donato Rimenti
 */
public class BroadcastReceiverDelegator extends BroadcastReceiver implements Closeable {

    /**
     * Callback for Bluetooth events.
     */
    private final BluetoothDiscoveryDeviceListener listener;

    /**
     * Tag string used for logging.
     */
    private final String TAG = "BroadcastReceiver";

    /**
     * The context of this object.
     */
    private final Context context;

    /**
     * Instantiates a new BroadcastReceiverDelegator.
     *
     * @param context   the context of this object.
     * @param listener  a callback for handling Bluetooth events.
     * @param bluetooth a controller for the Bluetooth.
     */
    public BroadcastReceiverDelegator(Context context, BluetoothDiscoveryDeviceListener listener, BluetoothController bluetooth) {
        this.listener = listener;
        this.context = context;
        this.listener.setBluetoothController(bluetooth);

        // Register for broadcasts when a device is discovered.
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        context.registerReceiver(this, filter);
    }

    /**
     * Called when a new Intent is received from the OS.
     *
     * @param context the current context.
     * @param intent  the intent received.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "Incoming intent : " + action);
        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            // Discovery has found a device. Get the BluetoothDevice
            // object and its info from the Intent.
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            Log.d(TAG, "Device discovered! " + BluetoothController.deviceToString(device));
            listener.onDeviceDiscovered(device);
        }
        if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            // Discovery has ended.
            Log.d(TAG, "Discovery ended.");
            listener.onDiscoveryEnd();
        }
    }

    /**
     * Called when device discovery starts.
     */
    public void onDeviceDiscoveryStarted() {
        listener.onDeviceDiscoveryStarted();
    }

    /**
     * Disposes this object.
     */
    @Override
    public void close() {
        context.unregisterReceiver(this);
    }
}
