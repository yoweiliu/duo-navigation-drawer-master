package com.example.simon.bluetooth;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//import java.nio.channels.AlreadyConnectedException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    //1)
    Button IdEncender, IdApagar, IdDesconectar,button,button2;
    TextView IdBufferIn;
    //---------------------------------------------------------------------------------------
    Handler bluetoothIn;
    final int handlerState=0;
    private BluetoothAdapter btAdapter =null;
    private BluetoothSocket btSocket=null;
    private StringBuilder DataStringIN=new StringBuilder();
    private ConnectedThread MyConexionBT;
    //Identificador unico de servicio -SPP UUID
    private static final UUID BTMODULEUUID =UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    //String para la direccion MAC
    private static String address =null;
    //------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        //2)
        IdEncender = (Button) findViewById( R.id.IdEncender );
        IdApagar = (Button) findViewById( R.id.IdApagar );
        button2=(Button)findViewById(R.id.button2);
        button=(Button)findViewById(R.id.button);
        IdDesconectar = (Button) findViewById( R.id.IdDesconectar );
        IdBufferIn = (TextView) findViewById( R.id.IdBufferIn );

        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {
                    String readMessage = (String) msg.obj;
                    DataStringIN.append(readMessage);

                    int endOfLineIndex = DataStringIN.indexOf("#");

                    if (endOfLineIndex > 0) {
                        String dataInPrint = DataStringIN.substring(0, endOfLineIndex);
                        IdBufferIn.setText("Dato: " + dataInPrint);//<-<- PARTE A MODIFICAR >->->
                        DataStringIN.delete(0, DataStringIN.length());
                    }
                }
            }
        };

        btAdapter=BluetoothAdapter.getDefaultAdapter();
        VerificarEstadoBT();

        IdEncender.setOnClickListener( new View.OnClickListener() {  //RED
            @Override
            public void onClick(View view) {
                MyConexionBT.write("a");
            }
        } );

        IdApagar.setOnClickListener( new View.OnClickListener() {  //GREEN

            public void onClick(View view) {
                MyConexionBT.write("b");
            }

        } );
        button2.setOnClickListener( new View.OnClickListener() {  //BLUE

            public void onClick(View view) {
                MyConexionBT.write("c");
            }

        } );
        button.setOnClickListener( new View.OnClickListener() {  //OFF

            public void onClick(View view) {
                MyConexionBT.write("d");
            }

        } );

        IdDesconectar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (btSocket!=null)
                {
                    try{btSocket.close();}
                    catch (IOException e)
                    {
                        Toast.makeText( getBaseContext(),"Error", Toast.LENGTH_SHORT ).show();;}
                }
                finish();
            }
        });
    }
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException
    {
        return device.createInsecureRfcommSocketToServiceRecord( BTMODULEUUID );
    }

    public void onResume()
    {
        super.onResume();

        Intent intent=getIntent();

        address = intent.getStringExtra(DispositivosBT.EXTRA_DEVICE_ADDRESS);

        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        try
        {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e){
            Toast.makeText(getBaseContext(), "La creaccion del Socket fallow", Toast.LENGTH_LONG).show();
        }

        try{
            btSocket.connect();
        }catch(IOException e){
            try{
                btSocket.close();
            }catch (IOException e2) {}
        }
        MyConexionBT=new ConnectedThread(btSocket);
        MyConexionBT.start();

    }
    public void onPause()
    {
        super.onPause();
        try
        {
            btSocket.close();
        }catch(IOException e2){}
    }

    private void VerificarEstadoBT(){

        if(btAdapter==null){
            Toast.makeText(getBaseContext(), "E1 dispositivo no soport bluetooth", Toast.LENGTH_LONG).show();
        }else {
            if (btAdapter.isEnabled()) {
            }else {
                Intent enableBtIntent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent,1);
            }
        }
    }

    private class ConnectedThread extends Thread
    {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket)
        {
            InputStream tmpIn =null;
            OutputStream tmpOut=null;
            try{
                tmpIn= socket.getInputStream();
                tmpOut=socket.getOutputStream();

            }catch (IOException e){}
            mmInStream =tmpIn;
            mmOutStream=tmpOut;
        }

        public void run()
        {
            byte[] buffer=new byte[256];
            int bytes;

            while (true){
                try{
                    bytes=mmInStream.read(buffer);
                    String readMessage = new String(buffer,0,bytes);
                    bluetoothIn.obtainMessage(handlerState,bytes,-1,readMessage).sendToTarget();
                }catch (IOException e){
                    break;
                }
            }
        }
        public void write(String input)
        {
            try{
                mmOutStream.write(input.getBytes());
            }catch(IOException e)
            {
                Toast.makeText(getBaseContext(),"連線失敗",Toast.LENGTH_LONG).show();
                finish();
            }
        }
//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXx
    }}
