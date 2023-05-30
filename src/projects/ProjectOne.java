package projects;
import java.util.Scanner;
import java.util.InputMismatchException;

/*
*  Goal: "Find Number of Days Above Average Temperature"
*  Input: # of days worth of temperatures, temp for each day
*  Output:
*   - Average Temp
*   - How many days were above the average
*  Code To Execute: `new ProjectOne();`
*/

/** Represents class for running project one
* @author Aaron Skeels
* @author aaronskeels.work/
* @version 1.0.0
*/
public class ProjectOne {
  double[] temperatureArray;
  int numberOfTemperatures = 0, daysAboveAverage = 0;
  double averageTemperature = 0;
  
  /** Initializes project one operations
  * @version 1.0.0
  * @since 1.0.0
  */
  public ProjectOne() {
    Scanner scanner = new Scanner(System.in);
    try {
      // Get how many temperatures
      System.out.print("How many days' temperatures will you be sending?: ");
      numberOfTemperatures = scanner.nextInt();
      temperatureArray = new double[numberOfTemperatures];
      // Get temperatures
      for (int i = 0;i < numberOfTemperatures;i++) {
        System.out.print("Please enter day " + (i+1) + "'s temperature: ");
        temperatureArray[i] = scanner.nextDouble();
      }
      // Calculate average
      for (int i = 0;i < temperatureArray.length;i++) {
        averageTemperature += temperatureArray[i];
      }
      averageTemperature /= temperatureArray.length;
      // Calculate days above average
      for (int i = 0;i < temperatureArray.length;i++) {
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
}