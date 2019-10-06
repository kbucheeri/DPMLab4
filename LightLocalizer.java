package ca.mcgill.ecse211.lab4;

import static ca.mcgill.ecse211.lab4.Resources.leftMotor;
import static ca.mcgill.ecse211.lab4.Resources.rightMotor;
import lejos.hardware.Sound;
import static ca.mcgill.ecse211.lab4.Resources.odometer;
import static ca.mcgill.ecse211.lab4.Resources.colorSensor;

public class LightLocalizer {
  public static void Localize()
  {
    //TODO implement light localization
  
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
  
  if (intensity < 0.92 * floor_intensity && (leftMotor.getSpeed() + rightMotor.getSpeed()) > 0) {
   // double angle = odometer.getXYT()[2];
    /**
     * When headed 0 degree
     * Get orientation 
     */
    if (UltrasonicLocalizer.getAngle() == 0) {
      //Detects y Line
      Sound.beep();
    }
    /**
     * When headed 90 degree
     */
    else if (UltrasonicLocalizer.getAngle() == 90) {
      //X Line
      Sound.beep();
    }

  }
 
  }
}

