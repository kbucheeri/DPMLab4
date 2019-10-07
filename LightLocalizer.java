package ca.mcgill.ecse211.lab4;

import static ca.mcgill.ecse211.lab4.Resources.leftMotor;
import static ca.mcgill.ecse211.lab4.Resources.rightMotor;
import lejos.hardware.Sound;
import static ca.mcgill.ecse211.lab4.Resources.odometer;
import static ca.mcgill.ecse211.lab4.Resources.colorSensor;

public class LightLocalizer {
  static int direction =0;
  public static void Localize()
  { 
    if (LocalizeDirection() == 1) {
      //Move East
    } else if (LocalizeDirection() == 2) {
      //Move North
    }
  //TODO implement light localization
  }
  public static int LocalizeDirection() {
    
    
  
    /**
     * Float Array to store RGB Raw values
     */
  float[] lightData = new float[3];
  double floor_intensity = 0;
  
  
  
  colorSensor.getRGBMode().fetchSample(lightData, 0);
  
  /**
   * Resizing the actual intensity values to make it more readable
   * and thus easier to test
   */
  for (int i = 0; i < 3; i++) {
      lightData[i] *= 2000; 
    }
  double intensity = lightData[0] + lightData[1] + lightData[2];
  
  if (intensity < 1000 && (leftMotor.getSpeed() + rightMotor.getSpeed()) > 0) {
   // double angle = odometer.getXYT()[2];
    /**
     * When headed 0 degree
     * Get orientation 
     */
    if (UltrasonicLocalizer.getAngle() == 0) {
      //Detects y Line
      direction = 1; //at (X,1)
      Sound.beep();
    }
    /**
     * When headed 90 degree
     */
    else if (UltrasonicLocalizer.getAngle() == 90) {
      //X Line
      direction =2; //at (1,Y)
      Sound.beep();
    }

  }
 return direction;
  }
  
  
  
}


