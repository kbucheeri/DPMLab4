package ca.mcgill.ecse211.lab4;

import static ca.mcgill.ecse211.lab4.Resources.leftMotor;
import static ca.mcgill.ecse211.lab4.Resources.rightMotor;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import static ca.mcgill.ecse211.lab4.Resources.odometer;
import static ca.mcgill.ecse211.lab4.Resources.colorSensor;

public class LightLocalizer {
  public static int[] buffer = new int[5];


  /**
   * Performs light localization
   * Assumes it starts facing 0 degrees in the corner block
   * Makes the robot at 1,1
   */
  public static void localizeDistance() {
    /**
     * Float Array to store RGB Raw values
     */
    leftMotor.setSpeed(100);
    rightMotor.setSpeed(100);
    leftMotor.forward();
    rightMotor.forward();
    // if light sensing returns to 0 to prevent multiple line readings.
    boolean steadyState = true;
    /*
     * in the sweep. stops when either of the motors stop.
     */
    while (true) {

      int diff = lightPoller.getIntensity();
      if (diff > 0) // negative value, so has returned to steady state (fluctuations around 0,0)
        steadyState = true;
      /*
       * spike of less than -75 implies a line has been detected. steadyState makes it
       * so that it only looks for lines again after it goes back to 0, i.e doesn't detect same lines multiple times.
       */
      if (diff < -75 && steadyState == true) {
        
        Sound.beepSequence();
        steadyState = false;
        break;

      }
      sleepFor(100);
    }
    odometer.setY(Resources.SENSOR_TO_WHEEL_DISTANCE);
    leftMotor.stop();
    rightMotor.stop();
    //back up
    leftMotor.rotate(-Navigation.convertDistance(15), true);
    rightMotor.rotate(-Navigation.convertDistance(15), false);
    Sound.beepSequenceUp();
    Navigation.turnTo(90);
    
    leftMotor.forward();
    rightMotor.forward();
    steadyState = true;
    while (true) {
      int diff = lightPoller.getIntensity();
      if (diff > 0) // negative value, so has returned to steady state (fluctuations around 0,0)
        steadyState = true;
      /*
       * spike of less than -75 implies a line has been detected. steadyState makes it
       * so that it only looks for lines again after it goes back to 0, i.e doesn't detect same lines multiple times.
       */
      LCD.drawString("Diff: " + diff, 0, 5);
      if (diff < -75 && steadyState == true) {
        
        Sound.beepSequence();
        steadyState = false;
        break;

      }
      sleepFor(40);
    }
    odometer.setX(Resources.SENSOR_TO_WHEEL_DISTANCE);
    leftMotor.stop();
    rightMotor.stop();
    
    Navigation.travelTo(0, 0);
    Navigation.turnTo(0);
    System.exit(0);
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
  * performs angle correction to 0,0.
  */
 public static void localizeAngle()
 {
   leftMotor.setSpeed(100);
   rightMotor.setSpeed(100);
   //Resources.SENSOR_TO_WHEEL_ANGLE;
   leftMotor.rotate(Navigation.convertAngle(360), true);
   rightMotor.rotate(-Navigation.convertAngle(360), false);
   int count = 0;
   boolean steadyState = true;
   while(leftMotor.isMoving())
   {

     int diff = lightPoller.getIntensity();
     if (diff > 0) // negative value, so has returned to steady state (fluctuations around 0,0)
       steadyState = true;
     /*
      * spike of less than -75 implies a line has been detected. steadyState makes it
      * so that it only looks for lines again after it goes back to 0, i.e doesn't detect same lines multiple times.
      */
     if (diff < -50 && steadyState == true) {
       
       Sound.beepSequence();
       steadyState = false;
       count++; //increment lines detected
       LCD.drawString("lines: " + count, 0, 6);
       System.out.println("Line detected at: " + odometer.getXYT()[2]);
     }
     sleepFor(50);
     //int error = theta - detectionAngle;
   }
 }
}


