// Strategy pattern

abstract class Trainer extends Employee {
    abstract String getMessage();

    abstract String train(Pet pet);
}

class HaphazardTrainer extends Trainer {
    final String message;

    HaphazardTrainer(String n) {
        name = n;
        message = name + " is training the pets haphazardly.";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String train(Pet pet) {
        // 10% chance of toggling housebroken attribute
        double prob = Math.random();
        if (prob <= 0.1) {
            if (pet instanceof Dog) {
                if (((Dog) pet).housebroken) {
                    ((Dog) pet).housebroken = false;
                    return pet.summary() + " is no longer housebroken.";
                } else {
                    ((Dog) pet).housebroken = true;
                    return pet.summary() + " is now housebroken.";
                }
            }
            if (pet instanceof Cat) {
                if (((Cat) pet).housebroken) {
                    ((Cat) pet).housebroken = false;
                    return pet.summary() + " is no longer housebroken.";
                } else {
                    ((Cat) pet).housebroken = true;
                    return pet.summary() + " is now housebroken.";
                }
            }
            if (pet instanceof Ferret) {
                if (((Ferret) pet).housebroken) {
                    ((Ferret) pet).housebroken = false;
                    return pet.summary() + " is no longer housebroken.";
                } else {
                    ((Ferret) pet).housebroken = true;
                    return pet.summary() + " is now housebroken.";
                }
            }
        }
        return pet.summary() + " no change.";
    }
}

// Negative Reinforcement Algorithm Trainer
class nrTrainer extends Trainer {
    final String message;

    nrTrainer(String n) {
        name = n;
        message = name + " is training the animals using negative reinforcement.";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String train(Pet pet) {
        double prob = Math.random();
        if (pet instanceof Dog) {
            if (((Dog) pet).housebroken && prob <= 0.2) { // 20% chance of True to False
                ((Dog) pet).housebroken = false;
                return pet.summary() + " is no longer housebroken.";
            } else if (prob <= 0.4) { // 40% chance of False to True
                ((Dog) pet).housebroken = true;
                return pet.summary() + " is now housebroken.";
            }
        }
        if (pet instanceof Cat) {
            if (((Cat) pet).housebroken && prob <= 0.2) { // 20% chance of True to False
                ((Cat) pet).housebroken = false;
                return pet.summary() + " is no longer housebroken.";
            } else if (prob <= 0.4) { // 40% chance of False to True
                ((Cat) pet).housebroken = true;
                return pet.summary() + " is now housebroken.";
            }
        }
        if (pet instanceof Ferret) {
            if (((Ferret) pet).housebroken && prob <= 0.2) { // 20% chance of True to False
                ((Ferret) pet).housebroken = false;
                return pet.summary() + " is no longer housebroken.";
            } else if (prob <= 0.4) { // 40% chance of False to True
                ((Ferret) pet).housebroken = true;
                return pet.summary() + " is now housebroken.";
            }
        }
        return pet.summary() + " no change.";
    }
}

// positive reinforcement trainer
class prTrainer extends Trainer {
    final String message;

    prTrainer(String n) {
        name = n;
        message = name + " is training the pets using positive reinforcement.";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String train(Pet pet) {
        double prob = Math.random();
        if (pet instanceof Dog) {
            if (!((Dog) pet).housebroken && prob <= 0.5) ((Dog) pet).housebroken = true; // 50% chance of False to True
            return pet.summary() + " is now housebroken.";
        }
        if (pet instanceof Cat) {
            if (!((Cat) pet).housebroken && prob <= 0.5) ((Cat) pet).housebroken = true; // 50% chance of False to True
            return pet.summary() + " is now housebroken.";
        }
        if (pet instanceof Ferret) {
            if (!((Ferret) pet).housebroken && prob <= 0.5)
                ((Ferret) pet).housebroken = true; // 50% chance of False to True
            return pet.summary() + " is now housebroken.";
        }
        return pet.summary() + " no change.";
    }
}
