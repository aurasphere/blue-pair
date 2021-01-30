package co.aurasphere.bluepair.service;

import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import co.aurasphere.bluepair.R;
import co.aurasphere.bluepair.bluetooth.BluetoothController;

/**
 * Background service extension for Blue Pair. This class manages the actual working thread which is
 * {@link BluetoothServiceThread}.
 *
 * @author Donato Rimenti
 */
public class BluePairService extends Service {

    /**
     * Logger tag.
     */
    private static final String TAG = "BluePairService";

    /**
     * Intent action to stop the service.
     */
    public static final String ACTION_STOP_SERVICE = "STOP_SERVICE";

    /**
     * Intent action to start the service.
     */
    public static final String ACTION_START_SERVICE = "START_SERVICE";

    /**
     * ID of the service notification. Random number. Important, can't be 0.
     */
    public static final int NOTIFICATION_ID = 1;

    /**
     * Background working thread.
     */
    private BluetoothServiceThread workingThread;

    /**
     * Controller for Bluetooth operations.
     */
    private BluetoothController bluetooth;

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // Does nothing.
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Gets the command this service should perform. If null, the service won't start.
        String action = intent.getAction();
        Log.d(TAG, "onStartCommand with action: " + action);
        if (action == null) {
            Log.e(TAG, "No action in intent");

            // Block the service before starting.
            stopSelf();
            return START_NOT_STICKY;
        }

        // Lazy init.
        if (workingThread == null) {
            this.bluetooth = new BluetoothController(this,
                    BluetoothAdapter.getDefaultAdapter(), new BluePairServiceListener());
            this.workingThread = new BluetoothServiceThread(this.bluetooth);
        }

        // Switch according to the received command.
        switch (action) {
            case ACTION_START_SERVICE:
                startService();
                break;
            case ACTION_STOP_SERVICE:
                stopService();
                break;
            default:
                Log.e(TAG, "Unrecognized action in intent");
        }

        // Returns a default code.
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * Stops this service.
     */
    private synchronized void stopService() {
        // Service is already stopped.
        if (!this.workingThread.isRunning()) {
            Toast.makeText(this, "Service is already stopped!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "Stopping service...", Toast.LENGTH_SHORT).show();
        this.workingThread.setRunning(false);

        // Closes the Bluetooth.
        if (bluetooth != null) {
            bluetooth.close();
        }

        // Removes the notification and stops itself.
        stopForeground(true);
        stopSelf();
    }

    /**
     * Starts this service.
     */
    private synchronized void startService() {
        // Service is already started.
        if (this.workingThread.isRunning()) {
            Toast.makeText(this, "Service is already running!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Starts the worker thread.
        Toast.makeText(this, "Starting service...", Toast.LENGTH_SHORT).show();
        this.workingThread.setRunning(true);
        this.workingThread.start();

        // Builds the intent to launch on the notification button pressed.
        Intent stopBluetoothIntent = new Intent(this, BluePairService.class);
        stopBluetoothIntent.setAction(ACTION_STOP_SERVICE);
        PendingIntent stopBluetoothPendingIntent =
                PendingIntent.getService(this, 0, stopBluetoothIntent, 0);

        // Builds the notification.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_bluetooth_notification)
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setContentTitle("Blue Pair")
                .setContentText("Scanning in progress")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(stopBluetoothPendingIntent)
                .addAction(R.drawable.ic_stop_notification, getString(R.string.bluetooth_service_stop),
                        stopBluetoothPendingIntent);

        // Starts the service in foreground (high priority) with the given notification.
        startForeground(NOTIFICATION_ID, builder.build());
    }

}