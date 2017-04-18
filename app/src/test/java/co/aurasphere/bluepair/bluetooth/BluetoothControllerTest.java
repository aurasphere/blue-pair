package co.aurasphere.bluepair.bluetooth;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.widget.Toast;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Unit test for Bluetooth Controller.
 * @author evrnsky
 * @version 0.1
 * @since 19.04.2017
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({BluetoothAdapter.class, Toast.class})
public class BluetoothControllerTest {

    /**
     * When try check that bluetooth is enable should check that method return correct data.
     * @throws Exception if something error happened.
     */
    @Test
    public void whenTryCheckThatBluetoothIsEnabledShouldCheckThatMethodReturnCorrectData() throws Exception {
        Activity activity = mock(Activity.class);
        BluetoothDiscoveryDeviceListener listener = mock(BluetoothDiscoveryDeviceListener.class);
        BluetoothAdapter adapter = mock(BluetoothAdapter.class);
        mockStatic(BluetoothAdapter.class);
        when(BluetoothAdapter.getDefaultAdapter()).thenReturn(adapter);
        when(adapter.isEnabled()).thenReturn(true);
        BluetoothController controller = new BluetoothController(activity, adapter, listener);
        assertThat(controller.isBluetoothEnabled(), is(true));
    }

    /**
     * When try enable bluetooth should check that bluetooth adapter call enable method.
     * @throws Exception if something error happened.
     */
    @Test
    public void whenTryEnableBluetoothShouldCheckThatBluetoothAdapterCallEnableMethod() throws Exception {
        Activity activity = mock(Activity.class);
        BluetoothDiscoveryDeviceListener listener = mock(BluetoothDiscoveryDeviceListener.class);
        BluetoothAdapter adapter = mock(BluetoothAdapter.class);
        mockStatic(BluetoothAdapter.class);
        when(BluetoothAdapter.getDefaultAdapter()).thenReturn(adapter);
        BluetoothController controller = new BluetoothController(activity, adapter, listener);
        controller.turnOnBluetooth();
        verify(adapter).enable();
    }
}