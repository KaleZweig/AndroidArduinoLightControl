package com.example.kale.lightswitch;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.UUID;


public class MainActivity extends Activity {

    private BluetoothAdapter mAdapter;
    private BluetoothDevice btDevice;
    private BluetoothSocket btSocket;
    private OutputStream btOutStream;
    private Button forwardButton;

    private int motorSpeed = 150;
    private Button backButton;
    private Button leftButton;
    private Button rightButton;
    private Button connectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        btDevice = mAdapter.getRemoteDevice("20:14:08:26:26:71");

        connect();


        connectButton = (Button) findViewById(R.id.reconnect);
        connectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                connect();
            }
        } );

/*        final Button cnctbtn = (Button) findViewById(R.id.reconnect);
        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.relout);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,   RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE);
        rl.setLayoutParams(params);*/

        final ToggleButton pushitrealgood = (ToggleButton) findViewById(R.id.tglbtn);

        pushitrealgood.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (pushitrealgood.isChecked()) {
                    writeToBt(0);
                } else {
                    writeToBt(1);
                }
            }
        });



    }


    private void connect()
    {
        try {

            try {
                if(btSocket != null){
                    btSocket.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }


            btSocket = btDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"));
            btSocket.connect();
            btOutStream = btSocket.getOutputStream();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToBt(int sendover)
    {
        byte by = (byte) sendover;

        try {
            btOutStream.write(by);
            btOutStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
