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

import java.util.ArrayList;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

public class WaveService {
    private final String LOG_TAG = "WaveService";
    private final boolean mDebug = true;
    private final int duration = 10; // seconds
    /** 正弦波的高度 **/
    public static final int HEIGHT = 127;
    /** 2PI **/
    public static final double TWOPI = 2 * 3.1415;
    /**
     * 闊抽閲囨牱棰戠巼锛屽湪褰曢煶涓悓鏍蜂細鏈夌被浼煎弬鏁帮紱閫氫織璁叉槸姣忕杩涜44100娆￠噰鏍枫��
     * 璇﹁锛歨ttp://en.wikipedia.org/wiki/44,100_Hz
     */
    private final int sampleRate = 44100;
    private int numSamples = duration * sampleRate;
    private final double sample[] = new double[numSamples];
    /**
     * 闊抽淇″彿鐨勯鐜囷紝绾㈠闇�瑕�38kHz鐨勯鐜囥��
     * 鎵嬫満杈撳嚭鐨勯鐜囦竴鑸湪浜鸿�冲惉鍔涗腑鐨�
     * 20~20kHz鑼冨洿鍐咃紝鎵�浠ヨ繖閲屼粎浠呭彇鏈�楂橀鐜囥��
     * 璇ラ鐜囨槸鎸囨渶缁堢殑淇″彿鐨勯鐜囷紝閲囨牱鐜囦粎瀛樺湪浜庢暟瀛楅煶棰戜俊鍙风敓鎴愯繃绋嬩腑鐨勪竴涓蹇点��
     */
    private final int WAVELEN = 44100 / 20000;
    private final double freqOfTone = 20000; // hz  200000=>20khz(50us) 鏈�楂�

    private final byte generatedSnd[] = new byte[2 * numSamples];
    /** Data "1" 楂樼數骞冲搴� */
    private final float          INFRARED_1_HIGH_WIDTH = 0.56f ;
    /** Data "1" 浣庣數骞冲搴� */
    private final float           INFRARED_1_LOW_WIDTH = 1.69f;  // 2.25 - 0.56
    /** Data "0" 楂樼數骞冲搴� */
    private final float          INFRARED_0_HIGH_WIDTH = 0.56f ;
    /** Data "0" 浣庣數骞冲搴� */
    private final float           INFRARED_0_LOW_WIDTH = 0.565f ;// 1.125-0.56
    /** Leader code 楂樼數骞冲搴� */
    private final float INFRARED_LEADERCODE_HIGH_WIDTH = 9.0f  ;
    /** Leader code 浣庣數骞冲搴� */
    private final float  INFRARED_LEADERCODE_LOW_WIDTH = 4.50f ;
    /** Stop bit 楂樼數骞冲搴� */
    private final float    INFRARED_STOPBIT_HIGH_WIDTH = 0.56f ;
    /**
     * @param time unit:ms
     * @value 1 0
     * @return
     */
    
    private byte[] genTone(double time, float percent){
        numSamples = (int) (time/1000 * sampleRate);
        double sample[] = new double[numSamples];
        byte generatedSnd[] = new byte[2 * numSamples];

        // fill out the array
        for (int i = 0; i < numSamples; ++i) {
			/**
			 * 杩欓噷鐢ㄥ埌浜嗘暟瀛︿腑姝ｅ鸡娉㈢煡璇嗭紝鍏跺疄灏辨槸姹傚嚭鍥句腑S(i)鍊硷細
			 * http://en.wikipedia.org/wiki/File:Signal_Sampling.png
			 *
			 * 鍏充簬姝ｅ鸡娉㈢煡璇嗭紝璇﹁锛歨ttp://en.wikipedia.org/wiki/Sine_wave
			 * 鍏朵腑鍏紡涓猴細y(t) = A * sin (2蟺ft + 蠁)
			 * A: 鎸箙锛岃繖閲屼负1锛�
			 * f: 棰戠巼锛岃繖閲屼负freqOfTone;
			 * t: 鏃堕棿锛岃繖閲屼负(i/sampleRate);
			 * 蠁: 鍒濈浉浣嶏紝杩欓噷涓�0锛�
			 */
            sample[i] = Math.sin(2 * Math.PI * freqOfTone * (i / sampleRate));
        }

        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalised.
        int idx = 0;
        for (final double dVal : sample) {
            // scale to maximum amplitude
            final short val = (short) (dVal * 32767 * percent);
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);

        }

        return generatedSnd;
    }
    /** 
     * @param waveLen 周期，每个周期占用多少次采样率 
     * @param length  长度，单位同样是占用了多少次采样率。例如采样率为44100，需要1秒长，则length值为44100/waveLen 
     * @return 
     */  
    public static byte[] getHigh(int waveLen, int length) {  
        byte[] wave = new byte[length];  
        for (int i = 0; i < length; i++) {  
            wave[i] = (byte) (HEIGHT * (1 - Math.sin(2 * Math.PI  
                    * ((i % waveLen) * 1.00 / waveLen))));  
        }  
        return wave;  
    }  
  
    public static byte[] getLow(int waveLen, int length) {  
        byte[] wave = new byte[length];  
        for (int i = 0; i < length; i++) {  
            wave[i] = (byte) (HEIGHT);  
        }  
        return wave;  
    }
    
    private byte[] get0() {  
        int highLength = (int) Math.round(sampleRate / 1000000.0 * INFRARED_0_HIGH_WIDTH / WAVELEN) * WAVELEN;//采样率/1000000.0*高电平时间=高电平占用的采样次数，先除后乘是想得到一个整数倍的周期，具体有没有益处未证实  
        byte[] high = getHigh(WAVELEN, highLength);  
        int lowLength = (int) Math.round(sampleRate / 1000000.0 * INFRARED_0_LOW_WIDTH / WAVELEN) * WAVELEN;  
        byte[] low = getLow(WAVELEN, lowLength);  
        byte[] res = new byte[highLength + lowLength];  
        System.arraycopy(high, 0, res, 0, high.length);  
        System.arraycopy(low, 0, res, high.length, low.length);  
        return res;  
    }  
  
    private byte[] get1() {  
        int highLength = (int) Math.round(sampleRate / 1000000.0 * INFRARED_1_HIGH_WIDTH / WAVELEN) * WAVELEN;  
        byte[] high = getHigh(WAVELEN, highLength);  
        int lowLength = (int) Math.round(sampleRate / 1000000.0 * INFRARED_1_LOW_WIDTH / WAVELEN) * WAVELEN;  
        byte[] low = getLow(WAVELEN, lowLength);  
        byte[] res = new byte[highLength + lowLength];  
        System.arraycopy(high, 0, res, 0, high.length);  
        System.arraycopy(low, 0, res, high.length, low.length);  
        return res;  
    }  
    private byte[] genTone(){
        // fill out the array
        for (int i = 0; i < numSamples; ++i) {
            sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/freqOfTone));
        }

        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalised.
        int idx = 0;
        for (final double dVal : sample) {
            // scale to maximum amplitude
            final short val = (short) ((dVal * 32767));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);

        }

        return generatedSnd;
    }

    public void sendSignal(short userCode, byte dataCode) {
    	Runnable r = new sendThread(userCode, dataCode);
    	new Thread(r).start();
    }

	private class sendThread implements Runnable {
		short userCode;
		byte dataCode;

		public sendThread(short userCode1, byte dataCode1) {
			// store parameter for later user
			userCode = userCode1;
			dataCode = dataCode1;
		}

		public void run() {
			playSound(userCode, dataCode);
		}
	}

    private void playSound(short userCode, byte dataCode){
        byte[] dst = getWave(userCode, dataCode);
        final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_OUT_STEREO,
                AudioFormat.ENCODING_PCM_16BIT, dst.length,
                AudioTrack.MODE_STATIC);
        if(mDebug) Log.d(LOG_TAG, "length=" + dst.length);
        System.out.println("audioTrack"+audioTrack);
        System.out.println("dst"+dst);
        audioTrack.write(dst, 0, dst.length);
        audioTrack.play();
/*        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate,  
                AudioFormat.CHANNEL_OUT_MONO,  
                AudioFormat.ENCODING_PCM_8BIT, wave.length, AudioTrack.MODE_STATIC);  
        if (audioTrack != null) {  
            audioTrack.write(wave, 0, wave.length);  
            audioTrack.play();  
        }  */
    }

    /**
     * PPM wave 0
     * @return
     */
    private byte[] getLow() {
        //(1.125-0.56) + 0.56
        //INFRARED_0_HIGH_WIDTH  0.56
        //INFRARED_0_LOW_WIDTH   0.565 // 1.125 - 0.56
        byte[] one = genTone(INFRARED_0_HIGH_WIDTH, 1);
        byte[] two = genTone(INFRARED_0_LOW_WIDTH, 0);
        byte[] combined = new byte[one.length + two.length];

        System.arraycopy(one,0,combined,0,one.length);
        System.arraycopy(two,0,combined,one.length,two.length);
        return combined;
    }
    /**
     * PPM wave 1
     * @return
     */
    private byte[] getHigh() {
        //0.56ms + (2.25 - 0.56)
        //INFRARED_1_HIGH_WIDTH  0.56
        //INFRARED_1_LOW_WIDTH   1.69 // 2.25 - 0.56
        byte[] one = genTone(INFRARED_1_HIGH_WIDTH, 1);
        byte[] two = genTone(INFRARED_1_LOW_WIDTH, 0);
        byte[] combined = new byte[one.length + two.length];

        System.arraycopy(one,0,combined,0,one.length);
        System.arraycopy(two,0,combined,one.length,two.length);
        return combined;
    }

    private byte[] getLittleHigh() {
        byte[] one = genTone(INFRARED_1_LOW_WIDTH, 0.08f);
        byte[] two = genTone(INFRARED_1_HIGH_WIDTH, 0);
        byte[] combined = new byte[one.length + two.length];

        System.arraycopy(one,0,combined,0,one.length);
        System.arraycopy(two,0,combined,one.length,two.length);
        return combined;
    }

    private byte[] getTou() {
        ArrayList<byte[]> wave_list = new ArrayList<byte[]>();
        int totalLength = 0;
        for(int i=0; i<3; ++i) {
            wave_list.add(genTone(10, 0));         // 10ms 0

            for(int j=1; j<4; ++j) {               // 鍙栨渶楂樹綅
                wave_list.add(getLittleHigh());
            }

            wave_list.add(genTone(10, 0));         // 10ms 0
        }

        for( byte[] byteTmp : wave_list)
            totalLength += byteTmp.length;

        int currentPosition = 0;
        byte userCodeWaveArray[] = new byte[totalLength];

        for(byte[] byteArray : wave_list) {
            System.arraycopy(byteArray,0,userCodeWaveArray,currentPosition,byteArray.length);
            currentPosition += byteArray.length;
        }

        return userCodeWaveArray;
    }

    //byte[] getWave(float leaderCode, float space, int userCode ) {

    //                   0x0707         0x05
    private byte[] getWave(short userCode, byte dataCode) {
        if(mDebug) Log.d(LOG_TAG, "userCode = 0x" + Integer.toHexString(userCode) + " dataCode = 0x" + Integer.toHexString(dataCode));
        ArrayList<byte[]> wave_list = new ArrayList<byte[]>();
        int totalLength = 0;

        wave_list.add(getTou());
        wave_list.add(getleaderCode());
        wave_list.add(getUserCodeToWave(userCode));
        wave_list.add(getDataCodeToWave(dataCode));
        wave_list.add(getStopBit());
        wave_list.add(getRepeatCode());
        wave_list.add(getTou());

        for( byte[] byteTmp : wave_list)
            totalLength += byteTmp.length;

        int currentPosition = 0;
        byte totalWaveArray[] = new byte[totalLength];

        for(byte[] byteArray : wave_list) {
            System.arraycopy(byteArray,0,totalWaveArray,currentPosition        ,byteArray.length);
            currentPosition += byteArray.length;
        }

        return totalWaveArray;
    }


    /**
     * 1.leader code
     * @return
     */
    private byte[] getleaderCode() {
        //9.0ms + 4.50ms Infrared
        //INFRARED_LEADERCODE_HIGH_WIDTH  9.0
        //INFRARED_LEADERCODE_LOW_WIDTH   4.50
        byte[] one = genTone(INFRARED_LEADERCODE_HIGH_WIDTH, 1);
        byte[] two = genTone(INFRARED_LEADERCODE_LOW_WIDTH, 0);
        byte[] combined = new byte[one.length + two.length];

        System.arraycopy(one,0,combined,0         ,one.length);
        System.arraycopy(two,0,combined,one.length,two.length);

        return combined;
    }

    /**
     * 2. user code
     * @param userCode
     * @return
     */
    private byte[] getUserCodeToWave(short userCode) {
        ArrayList<byte[]> wave_list = new ArrayList<byte[]>();
        int totalLength = 0;
        for(int i=0; i<16; ++i) {             // 鍙栨渶楂樹綅
            if(((userCode >> i) & 0x1) == 1) { // 1
                wave_list.add(get1());
                Log.i(LOG_TAG, "1");
            } else {                           // 0
                Log.i(LOG_TAG, "0");
                wave_list.add(get0());
            }
            totalLength += wave_list.get(i).length;
        }

        int currentPosition = 0;
        byte userCodeWaveArray[] = new byte[totalLength];

        for(byte[] byteArray : wave_list) {
            System.arraycopy(byteArray,0,userCodeWaveArray,currentPosition        ,byteArray.length);
            currentPosition += byteArray.length;
        }

        return userCodeWaveArray;
    }
    /**
     * 3. data code: sign-and-magnitude+ones'complement
     * @param dataCode
     * @return
     */
    private byte[] getDataCodeToWave(byte dataCode) {
        ArrayList<byte[]> wave_list = new ArrayList<byte[]>();
        int totalLength = 0;
                                               // 鍙栨渶楂樹綅
        for(int i=0; i<8; ++i) {              // sign-and-magnitude
            if(((dataCode >> i) & 0x1) == 1) { // 1
                wave_list.add(get1());
            } else {                           // 0
                wave_list.add(get0());
            }
            totalLength += wave_list.get(i).length;
        }
                                               // 鍙栨渶楂樹綅
        for(int i=0; i<8; ++i) {              // ones'complement
            if(((dataCode >> i) & 0x1) == 1) { // 1
                wave_list.add(get0());
            } else {                           // 0
                wave_list.add(get1());
            }
            totalLength += wave_list.get(8 + i).length;
        }

        int currentPosition = 0;
        byte userCodeWaveArray[] = new byte[totalLength];
        for(byte[] byteArray : wave_list) {
            System.arraycopy(byteArray,0,userCodeWaveArray,currentPosition        ,byteArray.length);
            currentPosition += byteArray.length;
        }

        return userCodeWaveArray;
    }

    /**
     * 4.stop bit
     * @return
     */
    private byte[] getStopBit() {
        //0.56ms
        //INFRARED_STOPBIT_HIGH_WIDTH    0.56
        return genTone(INFRARED_STOPBIT_HIGH_WIDTH, 1);
    }

    /**
     * 鐢ㄤ簬闀挎寜鏁堟灉
     * 杩欎釜鏂规硶涓殑鍙傛暟璁板緱鏄疄娴嬪緱鏉ョ殑銆�
     * @return
     */
    private byte[] getRepeatCode() {
    	//9.0ms(high) + 2.25ms(low) + 0.56ms(high)
        ArrayList<byte[]> waveList = new ArrayList<byte[]>();
        int totalLength = 0;

        waveList.add(genTone(110, 0));          // 110ms  0
        waveList.add(genTone(9.00, 1));         // 9.00ms 1
        waveList.add(genTone(2.25, 0));         // 2.25ms 0
        waveList.add(genTone(0.56, 1));         // 0.56ms 1


        for( byte[] byteTmp : waveList)
            totalLength += byteTmp.length;

        int currentPosition = 0;
        byte repeatCodeArray[] = new byte[totalLength];

        for(byte[] byteArray : waveList) {
            System.arraycopy(byteArray,0,repeatCodeArray,currentPosition        ,byteArray.length);
            currentPosition += byteArray.length;
        }

        return repeatCodeArray;
    }
}
