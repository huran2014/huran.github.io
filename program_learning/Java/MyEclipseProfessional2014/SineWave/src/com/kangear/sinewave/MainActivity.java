/*
 * Copyright (C) 2014 kangear@163.com
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

package com.kangear.sinewave;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    final String LOG_TAG = "MainActivity";
    private final boolean mDebug = false;
    
    WaveService mWaveService = new WaveService();
    TextView mTextViewLength = null;
    
    int currentVolume = 0;
    boolean isHeadsetOn;
    boolean isHeadsetConnected;

    Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mTextViewLength = (TextView) this.findViewById(R.id.textview_length);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        startPlayback();
        
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        stopPlayback();
    }

    public void onClick(View v) {
    	switch (v.getId()) {
		case R.id.volume_add_button:
			mWaveService.sendSignal((short)0x00ff, (byte)0x28);
			break;
		case R.id.volume_sub_button:
			mWaveService.sendSignal((short)0x00ff, (byte)0x01);
			break;
			
		default:
			break;
		}

    }
    
    private void startPlayback() {
        registerHeadsetPlugReceiver();
    }

    private void stopPlayback() {
        updateSettings(false);
        unregisterReceiver();
    }
    
    /**
     * FSM:状态机
     * front && plugin    updateSettings
     * front && unplugin  updateSettings
     */
    void updateSettings(boolean isFront) {
        AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        
        if (isFront && isHeadsetConnected) {
            /* backup current volume */
            currentVolume = mAudioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC);

            /* set headset stream music volume */
            mAudioManager
                    .setStreamVolume(AudioManager.STREAM_MUSIC, mAudioManager
                            .getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);

        } else if (!isFront && isHeadsetConnected) {
            /* back volume */
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                    currentVolume, 0);

        } else if (isFront && !isHeadsetConnected) {
            // do nothing

        } else if (!isFront && !isHeadsetConnected) {
            // do nothing
            
        } else {
            Log.e(LOG_TAG, "State error!");
            Toast.makeText(
                    this,
                    "State error! isFront:" + String.valueOf(isFront)
                            + " isHeadsetConnected:"
                            + String.valueOf(isHeadsetConnected),
                    Toast.LENGTH_SHORT).show();
        }
    }
    
    HeadsetPlugReceiver headsetPlugReceiver; 
    private void registerHeadsetPlugReceiver(){
        headsetPlugReceiver  = new HeadsetPlugReceiver();
        IntentFilter  filter = new IntentFilter();
        filter.addAction("android.intent.action.HEADSET_PLUG");
        registerReceiver(headsetPlugReceiver, filter);
    }
    
    private void unregisterReceiver(){
        this.unregisterReceiver(headsetPlugReceiver);
    }
    
    public class HeadsetPlugReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            
                if(intent.hasExtra("state")){
                    if(intent.getIntExtra("state", 0)==0){
                        if(mDebug) Log.d(LOG_TAG, "headset not connected");
                        isHeadsetConnected = false;
                        updateSettings(true);
                    }
                    else if(intent.getIntExtra("state", 0)==1){
                        if(mDebug) Log.d(LOG_TAG, "headset  connected");
                        isHeadsetConnected = true;
                        updateSettings(true);
                    }
                }
        }

    }
}
