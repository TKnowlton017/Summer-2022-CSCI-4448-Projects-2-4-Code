import java.util.ArrayList;
import java.util.Objects;

// Tracker.java is meant to track sales info for each employee and extends from clerks class
// SINGLETON PATTERN using LAZY INSTANTIATION
public class Tracker extends Clerk {

    public Double totalSales;
    public int totalItemsSold;

    private static Tracker instance = null;

    //constructor will assign name and initialize values to 0
    private Tracker(String clerkName) {
        super(clerkName);
        this.totalSales = 0.0;
        this.totalItemsSold = 0;
    }

    public static Tracker getInstance(String clerkName) {
        if (instance == null) {
            instance = new Tracker(clerkName);
        }
        return instance;
    }

    //print out daily summary of up to date sale totals
    public static void dailySummary(int simDay, ArrayList<Tracker> track_employee_list) {

        System.out.println(" ");

        //Current Clerk Sales for each day
        System.out.println("Tracker: Day " + simDay);
        System.out.println("Clerks      Items Sold      Purchase Price Total");
        for (Tracker t : track_employee_list) {
            System.out.format("%-12s%-16d$%.2f", t.getName(), t.totalItemsSold, t.totalSales);
            System.out.println(" ");

        }
    }

    //add sales info for each purchase to correct employee
    public static void addSalesData(ArrayList<Tracker> track_employee_list, String currentClerk, double salePrice) {
        //access correct employee in the list to attribute sales to
        int index_value = 0;
        for (int i = 0; i < track_employee_list.size(); i++) {
            if (Objects.equals(track_employee_list.get(i).getName(), currentClerk)) {
                index_value = i;
                break;
            }
        }
        track_employee_list.get(index_value).totalSales += salePrice;//add sale price to employee's total sales
        track_employee_list.get(index_value).totalItemsSold += 1;//employee sells another item

    }

}//end of tracker class
