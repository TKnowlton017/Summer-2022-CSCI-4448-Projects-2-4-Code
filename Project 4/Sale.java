import java.util.ArrayList;

interface Sale {
    String saleDescription();

    double saleTotal();

    ArrayList<String> getAddons();
}

class StandardSale implements Sale {
    Item item;
    double total;
    public ArrayList<String> addons;

    StandardSale(Item i, double t, ArrayList<String> a) {
        item = i;
        total = t;
        addons = a;
    }

    public String saleDescription() {
        return item.summary();
    }

    public double saleTotal() {
        return total;
    }

    public ArrayList<String> getAddons() {
        return addons;
    }
}

class MicrochipSale extends StandardSale {
    MicrochipSale(Item i, double t, ArrayList<String> a) {
        super(i, t, a);
        addons.add("microchip");
    }

    @Override
    public double saleTotal() {
        return total + 50; // cost of microchip
    }

}

class InsuranceSale extends StandardSale {
    InsuranceSale(Item i, double t, ArrayList<String> a) {
        super(i, t, a);
        addons.add("pet insurance");
    }

    @Override
    public double saleTotal() {
        return total + 50; // cost of pet insurance
    }
}

class CheckupSale extends StandardSale {
    int numCheckups;

    CheckupSale(Item i, double t, ArrayList<String> a) {
        super(i, t, a);
        numCheckups = (int) ((Math.random() * (4 - 1)) + 1); // random number of checkups between 1 and 4

        for (int ind = 0; ind < numCheckups; ind++) {
            addons.add("vet checkup");
        }
    }

    @Override
    public double saleTotal() {
        return total + (25 * numCheckups);
    }
}