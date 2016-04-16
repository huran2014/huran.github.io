#include "Arduino.h"

#include <EEPROM.h>

#define EEPROM_write(address, p) {int i = 0; byte *pp = (byte*)&(p);for(; i < sizeof(p); i++) EEPROM.write(address+i, pp[i]);}
#define EEPROM_read(address, p)  {int i = 0; byte *pp = (byte*)&(p);for(; i < sizeof(p); i++) pp[i]=EEPROM.read(address+i);}

struct config_type
{
  int eeprom_correct_vol[4];
  boolean eeprom_mode[2];
};

//======================================
void eeprom_read()
{
  //EEPROM读取赋值
  config_type config_readback;
  EEPROM_read(0, config_readback);

  mode[0]=config_readback.eeprom_mode[0];
#if defined(__AVR_ATmega1284P__) || defined(__AVR_ATmega644P__) || defined(__AVR_ATmega128RFA1__)
  mode[1]=config_readback.eeprom_mode[1];
#endif

  for(int a=0;a<4;a++)
  {
    joy_correct_vol[a]=config_readback.eeprom_correct_vol[a];
  }
}

void eeprom_write()
{
  // 定义结构变量config，并定义config的内容
  config_type config;

  config.eeprom_mode[0] = mode[0];

#if defined(__AVR_ATmega1284P__) || defined(__AVR_ATmega644P__) || defined(__AVR_ATmega128RFA1__)
  config.eeprom_mode[1] = mode[1];
#endif

  for(int a=0;a<4;a++)
  {
    config.eeprom_correct_vol[a] = abs(joy_correct_vol[a])+joy_DEAD_ZONE;
  } 

  // 变量config存储到EEPROM，地址0写入
  EEPROM_write(0, config); 	
}
