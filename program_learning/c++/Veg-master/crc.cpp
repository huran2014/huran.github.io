#include "crc.h"

unsigned short int cal_crc(unsigned char *ptr, unsigned int  len)
{
 unsigned char i;
 unsigned int crc=0;
 while(len--!=0)
 {
     for(i=0x80; i!=0; i/=2)
  {
        if((crc&0x8000)!=0) 
   {
    crc*=2;
    crc^=0x18005;
   }
         else
   {
    crc*=2;
   }
   if((*ptr&i)!=0) crc^=0x18005; 
     }
     ptr++;
   }
   return(crc); 
}

