package ca.mcgill.ecse211.lab4;

import static ca.mcgill.ecse211.lab4.Resources.leftMotor;
import static ca.mcgill.ecse211.lab4.Resources.rightMotor;
import lejos.hardware.Sound;
import static ca.mcgill.ecse211.lab4.Resources.odometer;
import static ca.mcgill.ecse211.lab4.Resources.colorSensor;

public class LightLocalizer {
  public static int[] buffer = new int[5];

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

    double intensity4 = 0;
    int count = 0;
    initalize(buffer);
    leftMotor.setSpeed(100);
    rightMotor.setSpeed(100);
    leftMotor.rotate(Navigation.convertAngle(360), true);
    rightMotor.rotate(-Navigation.convertAngle(360), true);
    int prevIntensity = 1500;
    int intensity = 2000;
    // difference
    int diff = 1000;
    int prevDiff = 100;
    int lines = 0;
    // if light sensing returns to 0 to prevent multiple line readings.
    boolean steadyState = true;
    /*
     * in the sweep. stops when either of the motors stop.
     */
    while (leftMotor.isMoving()) {

      intensity = calculateIntensity();
      if (diff < 0) // positive value, so has returned to steady state
        steadyState = true;
      /*
       * only check for lines every 5 iterations so that polling and action frequency aren't the same. also skip over
       * first ten counts because of errors when polling starts.
       * 
       */
      if (diff > 75 && steadyState == true) {
        Sound.beepSequence();
        // System.out.println("\n\nThink I detected a line at: " + odometer.getXYT()[2] + "\n\n");
        count = 0; // reset counter so it doesn't check for lines for 5 samples
        steadyState = false;
        lines++;
      }


      // create a variable that resets when it goes positive
      diff = intensity - prevIntensity;
      System.out.println(odometer.getXYT()[2] + ", " + intensity + ", " + diff + ", " + lines * 50);

      sleepFor(40);


      prevIntensity = intensity;
      prevDiff = diff;
    }
    // store previous value of intensity in prevIntensity

    System.exit(0);
  }
  /*
   * returns RMS of an int array
   * 
   */

  public static int average(int[] arr) {
    double sum = 0;
    for (int i = 0; i < arr.length; i++)
      sum = sum + arr[i] * arr[i];

    return (int) Math.sqrt((sum / arr.length));
  }

  /**
   * sleeps thread for a set amount of time
   * 
   * @param duration amount to sleep for
   */
  public static void sleepFor(int duration) {
    try {
      Thread.sleep(duration);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * initalizes the buffer array
   * 
   * @param buffer array to initalize
   */
  public static void initalize(int[] buffer) {

    float[] lightData = new float[3];
    colorSensor.getRGBMode().fetchSample(lightData, 0);
    /**
     * Resizing the actual intensity values to make it more readable and thus easier to test. Also easier to deal with
     * ints than double precision
     */

    for (int i = 0; i < 3; i++) {
      lightData[i] *= 2000;
    }
    int init = (int) (lightData[0] + lightData[1] + lightData[2]);
    
    for (int i = 0; i < buffer.length; i++) {
      buffer[i] = init;
    }
  }

}


