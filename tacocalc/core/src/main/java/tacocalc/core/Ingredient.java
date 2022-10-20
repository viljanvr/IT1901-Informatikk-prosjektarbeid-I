package tacocalc.core;

/**
 * Class for an ingredient with the properties: name, amount and bought.
 */
public class Ingredient {
  private String name;
  private Integer amount;
  private Double perPerson = 0.0d;

  private Boolean bought = false;

  public Ingredient(String name, Integer amount) {
    setName(name);
    setAmount(amount);
  }

  public String getName() {
    return name;
  }

  protected void setName(String name) {
    this.name = name;
  }

  public Boolean getBought() {
    return bought;
  }

  protected void setBought(Boolean bought) {
    this.bought = bought;
  }

  public Integer getAmount() {
    return amount;
  }

  protected void setAmount(Integer amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount must be a positive number, but was " + amount);
    }
    this.amount = amount;
  }

  public Double getPerPerson() {
    return perPerson;
  }

  protected void setPerPerson(Double perPerson) {
    if (perPerson <= 0) {
      throw new IllegalArgumentException(
          "perPerson must be a positive number, but was " + perPerson);
    }
    this.perPerson = perPerson;
  }

  public String toString() {
    return ("[" + (getBought() ? "x" : " ") + "]: " + getAmount() + "x " + getName());
  }
}
