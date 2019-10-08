package ca.mcgill.ecse211.lab4;

import static ca.mcgill.ecse211.lab4.Resources.leftMotor;
import static ca.mcgill.ecse211.lab4.Resources.rightMotor;
import lejos.hardware.Sound;
import static ca.mcgill.ecse211.lab4.Resources.odometer;
import static ca.mcgill.ecse211.lab4.Resources.colorSensor;

public class LightLocalizer {
  public static int[] buffer = {1000, 1000, 1000, 1000};

  /**
   * calculates the intensity from the light sensor and applies rudimentary filtering In the form of the arithmetic
   * mean.
   * 
   * @return The average of the previous light sensor intensity values.
   */
  public static int calculateIntensity() {
    float[] lightData = new float[3];
    colorSensor.getRGBMode().fetchSample(lightData, 0);
    /**
     * Resizing the actual intensity values to make it more readable and thus easier to test. Also easier to deal with
     * ints than double precision
     */

    for (int i = 0; i < 3; i++) {
      lightData[i] *= 2000;
    }
    for (int i = 0; i < buffer.length - 1; i++) {
      buffer[i] = buffer[i + 1];
    }
    int intensity = (int) (lightData[0] + lightData[1] + lightData[2]);
    buffer[buffer.length - 1] = intensity;
    
    return average(buffer);
  }

  /**
   * Performs light localization
   */
  public static void Localize() {
    /**
     * Float Array to store RGB Raw values
     */
    
    
    int prevIntensity = 1500;
    int intensity = 2000;
    // difference
    int diff = 100;
    int prevDiff = 100;
    /*
     * in the sweep. stops when either of the motors stop.
     */
   

     
      double odoAngle = odometer.getXYT()[2];
      int intensityLine = calculateIntensity();
      double intensity4 = 0;
      int count = 0;
      
      if (odoAngle > 70 && odoAngle <100) {
        
      //Make it move forward  
      leftMotor.forward();
      rightMotor.forward();
      
      //Detects Line
      if (intensityLine < 1000) {
      //Heading 90 degree
      leftMotor.stop();
      rightMotor.stop();
      Sound.beep();
      }
    }
    // store previous value of intensity in prevIntensity
    prevIntensity = intensity;
    prevDiff = diff;
    System.exit(0);
  }
/*
 * Weighted average
 * 
 */
  
  public static int average(int[] arr) {
    int sum = 0;
    for (int i = 0; i < arr.length; i++)
      sum  = sum + arr[i];

    return sum / arr.length;
  }

  public static void sleepFor(int duration) {
    try {
      Thread.sleep(duration);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}

