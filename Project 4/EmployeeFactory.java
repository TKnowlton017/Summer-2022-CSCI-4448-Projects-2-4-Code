// Employee creation method utilizing FACTORY PATTERN
public class EmployeeFactory {
    public Employee createNewEmployee(String employeeName) { // if only given employeeName, new employee is a CLERK
        if (employeeName == null) {
            return null;
        }
        return new Clerk(employeeName);
    }

    public Employee createNewEmployee(String employeeName, String trainingType) { // if overloaded with 2 arguments, new employee is a TRAINER
        if (employeeName == null || trainingType == null) {
            return null;
        }
        switch (trainingType.toUpperCase()) {
            case "HAPHAZARD":
                return new HaphazardTrainer(employeeName);
            case "POSITIVE":
                return new prTrainer(employeeName);
            case "NEGATIVE":
                return new nrTrainer(employeeName);
        }
        return null;
    }
}

abstract class Employee {
    String name;
    public int daysWorked;

    String getName() {
        return name;
    }
}

// Trainer definitions found in Trainer.java