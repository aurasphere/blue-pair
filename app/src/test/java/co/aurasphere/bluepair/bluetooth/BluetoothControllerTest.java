/*
 * MIT License
 * <p>
 * Copyright (c) 2017 Donato Rimenti
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package co.aurasphere.bluepair.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.widget.Toast;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.util.HashSet;
import java.util.Set;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;

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

    /**
     * When try call discovering method on bluetooth should check that is called.
     * @throws Exception if something error happened.
     */
    @Test
    public void whenTryCallDiscoveringMethodOnBluetoothShouldCheckThatIsCalled() throws Exception {
        Activity activity = mock(Activity.class);
        BluetoothDiscoveryDeviceListener listener = mock(BluetoothDiscoveryDeviceListener.class);
        BluetoothAdapter adapter = mock(BluetoothAdapter.class);
        mockStatic(BluetoothAdapter.class);
        when(BluetoothAdapter.getDefaultAdapter()).thenReturn(adapter);
        BluetoothController controller = new BluetoothController(activity, adapter, listener);
        controller.isDiscovering();
        verify(adapter).isDiscovering();
    }


    /**
     * When try get pair status of null device should check that method throw exception.
     * @throws Exception if some error happened.
     */
    @Test(expected = IllegalStateException.class)
    public void whenTryBoundingDeviceButDeviceIsNullShouldCheckThatThrowException() throws Exception {
        Activity activity = mock(Activity.class);
        BluetoothDiscoveryDeviceListener listener = mock(BluetoothDiscoveryDeviceListener.class);
        BluetoothAdapter adapter = mock(BluetoothAdapter.class);
        mockStatic(BluetoothAdapter.class);
        when(BluetoothAdapter.getDefaultAdapter()).thenReturn(adapter);
        BluetoothController controller = new BluetoothController(activity, adapter, listener);
        controller.getPairingDeviceStatus();
    }

    /**
     * When try get device name should check that all is ok.
     * @throws Exception if some error happened.
     */
    @Test
    public void whenTryGetDeviceNameShouldCheckThatAllIsOK() throws Exception {
        BluetoothDevice device = mock(BluetoothDevice.class);
        when(device.getName()).thenReturn("android");
        assertThat(BluetoothController.getDeviceName(device), is("android"));
    }

    /**
     * When try get device name but it is null should check that controller return address.
     * @throws Exception if some error happened.
     */
    @Test
    public void whenTryGetDeviceNameButItIsNullShouldCheckThatControllerReturnAddress() throws Exception {
        BluetoothDevice device = mock(BluetoothDevice.class);
        when(device.getName()).thenReturn(null);
        when(device.getName()).thenReturn("08-ED-B9-49-B2-E5");
        assertThat(BluetoothController.getDeviceName(device), is("08-ED-B9-49-B2-E5"));
    }

    /**
     * When try check that devices already paired should check that method works correct.
     * @throws Exception if some error happened.
     */
    @Test
    public void whenTryCheckThatDevicesAlreadyPairedShouldCheckThatMethodWorksCorrect() throws Exception {
        Activity activity = mock(Activity.class);
        Set<BluetoothDevice> devices = new HashSet<>();
        BluetoothDevice device = mock(BluetoothDevice.class);
        devices.add(device);
        BluetoothDiscoveryDeviceListener listener = mock(BluetoothDiscoveryDeviceListener.class);
        BluetoothAdapter adapter = mock(BluetoothAdapter.class);
        mockStatic(BluetoothAdapter.class);
        when(BluetoothAdapter.getDefaultAdapter()).thenReturn(adapter);
        when(adapter.getBondedDevices()).thenReturn(devices);
        BluetoothController controller = new BluetoothController(activity, adapter, listener);
        assertThat(controller.isAlreadyPaired(device), is(true));
    }


    /**
     * When try cast device to string should check that all is ok.
     * @throws Exception if some error happened.
     */
    @Test
    public void whenTryCastDeviceToStringShouldCheckThatAllIsOk() throws Exception {
        BluetoothDevice device = mock(BluetoothDevice.class);
        when(device.getName()).thenReturn("device");
        when(device.getAddress()).thenReturn("08-ED-B9-49-B2-E5");
        String actual = "[Address: 08-ED-B9-49-B2-E5, Name: device]";
        assertEquals(actual, BluetoothController.deviceToString(device));
    }

    /**
     * When turn on bluetooth and schedule discovery should check that bluetooth enabled.
     * @throws Exception if some error happeened.
     */
    @Test
    public void whenTurnOnBluetoothAndScheduleDiscoveryShouldCheckThatBTenabled() throws Exception {
        Activity activity = mock(Activity.class);
        BluetoothDiscoveryDeviceListener listener = mock(BluetoothDiscoveryDeviceListener.class);
        BluetoothAdapter adapter = mock(BluetoothAdapter.class);
        mockStatic(BluetoothAdapter.class);
        when(BluetoothAdapter.getDefaultAdapter()).thenReturn(adapter);
        BluetoothController controller = new BluetoothController(activity, adapter, listener);
        controller.turnOnBluetoothAndScheduleDiscovery();
        verify(adapter).enable();
    }
}
