#include <SoftwareSerial.h>
SoftwareSerial mySerial(4, 5); // RX, TX
int a;
void setup()  
{
  // Open serial communications and wait for port to open:
  Serial.begin(9600);
  // set the data rate for the SoftwareSerial port
  mySerial.begin(9600);
}
 
void loop() // run over and over
{
  if (Serial.available())  
  {
    mySerial.write(Serial.read());
  }
  if(mySerial.available())  
  {
    Serial.write(mySerial.read());
  }
  a = analogRead(A0);
  if ( a < 600 )
  {
    mySerial.write(10);
  }
  delay(500);
}
