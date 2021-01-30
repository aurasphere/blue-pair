package co.aurasphere.bluepair.service;

import android.os.SystemClock;
import android.util.Log;

import co.aurasphere.bluepair.bluetooth.BluetoothController;

/**
 * Worker thread for the {@link BluePairService}. Performs regular discoveries and sends the
 * callbacks to the {@link BluePairServiceListener}.
 *
 * @author Donato Rimenti
 */
public class BluetoothServiceThread extends Thread {

    /**
     * Logger tag.
     */
    private static final String TAG = "BluetoothServiceThread";

    /**
     * Delay between discovery iterations in milliseconds.
     */
    private static final long BLUETOOTH_DISCOVERY_DELAY = 10000;

    /**
     * Controller for Bluetooth related operations.
     */
    private BluetoothController bluetooth;

    /**
     * Whether this service is running or not.
     */
    private volatile boolean running;

    /**
     * Creates a new BluetoothServiceThread.
     *
     * @param bluetooth the controller used by this thread.
     */
    BluetoothServiceThread(BluetoothController bluetooth) {
        this.bluetooth = bluetooth;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        Log.i(TAG, "Starting BluetoothServiceThread");
        while (running) {
            Log.d(TAG, "Starting discovery loop");
            if (!bluetooth.isBluetoothEnabled()) {
                Log.i(TAG, "Turning on Bluetooth and looking for nearby devices");
                bluetooth.turnOnBluetoothAndScheduleDiscovery();
            } else {
                // Starts a new discovery only if there's no one currently in progress.
                if (!bluetooth.isDiscovering()) {
                    // Starts the discovery.
                    Log.i(TAG, "Looking for nearby devices");
                    bluetooth.startDiscovery();
                }
            }

            // Wait before starting again.
            SystemClock.sleep(BLUETOOTH_DISCOVERY_DELAY);
        }
    }

    /**
     * Gets {@link #running}.
     *
     * @return {@link #running}.
     */
    boolean isRunning() {
        return running;
    }

    /**
     * Sets {@link #running}.
     *
     * @param running {@link #running}.
     */
    void setRunning(boolean running) {
        this.running = running;
    }

}