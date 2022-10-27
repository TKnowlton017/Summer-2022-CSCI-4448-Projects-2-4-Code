import java.util.*;

//Observer Pattern
//Observer Class will hold an arrayList of observers and send them daily updates
public class Observer {
    ArrayList<String> subscription = new ArrayList<>();
    
    public Observer(){
        addObservers("Observer 1");//add one class initially
    }

    public void addObservers(String name){
        subscription.add(name);
    }

    public void removeObservers(String name){
        subscription.remove(name);
    }
    
    //will make a call to dailySummary() function in Tracker.java to get updates for every employee
    public void displayDailySummary(int simDay, ArrayList<Tracker> track_employee_list){
        for (int i=0; i<subscription.size(); i++){
            System.out.println(" ");
            System.out.println(subscription.get(i) + " IS RECIEVING THEIR DAILY TRACKER UPDATE: ");
            Tracker.dailySummary(simDay, track_employee_list);
        }
    }    
}//end of observer class
