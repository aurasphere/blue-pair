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
 * Created by Donato on 02/04/2017.
 */
public class BroadcastReceiverDelegator extends BroadcastReceiver implements Closeable {

    private final BluetoothDiscoveryDeviceListener listener;

    private final String TAG = "BroadcastReceiver";

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

    private final Context context;

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

    public void onDeviceDiscoveryStarted(){
        listener.onDeviceDiscoveryStarted();
    }

    @Override
    public void close(){
        context.unregisterReceiver(this);
    }
}
