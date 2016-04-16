#include "Arduino.h"

#include <Adafruit_GFX.h>    // Core graphics library
#include <Adafruit_ST7735.h> // Hardware-specific library
#include <SPI.h>

Adafruit_ST7735 tft = Adafruit_ST7735(5, 4, -1);    //cs,dc,rst
//-------字体设置，大、中、小
#define setFont_L  tft.setTextSize(3)
#define setFont_M tft.setTextSize(2)
#define setFont_S tft.setTextSize(0)

#define tft_width  128
#define tft_height 160

#define tft_bat_x 14
#define tft_bat_y 7
#define tft_bat_x_s 2
#define tft_bat_y_s 3

#define tft_font_s_height 8
#define tft_font_m_height 16
#define tft_font_l_height 24

#define _Q_font_x 2
#define _Q_font_y 23

#define _Q_x 33
#define _Q_y 24
#define _W_x 93
#define _W_y 5

boolean tft_cache=true;
//======================================
void TFT_clear()
{
  tft.fillScreen(TFT_BUT);
}

void TFT_init(boolean _init)
{
  if(_init)
  {
    tft.initR(INITR_BLACKTAB);   // initialize a ST7735S chip, black tab
    //  Serial.println("init");
    tft.fillScreen(TFT_BUT);
  }

  tft.fillRect(0, 0,ST7735_TFTWIDTH, 40, TFT_TOP);  
  tft.setTextColor(TFT_BUT);
  setFont_M;
  tft.setCursor(26, 6);
  tft.print("Joypad");
  setFont_S;
  tft.setCursor(32, 24);
  tft.print("Microduino");
  tft.fillRect(0, 40,ST7735_TFTWIDTH, 120, TFT_BUT);  
}

void TFT_begin()
{
  setFont_S;

  tft.setTextColor(TFT_TOP);
  tft.setCursor(_Q_font_x,44);
  tft.println("[key1] enter config");

  setFont_M;
  tft.setCursor(4, 150); 
  for(int a=0;a<(millis()-TIME1)/(interval_TIME1/10);a++)  tft.print("-");
}

void Joy_correct()
{
  tft.setTextColor(TFT_TOP);

  if(tft_cache)
  {
    tft.setCursor(_Q_font_x,44);
    tft.print("Move Joystick Middle");

    tft.setCursor(_Q_font_x,77);
    tft.println("[key3] mode config");

    tft.setCursor(_Q_font_x,110);
    tft.println("[key4] mpu config");

    tft.setCursor(_Q_font_x,150);
    tft.println("[key2] exit config");
  }

  //====================================
  tft.drawRect(2, 54, 124, 16, TFT_TOP);
  {
    joy_correct_vol[0]=Joypad.readJoystickX();
    joy_correct_vol[1]=Joypad.readJoystickY();
    joy_correct_vol[2]=Joypad.readJoystick1X();
    joy_correct_vol[3]=Joypad.readJoystick1Y();

    for(int a=0;a<4;a++)
    {
      tft.fillRect(6+a*31,  58, 24, 9, TFT_BUT);
      tft.setCursor(6+a*31, 58+1);
      tft.print(joy_correct_vol[a]);
    }
  }

  //but---------------------------------
  tft.drawRect(2, 87, 124, 16, TFT_TOP);
  if(key_get(2,1) || tft_cache)
  {
    mode[0]=!mode[0];    

    tft.fillRect(3, 88, 61, 14, mode[0] ? TFT_BUT : TFT_TOP);
    tft.setCursor(6,92);
    tft.setTextColor( mode[0] ? TFT_TOP : TFT_BUT);
    tft.print("Quad.");

    tft.fillRect(64, 88, 61, 14,  mode[0] ? TFT_TOP : TFT_BUT);
    tft.setCursor(80,92);
    tft.setTextColor( mode[0] ? TFT_BUT : TFT_TOP);
    tft.print("Robot");
  }

  //but---------------------------------
#if defined(__AVR_ATmega1284P__) || defined(__AVR_ATmega644P__) || defined(__AVR_ATmega128RFA1__)
  tft.drawRect(2, 120, 124, 16, TFT_TOP);
  if(key_get(3,1) || tft_cache)
  {
    mode[1]=!mode[1];

    tft.fillRect(3, 121, 61, 14, mode[1] ? TFT_BUT : TFT_TOP);
    tft.setCursor(6,125);
    tft.setTextColor(mode[1] ? TFT_TOP : TFT_BUT);
    tft.print("MPU OFF");

    tft.fillRect(64, 121, 61, 14, mode[1] ? TFT_TOP : TFT_BUT);
    tft.setCursor(80, 125);
    tft.setTextColor(mode[1] ? TFT_BUT : TFT_TOP);
    tft.print("MPU ON");
  }  
#endif

  tft_cache=false;
}

//------------------
const int _C_x_S=_Q_x+1;
const int _C_x_M=_Q_x+((_W_x+1)/2);
const int _C_x_E=_Q_x+_W_x-1;

char* NAME[8]={
  "THROT","ROLL","PITCH","YAW","AUX1","AUX2","AUX3","AUX4"};

void TFT_ready()
{
  tft.fillRect(0, 0,128, 11, TFT_TOP);

  tft.drawRect(tft_width-tft_bat_x-tft_bat_x_s-2, 2, tft_bat_x, tft_bat_y, TFT_BUT);
  tft.drawRect(tft_width-tft_bat_x_s-2, 2+(tft_bat_y-tft_bat_y_s)/2, tft_bat_x_s, tft_bat_y_s, TFT_BUT);

  tft.setTextColor(TFT_BUT);
  setFont_S;

  tft.setCursor(_Q_font_x, 2);
  tft.print("MODE: ");
  tft.print(mode[0] ? "Robot" : "Quad.");

  tft.setTextColor(TFT_TOP);
  for(int a=0;a<8;a++)
  {
    int _a=a*15;

    tft.setCursor(_Q_font_x, _Q_font_y+_a);
    tft.print(NAME[a]);
    //------------------------------------------
    tft.drawRect(_Q_x, _Q_y+_a, _W_x, _W_y, TFT_TOP);  
  }
}

void TFT_run()
{
  //battery------------------
  tft.fillRect(tft_width-tft_bat_x-3, 3, map(_V_bat,_V_min,_V_max,0,tft_bat_x-2) , tft_bat_y-2, TFT_BUT);
  tft.fillRect(tft_width-tft_bat_x-3+map(_V_bat,_V_min,_V_max,0,tft_bat_x-2), 3, map(_V_bat,_V_min,_V_max,tft_bat_x-2,0) , tft_bat_y-2, TFT_TOP);

  //------------------
  for(int a=0;a<8;a++)
  {
    int _a=a*15;

    //------------------------------------------
    int _C_x_A0,_C_x_B0,_C_x_A,_C_x_B,_C_x_A1,_C_x_B1;
    int _C_x;

    if(outBuf[a]<Joy_MID)
    {
      _C_x=map(outBuf[a],Joy_MID-Joy_maximum,Joy_MID,_C_x_S,_C_x_M);

      _C_x_A0=_C_x_S;
      _C_x_B0=_C_x-_C_x_S;

      _C_x_A=_C_x;
      _C_x_B=_C_x_M-_C_x;

      _C_x_A1=_C_x_M;
      _C_x_B1=_C_x_E-_C_x_M;
    }
    else if(outBuf[a]>Joy_MID)
    {
      _C_x=map(outBuf[a],Joy_MID,Joy_MID+Joy_maximum,_C_x_M,_C_x_E);

      _C_x_A0=_C_x_S;
      _C_x_B0=_C_x_M-_C_x_S;

      _C_x_A=_C_x_M;
      _C_x_B=_C_x-_C_x_M;

      _C_x_A1=_C_x;
      _C_x_B1=_C_x_E-_C_x;
    }
    else
    {
      _C_x_A0=_C_x_S;
      _C_x_B0=_C_x_M-_C_x_S;

      _C_x_A=_C_x_M;
      _C_x_B=0;

      _C_x_A1=_C_x_M;
      _C_x_B1=_C_x_E-_C_x_M;
    }
    tft.fillRect(_C_x_A0,  _Q_y+_a+1, _C_x_B0, _W_y-2, TFT_BUT);
    tft.fillRect(_C_x_A,  _Q_y+_a+1, _C_x_B, _W_y-2, ST7735_BLUE);
    tft.fillRect(_C_x_A1,  _Q_y+_a+1, _C_x_B1, _W_y-2, TFT_BUT);

    tft.fillRect(_C_x_M,  _Q_y+_a-1, 1, _W_y+2, ST7735_RED);

    //--------------------------    
    //    tft.drawLine(80, 18, 80, 140, ST7735_RED);
  }
  //netsta------------------
  tft.fillRect(0, 158, 128, 2, node_clock_error ? ST7735_RED : ST7735_GREEN);
}

