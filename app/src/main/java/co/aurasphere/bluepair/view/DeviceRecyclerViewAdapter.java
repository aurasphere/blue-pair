package co.aurasphere.bluepair.view;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import co.aurasphere.bluepair.R;
import co.aurasphere.bluepair.bluetooth.BluetoothController;
import co.aurasphere.bluepair.bluetooth.BluetoothDiscoveryDeviceListener;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link BluetoothDevice} and makes a call to the
 * specified {@link ListInteractionListener}.
 */
public class DeviceRecyclerViewAdapter extends RecyclerView.Adapter<DeviceRecyclerViewAdapter.ViewHolder> implements BluetoothDiscoveryDeviceListener {

    private final List<BluetoothDevice> devices;
    private final ListInteractionListener<BluetoothDevice> listener;
    private BluetoothController bluetooth;

    public DeviceRecyclerViewAdapter(ListInteractionListener<BluetoothDevice> listener) {
        devices = new ArrayList<>();
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_device_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = devices.get(position);
        holder.mImageView.setImageResource(getDeviceIcon(devices.get(position)));
        holder.mDeviceNameView.setText(devices.get(position).getName());
        holder.mDeviceAddressView.setText(devices.get(position).getAddress());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listener.onItemClick(holder.mItem);
                }
            }
        });
    }

    private final int getDeviceIcon(BluetoothDevice device){
        if(bluetooth.isAlreadyPaired(device)){
            return R.drawable.ic_bluetooth_connected_black_24dp;
        } else {
            return R.drawable.ic_bluetooth_disabled_black_24dp;
        }
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    @Override
    public void onDeviceDiscovered(BluetoothDevice device) {
        listener.endProgress(false);
        devices.add(device);
        notifyDataSetChanged();
    }

    @Override
    public void onDeviceDiscoveryStarted() {
        devices.clear();
        notifyDataSetChanged();
        listener.startProgress();
    }

    @Override
    public void setBluetoothController(BluetoothController bluetooth) {
        this.bluetooth = bluetooth;
    }

    @Override
    public void onDiscoveryEnd() {
        listener.endProgress(true);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImageView;
        public final TextView mDeviceNameView;
        public final TextView mDeviceAddressView;
        public BluetoothDevice mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.device_icon);
            mDeviceNameView = (TextView) view.findViewById(R.id.device_name);
            mDeviceAddressView = (TextView) view.findViewById(R.id.device_address);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + BluetoothController.deviceToString(mItem) + "'";
        }
    }
}
