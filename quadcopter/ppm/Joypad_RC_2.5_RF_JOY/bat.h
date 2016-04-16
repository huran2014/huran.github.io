int _V_bat=_V_min;

#if defined(__AVR_ATmega128RFA1__)
#define _V_math 12.1786
#else
#define _V_math 8.0457
#endif

void vobat()
{
  //_V_bat=10*((voltage*analogRead(PIN_bat)/1023.0f)/(33.0f/(51.0f+33.0f)));
  _V_bat=analogRead(PIN_bat)/_V_math;
  if(_V_bat<_V_min) _V_bat=_V_min;
  if(_V_bat>_V_max) _V_bat=_V_max;

#ifdef Serial_DEBUG
  Serial.print("_V_bat: ");
  Serial.println(_V_bat);
#endif
}

