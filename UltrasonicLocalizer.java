package ca.mcgill.ecse211.lab4;

import static ca.mcgill.ecse211.lab4.Resources.leftMotor;
import static ca.mcgill.ecse211.lab4.Resources.rightMotor;
import ca.mcgill.ecse211.lab4.Resources;
import lejos.hardware.Button;
import lejos.hardware.Sound;

public class UltrasonicLocalizer {
  public static boolean sweepDone = false; // currently sweeping

  public static void RisingEdge() {
    // TODO implement localization

    // record first edge angle, second edge angle, get average.
    int firstEdge = 0; // initalize to low value so that it doesn't satisy the if statements
    int secondEdge = 0;
    int prevData = 0;
    leftMotor.setSpeed(160);
    rightMotor.setSpeed(160);
    leftMotor.rotate(Navigation.convertAngle(360), true);
    rightMotor.rotate(-Navigation.convertAngle(360), true);
    while ((leftMotor.isMoving() && rightMotor.isMoving()) == true) {
      int theta = (int) Resources.odometer.getXYT()[2];
      int data = UltrasonicPoller.getDistance();
      /**
       * Is below threshold and there was also a drop TODO implement noise margin
       */
      if (data < Resources.EDGE_THRESHOLD && prevData > Resources.EDGE_THRESHOLD) {
        firstEdge = theta;
        Sound.beep();
      }
      if (data > Resources.EDGE_THRESHOLD && prevData < Resources.EDGE_THRESHOLD) {
        secondEdge = theta;
        Sound.beep();
      }
      prevData = data;
    }

    UltrasonicPoller.setSleepTime(70);
    // get average of both data
    int ave = (firstEdge + secondEdge) / 2;
    System.out.println("\n\n\n\n");
      System.out.println(firstEdge + ",  " + secondEdge + " average: " + ave);
    double dtheta;
    if(firstEdge < secondEdge)
      dtheta = 240 - ave;
    else
      dtheta = 240 - 180 - ave;
    Resources.odometer.incrementTheta(dtheta);
    Navigation.turnTo(0);
    Sound.beep();
    
    //wait for reading to stabilize before measuring vertical distance.
   /* try {
      Thread.sleep(300);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    int distY = UltrasonicPoller.getDistance();
    Navigation.turnTo(270);
    //wait for reading to stabilize before measuring vertical distance.
    try {
      Thread.sleep(300);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    int distX = UltrasonicPoller.getDistance();
    Resources.odometer.setX(distX + 5); //sensor offset
    Resources.odometer.setY(distY + 5);
    try {
      Thread.sleep(300);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    Navigation.travelTo(30.48, 30.48);
    Navigation.turnTo(0);*/
    if(Button.waitForAnyPress() == Button.ID_ESCAPE)
      System.exit(0);
    

  }

  public static void FallingEdge() {
    // TODO implement localization
  }
}
