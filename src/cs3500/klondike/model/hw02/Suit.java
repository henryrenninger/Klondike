package cs3500.klondike.model.hw02;


/**
 * Enumeration representing the suits of playing cards.
 * Each suit has a string representation and a color.
 */
public enum Suit {
  CLUB("♣", SuitColor.BLACK),
  DIAMOND("♢", SuitColor.RED),
  HEART("♡", SuitColor.RED),
  SPADE("♠", SuitColor.BLACK);



  private final String val;
  private final SuitColor color;

  /**
   * The constructor for this enumeration.
   *
   * @param val The string representation.
   * @param color The SuitColor representation.
   */
  Suit(String val, SuitColor color) {
    this.val = val;
    this.color = color;
  }

  /**
   * Returns the string representation of the suit.
   *
   * @return The string representation.
   */
  public String toString() {
    return this.val;
  }

  /**
   * Returns the color of the suit.
   *
   * @return The color of the suit.
   */
  public SuitColor toColor() {
    return this.color;
  }

}
