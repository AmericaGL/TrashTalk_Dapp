#include <SoftwareSerial.h>
#include <Wire.h>
#include "rgb_lcd.h"
#include "Ultrasonic.h"

rgb_lcd lcd;
Ultrasonic ultrasonic(7);
void setup() 
{  
  //set up the LCD's number of columns and rows:
  lcd.begin(16,2);
  //initialize the serial communications:
  Serial.begin(9600);
}

void loop() 
{
  //variable to hold a distance
  long RangeInCentimeters;
  long WarningZone = 30;
  long DangerZone = 15;
  RangeInCentimeters = ultrasonic.MeasureInCentimeters();
  delay(150);
  //Clear the display
  lcd.clear();
  //Set the cursor to the top left position
  lcd.setCursor(0,0);
  //Print message to display
  lcd.print("Powered by NEM");
  //Set the cursor to the second row
  lcd.setCursor(0,1);
  //This function will print the range out as the decimal value
  lcd.print("TrashTalk:");
  //Turn the background RED if we are closer than DangerZone
  if(RangeInCentimeters < DangerZone)
  {
    lcd.setRGB(255,0,0);
    lcd.setCursor(10,1) ;
    lcd.print("FULL");
  //Turn the background Yellow if we are between DangerZone and Warning Zone
  }else if(RangeInCentimeters < WarningZone){
    lcd.setRGB(255,255,0);
    lcd.setCursor(10,1) ;
    lcd.print("CAUTION");
  //Turn the background green if we are further than WarningZone
  }else{
    lcd.setRGB(0,255,0);
    lcd.setCursor(10,1) ;
    lcd.print("ACTIVE");
  }
  //Print the units after the distance
  //lcd.setCursor(5,1) ;
  //lcd.print("cm");
} 

