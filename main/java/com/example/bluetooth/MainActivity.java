package com.example.bluetooth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;


    TextView mStatusBlueTv, mPairedTv;
    ImageView mBlueTv;
    Button mOnBtn, mOffbtn, mDiscoverablebtn, mPairedbtn;

    BluetoothAdapter mBlueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStatusBlueTv = findViewById(R.id.statusBluetoothTv);
        mPairedTv = findViewById(R.id.pairedTv);
        mBlueTv = findViewById(R.id.bluetoothTv);
        mOnBtn = findViewById(R.id.onBtn);
        mOffbtn = findViewById(R.id.offBtn);
        mDiscoverablebtn = findViewById(R.id.discoverableBtn);
        mPairedbtn = findViewById(R.id.pairedBtn);

        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBlueAdapter == null) {
            mStatusBlueTv.setText("Bluetooth is not available");
        } else {
            mStatusBlueTv.setText("Bluetooth is available");
        }

        if (mBlueAdapter.isEnabled()) {
            mBlueTv.setImageResource(R.drawable.ic_action_on);
        } else {
            mBlueTv.setImageResource(R.drawable.ic_action_off);
        }


        mOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBlueAdapter.isEnabled()) {
                    showToast("Turning On BlueTooth...");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUEST_ENABLE_BT);
                } else {
                    showToast("BlueTooth is already on");
                }
            }
        });

        mDiscoverablebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBlueAdapter.isEnabled()) {
                    showToast("Making your device discoverable");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent, REQUEST_DISCOVER_BT);
                }
            }
        });

        mOffbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBlueAdapter.isEnabled()) {
                    mBlueAdapter.disable();
                    showToast("Turning Off BlueTooth...");
                    mBlueTv.setImageResource(R.drawable.ic_action_off);
                } else {
                    showToast("BlueTooth is already off");
                }
            }
        });

        mPairedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBlueAdapter.isEnabled()) {
                    mPairedTv.setText("paired devices");
                    Set<BluetoothDevice> devices = mBlueAdapter.getBondedDevices();
                    for (BluetoothDevice device : devices) {
                        mPairedTv.append("\nDevices" + device.getName() + "," + device);
                    }
                } else {
                    showToast("Turn on BlueTooth to get paired devices");
                }
            }
        });
    }

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            switch (requestCode) {
                case REQUEST_ENABLE_BT:
                    if (resultCode == RESULT_OK) {
                        mBlueTv.setImageResource(R.drawable.ic_action_on);
                        showToast("BlueTooth is On");
                    } else
                        showToast("Couldn't on Bluetooth");
            }
            super.onActivityResult(requestCode, resultCode, data);

        }

        private void showToast (String msg){
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
}