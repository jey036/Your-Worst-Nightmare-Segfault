/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author: Your Worst Nightmare Segfault
 */

public class Skyfault extends JFrame {

    JCheckBox Jan;
    JCheckBox Feb;
    JCheckBox Mar;
    JCheckBox Apr;
    JCheckBox May;
    JCheckBox Jun;
    JCheckBox Jul;
    JCheckBox Aug;
    JCheckBox Sep;
    JCheckBox Oct;
    JCheckBox Nov;
    JCheckBox Dec;
    static int[] arrofmonth = new int[12];

    JButton montht, dontcaret;
    JButton month, dontcare;
    JPanel p1, p2, p3;
    ImageIcon pic;
    JLabel buttom = new JLabel("Do you have any month(s) in mind?");

    static double[] rankings;
    static String cheapestMonth;

    Skyfault() {
        setTitle("Find the most affordable vacation month!");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        month = new JButton("Yes");
        dontcare = new JButton("No");

        p1 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();
        month.addActionListener(new monthListener());
        dontcare.addActionListener(new dontcareListener());

        add(p1);

        pic = new ImageIcon("background.jpg");

        p2.add(new JLabel(pic));
        this.add(p2, BorderLayout.NORTH);
        p1.add(buttom, BorderLayout.CENTER);
        p1.add(month, BorderLayout.SOUTH);
        p1.add(dontcare, BorderLayout.SOUTH);
        this.pack();
        setVisible(true);

    }

    class monthListener implements ActionListener {

        public void actionPerformed(ActionEvent b) {
            for (int j = 0; j < arrofmonth.length; j++) {
                arrofmonth[j] = 0;
            }
            if (b.getActionCommand().equals("Yes")) {
                Jan = new JCheckBox("Jan");
                Feb = new JCheckBox("Feb");
                Mar = new JCheckBox("Mar");
                Apr = new JCheckBox("Apr");
                May = new JCheckBox("May");
                Jun = new JCheckBox("Jun");
                Jul = new JCheckBox("Jul");
                Aug = new JCheckBox("Aug");
                Sep = new JCheckBox("Sep");
                Oct = new JCheckBox("Oct");
                Nov = new JCheckBox("Nov");
                Dec = new JCheckBox("Dec");
                Jan.addItemListener(new cbListener());
                Feb.addItemListener(new cbListener());
                Mar.addItemListener(new cbListener());
                Apr.addItemListener(new cbListener());
                May.addItemListener(new cbListener());
                Jun.addItemListener(new cbListener());
                Jul.addItemListener(new cbListener());
                Aug.addItemListener(new cbListener());
                Sep.addItemListener(new cbListener());
                Oct.addItemListener(new cbListener());
                Nov.addItemListener(new cbListener());
                Dec.addItemListener(new cbListener());
                String message = "Select from the following months:";
                Object[] params = {message, Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec};
                int n = JOptionPane.showConfirmDialog(null, params, "Disconnect Products", JOptionPane.OK_CANCEL_OPTION);
                double temp = 0;
                int index = 0;
                for (int i = 0; i < arrofmonth.length; i++) {
                    if (arrofmonth[i] == 1) {
                        temp = rankings[i];
                        index = i;
                        break;
                    }
                }
                for (int j = 0; j < rankings.length; j++) {
                    if (arrofmonth[j] == 1) { // selected
                        if (temp > rankings[j]) {
                            temp = rankings[j];
                            index = j;
                        }
                    }
                }
                boolean checked = false;
                for (int i = 0; i < arrofmonth.length; i++) {
                    if (arrofmonth[i] == 1) {
                        checked = true;
                    }
                }
                if (n == 0) {
                    if (checked) {
                        JOptionPane.showMessageDialog(null, "Here is the best price for the month you have chosen: " + getMonthName(index));
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Please select a month.");                        
                    }
                }
            }
        }
    }

    class dontcareListener implements ActionListener {

        public void actionPerformed(ActionEvent b) {
            if (b.getActionCommand().equals("No")) {
                JOptionPane.showMessageDialog(null, "Here is the best price: " + cheapestMonth);
            }
        }
    }

    class cbListener implements ItemListener {

        public void itemStateChanged(ItemEvent b) {
            if (b.getSource() == Jan) {
                if (Jan.isSelected()) {
                    arrofmonth[0] = 1;
                }
            }
            if (b.getSource() == Feb) {
                if (Feb.isSelected()) {
                    arrofmonth[1] = 1;
                }
            }
            if (b.getSource() == Mar) {
                if (Mar.isSelected()) {
                    arrofmonth[2] = 1;
                }
            }
            if (b.getSource() == Apr) {
                if (Apr.isSelected()) {
                    arrofmonth[3] = 1;
                }
            }
            if (b.getSource() == May) {
                if (May.isSelected()) {
                    arrofmonth[4] = 1;
                }
            }
            if (b.getSource() == Jun) {
                if (Jun.isSelected()) {
                    arrofmonth[5] = 1;
                }
            }
            if (b.getSource() == Jul) {
                if (Jul.isSelected()) {
                    arrofmonth[6] = 1;
                }
            }
            if (b.getSource() == Aug) {
                if (Aug.isSelected()) {
                    arrofmonth[7] = 1;
                }
            }
            if (b.getSource() == Sep) {
                if (Sep.isSelected()) {
                    arrofmonth[8] = 1;
                }
            }
            if (b.getSource() == Oct) {
                if (Oct.isSelected()) {
                    arrofmonth[9] = 1;
                }
            }
            if (b.getSource() == Nov) {
                if (Nov.isSelected()) {
                    arrofmonth[10] = 1;
                }
            }
            if (b.getSource() == Dec) {
                if (Dec.isSelected()) {
                    arrofmonth[11] = 1;
                }
            }
        }

    }

    /**
     * This function takes in datas and returns its priority (expensiveness);
     * smaller number = cheaper.
     */
    public static double[] priority(
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
     * This function takes in an array of double and returns the priority
     * (expensiveness) of each month.
     */
    private static double[] calcNmScore(double[] months) {
        double[] score = new double[months.length];

        double avg = calcAvg(months);

        double stdev = calcStdev(months, avg);

        for (int i = 0; i < months.length; i++) {
            score[i] = (months[i] - avg) / stdev;
        }

        return score;
    }

    /**
     * This function takes in an array of double and returns the avg of the
     * numbers in array.
     */
    private static double calcAvg(double[] months) {
        double total = 0;
        for (int i = 0; i < months.length; i++) {
            total = total + months[i];
        }

        return total / months.length;
    }

    /**
     * This function takes in an array of double and double value and returns
     * the standard deviation of the array.
     */
    private static double calcStdev(double[] months, double avg) {
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

    private static final int FUEL_PRICES = 0;
    private static final int FUEL_CONSUMPTION_PRICES = 1;
    private static final int NUMBER_OF_PASSENGERS = 2;

    private static Map<Double, Integer> prices = null;

    public static double[] extractDataAndAverage(String table, int type) {
        prices = new HashMap<>();
        double[] averageValues = new double[12]; // SIZE

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM " + table);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String dateString = "";

                if (type == FUEL_CONSUMPTION_PRICES) {
                    dateString = rs.getString("Month");
                } else {
                    dateString = rs.getString("Date");

                }
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = sdf.parse(dateString);

                    long timestamp = date.getTime();
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(timestamp);
                    int month = cal.get(Calendar.MONTH);
                    double price;

                    switch (type) {
                        case FUEL_PRICES:
                            price = rs.getDouble("Price");
                            prices.put(price, month);
                            break;
                        case FUEL_CONSUMPTION_PRICES:
                            price = rs.getDouble("Domestic Cost (Millions of $)");
                            prices.put(price, month);
                            break;
                        case NUMBER_OF_PASSENGERS:
                            price = rs.getLong("Domestic");
                            prices.put(price, month);
                            break;
                    }

                } catch (ParseException ex) {

                }
            }

            double averageValue = 0;

            int nMonth = 0; // 0 = January
            if (type == NUMBER_OF_PASSENGERS) {
                while (nMonth < 12) {
                    long totalValue = 0;
                    int size = 0;

                    for (Map.Entry<Double, Integer> entry : prices.entrySet()) {
                        Double key = entry.getKey();
                        Integer value = entry.getValue();
                        if (value == nMonth) {
                            totalValue += key;
                            size += 1;
                        }
                    }
                    averageValue = totalValue / size;
                    averageValues[nMonth] = averageValue;

                    nMonth++;
                }
            } else {
                while (nMonth < 12) {
                    double totalValue = 0;
                    int size = 0;

                    for (Map.Entry<Double, Integer> entry : prices.entrySet()) {
                        Double key = entry.getKey();
                        Integer value = entry.getValue();
                        if (value == nMonth) {
                            totalValue += key;
                            size += 1;
                        }
                    }
                    averageValue = totalValue / size;
                    averageValues[nMonth] = averageValue;

                    nMonth++;
                }
            }

        } catch (SQLException ex) {

        }

        return averageValues;

    }

    public static String getName(int type) {
        switch (type) {
            case 0:
                return "FUEL_PRICES";
            case 1:
                return "FUEL_CONSUMPTION_PRICES";
            case 2:
                return "NUMBER_OF_PASSENGERS";
        }
        return "";
    }

    public String getMonthName(int index) {
        System.out.println(index);
        switch (index) {
            case 0:
                return "January";
            case 1:
                return "February";
            case 2:
                return "March";
            case 3:
                return "April";
            case 4:
                return "May";
            case 5:
                return "June";
            case 6:
                return "July";
            case 7:
                return "August";
            case 8:
                return "September";
            case 9:
                return "October";
            case 10:
                return "November";
            case 11:
                return "December";
        }
        return null;
    }

    public static void main(String[] args) {

        double[] fuelPrices = extractDataAndAverage("transportation.retail_deisel_prices", FUEL_PRICES);

        double[] fuelConsumptionPrices = extractDataAndAverage("transportation.usa_airline_fuel_cost_consumption", FUEL_CONSUMPTION_PRICES);

        double[] numberOfPassengers = extractDataAndAverage("transportation.monthy_number_of_airline_passengers_all_carriers", NUMBER_OF_PASSENGERS);

        rankings = priority(fuelPrices, numberOfPassengers, fuelConsumptionPrices);

        double temp = rankings[0];
        int index = 0;
        for (int i = 0; i < rankings.length; i++) {
            if (temp > rankings[i]) {
                temp = rankings[i];
                index = i;
            }
        }

        switch (index) {
            case 0:
                cheapestMonth = "January";
                break;
            case 1:
                cheapestMonth = "February";
                break;
            case 2:
                cheapestMonth = "March";
                break;
            case 3:
                cheapestMonth = "April";
                break;
            case 4:
                cheapestMonth = "May";
                break;
            case 5:
                cheapestMonth = "June";
                break;
            case 6:
                cheapestMonth = "July";
                break;
            case 7:
                cheapestMonth = "August";
                break;
            case 8:
                cheapestMonth = "September";
                break;
            case 9:
                cheapestMonth = "October";
                break;
            case 10:
                cheapestMonth = "November";
                break;
            case 11:
                cheapestMonth = "December";
                break;
        }

        Skyfault a2 = new Skyfault();
    }

}
