package recipecalc.core;

import java.util.regex.Pattern;

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

  /**
   * No args constructor for GSON.
   */
  public Ingredient() {

  }

  public String getName() {
    return name;
  }

  protected void setName(String name) {
    if (!isValidIngredientName(name)) {
      throw new IllegalArgumentException("\"" + name + "\" is not a valid ingredient name");
    }
    this.name = name.strip();
  }

  /**
   * Checks if a string is a suitable ingredient name.
   *
   * @param name The string to check
   * @return True if it's a suitable name
   */
  public static boolean isValidIngredientName(String name) {
    return Pattern.matches("^[æøåÆØÅa-zA-Z0-9 _-]*[æøåÆØÅa-zA-Z0-9_-][æøåÆØÅa-zA-Z0-9 _-]*$", name);
  }

  protected void setMeasuringUnit(String measuringUnit) {
    if (!isValidMeasuringUnit(measuringUnit)) {
      throw new IllegalArgumentException("Not a valid measuring unit");
    }
    this.measuringUnit = measuringUnit.strip();
  }

  /**
   * Checks if a string is a suitable measuring unit.
   *
   * @param unit The string to check
   * @return True if it's a suitable measuring unit
   */
  public static boolean isValidMeasuringUnit(String unit) {
    return Pattern.matches("^[æøåÆØÅa-zA-Z0-9 _-]*[æøåÆØÅa-zA-Z0-9_-][æøåÆØÅa-zA-Z0-9 _-]*$", unit);
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
    if (!isValidPerPersonAmount(amount)) {
      throw new IllegalArgumentException("Amount must be a positive number, but was " + amount);
    }
    this.perPersonAmount = amount;
  }

  public static boolean isValidPerPersonAmount(Double amount) {
    return amount > 0;
  }

  public Double getRoundUpTo() {
    return roundUpTo;
  }

  /**
   * Set the value that the total amount is rounded up to.
   *
   * @param roundUpTo the value to be rounded up to. If the value is 0, the total value won't be
   *        rounded.
   */
  public void setRoundUpTo(Double roundUpTo) {
    if (!isValidRoundUpTo(roundUpTo)) {
      throw new IllegalArgumentException("Number be a positive number, but was " + roundUpTo);
    }
    this.roundUpTo = roundUpTo;
  }

  public static boolean isValidRoundUpTo(Double roundUpTo) {
    return roundUpTo >= 0;
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
