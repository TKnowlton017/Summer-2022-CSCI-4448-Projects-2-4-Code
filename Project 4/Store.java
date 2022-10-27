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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class Store {

    // ENCAPSULATION: All sensitive information is hidden by using private access modifier
    private final Clerk[] clerks;
    private final Trainer[] trainers;
    private double register_cash, bank_withdrawal;
    public String storeName;

    Clerk currentClerk;
    Trainer currentTrainer;

    int simDay; // current day in the simulation

    ArrayList<Item> store_inventory = new ArrayList<>(); // healthy pets and supplies of type Item
    ArrayList<Pet> pets_array = new ArrayList<>(); // all pets of type Pet (needs to be of type Pet to access pet attributes)
    ArrayList<Item> sold_items = new ArrayList<>(); // sold items collection
    ArrayList<String> unique_item_list = new ArrayList<>();//list of name of every unique item for use in DoInventory() function
    ArrayList<Delivery> deliveries = new ArrayList<>(); // for processing deliveries
    ArrayList<Tracker> track_employee_list = new ArrayList<>();//for tracking employee sales
    public String textfile_output = " ";//for concatenating text output for logger


    // Constructor: Initialize a starting set of Store Items. Class definitions found in Item.java
    public Store(String name, Clerk[] clerkPool, Trainer[] trainerPool) {
        storeName = name;
        trainers = trainerPool;
        clerks = clerkPool;
        register_cash = 0.00; // no money in register initially
        bank_withdrawal = 0.00; // no initial withdrawal amount
        simDay = 0;

        // Initialize store inventory using a factory
        System.out.println("Initializing store inventory items at store: " + storeName);
        ItemFactory factory = new ItemFactory(simDay, store_inventory, pets_array);
        factory.createNewItem("DOG").order();
        factory.createNewItem("CAT").order();
        factory.createNewItem("BIRD").order();
        factory.createNewItem("FERRET").order();
        factory.createNewItem("SNAKE").order();
        factory.createNewItem("FOOD").order();
        factory.createNewItem("LEASH").order();
        factory.createNewItem("TOY").order();
        factory.createNewItem("CATLITTER").order();
        factory.createNewItem("TREAT").order();


        // to get unique list of items for use in DoInventory() function
        for (Item item : store_inventory) {
            //if unique item not found in unique_item_list then add it
            if (!(unique_item_list.contains(item.name))) {
                unique_item_list.add(item.name);
            }
        }
        //remove item that we don't want to reorder
        unique_item_list.remove("TOY");

        //System.out.println("unique_item_list = " + unique_item_list);//test case, keep

        //initialize the clerks in Tracker.class
        for (Clerk clerk : clerks) {
            Tracker newClerkObj = Tracker.getInstance(clerk.getName());
            track_employee_list.add(newClerkObj);
        }
    }//End of initialization before start of simulation


    // Helper functions
    public int getPoisson() { // returns a random integer from a poisson distrubution with mean lambda
        // src: https://stackoverflow.com/questions/1241555/algorithm-to-generate-poisson-and-binomial-random-numbers
        double limit = Math.exp(-3.0), prod = new Random().nextDouble();
        int n;
        for (n = 0; prod >= limit; n++)
            prod *= new Random().nextDouble();
        return n;
    }

    public void processSale(Item item, double price) {
        store_inventory.remove(item);
        sold_items.add(item);
        // DECORATOR PATTERN in use here to add addons to each sale.
        Sale sale = new StandardSale(item, price, new ArrayList<>());
        if (item instanceof Pet) { // only pet sales have the extra decorators attached
            double prob = Math.random();
            if (prob <= 0.5) sale = new MicrochipSale(item, sale.saleTotal(), sale.getAddons());
            prob = Math.random();
            if (prob <= 0.25) sale = new InsuranceSale(item, sale.saleTotal(), sale.getAddons());
            prob = Math.random();
            if (prob <= 0.25) sale = new CheckupSale(item, sale.saleTotal(), sale.getAddons());
        }
        item.salePrice = sale.saleTotal();
        item.daySold = simDay;
        System.out.format("    - Clerk " + currentClerk.getName() + " sold " + sale.saleDescription() + " (list price $%.2f) to customer for $%.2f", price, sale.saleTotal());
        System.out.println(" ");
        if (sale.getAddons().size() > 0) {
            System.out.println("        - with addons: ");
            for (String addon : sale.getAddons()) {
                System.out.println("            - " + addon);
            }
        }
        register_cash += sale.saleTotal();
        Tracker.addSalesData(track_employee_list, currentClerk.getName(), sale.saleTotal());
    }

    // main store methods as described in the write-up
    public void arriveAtStore(int day) // DONE
    {
        simDay = day;

        textfile_output = "";//start off with empty string every day
        textfile_output = textfile_output.concat("Start of day " + simDay + " at store " + storeName + "\n");
        textfile_output = textfile_output.concat("\n");

        //CHOOSING A CLERK
        int randomClerkIndex = (int) Math.floor(Math.random() * clerks.length);
        Clerk ClerkSelected = (clerks[randomClerkIndex]);
        int clerkSick = (int) (Math.random() * (11 - 1) + 1);
        if (ClerkSelected.equals(currentClerk)) { // ensure clerk does not work more than 3 days in a row
            currentClerk.daysWorked += 1;

            if (clerkSick == 1) {
                System.out.println("Clerk " + currentClerk.getName() + " is sick and will go home.");
            }

            // if clerk works too many days or is sick
            // NOTE: for some reason having ClerkTracker>2 instead of ClerkTracker>3 works
            if ((currentClerk.daysWorked > 2) || (clerkSick == 1)) {
                ArrayList<Clerk> clerksUpdated = new ArrayList<>(Arrays.asList(clerks)); // new arrayList that will hold values from clerks array
                clerksUpdated.remove(ClerkSelected);//get rid of clerk that can't work for the day
                //System.out.println("Clerk removed " + ClerkSelected.getName();//delete

                int clerkUpdatedPick = (int) (Math.random() * clerksUpdated.size()); // random element in new arrayList
                ClerkSelected = clerksUpdated.get(clerkUpdatedPick);//get new trainer for the day
                currentClerk.daysWorked = 0;
            }
        }
        System.out.println("Clerk " + ClerkSelected.getName() + " arrives at store on day " + simDay);
        textfile_output = textfile_output.concat("Clerk " + ClerkSelected.getName() + " arrives at store \n");
        currentClerk = ClerkSelected;

        //CHOOSING A TRAINER
        int randomTrainerIndex = (int) Math.floor(Math.random() * trainers.length);
        Trainer TrainerSelected = (trainers[randomTrainerIndex]);
        int trainerSick = (int) (Math.random() * (11 - 1) + 1);
        if (TrainerSelected.equals(currentTrainer)) { // ensure trainer does not work more than 3 days in a row
            currentTrainer.daysWorked += 1;

            if (trainerSick == 1) {
                System.out.println("Trainer " + currentTrainer.getName() + " is sick and will go home.");
            }

            //if trainer works too many days or is sick
            //NOTE: for some reason having trainerTracker>2 instead of trainerTracker>3 works
            if ((currentTrainer.daysWorked > 2) || (trainerSick == 1)) {
                ArrayList<Trainer> trainersUpdated = new ArrayList<Trainer>(Arrays.asList(trainers));//new arrayList that will hold values from trainers array
                trainersUpdated.remove(TrainerSelected);//get rid of trainer that can't work for the day

                int trainerUpdatedPick = (int) (Math.random() * trainersUpdated.size());//random element in new arrayList
                TrainerSelected = trainersUpdated.get(trainerUpdatedPick);//get new trainer for the day
                currentTrainer.daysWorked = 0;
            }
        }
        System.out.println("Trainer " + TrainerSelected.getName() + " arrives at store on day " + day);
        textfile_output = textfile_output.concat("Trainer " + TrainerSelected.getName() + " arrives at store \n");
        currentTrainer = TrainerSelected;

    }

    public void processDeliveries() // DONE
    {
        for (Delivery d : deliveries) {
            if (this.simDay == d.day) { // checks to see if a new item delivery has arrived
                System.out.println("Clerk " + currentClerk.getName() + " received a new shipment of item " + d.item + " and added them to the store's inventory.");
                textfile_output = textfile_output.concat("New delivery of " + d.item + " recieved \n");
                ItemFactory tempFactory = new ItemFactory(simDay, store_inventory, pets_array);
                if (d.item.equalsIgnoreCase("DOG")) tempFactory.createNewItem("DOG").order();
                else if (d.item.equalsIgnoreCase("CAT")) tempFactory.createNewItem("CAT").order();
                else if (d.item.equalsIgnoreCase("BIRD")) tempFactory.createNewItem("BIRD").order();
                else if (d.item.equalsIgnoreCase("FERRET")) tempFactory.createNewItem("FERRET").order();
                else if (d.item.equalsIgnoreCase("SNAKE")) tempFactory.createNewItem("SNAKE").order();
                else if (d.item.equalsIgnoreCase("FOOD")) tempFactory.createNewItem("FOOD").order();
                else if (d.item.equalsIgnoreCase("LEASH")) tempFactory.createNewItem("LEASH").order();
                else if (d.item.equalsIgnoreCase("TOY")) tempFactory.createNewItem("TOY").order();
                else if (d.item.equalsIgnoreCase("CATLITTER")) tempFactory.createNewItem("CATLITTER").order();
                else if (d.item.equalsIgnoreCase("TREAT")) tempFactory.createNewItem("TREAT").order();
            }
        }
    }

    public void feedAnimals() // DONE
    {
        // Must do sick pets before healthy pets in the for-loop
        System.out.println(currentTrainer.getName() + " is feeding the pets: ");

        // Loop through all the pets in the pets arrayList
        for (Pet pet : pets_array) {
            // sick pets in pets_array
            if (!pet.healthy) {
                System.out.println("    - " + currentTrainer.getName() + " feeds the " + pet.summary() + " who is NOT healthy.");
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
                System.out.println("    - " + currentTrainer.getName() + " feeds the " + pet.summary() + " who is healthy.");
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

        System.out.print(currentClerk.getName() + " has counted $");
        System.out.format("%.2f", register_cash);
        System.out.println(" in the register");
        String register_value_rounded_2_dec = String.format("%.2f", register_cash);
        textfile_output = textfile_output.concat("There is $" + register_value_rounded_2_dec + " in the register \n");
        if (register_cash < 200) {
            register_cash += (goToBank());
            System.out.println("There is now $" + register_cash + " in the register");
            textfile_output = textfile_output.concat("There is now $" + register_cash + " in the register after going to the bank \n");
        }
    }

    public double goToBank() // DONE
    {
        System.out.println(currentClerk.getName() + " has gone to the bank and withdrawn $1000");
        bank_withdrawal += 1000;
        System.out.println("Total money withdrawn from bank so far: $" + bank_withdrawal);
        return 1000;
    }

    //COHESION: This function exhibits strong cohesion as it does all inventory related jobs for the program
    public void doInventory() // DONE
    {
        double total_inventory_value = 0.0;
        System.out.println("Clerk " + currentClerk.getName() + " is doing inventory");

        int inventory_Count = 0;
        //add up the value of all the items in inventory (sick pets should not be in it due to FeedAnimals() function working)
        for (Item value : store_inventory) {
            total_inventory_value += (value.purchasePrice);
            inventory_Count += 1;
        }

        System.out.print("    - Clerk " + currentClerk.getName() + " has counted a total of $");
        System.out.format("%.2f", total_inventory_value);
        System.out.print(" in items in the store inventory.");
        System.out.println(" ");

        String rounded_inventory_value = String.format("%.2f", total_inventory_value);
        textfile_output = textfile_output.concat("There are " + inventory_Count + " items in the inventory with a total value of $" + rounded_inventory_value + "\n");

        //Functionality for if we are out of inventory on an item
        //loop through store_inventory ArrayList and store in temp
        ArrayList<String> temp = new ArrayList<>();
        for (Item item : store_inventory) {
            temp.add(item.name);
        }

        //System.out.println("Right now we have in our store a list of these items = " + temp);//useful test case, keep


        for (String name_item : unique_item_list) {
            //System.out.println("Checking if we have enough " + name_item);//test case, keep

            /*
             *temp has current list of items in store_inventory by name
             *if a unique value(every possible item in the store) in unique_item_list
             *is not found in temp, then
             *we are out of that item and need to call PlaceAnOrder()
             */
            if (!(temp.contains(name_item))) {
                System.out.println("We are out of this item: " + name_item);//test case, keep
                placeAnOrder(name_item);
            }
        }

    }

    public void placeAnOrder(String name_item) // DONE
    {
        System.out.println("Placing an order for item: " + name_item);

        if (name_item.equalsIgnoreCase("DOG")) deliveries.add(new Delivery("DOG", simDay));
        else if (name_item.equalsIgnoreCase("CAT")) deliveries.add(new Delivery("CAT", simDay));
        else if (name_item.equalsIgnoreCase("BIRD")) deliveries.add(new Delivery("BIRD", simDay));
        else if (name_item.equalsIgnoreCase("FERRET")) deliveries.add(new Delivery("FERRET", simDay));
        else if (name_item.equalsIgnoreCase("SNAKE")) deliveries.add(new Delivery("SNAKE", simDay));
        else if (name_item.equalsIgnoreCase("FOOD")) deliveries.add(new Delivery("FOOD", simDay));
        else if (name_item.equalsIgnoreCase("LEASH")) deliveries.add(new Delivery("LEASH", simDay));
//        else if(name_item.equalsIgnoreCase("TOY")) deliveries.add(new Delivery("TOY", simDay)); // no longer reordering TOYS
        else if (name_item.equalsIgnoreCase("CATLITTER")) deliveries.add(new Delivery("CATLITTER", simDay));
        else if (name_item.equalsIgnoreCase("TREAT")) deliveries.add(new Delivery("TREAT", simDay));
        textfile_output = textfile_output.concat("An order has been placed for " + name_item + "\n");
    }

    // trainAnimals uses STRATEGY design pattern to have different methods for training pets based on trainers.
    // Each trainer was initialized with a different class each of which has a different method of training pets.
    public void trainAnimals() // DONE
    {
        System.out.println(currentTrainer.getMessage());
        for (Pet pet : pets_array) { // only Dogs, Cats and Ferrets have housebroken attributes
            System.out.println("    - " + currentTrainer.train(pet));
        }
    }

    // DECORATOR PATTERN
    public void openTheStore() // DONE
    {
        int customerCount = 2 + getPoisson();
        textfile_output = textfile_output.concat(customerCount + " customers have visited the store \n");

        //go through all customers
        for (int i = 0; i < customerCount; i++) {
            System.out.println("Customer " + (i + 1) + " has arrived.");
            boolean bought_something = false;
            for (Item item : store_inventory) {
                double prob = Math.random();
                if (prob <= 0.1) { // with 10% probability, customer will try to buy item
                    prob = Math.random();
                    if (prob <= 0.5) { // 50% chance they pay list price for the item
                        bought_something = true;
                        processSale(item, item.listPrice);
                        break;
                    } else {
                        prob = Math.random();
                        if (prob <= 0.75) { // 75% chance they will buy at discount
                            bought_something = true;
                            processSale(item, item.listPrice * 0.9);
                            break;
                        }
                    }

                }
            }
            //if customer didn't buy anything
            if (!bought_something) {
                System.out.println("    - Customer left the store without buying anything.");
            }
        }
    }

    public void cleanTheStore() // DONE
    {
        System.out.println("Trainer " + currentTrainer.getName() + " is cleaning the animal cages.");
        for (Pet pet : pets_array) {
            int prob = (int) (Math.random() * (20 - 1 + 1) + 1);
            if (prob == 20) { // 5% chance of each pet escaping while cleaning
                System.out.println("    - " + pet.summary() + " escaped!");
                textfile_output = textfile_output.concat("A " + pet.summary() + " has escaped its cage\n");
                prob = (int) (Math.random() * (20 - 1 + 1) + 1);
                if (prob < 11) { // randomly, either the trainer or clerk will catch the escaped pet
                    System.out.println("        - Trainer " + currentTrainer.getName() + " caught " + pet.summary() + " and returned them to their cage.");
                    textfile_output = textfile_output.concat("Trainer " + currentTrainer.getName() + " has caught the escaped pet \n");
                } else {
                    System.out.println("        - Clerk " + currentClerk.getName() + " caught " + pet.summary() + " and returned them to their cage.");
                    textfile_output = textfile_output.concat("Clerk " + currentClerk.getName() + " has caught the escaped pet \n");
                }
            }
        }
        System.out.println("Clerk " + currentClerk.getName() + " is vacuuming the store.");
    }

    public void leaveTheStore(Logger myfile) // DONE
    {
        System.out.println("Clerk " + currentClerk.getName() + " has locked up the store. " + currentClerk.getName() + " and trainer " + currentTrainer.getName() + " are going home for the day.");
        textfile_output = textfile_output.concat("Clerk " + currentClerk.getName() + " has left the store \n");
        textfile_output = textfile_output.concat("Trainer " + currentTrainer.getName() + " has left the store \n");

        //to display daily sales up to today to every observer
        Observer obs = new Observer();
        obs.displayDailySummary(simDay, track_employee_list);

        //add current clerk sales up to that point
        textfile_output = textfile_output.concat("Tracker: Day " + simDay + " \n");
        textfile_output = textfile_output.concat("Clerks      Items Sold      Purchase Price Total \n");
        for (Tracker t : track_employee_list) {
            String rounded_sales = String.format("%.2f", t.totalSales);
            textfile_output = textfile_output.concat(t.getName() + "        " + t.totalItemsSold + "              " + rounded_sales + "\n");
        }


        //to put daily info into logger file
        textfile_output = textfile_output.concat("\n");
        textfile_output = textfile_output.concat("\n");
        myfile.writeLog(textfile_output);
    }

    public void summary() // DONE
    {
        System.out.println(" ");
        System.out.println("-----------30 day summary at store " + storeName + "-----------");
        System.out.println("Items left in inventory: ");
        double totalValue = 0.0;
        for (Item i : store_inventory) {
            System.out.println("    - " + i.summary());
            totalValue += i.listPrice;
        }
        System.out.print("Value of items in inventory: $");
        System.out.format("%.2f", totalValue);
        System.out.println(" ");

        //remove duplicates by using a HashSet
        HashSet<Item> sold_items2 = new HashSet<Item>(sold_items);

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