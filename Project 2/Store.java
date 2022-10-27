/*
 * Store class for all the actions employees will do
 * Must be in same file as Main.java, Item.java, and Delivery.java
 *
 * Useful links:
 * objects as elements in arraylist-  https://www.geeksforgeeks.org/how-objects-can-an-arraylist-hold-in-java/
 * using math.random()- https://www.javatpoint.com/how-to-generate-random-number-in-java
 *
 *
 */

import java.util.*;

public class Store {

    // ENCAPSULATION: All senstive information is hidden by using private access modifier
    private final String[] clerks = {"Rigby", "Mordecai"};
    private final String[] trainers = {"Skipps", "Muscle Man"};
    private double register_cash, bank_withdrawal;


    String currentClerk;
    String currentTrainer;

    // trackers for how many consecutive days the trainers have worked
    int clerkTracker;
    int trainerTracker;
    int simDay; // current day in the simulation

    ArrayList<Item> store_inventory = new ArrayList<>(); // healthy pets and supplies of type Item
    ArrayList<Pet> pets_array = new ArrayList<>(); // all pets of type Pet (needs to be of type Pet to access pet attributes)
    ArrayList<Item> sold_items = new ArrayList<>(); // sold items collection
    ArrayList<String> unique_item_list = new ArrayList<>();//list of name of every unique item for use in DoInventory() function
    ArrayList<Delivery> deliveries = new ArrayList<>(); // for processing deliveries


    // Constructor: Initialize a starting set of Store Items. Class definitions found in Item.java
    public Store() {
        System.out.println("Initializing store inventory items");
        register_cash = 0.00; // no money in register initially
        bank_withdrawal = 0.00; // no initial withdrawal amount
        clerkTracker = 0;
        trainerTracker = 0;
        simDay = 0;

        //Pet Initialization
        newDogs();
        newCats();
        newBirds();
        // Supplies Initialization
        newFoods();
        newLeashes();
        newToys();
        newCatLitter();

        // to get unique list of items for use in DoInventory() function
        for (int i = 0; i < store_inventory.size(); i++) {
            //if unique item not found in unique_item_list then add it
            if (!(unique_item_list.contains(store_inventory.get(i).name))) {
                unique_item_list.add(store_inventory.get(i).name);
            }
        }
        //System.out.println("unique_item_list = " + unique_item_list);//delete

    }//End of initialization before start of simulation


    // Helper functions
    private void newDogs() {
        for (int i = 0; i < 3; i++) {
            Dog newItem = new Dog(simDay);

            store_inventory.add(newItem);
            pets_array.add(newItem);
        }
    }

    private void newCats() {
        for (int i = 0; i < 3; i++) {
            Cat newItem = new Cat(simDay);

            store_inventory.add(newItem);
            pets_array.add(newItem);
        }
    }

    private void newBirds() {
        for (int i = 0; i < 3; i++) {
            Bird newItem = new Bird(simDay);

            store_inventory.add(newItem);
            pets_array.add(newItem);
        }
    }

    private void newFoods() {
        for (int i = 0; i < 3; i++) {
            Food newItem = new Food(simDay);

            store_inventory.add(newItem);
        }
    }

    private void newLeashes() {
        for (int i = 0; i < 3; i++) {
            Leash newItem = new Leash(simDay);

            store_inventory.add(newItem);
        }
    }

    private void newToys() {
        for (int i = 0; i < 3; i++) {
            Toy newItem = new Toy(simDay);

            store_inventory.add(newItem);
        }
    }

    private void newCatLitter() {
        for (int i = 0; i < 3; i++) {
            CatLitter newItem = new CatLitter(simDay);

            store_inventory.add(newItem);
        }
    }

    // Store methods as described in the write up
    public void arriveAtStore(int day) // DONE
    {
        simDay = day;
        int randomClerkIndex = (int) Math.floor(Math.random() * clerks.length);
        String ClerkSelected = (clerks[randomClerkIndex]);
        if (ClerkSelected.equals(currentClerk)) { // ensure clerk does not work more than 3 days in a row
            clerkTracker += 1;
            if (clerkTracker > 3) {
                ClerkSelected = (clerks[clerks.length - 1 - randomClerkIndex]);
                clerkTracker = 0;
            }
        }
        System.out.println("Clerk " + ClerkSelected + " arrives at store on day " + simDay);
        currentClerk = ClerkSelected;

        int randomTrainerIndex = (int) Math.floor(Math.random() * trainers.length);
        String TrainerSelected = (trainers[randomTrainerIndex]);
        if (TrainerSelected.equals(currentTrainer)) { // ensure trainer does not work more than 3 days in a row
            trainerTracker += 1;
            if (trainerTracker > 3) {
                TrainerSelected = (trainers[trainers.length - 1 - randomTrainerIndex]);
                trainerTracker = 0;
            }
        }
        System.out.println("Trainer " + TrainerSelected + " arrives at store on day " + day);
        currentTrainer = TrainerSelected;
    }

    public void processDeliveries() // DONE
    {
        for (Delivery d : deliveries) {
            if (this.simDay == d.day) { // checks to see if a new item delivery has arrived
                System.out.println("Clerk " + currentClerk + " received a new shipment of item " + d.item + " and added them to the store's inventory.");
                switch (d.item) {
                    case "Dog":
                        newDogs();
                        break;
                    case "Cat":
                        newCats();
                        break;
                    case "Bird":
                        newBirds();
                        break;
                    case "Food":
                        newFoods();
                        break;
                    case "Leash":
                        newLeashes();
                        break;
                    case "Toy":
                        newToys();
                        break;
                    case "Cat Litter":
                        newCatLitter();
                        break;
                }
            }
        }
    }

    public void feedAnimals() // DONE
    {
        // Must do sick pets before healthy pets in the for-loop
        System.out.println(currentTrainer + " is feeding the pets: ");

        // Loop through all the pets in the pets arrayList
        for (Pet pet : pets_array) {
            // sick pets in pets_array
            if (!pet.healthy) {
                System.out.println("    - " + currentTrainer + " feeds the " + pet.summary() + " who is NOT healthy.");
                double prob = Math.random();

                // 25% probability that animal will get better
                if (prob <= 0.25) {
                    pet.healthy = true; // pet now better
                    store_inventory.add(pet); // add pet back into inventory

                    System.out.println("        - Awesome! " + pet.summary() + " has gotten better. ");

                }
            }

            // healthy pets in pets_array
            else {
                System.out.println("    - " + currentTrainer + " feeds the " + pet.summary() + " who is healthy.");
                double prob = Math.random();

                // 5% chance of animal getting sick
                // Probability formula:   https://www.javatpoint.com/how-to-generate-random-number-in-java
                if (prob <= 0.05) {
                    pet.healthy = false; // pet now sick
                    store_inventory.remove(pet);  // pet taken out of inventory, (keep line)

                    System.out.println("        - Oh no! A " + pet.summary() + " has gotten sick. ");
                }
            }
        }
    }

    public void checkRegister() // DONE
    {
        
        System.out.print(currentClerk + " has counted $");
        System.out.format("%.2f", register_cash);
        System.out.println(" in the register");

        if (register_cash < 200) {
            register_cash += (goToBank());
            System.out.println("There is now $" + register_cash + " in the register");
        }
    }

    public double goToBank() // DONE
    {
        System.out.println(currentClerk + " has gone to the bank and withdrawn $1000");
        bank_withdrawal += 1000;
        System.out.println("Total money withdrawn from bank so far: $" + bank_withdrawal);

        return 1000;
    }

    //COHESION: This function exhibits strong cohesion as it does all inventory related jobs for the program
    public void doInventory() // DONE
    {
        double total_inventory_value = 0.0;
        System.out.println("Clerk " + currentClerk + " is doing inventory");

        //add up the value of all the items in inventory (sick pets should not be in it due to FeedAnimals() function working)
        for (int i = 0; i < store_inventory.size(); i++) {
            total_inventory_value += (store_inventory.get(i).purchasePrice);
        }

        System.out.print("Clerk " + currentClerk + " has counted a total of $");
        System.out.format("%.2f", total_inventory_value);
        System.out.print(" in items in the store inventory.");
        System.out.println(" ");


        //Functionality for if we are out of inventory on an item
        //loop through store_inventory ArrayList and store in temp
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < store_inventory.size(); i++) {
            temp.add(store_inventory.get(i).name);
        }

        //System.out.println("Right now we have in our store a list of these items = " + temp);//delete

        for (int i = 0; i < unique_item_list.size(); i++) {
            String name_item = unique_item_list.get(i);
            //System.out.println("Checking if we have enough " + name_item);//delete

            /*
             *temp has current list of items in store_inventory by name
             *if a unique value in unique_item_list is not found, then
             *we are out of that item and need to call PlaceAnOrder()
             */
            if (!(temp.contains(name_item))) {
                //System.out.println("We are out of this item: " + name_item);//delete
                placeAnOrder(name_item);
            }
        }

    }

    public void placeAnOrder(String name_item) // DONE
    {
        System.out.println("Placing an order for item: " + name_item);
        switch (name_item) {
            case "Dog":
                deliveries.add(new Delivery("Dog", simDay));
                break;
            case "Cat":
                deliveries.add(new Delivery("Cat", simDay));
                break;
            case "Bird":
                deliveries.add(new Delivery("Bird", simDay));
                break;
            case "Food":
                deliveries.add(new Delivery("Food", simDay));
                break;
            case "Leash":
                deliveries.add(new Delivery("Leash", simDay));
                break;
            case "Toy":
                deliveries.add(new Delivery("Toy", simDay));
                break;
            case "Cat Litter":
                deliveries.add(new Delivery("Cat Litter", simDay));
                break;
        }
    }

    public void openTheStore() // DONE
    {
        int customerCount = (int) (Math.random() * (10 - 3) + 3);
        for (int i = 0; i < customerCount; i++) {
            boolean bought_something=false;
            for (Item item : store_inventory) {
                double prob = Math.random();
                if (prob <= 0.1) { // with 10% probability, customer will try to buy item
                    prob = Math.random();
                    if (prob <= 0.5) { // 50% chance they pay list price for the item
                        System.out.print("Clerk " + currentClerk + " sold " + item.name + " to customer " + (i + 1) + " for $");
                        System.out.format("%.2f", item.listPrice);
                        System.out.println(" ");
                        bought_something=true;
                        item.salePrice = item.listPrice;
                        item.daySold = simDay;
                        store_inventory.remove(item);
                        sold_items.add(item);
                        register_cash += item.salePrice;
                        break;
                    } else {
                        prob = Math.random();
                        if (prob <= 0.75) { // 75% chance they will buy at discount
                            System.out.print("Clerk " + currentClerk + " sold " + item.name + " to customer " + (i + 1) + " at a 10% discount for $");
                            System.out.format("%.2f", item.listPrice);
                            System.out.println(" ");
                            bought_something=true;
                            item.salePrice = item.listPrice * 0.9;
                            item.daySold = simDay;
                            store_inventory.remove(item);
                            sold_items.add(item);
                            register_cash += item.salePrice;
                          break;
                        }
                    }
                }
            }
            //if customer didn't buy anything
            if (bought_something==false){
                System.out.println("Customer " + (i + 1) + " left the store without buying anything.");
            }
            
        }
    }

    public void cleanTheStore() // DONE
    {
        System.out.println("Trainer " + currentTrainer + " is cleaning the animal cages.");
        for (Pet pet : pets_array) {
            int prob = (int) (Math.random() * (20 - 1 + 1) + 1);
            if (prob == 20) { // 5% chance of each pet escaping while cleaning
                System.out.println("    - " + pet.summary() + " escaped!");
                prob = (int) (Math.random() * (20 - 1 + 1) + 1);
                if (prob < 11) { // randomly, either the trainer or clerk will catch the escaped pet
                    System.out.println("        - Trainer " + currentTrainer + " caught " + pet.summary() + " and returned them to their cage.");
                } else {
                    System.out.println("        - Clerk " + currentClerk + " caught " + pet.summary() + " and returned them to their cage.");

                }
            }
        }
        System.out.println("Clerk " + currentClerk + " is vacuuming the store.");
    }

    public void leaveTheStore() // DONE
    {
        System.out.print("Clerk " + currentClerk + " has locked up the store. " + currentClerk + " and trainer " + currentTrainer + " are going home for the day.");
    }

    public void summary() // DONE
    {
        System.out.println(" ");
        System.out.println("-----------30 day summary-----------");
        System.out.println("Items left in inventory: ");
        double totalValue = 0.0;
        for (Item i : store_inventory) {
            System.out.println("    - " + i.summary());
            totalValue += i.listPrice;
        }
        System.out.print("Value of items in inventory: $");
        System.out.format("%.2f",  totalValue);
        System.out.println(" ");

        //remove duplicates by using a HashSet
        HashSet<Item> sold_items2 = new HashSet<Item>(sold_items);
        //Collections.sort(sold_items2, Item.DaySoldComparator);
        
        

        System.out.println("Items sold: ");
        double sales = 0.0;
        for (Item i : sold_items2) {
            System.out.print("    - Day " + i.daySold + ": " + i.summary() + " sold for $");//summary() functions with constructor
            System.out.format("%.2f", i.salePrice);
            System.out.println(" ");

            sales += i.salePrice;
        }
        
        System.out.print("Total sales: $");
        System.out.format("%.2f", sales);
        System.out.println(" ");


        int sickPetCount = 0;
        for (Pet pet : pets_array) {
            if (!pet.healthy) {
                sickPetCount += 1;
            }
        }
        System.out.println("There is/are still " + sickPetCount + " sick pet(s) in the store.");
        System.out.print("Final count of money in register: $");
        System.out.format("%.2f", register_cash);
        System.out.println(" ");
        System.out.print("Money added to register from bank visits: $");
        System.out.format("%.2f", bank_withdrawal);
        System.out.println(" ");
    }

}//end of Store class