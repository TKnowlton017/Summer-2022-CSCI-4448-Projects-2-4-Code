class Clerk extends Employee {
    public Clerk(String clerkName) {
        name = clerkName;
    }

    @Override
    public String getName() {
        return name;
    }
}
