import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/*Command Pattern Is Used to Implement the Customer Interface as seen in this file*/

interface Customer_Interface {
    String execute(Store myStore);
}

class employeeName implements Customer_Interface {
    public String execute(Store myStore) {
        String trainerName = myStore.currentTrainer.getName();
        return ("The current store trainer is: " + trainerName);
    }
}

class currentTime implements Customer_Interface {
    public String execute(Store myStore) {
        // Date Formating Info:  https://www.javatpoint.com/java-get-current-date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return ("Clerk " + myStore.currentClerk.getName() + " says the time is " + dtf.format(now));
    }
}

class currentInventory implements Customer_Interface {
    StringBuilder result = new StringBuilder();

    public String execute(Store myStore) {
        for (Item i : myStore.store_inventory) {
            result.append("    - ").append(i.summary()).append("\n");
        }
        return result.toString();
    }
}

class buyItem implements Customer_Interface {
    public String execute(Store myStore) {
        //pulled from openTheStore() function in Store.java
        for (Item item : myStore.store_inventory) {
            double prob = Math.random();
            if (prob <= 0.1) { // with 10% probability, customer will try to buy item
                prob = Math.random();
                if (prob <= 0.5) { // 50% chance they pay list price for the item
                    myStore.processSale(item, item.listPrice);
                    break;
                } else {
                    prob = Math.random();
                    if (prob <= 0.75) { // 75% chance they will buy at discount
                        myStore.processSale(item, item.listPrice * 0.9);
                        break;
                    }
                }

            }
        }//end of pulled code from openTheStore()
        return ("");
    }
}

//Menu will actively run the entire customer interface and make appropriate method calls depending on user input
class Menu {
    public void customerMenu(Store myStore, int last_day) {
        boolean input = true;
        Scanner myObjOption = new Scanner(System.in);
        System.out.println("\n");
        System.out.println("Store " + myStore.storeName + " has been chosen");

        //start of final day with customer interaction
        myStore.arriveAtStore(last_day);

        while (input) {
            System.out.println("Please enter one of the following options with a single char or type 'quit' to quit customer interaction:");
            System.out.println("a: Ask an employee their name");
            System.out.println("b: Ask the clerk what time it is");
            System.out.println("c: Ask the trainer for the current store inventory");
            System.out.println("d: Buy a normal inventory item from the clerk");
            System.out.println("quit: Ask the trainer for the current store inventory \n");

            String storeInput = myObjOption.nextLine();

            switch (storeInput) {
                case "quit":
                    input = false;
                    break;
                case "a":
                    employeeName eName = new employeeName();
                    System.out.println(eName.execute(myStore) + "\n");
                    break;
                case "b":
                    currentTime cTime = new currentTime();
                    System.out.println(cTime.execute(myStore) + "\n");
                    break;
                case "c":
                    currentInventory cInventory = new currentInventory();
                    System.out.println("The current store inventory is ");
                    System.out.println(cInventory.execute(myStore) + "\n");
                    break;
                case "d":
                    buyItem bItem = new buyItem();
                    System.out.println("Customer has bought: ");
                    System.out.println(bItem.execute(myStore) + "\n");
                    break;
            }
        }
        myObjOption.close();
    }
}