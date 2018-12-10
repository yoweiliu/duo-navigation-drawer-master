package com.example.simon.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class DispositivosBT extends AppCompatActivity {

    //1)
    private static final String TAG="DispositivosBT";//<<<<PARTE A MODIFICAR>>>>

    ListView IdLista;

    public static String EXTRA_DEVICE_ADDRESS="device_address";

    private BluetoothAdapter mBtAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dispositivos_bt );
    }

    @Override
    public void onResume()
    {
        super.onResume();
        //---------------------------------------------------------------------------------------
        VerificarEstadoBT();

        mPairedDevicesArrayAdapter=new ArrayAdapter<String>(this, R.layout.number_dispositives);

        IdLista=(ListView) findViewById(R.id.IdLista);
        IdLista.setAdapter(mPairedDevicesArrayAdapter);
        IdLista.setOnItemClickListener(mDeviceClickListener);

        mBtAdapter=BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice>pairedDevices=mBtAdapter.getBondedDevices();

        if(pairedDevices.size()>0)
        {
            for(BluetoothDevice device : pairedDevices){
                mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }
    }


    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView av, View v, int arg2, long arg3) {

            // Obtener la dirección MAC del dispositivo, que son los últimos 17 caracteres en la vista
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            // Realiza un intent para iniciar la siguiente actividad
            // mientras toma un EXTRA_DEVICE_ADDRESS que es la dirección MAC.
            Intent i = new Intent(DispositivosBT.this, MainActivity.class);//<-<- PARTE A MODIFICAR >->->
            i.putExtra(EXTRA_DEVICE_ADDRESS, address);
            startActivity(i);
        }
    };

    private void VerificarEstadoBT(){
        mBtAdapter=BluetoothAdapter.getDefaultAdapter();
        if(mBtAdapter==null){
            Toast.makeText(getBaseContext(), "E1 dispositivo no soporta Bluetooth", Toast.LENGTH_SHORT);
        }else{
            if(mBtAdapter.isEnabled()){
                Log.d(TAG,"...Bluetooth Activado...");
            }else{
                Intent enableBtIntent =new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }

    }

}
