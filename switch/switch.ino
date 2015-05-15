#include <Servo.h> 

int incomingByte = 0;   // for incoming serial data 

Servo myservo;  // create servo object to control a servo 
 

void setup() {
        Serial.begin(9600);     // opens serial port, sets data rate to 9600 bps
 myservo.attach(9);
}

void loop() {

        // send data only when you receive data:
        if (Serial.available() > 0) {
                // read the incoming byte:
                incomingByte = Serial.read();

                // say what you got:
                Serial.print("received: ");
                Serial.println(incomingByte);
                
                if(incomingByte==1){
                myservo.write(54);
                }
                
                 if(incomingByte==0){
                myservo.write(80);
                delay(1000);
                myservo.write(54);
                }
                
                
        }
}
 
