package co.aurasphere.bluepair.service;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;

import com.permissioneverywhere.PermissionEverywhere;

import co.aurasphere.bluepair.R;
import co.aurasphere.bluepair.bluetooth.BluetoothController;
import co.aurasphere.bluepair.bluetooth.BluetoothDiscoveryDeviceListener;

/**
 * Listener for the {@link BluetoothServiceThread} discovery events.
 *
 * @author Donato Rimenti
 */
class BluePairServiceListener implements BluetoothDiscoveryDeviceListener {

    /**
     * Logger tag.
     */
    private static final String TAG = "BluePairServiceListener";

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDeviceDiscovered(BluetoothDevice device) {
        Log.i(TAG, "onDeviceDiscovered ->" + device);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDeviceDiscoveryStarted() {
        Log.i(TAG, "onDeviceDiscoveryStarted");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBluetoothController(BluetoothController bluetooth) {
        Log.i(TAG, "setBluetoothController");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDeviceDiscoveryEnd() {
        Log.i(TAG, "onDeviceDiscoveryEnd");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBluetoothStatusChanged() {
        Log.i(TAG, "onBluetoothStatusChanged");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBluetoothTurningOn() {
        Log.i(TAG, "onBluetoothTurningOn");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDevicePairingEnded() {
        Log.i(TAG, "onDevicePairingEnded");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getBluetoothPermission(Context context) {
        Log.i(TAG, "Getting Bluetooth permission for the service");
        try {
            PermissionEverywhere.getPermission(context,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    1,
                    "Notification title",
                    "This app needs  a write permission",
                    R.mipmap.ic_launcher)
                    .call();
        } catch (InterruptedException e) {
            Log.e(TAG, "Exception while requesting permission", e);
            Thread.currentThread().interrupt();
        }
    }

}