import java.util.ArrayList;

// FACTORY PATTERN
public class ItemFactory {
    public int simDay;
    ArrayList<Item> store_inventory;
    ArrayList<Pet> pets_array;

    ItemFactory(int day, ArrayList<Item> inv, ArrayList<Pet> pets) {
        simDay = day;
        store_inventory = inv;
        pets_array = pets;
    }

    public newItem createNewItem(String itemType) {
        if (itemType == null) {
            return null;
        }
        if (itemType.equalsIgnoreCase("DOG")) {
            return new newDogs(simDay, store_inventory, pets_array);
        } else if (itemType.equalsIgnoreCase("CAT")) {
            return new newCats(simDay, store_inventory, pets_array);
        } else if (itemType.equalsIgnoreCase("BIRD")) {
            return new newBirds(simDay, store_inventory, pets_array);
        } else if (itemType.equalsIgnoreCase("FERRET")) {
            return new newFerrets(simDay, store_inventory, pets_array);
        } else if (itemType.equalsIgnoreCase("SNAKE")) {
            return new newSnakes(simDay, store_inventory, pets_array);
        } else if (itemType.equalsIgnoreCase("FOOD")) {
            return new newFoods(simDay, store_inventory);
        } else if (itemType.equalsIgnoreCase("LEASH")) {
            return new newLeashes(simDay, store_inventory);
        } else if (itemType.equalsIgnoreCase("TOY")) {
            return new newToys(simDay, store_inventory);
        } else if (itemType.equalsIgnoreCase("CATLITTER")) {
            return new newCatLitters(simDay, store_inventory);
        } else if (itemType.equalsIgnoreCase("TREAT")) {
            return new newTreats(simDay, store_inventory);
        }
        return null;
    }
}

interface newItem {
    void order();
}

abstract class newPets implements newItem {
    public int simDay;
    ArrayList<Item> store_inventory;
    ArrayList<Pet> pets_array;

    newPets(int day, ArrayList<Item> inv, ArrayList<Pet> pets) {
        simDay = day;
        store_inventory = inv;
        pets_array = pets;
    }
}

class newDogs extends newPets {
    newDogs(int day, ArrayList<Item> inv, ArrayList<Pet> pets) {
        super(day, inv, pets);
    }

    @Override
    public void order() {
        for (int i = 0; i < 3; i++) {
            Dog newItem = new Dog(simDay);

            store_inventory.add(newItem);
            pets_array.add(newItem);
        }
    }
}

class newCats extends newPets {
    newCats(int day, ArrayList<Item> inv, ArrayList<Pet> pets) {
        super(day, inv, pets);
    }

    @Override
    public void order() {
        for (int i = 0; i < 3; i++) {
            Cat newItem = new Cat(simDay);

            store_inventory.add(newItem);
            pets_array.add(newItem);
        }
    }
}

class newBirds extends newPets {
    newBirds(int day, ArrayList<Item> inv, ArrayList<Pet> pets) {
        super(day, inv, pets);
    }

    @Override
    public void order() {
        for (int i = 0; i < 3; i++) {
            Bird newItem = new Bird(simDay);

            store_inventory.add(newItem);
            pets_array.add(newItem);
        }
    }
}

class newFerrets extends newPets {
    newFerrets(int day, ArrayList<Item> inv, ArrayList<Pet> pets) {
        super(day, inv, pets);
    }

    @Override
    public void order() {
        for (int i = 0; i < 3; i++) {
            Ferret newItem = new Ferret(simDay);

            store_inventory.add(newItem);
            pets_array.add(newItem);
        }
    }
}

class newSnakes extends newPets {
    newSnakes(int day, ArrayList<Item> inv, ArrayList<Pet> pets) {
        super(day, inv, pets);
    }

    @Override
    public void order() {
        for (int i = 0; i < 3; i++) {
            Snake newItem = new Snake(simDay);

            store_inventory.add(newItem);
            pets_array.add(newItem);
        }
    }
}

abstract class newStock implements newItem {
    int simDay;
    ArrayList<Item> store_inventory;

    newStock(int day, ArrayList<Item> inv) {
        simDay = day;
        store_inventory = inv;
    }
}

class newFoods extends newStock {
    newFoods(int day, ArrayList<Item> inv) {
        super(day, inv);
    }

    @Override
    public void order() {
        for (int i = 0; i < 3; i++) {
            Food newItem = new Food(simDay);

            store_inventory.add(newItem);
        }
    }
}

class newLeashes extends newStock {
    newLeashes(int day, ArrayList<Item> inv) {
        super(day, inv);
    }

    @Override
    public void order() {
        for (int i = 0; i < 3; i++) {
            Leash newItem = new Leash(simDay);

            store_inventory.add(newItem);
        }
    }
}

class newToys extends newStock {
    newToys(int day, ArrayList<Item> inv) {
        super(day, inv);
    }

    @Override
    public void order() {
        for (int i = 0; i < 3; i++) {
            Toy newItem = new Toy(simDay);

            store_inventory.add(newItem);
        }
    }
}

class newCatLitters extends newStock {
    newCatLitters(int day, ArrayList<Item> inv) {
        super(day, inv);
    }

    @Override
    public void order() {
        for (int i = 0; i < 3; i++) {
            CatLitter newItem = new CatLitter(simDay);

            store_inventory.add(newItem);
        }
    }
}

class newTreats extends newStock {
    newTreats(int day, ArrayList<Item> inv) {
        super(day, inv);
    }

    @Override
    public void order() {
        for (int i = 0; i < 3; i++) {
            Treat newItem = new Treat(simDay);

            store_inventory.add(newItem);
        }
    }
}