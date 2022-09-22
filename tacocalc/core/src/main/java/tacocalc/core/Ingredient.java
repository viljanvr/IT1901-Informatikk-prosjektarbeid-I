package tacocalc.core;

public class Ingredient {
    private String name;
    private Integer amount;

    private Boolean bought = false;

    protected Ingredient(String name, Integer amount) {
        setName(name);
        setAmount(amount);
    }

    // No args constructor to be used in Gson reading
    protected Ingredient() {
        
    }

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected Boolean getBought() {
        return bought;
    }

    protected void setBought(Boolean bought) {
        this.bought = bought;
    }

    protected Integer getAmount() {
        return amount;
    }

    protected void setAmount(Integer amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be a positive number, but was " + amount);
        }
        this.amount = amount;
    }

    public String toString() {
        return "[" + (getBought() ? "x" : " ") + "]: " + getAmount() + "x " + getName();
    }
}
