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
  double intensity4 = 0;
  int count = 0;
  
  leftMotor.setSpeed(100);
  rightMotor.setSpeed(100);
  leftMotor.rotate(Navigation.convertAngle(360), true);
  rightMotor.rotate(-Navigation.convertAngle(360), true);
  /**
   * Resizing the actual intensity values to make it more readable
   * and thus easier to test
   */
  int[] buffer = {1000, 1000, 1000};
  while(leftMotor.isMoving())
  {
    colorSensor.getRGBMode().fetchSample(lightData, 0);
  for (int i = 0; i < 3; i++) {
      lightData[i] *= 2000; 
    }
  for(int i = 0; i < buffer.length - 1; i++)
  {
    buffer[i] = buffer[i+1];
  }
  
  int intensity = (int)( lightData[0] + lightData[1] + lightData[2]);
  buffer[buffer.length-1] = intensity;
  if(count % 5 == 0)
    intensity4 = intensity;
  System.out.println(odometer.getXYT()[2] + ", " + intensity + ", " +  intensity4 + ", " + average(buffer));
  try {
    Thread.sleep(50);
  } catch (InterruptedException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
  count++;
   }
  System.exit(0);
  }
  public static int average(int[] arr)
  {
    int sum = 0;
    for(int i = 0; i < arr.length; i++)
    sum += arr[i];
  
  return sum/arr.length;
}
}
 


