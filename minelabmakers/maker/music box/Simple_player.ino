#define music_num_MAX 9        //歌曲最大数，可改

#include <SoftwareSerial.h>
//用户自定义部分------------------------

#include <Wire.h>

//EEPROM---------------------
#include <EEPROM.h>
#define EEPROM_write(address, p) {int i = 0; byte *pp = (byte*)&(p);for(; i < sizeof(p); i++) EEPROM.write(address+i, pp[i]);}
#define EEPROM_read(address, p)  {int i = 0; byte *pp = (byte*)&(p);for(; i < sizeof(p); i++) pp[i]=EEPROM.read(address+i);}

struct config_type
{
  int EEPROM_music_num;
  int EEPROM_music_vol;
};

//用户自定义部分------------------------
#include "audio.h"

#include "U8glib.h"
//-------字体设置，大、中、小
#define setFont_L u8g.setFont(u8g_font_7x13)
#define setFont_M u8g.setFont(u8g_font_fixed_v0r)
#define setFont_S u8g.setFont(u8g_font_fixed_v0r)
/*
font:
 u8g_font_7x13
 u8g_font_fixed_v0r
 u8g_font_chikitar
 u8g_font_osb21
 u8g_font_courB14r
 u8g_font_courB24n
 u8g_font_9x18Br
 */

//屏幕类型--------
U8GLIB_SSD1306_128X64 u8g(U8G_I2C_OPT_NONE);

#define init_draw 500	//主界面刷新时间
unsigned long timer_draw;

int MENU_FONT = 1;	//初始化字体大小 0：小，1：中，2：大

boolean music_status = false;	//歌曲播放状态
int music_num = 1;		//歌曲序号
int music_vol=20;               //音量0~30


boolean key,key_cache;

unsigned long music_vol_time_cache=0;
unsigned long music_vol_time=0;
boolean music_vol_sta=false;

int uiStep()
{
  if(analogRead(A6)<10)
  {
    delay(50);
    if(analogRead(A6)<10)
      return 1;
  }
  if(analogRead(A6)>150 && analogRead(A6)<310)
  {
    delay(50);
    if(analogRead(A6)>150 && analogRead(A6)<300)
      return 2;
  }
  if(analogRead(A6)>400 && analogRead(A6)<580)
  {
    delay(50);
    if(analogRead(A6)>400 && analogRead(A6)<580)
      return 3;
  }
  return 0;
}

void setup() 
{
  Serial.begin(9600);

  //EEPROM---------------------
  eeprom_READ();

  //初始化mp3模块
  audio_init(DEVICE_TF,MODE_loopOne,music_vol);		//初始化mp3模块

  // u8g.setRot180();  // rotate screen, if required
}

void loop() 
{ 
  int vol=uiStep();	//检测输入动作
  //  Serial.print("vol:");
  //  Serial.println(vol);

  if(vol==1) key=true;
  else key=false;

  if(!key && key_cache)		//按下松开后
  {
    key_cache=key;		//缓存作判断用
    music_status=!music_status;	//播放或暂停
    if(music_status == true)	//播放
    {
      Serial.println("play");
//      audio_choose(1);
      audio_play();
    }
    else	//暂停
    {
      Serial.println("pause");
      audio_pause();
    }
  }
  else
  {
    key_cache=key;		//缓存作判断用
  }

  if(vol==0)
  {
    //    Serial.println("no");
    music_vol_time_cache=millis();
    music_vol_time=music_vol_time_cache;
    music_vol_sta=false;
  }
  else if(vol==2)
  {
    music_vol_time=millis()-music_vol_time_cache;
    //    if(music_vol_time>200)
    delay(500);
    if(uiStep()==0 && !music_vol_sta)
    {
      Serial.println("next");

      music_num++;	//歌曲序号加
      if(music_num>music_num_MAX)	//限制歌曲序号范围
      {
        music_num=1;
      }
      audio_choose(music_num);
      audio_play();
      //        delay(500);
      music_status=true;
      eeprom_WRITE();

    }
    else if(music_vol_time>1500)
    {
      music_vol_sta=true;
      music_vol++;
      if(music_vol>30) music_vol=30;
      audio_vol(music_vol);
      Serial.println("++");
      delay(100);
      eeprom_WRITE();
    }
  }
  else if(vol==3)
  {
    music_vol_time=millis()-music_vol_time_cache;
    //    if(music_vol_time>200)
    delay(500);
    if(uiStep()==0 && !music_vol_sta)
    {
      Serial.println("perv");

      music_num--;	//歌曲序号加
      if(music_num<1)	//限制歌曲序号范围
      {
        music_num=music_num_MAX;
      }
      audio_choose(music_num);
      audio_play();
      //        delay(500);
      music_status=true;
      eeprom_WRITE();
    }
    else if(music_vol_time>1500)
    {
      music_vol_sta=true;
      music_vol--;
      if(music_vol<1) music_vol=1;
      audio_vol(music_vol);
      Serial.println("--");
      delay(100);
      eeprom_WRITE();
    }
  }

  if(millis()-timer_draw>init_draw)
  {
    u8g.firstPage();  
    do {
      draw();
    } 
    while( u8g.nextPage() );
    timer_draw=millis();
  }
}

void eeprom_WRITE()
{
  config_type config;  		// 定义结构变量config，并定义config的内容
  config.EEPROM_music_num=music_num;
  config.EEPROM_music_vol=music_vol;

  EEPROM_write(0, config); 	// 变量config存储到EEPROM，地址0写入
}

void eeprom_READ()
{
  config_type config_readback;
  EEPROM_read(0, config_readback);
  music_num=config_readback.EEPROM_music_num;
  music_vol=config_readback.EEPROM_music_vol;
}

unsigned int freeRam () {
  extern unsigned int __heap_start, *__brkval; 
  unsigned int v; 
  return (unsigned int) &v - (__brkval == 0 ? (unsigned int) &__heap_start : (unsigned int) __brkval); 
}

//主界面，可自由定义
void draw()
{
  setFont_L;

  u8g.setPrintPos(4, 16);
  u8g.print("Music_sta:");
  u8g.print(music_status ? "play" : "pause");

  u8g.setPrintPos(4, 16*2);
  u8g.print("Music_vol:");
  u8g.print(music_vol);
  u8g.print("/30");
  u8g.setPrintPos(4, 16*3);
  u8g.print("Music_num:");
  u8g.print(music_num);
  u8g.print("/");
  u8g.print(music_num_MAX);
  u8g.setPrintPos(4, 16*4);
  u8g.print("....Microduino....");
}
