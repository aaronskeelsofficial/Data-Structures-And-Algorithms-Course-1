package src.projects;
import java.util.Scanner;
import java.util.InputMismatchException;

/*
*  Goal: "Find Number of Days Above Average Temperature"
*  Input: # of days worth of temperatures, temp for each day
*  Output:
*   - Average Temp
*   - How many days were above the average
*  Code To Execute: `ProjectOne.Test();`
*/

/** Represents class for running project one
* @author Aaron Skeels
* @author aaronskeels.work/
* @version 1.0.1
*/
public class ProjectOne {
  double[] temperatureArray;
  int numberOfTemperatures = 0, daysAboveAverage = 0;
  double averageTemperature = 0;
  
  /** Initializes project one operations
  * @version 1.0.1
  * @since 1.0.0
  * V1.0.1 Changelog:
  *  - Replaced all `temperatureArray.length` calls with `numberOfTemperatures` for one less step to get value (more optimized?)
  *  - Combined functionality of two (get temperatures and calculate average) for loops into one (more optimized)
  */
  public ProjectOne() {
    Scanner scanner = new Scanner(System.in);
    try {
      // Get how many temperatures
      System.out.print("How many days' temperatures will you be sending?: ");
      numberOfTemperatures = scanner.nextInt();
      temperatureArray = new double[numberOfTemperatures];
      // Get temperatures & calculate average
      for (int i = 0;i < numberOfTemperatures;i++) {
        System.out.print("Please enter day " + (i+1) + "'s temperature: ");
        temperatureArray[i] = scanner.nextDouble();
        averageTemperature += temperatureArray[i];
      }
      averageTemperature /= numberOfTemperatures;
      // Calculate days above average
      for (int i = 0;i < numberOfTemperatures;i++) {
        if (temperatureArray[i] > averageTemperature)
          daysAboveAverage++;
      }
      // Provide output
      System.out.println("\nAverage = " + averageTemperature);
      System.out.println(daysAboveAverage + " day(s) were above the average.");
    } catch (InputMismatchException e) {
      //TODO - Split try catch into separate integer/double explanations
      System.out.println("You must enter only a number.");
    }
  }





  /* --------------------------------------------------
  *  - TEST METHODS
  *  - Purpose: All following methods were used to test functionality implementations.
  *     They will have no documentation. Probably ignore.
  *  --------------------------------------------------
  */
  public static void Test() {
    new ProjectOne();
  }
}