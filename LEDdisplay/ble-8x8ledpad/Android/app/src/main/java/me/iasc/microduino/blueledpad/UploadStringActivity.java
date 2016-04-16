/*
 * Copyright (C) 2015 Iasc CHEN
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.iasc.microduino.blueledpad;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.*;
import me.iasc.microduino.blueledpad.ble.BluetoothLeService;

public class UploadStringActivity extends AbstractBleControlActivity implements SensorEventListener {
    private final static String TAG = UploadStringActivity.class.getSimpleName();

    public static int BLE_MSG_BUFFER_LEN = 8;
    private EditText editMsg;

    private RadioGroup rgColor, rgDirection;
    private RadioButton rButtonRed, rButtonYellow, rButtonGreen, rButtonLeft, rButtonUp, rButtonRight, rButtonDown;

    public static int COLOR_RED = 1, COLOR_YELLOW = 2, COLOR_GREEN = 3;
    public static int DIRECTION_LEFT = 0, DIRECTION_UP = 1, DIRECTION_RIGHT = 2, DIRECTION_DOWN = 3;

    int colorIndex = 0, directionIndex = 0;

    //shake
    SensorManager sensorManager = null;
    Vibrator vibrator = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_upload_string);

        super.onCreate(savedInstanceState);

        // Sets up UI references.
        mConnectionState = (TextView) findViewById(R.id.connection_state);
        // is serial present?
        isSerial = (TextView) findViewById(R.id.isSerial);

        editMsg = (EditText) findViewById(R.id.editMessage);

        rgColor = (RadioGroup) findViewById(R.id.radioGroupColor);
        rButtonRed = (RadioButton) rgColor.findViewById(R.id.rButtonRed);
        rButtonYellow = (RadioButton) rgColor.findViewById(R.id.rButtonYellow);
        rButtonGreen = (RadioButton) rgColor.findViewById(R.id.rButtonGreen);

        rgDirection = (RadioGroup) findViewById(R.id.radioGroupDirection);
        rButtonLeft = (RadioButton) rgDirection.findViewById(R.id.rButtonLeft);
        rButtonUp = (RadioButton) rgDirection.findViewById(R.id.rButtonUp);
        rButtonRight = (RadioButton) rgDirection.findViewById(R.id.rButtonRight);
        rButtonDown = (RadioButton) rgDirection.findViewById(R.id.rButtonDown);

        rgColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup rg, int arg1) {
                int radioButtonId = rg.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) rg.findViewById(radioButtonId);

                if (rButtonYellow == rb) {
                    colorIndex = COLOR_YELLOW;
                } else if (rButtonGreen == rb) {
                    colorIndex = COLOR_GREEN;
                } else {
                    colorIndex = COLOR_RED;
                }
            }
        });

        rgDirection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup rg, int arg1) {
                int radioButtonId = rg.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) rg.findViewById(radioButtonId);

                if (rButtonUp == rb) {
                    directionIndex = DIRECTION_UP;
                } else if (rButtonRight == rb) {
                    directionIndex = DIRECTION_RIGHT;
                } else if (rButtonDown == rb) {
                    directionIndex = DIRECTION_DOWN;
                } else {
                    directionIndex = DIRECTION_LEFT;
                }
            }
        });

        buttonSend = (Button) findViewById(R.id.sendButton);
        buttonSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                msgBuffer = new StringBuilder("M:");
                msgBuffer.append(colorIndex).append(",");
                msgBuffer.append(directionIndex).append(",");
                msgBuffer.append(editMsg.getText()).append("\n");

                Log.v(TAG, "message = " + msgBuffer.toString());

                UploadAsyncTask asyncTask = new UploadAsyncTask();
                asyncTask.execute();
            }
        });
//
//        infoButton = (ImageView) findViewById(R.id.infoImage);
//        infoButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                iascDialog();
//            }
//        });

        getActionBar().setTitle(getString(R.string.title_text));
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);

        //shake
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        sensorManager.unregisterListener((SensorEventListener) this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        sensorManager.registerListener((SensorEventListener) this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        //当传感器精度改变时回调该方法，Do nothing.
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {

        int sensorType = event.sensor.getType();
        //values[0]:X轴，values[1]：Y轴，values[2]：Z轴
        float[] values = event.values;
        if (sensorType == Sensor.TYPE_ACCELEROMETER)
        {
            if ((Math.abs(values[0]) > 17 || Math.abs(values[1]) > 17 || Math
                    .abs(values[2]) > 17))
            {
                Log.d("sensor x ", "============ values[0] = " + values[0]);
                Log.d("sensor y ", "============ values[1] = " + values[1]);
                Log.d("sensor z ", "============ values[2] = " + values[2]);
                //tv.setText("摇一摇成功!!!");
                //摇动手机后，再伴随震动提示~~
                msgBuffer = new StringBuilder("M:");
                msgBuffer.append(colorIndex).append(",");
                msgBuffer.append(directionIndex).append(",");
                msgBuffer.append(editMsg.getText()).append("\n");

                Log.v(TAG, "message = " + msgBuffer.toString());

                UploadAsyncTask asyncTask = new UploadAsyncTask();
                asyncTask.execute();


                vibrator.vibrate(500);
            }

        }
    }
    protected int getMsgBufferLen() {
        return BLE_MSG_BUFFER_LEN;
    }
}