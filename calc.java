import java.util.*;

public class calc {

  /**
   * This function takes in datas and returns its priority (expensiveness);
   * smaller number = cheaper.
   */
  public double[] priority (
            double[] price, double[] passengers, double[] consumption) {

    double[] getPrice = calcNmScore(price);
    double[] getPassengers = calcNmScore(passengers);
    double[] getConsumption = calcNmScore(consumption);

    double[] priority = new double[price.length];

    for (int i = 0; i < price.length; i++) {
      priority[i] = getPrice[i] + getPassengers[i] + getConsumption[i];
    }

    return priority;
  }

  /**
   * This function takes in an array of double and
   * returns the priority (expensiveness) of each
   * month.
   */
  private double[] calcNmScore (double[] months) {
    double[] score = new double[months.length];

    double avg = calcAvg(months);

    double stdev = calcStdev(months, avg);

    for (int i = 0; i < months.length; i++) {
      score[i] = (months[i] - avg) / stdev;
    }

    return score;
  }

  /**
   * This function takes in an array of double and
   * returns the avg of the numbers in array.
   */
  private double calcAvg (double[] months) {
    double total = 0;
    for (int i = 0; i < months.length; i++) {
      total = total + months[i];
    }

    return total / months.length;
  }

  /**
   * This function takes in an array of double and
   * double value and returns the standard deviation
   * of the array.
   */
  private double calcStdev (double[] months, double avg) {
    double total = 0;
    for (int i = 0; i < months.length; i++) {
      double temp = (months[i] - avg);
      temp = temp * temp;
      total = total + temp;
    }

    double stdev = total / avg;
    stdev = Math.sqrt(stdev);

    return stdev;
  }
}
