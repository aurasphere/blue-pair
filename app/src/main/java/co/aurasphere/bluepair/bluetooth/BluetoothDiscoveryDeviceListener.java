package co.aurasphere.bluepair.bluetooth;

import android.bluetooth.BluetoothDevice;

/**
 * Created by Donato on 02/04/2017.
 */
public interface BluetoothDiscoveryDeviceListener {

    void onDeviceDiscovered(BluetoothDevice device);

    void onDeviceDiscoveryStarted();

    void setBluetoothController(BluetoothController bluetooth);

    void onDiscoveryEnd();
}
