package tacocalc.core;

/**
 * Class for an ingredient with the properties: name, amount and bought.
 */
public class Ingredient {
  private String name;
  private Double perPersonAmount;
  private String measuringUnit = "Default";
  private Double roundUpTo;

  private Boolean bought = false;

  /**
   * The constructor of the Ingredient.
   *
   * @param name another thing
   * @param perPersonAmount the other thing
   * @param measuringUnit the thing
   */
  public Ingredient(String name, Double perPersonAmount, String measuringUnit) {
    this(name, perPersonAmount, 0.0, measuringUnit);
  }

  /**
   * Constructor for ingredient class.
   *
   * @param name the name of the new ingredient
   * @param perPersonAmount the amount per person
   * @param roundUpTo the amount to round up to
   */
  public Ingredient(String name, Double perPersonAmount, Double roundUpTo, String measuringUnit) {
    setName(name);
    setPerPersonAmount(perPersonAmount);
    setMeasuringUnit(measuringUnit);
    setRoundUpTo(roundUpTo);
  }

  public String getName() {
    return name;
  }

  protected void setName(String name) {
    this.name = name;
  }

  protected void setMeasuringUnit(String measuringUnit) {
    this.measuringUnit = measuringUnit;
  }

  public String getMeasuringUnit() {
    return measuringUnit;
  }

  public Boolean getBought() {
    return bought;
  }

  protected void setBought(Boolean bought) {
    this.bought = bought;
  }

  public Double getPerPersonAmount() {
    return perPersonAmount;
  }

  protected void setPerPersonAmount(Double amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount must be a positive number, but was " + amount);
    }
    this.perPersonAmount = amount;
  }

  public Double getRoundUpTo() {
    return roundUpTo;
  }

  /**
   * Set the value that the total amount is rounded up to.
   *
   * @param roundUpTo the value to be rounded up to. If the value is 0, the total value wount be
   *        rounded.
   */
  public void setRoundUpTo(Double roundUpTo) {
    if (roundUpTo < 0) {
      throw new IllegalArgumentException("Number be a positive number, but was " + roundUpTo);
    }
    this.roundUpTo = roundUpTo;
  }

  /**
   * Get the total value of an ingredient, scaled to the number of people given.
   *
   * @param numberOfPeople the number of people to eat
   * @return a total amount as a double
   */
  public Double getTotalAmount(Integer numberOfPeople) {
    if (roundUpTo == 0) {
      return perPersonAmount * numberOfPeople;
    } else {
      Double totalAmount = perPersonAmount * numberOfPeople;
      return Math.ceil(totalAmount / roundUpTo) * roundUpTo;
    }
  }

  public String toString() {
    return "[" + (getBought() ? "x" : " ") + "]: " + formatDouble(getPerPersonAmount()) + " "
        + getMeasuringUnit() + " " + getName();
  }

  public static String formatDouble(Double d) {
    return d % 1 == 0 ? String.valueOf((d.intValue())) : d.toString();
  }


}
