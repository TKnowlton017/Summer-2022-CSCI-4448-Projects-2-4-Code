import org.junit.Assert;
import org.junit.Test;

// Class File Outlining all of our unit tests

public class Tests {
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

    @Test
    public void storeInitInventorySize() {
        Store testStore = new Store("Test", Clerks, Trainers);
        // at the start, there should be 3 of each type of item, there are 10 types of items total.
        assert testStore.store_inventory.size() == 3 * 10;
    }

    @Test
    public void storeInitUniqueItems() {
        Store testStore = new Store("Test", Clerks, Trainers);
        // at the start, there should be 10 different types of items in the store.
        assert testStore.unique_item_list.size() == 10;
    }

    @Test
    public void poissonDistCheck() {
        Store testStore = new Store("Test", Clerks, Trainers);
        // Our poission distribution function should return a value between 1 to 6/7 with rare spikes to 10
        // testing it 30 times (because 30 days) to see if it goes out of range
        for (int i = 0; i < 30; i++) {
            int tmpPoisson = testStore.getPoisson();
            if (tmpPoisson < 0 || tmpPoisson > 10) Assert.fail("Poisson var not in expected range");
        }
    }

    @Test
    public void ensureEmployeeBreaks() {
        Store testStore = new Store("Test", Clerks, Trainers);
        // Employees must not work more than 3 days at a time per project instructions
        for (int i = 1; i < 31; i++) {
            testStore.arriveAtStore(i);
            if (testStore.currentClerk.daysWorked > 2 || testStore.currentTrainer.daysWorked > 2)
                Assert.fail("An employee has worked more than 3 consecutive days");
        }
    }

    @Test
    public void itemFactoryTest() {
        Store testStore = new Store("Test", Clerks, Trainers);
        ItemFactory testFactory = new ItemFactory(testStore.simDay, testStore.store_inventory, testStore.pets_array);
        int initialSize = testStore.store_inventory.size();
        testFactory.createNewItem("DOG").order();
        int newSize = testStore.store_inventory.size();
        // after ordering new dogs, the store inventory should increase by 3
        assert newSize == initialSize + 3;
    }

    @Test
    public void placeOrderCheck() {
        Store testStore = new Store("Test", Clerks, Trainers);
        int deliveriesArrayLength = testStore.deliveries.size();
        testStore.placeAnOrder("CAT");
        int newDeliveriesArrayLength = testStore.deliveries.size();
        assert newDeliveriesArrayLength == deliveriesArrayLength + 1;
    }

    // the command tests are here to ensure that execute() for each is always returning a String
    @Test
    public void commandEmployeeName() {
        Store testStore = new Store("Test", Clerks, Trainers);
        testStore.arriveAtStore(1);
        employeeName name = new employeeName();
        assert name.execute(testStore) != null;
    }

    @Test
    public void commandCurrentTime() {
        Store testStore = new Store("Test", Clerks, Trainers);
        testStore.arriveAtStore(1);
        currentTime cTime = new currentTime();
        assert cTime.execute(testStore) != null;
    }

    @Test
    public void commandCurrentInventory() {
        Store testStore = new Store("Test", Clerks, Trainers);
        testStore.arriveAtStore(1);
        currentInventory cInv = new currentInventory();
        assert cInv.execute(testStore) != null;
    }

    @Test
    public void commandBuyItem() {
        Store testStore = new Store("Test", Clerks, Trainers);
        testStore.arriveAtStore(1);
        buyItem cInv = new buyItem();
        assert cInv.execute(testStore) != null;
    }

    // testing to be sure Logger follows singleton pattern logic
    @Test
    public void singletonLogger() {
        Logger instance1 = Logger.getInstance(0);
        Logger instance2 = Logger.getInstance(0);
        //Passes
        Assert.assertSame("2 objects are same and are therefore singleton", instance1, instance2);
    }

    // testing to be sure Tracker follows singleton pattern logic
    @Test
    public void singletonTracker() {
        Tracker instance1 = Tracker.getInstance("");
        Tracker instance2 = Tracker.getInstance("");
        //Passes
        Assert.assertSame("2 objects are same and are therefore singleton", instance1, instance2);
    }

    @Test
    public void checkPetsArray() {
        // pets array must be filled only with pets
        Store testStore = new Store("Test", Clerks, Trainers);
        for (Item i : testStore.pets_array) {
            assert i instanceof Pet;
        }
    }

    @Test
    public void salesCheck() {
        // ensure processSales() works as planned
        Store testStore = new Store("Test", Clerks, Trainers);
        testStore.arriveAtStore(2);
        Item firstItem = testStore.store_inventory.get(0);
        int initInvSize = testStore.store_inventory.size();
        int initSoldItemCount = testStore.sold_items.size();
        testStore.processSale(firstItem, firstItem.listPrice);

        assert testStore.store_inventory.size() == initInvSize - 1; // inventory should decrease
        assert testStore.sold_items.size() == initSoldItemCount + 1; // sold item collection should increase
        assert firstItem.daySold == 2; // this is all happening day 2
        assert firstItem.salePrice > 0; // sale price should update for item
    }

    @Test
    public void deliveryCheck() {
        // when a delivery "arrives" to the store on a certain day, it should be placed in the store inventory
        Store testStore = new Store("Test", Clerks, Trainers);
        // a dog treat is ordered on day 4
        Delivery testDelivery = new Delivery("TREAT", 4);
        testStore.deliveries.add(testDelivery);

        // should arrive in the next 3 days
        int initialInvSize = testStore.store_inventory.size();
        for (int i = 4; i < 8; i++) {
            testStore.arriveAtStore(i);
            testStore.processDeliveries();
        }
        assert testStore.store_inventory.size() > initialInvSize; // ensure item is added to store inventory during the 3 days
    }
}