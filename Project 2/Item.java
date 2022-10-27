/*
  Item class and its subclasses
  NOTE: Must be in the same directory as Main.java, Store.java, and Delivery.java
*/

//INHERITANCE: Throughout this Item.java we are using inheritance
// where once class inherits from another 

import java.util.Comparator;
import java.util.Random;

//Superclass
abstract class Item {
    public String name;
    public double purchasePrice;
    public double listPrice;
    public double salePrice;
    public int dayArrived;
    public int daySold;

    public Item(int dayArrived) {
//        this.name = name;
        this.purchasePrice = (Math.random() * (100 - 1 + 1) + 1); // random purchase price between 1 and 100
        this.listPrice = purchasePrice * 2;
        this.dayArrived = dayArrived;
        this.daySold = -1; // initialize to day 1
    }

    // helper functions
    public String getRandomElement(String[] list)
    {
        Random rand = new Random();
        return list[rand.nextInt(list.length)];
    }

    public Boolean getRandomBoolean() {
        Random rand = new Random();
        return rand.nextBoolean();
    }

    public String summary() {
        return "Item: " + name;
    }

    
}

//Pet inherits from Item
abstract class Pet extends Item {
    public String breed;
    public int age;
    public boolean healthy;

    public Pet(int dayArrived) {
        super(dayArrived);
        this.age = (int) (Math.random() * (20 - 1 + 1) + 1); // random pet age between 1 and 20
        this.healthy = true;
    }
}

//Supplies inherits from Item
abstract class Supplies extends Item {
    Supplies(int dayArrived) {
        super(dayArrived);//call Item constructor
    }

    //POLYMORPHISM: this summary() method will override the summary() method in the ITEM class
    public String summary() { return " "; };
}

//Dog inherits from Pet
class Dog extends Pet {
    public String size;
    public String color;
    public boolean housebroken;
    public boolean purebreed;

    public Dog(int dayArrived) {
        super(dayArrived);
        this.name = "Dog";

        String[] sizes = {"small", "medium", "big"};
        String[] colors = {"brown", "black", "yellow", "white", "grey"};
        String[] breed = {"labrador", "bulldog", "retriever", "german shepard", "mixed", "poodle"};
        this.breed = getRandomElement(breed);
        this.size = getRandomElement(sizes);
        this.color = getRandomElement(colors);
        this.housebroken = getRandomBoolean();
        this.purebreed = getRandomBoolean();
    }

    //POLYMORPHISM: this summary() method will override the summary() method in the ITEM class
    @Override
    public String summary() {
        return String.format("%s %s %s dog", size, color, breed);
    }
}

//Cat inherits from Pet
class Cat extends Pet {
    public String color;
    public boolean housebroken;
    public boolean purebreed;

    public Cat(int dayArrived) {
        super(dayArrived);
        this.name = "Cat";

        String[] colors = {"brown", "black", "yellow", "white", "grey"};
        String[] breed = {"shorthair", "longhair", "siamese", "ragdoll", "bengal", "mixed"};
        this.breed = getRandomElement(breed);
        this.color = getRandomElement(colors);
        this.housebroken = getRandomBoolean();
        this.purebreed = getRandomBoolean();
    }

    @Override
    public String summary() {
        return String.format("%s %s cat", color, breed);
    }
}

//Bird inherits from Pet
class Bird extends Pet {
    public String size;
    public boolean mimicry;
    public boolean exotic;
    public boolean papers;

    public Bird(int dayArrived) {
        super(dayArrived);
        this.name = "Bird";

        String[] sizes = {"small", "medium", "big"};
        String[] breed = {"parakeet", "parrot", "lovebird", "cockatoo", "finch"};
        this.breed = getRandomElement(breed);
        this.size = getRandomElement(sizes);
        this.mimicry = getRandomBoolean();
        this.exotic = getRandomBoolean();
        this.papers = getRandomBoolean();
    }

    @Override
    public String summary() {
        return String.format("%s %s bird", size, breed);
    }
}

//Food inherits from Supplies
class Food extends Supplies {
    public String size;
    public String animal;
    public String type;

    public Food(int dayArrived) {
        super(dayArrived);

        this.name = "Food";
        String[] sizes = {"small", "medium", "big"};
        String[] animals = {"dog", "cat", "bird"};
        String[] types = {"wet", "dry"};
        this.size = getRandomElement(sizes);
        this.animal = getRandomElement(animals);
        this.type = getRandomElement(types);
    }

    @Override
    public String summary() {
        return String.format("%s %s %s food", size, type, animal);
    }
}

//Leash inherits from Supplies
class Leash extends Supplies {
    public String animal;

    public Leash(int dayArrived) {
        super(dayArrived);

        this.name = "Leash";
        String[] animals = {"dog", "cat", "bird"};
        this.animal = getRandomElement(animals);
    }
    @Override
    public String summary() {
        return String.format("%s leash", animal);
    }
}

//Toy inherits from Supplies
class Toy extends Supplies {
    public String animal;

    public Toy(int dayArrived) {
        super(dayArrived);

        this.name = "Toy";
        String[] animals = {"dog", "cat", "bird"};
        this.animal = getRandomElement(animals);
    }
    @Override
    public String summary() {
        return String.format("%s toy", animal);
    }
}

//Catlitter inherits from Supplies
class CatLitter extends Supplies {
    public String size;

    public CatLitter(int dayArrived) {
        super(dayArrived);

        this.name = "Cat Litter";
        String[] sizes = {"small", "medium", "large"};
        this.size = getRandomElement(sizes);
    }

    @Override
    public String summary() {
        return String.format("%s cat litter", size);
    }
}