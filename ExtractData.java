/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author David
 */
public class ExtractData {

    private static final int FUEL_PRICES = 0;
    private static final int FUEL_CONSUMPTION_PRICES = 1;
    private static final int NUMBER_OF_PASSENGERS = 2;
    
    private Map<Double, Integer> prices = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
/*
        extractDataAndAverage("transportation.retail_deisel_prices", FUEL_PRICES);
        
        extractDataAndAverage("transportation.usa_airline_fuel_cost_consumption", FUEL_CONSUMPTION_PRICES);
        
        extractDataAndAverage("transportation.monthy_number_of_airline_passengers_all_carriers", NUMBER_OF_PASSENGERS);
        
*/
    }

    public double[] extractDataAndAverage(String table, int type) {
        prices = new HashMap<>();
        double[] averageValues = new double[12];

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
            }
            else {
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

    public String getName(int type) {
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
    
}
