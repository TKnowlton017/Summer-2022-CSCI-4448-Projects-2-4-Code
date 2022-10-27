/* Project 2 Main Function
 *
 * NOTE: See Item.java for item definitions,
 *       Store.java for store functions, and
 *       Delivery.java for the delivery function
 *       Make sure they're in the same directory as this file.
 *
 */

class Main {
    public static void main(String[] args) {

        // new unique IDENTITY of Store class
        Store s = new Store();

        // Days loop for 30 days
        for (int day = 1; day <= 30; day++) {
            // Do the Store functions in order, see Store.java for function definitions
            System.out.println(" ");
            System.out.println("-----------Start of day " + day + "-----------");

            /*ABSTRACTION: All of our functions below are easily called 
            with a single line of code and the correct parameters*/
            s.arriveAtStore(day);
            s.processDeliveries();
            s.feedAnimals();
            s.checkRegister();
            s.doInventory();
            s.openTheStore();
            s.cleanTheStore();
            s.leaveTheStore();
        }
        s.summary();

    }
}

