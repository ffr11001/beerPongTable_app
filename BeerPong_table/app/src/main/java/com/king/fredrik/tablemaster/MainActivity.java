package com.king.fredrik.tablemaster;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends Activity {

    String TAG = "Test";

    BluetoothDevice mmDevice;
    BluetoothSocket mmSocket;
    InputStream mmInStream;
    OutputStream mmOutStream;

    ImageView imV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(!mBluetoothAdapter.isEnabled())
        {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                if(device.getName().equals("HC-06")){
                    System.out.println("Found: HC-06");
                    mmDevice = device;
                    break;
                }
            }
        }

        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard SerialPortService ID
        try {
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mmSocket.connect();
            System.out.println("Connected: HC-06");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mmOutStream = mmSocket.getOutputStream();
            System.out.println("Got: OUPUT_STREAM");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mmInStream = mmSocket.getInputStream();
            System.out.println("Got: INPUT_STREAM");
        } catch (IOException e) {
            e.printStackTrace();
        }

        final SeekBar sk=(SeekBar) findViewById(R.id.seekBar);
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {

                String init = "1";
                char temp = (char)(48+progress);
                String msg = init+Character.toString(temp);
                try {
                    mmOutStream.write(msg.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            mmSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Red_Blue(View view) {

        String init = "0";
        String msg = init+"0";
        try {
            mmOutStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Blue_Red(View view) {

        String init = "0";
        String msg = init+"1";
        try {
            mmOutStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void Green_Green(View view) {

        String init = "0";
        String msg = init+"2";
        try {
            mmOutStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void Take_Turn(View view) {

        String init = "0";
        String msg = init+"3";
        try {
            mmOutStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void Event(View view) {

        String init = "0";
        String msg = init+"4";
        try {
            mmOutStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void Madness(View view) {

        String init = "0";
        String msg = init+"5";
        try {
            mmOutStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void fadeInput(View view) {

        String init = "2";
        String msg = init+"0";
        try {
            mmOutStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void auxInput(View view) {

        String init = "2";
        String msg = init+"1";
        try {
            mmOutStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void micInput(View view) {

        String init = "2";
        String msg = init+"2";
        try {
            mmOutStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void Close (View view){

        finish();
    }

    public void Settings (View view){

        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

}
