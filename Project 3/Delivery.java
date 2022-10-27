// A delivery item has an associated day in which it will arrive
public class Delivery {
    public String item;
    public int day;

    public Delivery(String i, int currentDay) {
        this.item = i;
        this.day = (int) (Math.random() * (3 - 1 + 1) + 1) + currentDay; // generate a random number between 1 and 3 from the simulations current day

    }
}
