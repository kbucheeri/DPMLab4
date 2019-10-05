package ca.mcgill.ecse211.lab4;
import ca.mcgill.ecse211.lab4.Resources;

public class UltrasonicLocalizer {
  public static boolean sweepDone = false;
  public static void RisingEdge() {
    //TODO implement localization
    
    //record first edge angle, second edge angle, get average.
    int[] firstEdge = new int[3];
    int[] secondEdge = new int[3];
    int [] prevData = {0, 0, 0};
    while(sweepDone == false)
    {
      int theta = (int) Resources.odometer.getXYT()[2];
      int[] data = UltrasonicPoller.compareMethods();
      for(int i = 0; i < 3; i++) {
        if(data[i] < Resources.EDGE_THRESHOLD  && prevData[i] > Resources.EDGE_THRESHOLD)
        firstEdge[i] = theta;}
      for(int i = 0; i < 3; i++) {
        if(data[i] > Resources.EDGE_THRESHOLD  && prevData[i] < Resources.EDGE_THRESHOLD )
        secondEdge[i] =theta;
        }
      prevData = data;
      
    }
    System.out.println("\n\n\n\n");
    for(int i = 0; i < 3; i++)
      System.out.println("Schema " + i + ": " + firstEdge[i] + ",  " + secondEdge[i]
          + "average: " + (firstEdge[i] + secondEdge[i])/2);
  }

  public static void FallingEdge() {
    // TODO implement localization
  }
}
