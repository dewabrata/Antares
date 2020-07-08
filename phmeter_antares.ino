#include <DHTesp.h>

#include <AntaresESP8266HTTP.h>
#include <ArduinoJson.h>
#include "DHT.h"
#define DHTPIN D1
#define DHTTYPE DHT11
#define ACCESSKEY "ac562cdbf7891f3f:20a076d283e8506f"
#define WIFISSID "Juaracoding"
#define PASSWORD "bolobolo"
#define projectName "antares_workshop"
#define deviceName "phsensor"
AntaresESP8266HTTP antares(ACCESSKEY);
DHT dht(DHTPIN, DHTTYPE);

/*
  # This sample code is used to test the pH meter V1.1.
  # Editor : YouYou
  # Ver    : 1.1
  # DAT    : 2014.06.23
  # Product: analog pH meter V1.1
  # SKU    : SEN0161
*/
#define SensorPin A0            //pH meter Analog output to Arduino Analog Input 2
#define Offset 0.30            //deviation compensate
#define LED D2
#define samplingInterval 20
#define printInterval 800
#define ArrayLenth  40    //times of collection
int pHArray[ArrayLenth];   //Store the average value of the sensor feedback
int pHArrayIndex = 0;
 int saklar ;
 
 String Value,Unit,Label;
void setup(void)
{
  pinMode(LED, OUTPUT);
  pinMode(D5,OUTPUT);
  Serial.begin(9600);
  Serial.println("pH meter experiment!");    //Test the serial monitor
  dht.begin();
  antares.setDebug(true);
  antares.wifiConnection(WIFISSID,PASSWORD);
  
}
void loop(void)
{
  static unsigned long samplingTime = millis();
  static unsigned long printTime = millis();
  static float pHValue, voltage;
  if (millis() - samplingTime > samplingInterval)
  {
    pHArray[pHArrayIndex++] = analogRead(SensorPin);
    if (pHArrayIndex == ArrayLenth)pHArrayIndex = 0;
    voltage = avergearray(pHArray, ArrayLenth) * 5.0 / 1024 + 0.05;
    pHValue = 3.5 * voltage + Offset;
    samplingTime = millis();
  }
  if (millis() - printTime > printInterval)  //Every 800 milliseconds, print a numerical, convert the state of the LED indicator
  {
    Serial.print("Voltage:");
    Serial.print(voltage, 2);
    Serial.print("    pH value: ");
    Serial.println(pHValue);
    Serial.println(pHValue, 2);
    digitalWrite(LED, digitalRead(LED) ^ 1);
    printTime = millis();
  }

   float h = dht.readHumidity();
   float t = dht.readTemperature();
   float f = dht.readTemperature(true);
   if (isnan(h) || isnan(t) || isnan(f)) {
    Serial.println("Failed to read from DHT sensor!");
    return;
   }
    String dataFromAntares = antares.retrieveLatestData(projectName, deviceName);

 if (dataFromAntares != "")
 {
      StaticJsonBuffer<1000> jsonBuffer;
       
       dataFromAntares.replace(" ","");
       dataFromAntares.replace("\n","");
       dataFromAntares.replace("\r","");
       dataFromAntares.replace("\\\""," ");
       dataFromAntares.replace("[","");
       dataFromAntares.replace("]","");
       Serial.println(dataFromAntares);
       JsonObject& root = jsonBuffer.parseObject(dataFromAntares);
       JsonObject& root4 =
        jsonBuffer.parseObject(root["m2m:cin"].as<String>());
       Label = root4["pi"].as<String>();

         String con = root4["con"].as<String>();
         con.replace(" ","\"");

       JsonObject& contentAntares = jsonBuffer.parseObject(con);
       saklar = contentAntares["saklar"].as<int>();
       Serial.println(saklar);
       if (saklar==1)
           {
           digitalWrite(D5,HIGH);
           }
           else
           {
           digitalWrite(D5,LOW);
           }

       dataFromAntares=""; 
     
         antares.add("temperature", t);
         antares.add("humidity", h);
         antares.add("ph",pHValue);
   
        antares.add("saklar",saklar);
         antares.sendNonSecure(projectName, deviceName);
        
 }
 delay(10000);
}


double avergearray(int* arr, int number)
{
  int i;
  int max, min;
  double avg;
  long amount = 0;
  if (number <= 0)
  {
    Serial.println("Error number for the array to avraging!/n");
    return 0;
  }
  if (number < 5) //less than 5, calculated directly statistics
  {
    for (i = 0; i < number; i++)
    {
      amount += arr[i];
    }
    avg = amount / number;
    return avg;
  }
  else
  {
    if (arr[0] < arr[1])
    {
      min = arr[0]; max = arr[1];
    }
    else
    {
      min = arr[1]; max = arr[0];
    }
    for (i = 2; i < number; i++)
    {
      if (arr[i] < min)
      {
        amount += min;      //arr<min
        min = arr[i];
      }
      else
      {
        if (arr[i] > max)
        {
          amount += max;  //arr>max
          max = arr[i];
        }
        else
        {
          amount += arr[i]; //min<=arr<=max
        }
      }//if
    }//for
    avg = (double)amount / (number - 2);
  }//if
  return avg;
}
