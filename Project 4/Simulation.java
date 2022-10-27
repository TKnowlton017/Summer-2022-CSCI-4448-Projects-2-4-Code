import java.util.ArrayList;
import java.util.Scanner;

//Simulation class to run the simulation, called from Main.java

public class Simulation {
    ArrayList<String> stores = new ArrayList<>();
    ArrayList<Store> store_object_list = new ArrayList<>();

    Simulation() {
        // initialize 2 stores
        stores.add("North");
        stores.add("South");

        // employee pool built using FACTORY PATTERN
        EmployeeFactory employeeFactory = new EmployeeFactory();
        Clerk[] Clerks = {
                (Clerk) employeeFactory.createNewEmployee("Rigby"),
                (Clerk) employeeFactory.createNewEmployee("Mordecai"),
                (Clerk) employeeFactory.createNewEmployee("Benson"),
                (Clerk) employeeFactory.createNewEmployee("Hopper")
        };

        Trainer[] Trainers = {
                (Trainer) employeeFactory.createNewEmployee("Skipps", "HAPHAZARD"),
                (Trainer) employeeFactory.createNewEmployee("Muscle Man", "POSITIVE"),
                (Trainer) employeeFactory.createNewEmployee("Pops", "NEGATIVE"),
                (Trainer) employeeFactory.createNewEmployee("Joyce", "POSITIVE")
        };

        // create new store objects
        for (String s : stores) {
            Store newStore = new Store(s, Clerks, Trainers);
            store_object_list.add(newStore);
        }
    }

    public void simulateStores() {
        int max_days = (int) ((Math.random() * (30 - 10)) + 10);
        // Days loop for max number of days
        for (int day = 1; day <= max_days; day++) {
            Logger myfile = Logger.getInstance(day); // SINGLETON Logger using lazy instantiation
            // go through each store
            for (Store store : store_object_list) {
                // Do the Store functions in order, see Store.java for function definitions
                System.out.println(" ");
                System.out.println("-----------Start of day " + day + " at store: " + store.storeName + "-----------");

                /*ABSTRACTION: All of our functions below are easily called
                with a single line of code and the correct parameters*/
                store.arriveAtStore(day);
                store.processDeliveries();
                store.feedAnimals();
                store.checkRegister();
                store.doInventory();
                store.trainAnimals();
                store.openTheStore();
                store.cleanTheStore();
                store.leaveTheStore(myfile);
            }
        }

        // Start of customer interaction with specific store
        System.out.print("Enter which store you would like to visit: ( ");
        // print out all store options you can visit
        for (String store : stores) {
            System.out.print(store + " ");
        }
        System.out.println(")");
        Scanner myObjStore = new Scanner(System.in);

        boolean input_values = true;
        while (input_values) {
            String storeInput = myObjStore.nextLine();
            // if inputted store string is in the list of stores
            if ((stores.contains(storeInput))) {
                // search for correct store object to pass to Customer_Interface.java menu function
                for (Store store : store_object_list) {
                    // pass in correct store object to a new Menu (found in Customer_Interface.java)
                    if (store.storeName.equalsIgnoreCase(storeInput)) {
                        Menu customer = new Menu();
                        customer.customerMenu(store, max_days + 1);
                    }
                }
                input_values = false;
            } else {
                System.out.println("Error: you need to enter the name of the store exactly as it is shown.");
            }
        }
        myObjStore.close();
        // end of customer interaction

        // print out summary for each store
        for (Store store : store_object_list) {
            store.summary();
        }
    }
}
