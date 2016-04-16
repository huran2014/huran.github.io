#include "Arduino.h"

#if defined(__AVR_ATmega128RFA1__)
#include <ZigduinoRadio.h>
#endif

int RCin[8],RCoutA[8],RCoutB[8];

int p;
uint16_t read16() 
{
  uint16_t r = (inBuf[p++]&0xFF);
  r+= (inBuf[p++]&0xFF)<<8;
  return r;
}

uint16_t t,t1,t2;
uint16_t write16(boolean a) 
{
  if(a)
  {
    t1=outBuf[p++]>>8;
    t2=outBuf[p-1]-(t1<<8);
    t=t1;
  }
  else
    t=t2;
  return t;
}

typedef  unsigned char byte;
byte getChecksum(byte length,byte cmd,byte mydata[])
{
  //三个参数分别为： 数据长度  ，  指令代码  ，  实际数据数组
  byte checksum=0;
  checksum ^= (length&0xFF);
  checksum ^= (cmd&0xFF);
  for(int i=0;i<length;i++)
  {
    checksum ^= (mydata[i]&0xFF);
  }
  return checksum;
} 

void data_rx()
{
  //  s_struct_w((uint8_t*)&inBuf,16);
  p=0;
  for(int i=0;i<8;i++) 
  {
    RCin[i]=read16();
    /*
    Serial.print("RC[");
     Serial.print(i+1);
     Serial.print("]:");
     
     Serial.print(inBuf[2*i],DEC);
     Serial.print(",");
     Serial.print(inBuf[2*i+1],DEC);
     
     Serial.print("---");
     Serial.println(RCin[i]);
     */
    //    delay(50);        // delay in between reads for stability
  }
}

void data_tx()
{
  p=0;
  for(int i=0;i<8;i++) 
  {
    RCoutA[i]=write16(1);
    RCoutB[i]=write16(0);

    /*
    Serial.print("RC[");
     Serial.print(i+1);
     Serial.print("]:");
     
     Serial.print(RCout[i]);
     
     Serial.print("---");
     
     Serial.print(RCoutA[i],DEC);
     Serial.print(",");
     Serial.print(RCoutB[i],DEC);
     
     Serial.println("");
     */
    //    delay(50);        // delay in between reads for stability
  }
}

void data_send()
{
  data_tx();

#if !defined(__AVR_ATmega128RFA1__)
  static byte buf_head[3];
  buf_head[0]=0x24;  
  buf_head[1]=0x4D;  
  buf_head[2]=0x3C;

  static byte buf_length[1];
  buf_length[0]=0x10;

  static byte buf_code[1];
  buf_code[0]=0xC8;
#endif


  static byte buf_data[16];
  for(int a=0;a<8;a++)
  {
    buf_data[2*a]=RCoutB[a];
    buf_data[2*a+1]=RCoutA[a];
  }

  byte check_sum=getChecksum(16,200,buf_data);
  static byte buf_body[1];
  buf_body[0]=check_sum;

  //----------------------
#if defined(__AVR_ATmega128RFA1__)
  mwc_port.beginTransmission();

  mwc_port.write(0xaa);
  mwc_port.write(0xbb);
  mwc_port.write(0xaa);

  mwc_port.write(buf_data[0]);
  mwc_port.write(buf_data[1]);
  mwc_port.write(buf_data[2]);
  mwc_port.write(buf_data[3]);
  mwc_port.write(buf_data[6]);
  mwc_port.write(buf_data[7]);
  mwc_port.write(buf_data[4]);
  mwc_port.write(buf_data[5]);

  mwc_port.write(buf_data[8]);
  mwc_port.write(buf_data[9]);
  mwc_port.write(buf_data[10]);
  mwc_port.write(buf_data[11]);
  mwc_port.write(buf_data[12]);
  mwc_port.write(buf_data[13]);
  mwc_port.write(buf_data[14]);
  mwc_port.write(buf_data[15]);

  mwc_port.write(0x0d);
  mwc_port.write(0x0a);

  mwc_port.endTransmission();
#else
  for(int a=0;a<3;a++)
    mwc_port.write(buf_head[a]);
  for(int a=0;a<1;a++)
    mwc_port.write(buf_length[a]);
  for(int a=0;a<1;a++)
    mwc_port.write(buf_code[a]);
  for(int a=0;a<16;a++)
    mwc_port.write(buf_data[a]);
  for(int a=0;a<1;a++)
    mwc_port.write(buf_body[a]);
#endif
}




