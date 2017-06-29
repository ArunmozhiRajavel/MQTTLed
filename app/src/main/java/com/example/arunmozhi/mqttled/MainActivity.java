package com.example.arunmozhi.mqttled;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MainActivity extends Activity implements MqttCallback{

    private TextView mTextViewInfo;
    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mTextViewInfo = (TextView) findViewById(R.id.msg);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate..... MQTT LED");
        try {
            MqttClient client = new MqttClient("tcp://52.163.255.92:1883", "led_client", new MemoryPersistence());

            client.connect();

            String topic = "topic/led";
            client.subscribe(topic,0);
            client.setCallback(this);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void connectionLost(Throwable cause) {
        Log.d(TAG, "connectionLost....");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String payload = new String(message.getPayload());
        Log.d(TAG, payload);
        switch (payload) {
            case "1":
                //mTextViewInfo.setText(mTextViewInfo.getText() + "\nBattery Level : ");
                Log.d(TAG, "LED ON");
                //Toast.makeText(getApplicationContext(),"AC switched ON",Toast.LENGTH_SHORT).show();
                break;
            case "0":
                //Toast.makeText(getApplicationContext(),"AC switched OFF",Toast.LENGTH_SHORT).show();
                Log.d(TAG, "LED OFF");
                break;
            default:
                Log.d(TAG, "Message not supported!");
                break;
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        Log.d(TAG, "deliveryComplete....");
    }


}
