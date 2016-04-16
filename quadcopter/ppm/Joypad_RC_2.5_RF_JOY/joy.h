#include "Arduino.h"

#include <Joypad.h>

boolean mode[2]=
{
  true,true
};    //{(0: 0 is mwc, 1 is nrf),(1: 0 is mpu, 1 is no mpu)}

int joy_correct_vol[4];
int Joy_x,Joy_y,Joy1_x,Joy1_y;

int s_lig,s_mic;

boolean Joy_sw,Joy1_sw;

boolean but1,but2,but3,but4;

boolean sw_l,sw_r;

//======================================
int Joy_dead_zone(int _Joy_num)
{
  int _Joy_vol;
  switch(_Joy_num)
  {
  case 0:
    _Joy_vol=Joypad.readJoystickX();
    break;
  case 1:
    _Joy_vol=Joypad.readJoystickY();
    break;
  case 2:
    _Joy_vol=Joypad.readJoystick1X();
    break;
  case 3:
    _Joy_vol=Joypad.readJoystick1Y();
    break;
  }

  if(abs(_Joy_vol)>(joy_correct_vol[_Joy_num]))
    return ((_Joy_vol>0) ? (_Joy_vol-joy_correct_vol[_Joy_num]) : (_Joy_vol+joy_correct_vol[_Joy_num]));
  else
    return 0;
}

int Joy_i(int _Joy_i,int _Joy_MIN,int _Joy_MAX)
{
  int _Joy_a=Joy_dead_zone(_Joy_i);

  if(_Joy_a<0)
    _Joy_a=map(_Joy_a, joy_correct_vol[_Joy_i]-512,0, _Joy_MAX, Joy_MID);
  else
    _Joy_a=map(_Joy_a, 0,512-joy_correct_vol[_Joy_i], Joy_MID, _Joy_MIN);

  if(_Joy_a<_Joy_MIN) _Joy_a=_Joy_MIN;
  if(_Joy_a>_Joy_MAX) _Joy_a=_Joy_MAX;

  return _Joy_a;
}

void Joy()
{
  sw_l=Joypad.readButton(CH_SWITCH_L);
  sw_r=Joypad.readButton(CH_SWITCH_R);

  //------------------------------------
  //s_lig=Joypad.readLightSensor();
  //s_mic=Joypad.readMicrophone();

  //------------------------------------
  Joy_sw=Joypad.readButton(CH_JOYSTICK_SW);
  Joy1_sw=Joypad.readButton(CH_JOYSTICK1_SW);

  //------------------------------------
  but1=Joypad.readButton(CH_SWITCH_1);
  but2=Joypad.readButton(CH_SWITCH_2);
  but3=Joypad.readButton(CH_SWITCH_3);
  but4=Joypad.readButton(CH_SWITCH_4);

#if defined(__AVR_ATmega1284P__) || defined(__AVR_ATmega644P__) || defined(__AVR_ATmega128RFA1__)
  //====================================
  int y[3];        //MPU---------------------------------
  if(mode[1])      //MPU---------------------------------
  {
    for(int a=0;a<3;a++)
    {
      y[a]=ypr[a]*180/M_PI;
      if(y[a]>MPU_maximum) y[a]=MPU_maximum;
      if(y[a]<-MPU_maximum) y[a]=-MPU_maximum;
    }
  }
#endif

  if(Joypad.readButton(CH_SWITCH_R))
  {
    Joy_x=Joy_i(0,Joy_MID-Joy_maximum,Joy_MID+Joy_maximum);
    Joy_y=Joy_i(1,Joy_MID-Joy_maximum,Joy_MID+Joy_maximum);

#if defined(__AVR_ATmega1284P__) || defined(__AVR_ATmega644P__) || defined(__AVR_ATmega128RFA1__)
    if(mode[1])      //MPU---------------------------------
    {
      Joy1_x=map(y[2],-MPU_maximum,MPU_maximum,Joy_MID-Joy_maximum,Joy_MID+Joy_maximum);
      Joy1_y=map(y[1],-MPU_maximum,MPU_maximum,Joy_MID-Joy_maximum,Joy_MID+Joy_maximum);
    }
    else
#endif
    {
      Joy1_x=Joy_i(2,Joy_MID-Joy_maximum,Joy_MID+Joy_maximum);
      Joy1_y=Joy_i(3,Joy_MID-Joy_maximum,Joy_MID+Joy_maximum);
    }
  }
  else
  {
    Joy_x=Joy_i(0,Joy_MID-Joy_s_maximum,Joy_MID+Joy_s_maximum);
    Joy_y=Joy_i(1,mode[0] ? Joy_MID-Joy_s_maximum : Joy_MID-Joy_maximum,mode[0] ? Joy_MID+Joy_s_maximum : Joy_MID+Joy_maximum);//  Robot,QuadCopter

#if defined(__AVR_ATmega1284P__) || defined(__AVR_ATmega644P__) || defined(__AVR_ATmega128RFA1__)
    if(mode[1])      //MPU---------------------------------
    {
      Joy1_x=map(y[2],-MPU_maximum,MPU_maximum,Joy_MID-Joy_s_maximum,Joy_MID+Joy_s_maximum);
      Joy1_y=map(y[1],-MPU_maximum,MPU_maximum,Joy_MID-Joy_s_maximum,Joy_MID+Joy_s_maximum);
    }
    else
#endif
    {
      Joy1_x=Joy_i(2,Joy_MID-Joy_s_maximum,Joy_MID+Joy_s_maximum);
      Joy1_y=Joy_i(3,Joy_MID-Joy_s_maximum,Joy_MID+Joy_s_maximum);
    }
  }
}







