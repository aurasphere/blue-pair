package co.aurasphere.bluepair;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import co.aurasphere.bluepair.bluetooth.BluetoothController;
import co.aurasphere.bluepair.view.DeviceRecyclerViewAdapter;
import co.aurasphere.bluepair.view.ListInteractionListener;

public class MainActivity extends AppCompatActivity implements ListInteractionListener<BluetoothDevice> {

    private BluetoothController bluetooth;

    private ProgressBar progressBar;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Sets up the RecyclerView.
        DeviceRecyclerViewAdapter recyclerViewAdapter = new DeviceRecyclerViewAdapter(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);

        // Sets up the bluetooth controller.
        this.bluetooth = new BluetoothController(this, recyclerViewAdapter);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.device_discovery_started, Snackbar.LENGTH_SHORT).show();

                // Starts the discovery.
                bluetooth.startDiscovery();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            showAbout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAbout() {
        // Inflate the about message contents
        View messageView = getLayoutInflater().inflate(R.layout.about, null, false);

        // When linking text, force to always use default color. This works
        // around a pressed color state bug.
        TextView textView = (TextView) messageView.findViewById(R.id.about_credits);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(R.string.app_name);
        builder.setView(messageView);
        builder.create();
        builder.show();
    }

    @Override
    public void onItemClick(BluetoothDevice device) {
        bluetooth.pair(device);
    }

    @Override
    public void startProgress() {
        this.progressBar.setVisibility(View.VISIBLE);

        // Changes the button icon.
        fab.setImageResource(R.drawable.ic_bluetooth_searching_white_24dp);

    }

    @Override
    public void endProgress(boolean discoveryEnded) {
        if (this.progressBar.getVisibility() == View.VISIBLE) {
            this.progressBar.setVisibility(View.GONE);
        }
        // If discovery has ended, changes the button icon.
        if (discoveryEnded) {
            fab.setImageResource(R.drawable.ic_bluetooth_white_24dp);
        }
    }

    @Override
    protected void onDestroy() {
        bluetooth.close();
        super.onDestroy();
    }

}
