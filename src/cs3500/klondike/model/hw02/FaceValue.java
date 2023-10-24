package cs3500.klondike.model.hw02;

/**
 * Enumeration representing the face values of playing cards.
 * Each face value has a string representation and an integer value.
 */
public enum FaceValue {
  ACE("A", 1),
  TWO("2", 2),
  THREE("3", 3),
  FOUR("4", 4),
  FIVE("5", 5),
  SIX("6", 6),
  SEVEN("7", 7),
  EIGHT("8", 8),
  NINE("9", 9),
  TEN("10", 10),
  JACK("J", 11),
  QUEEN("Q", 12),
  KING("K", 13);

  private final String stringVal;
  private final int intVal;

  /**
   * The constructor for this enumeration.
   *
   * @param stringVal The string representation.
   * @param intVal The int representation.
   */
  FaceValue(String stringVal, int intVal) {
    this.stringVal = stringVal;
    this.intVal = intVal;
  }

  /**
   * Returns the string representation of the face value.
   *
   * @return The string representation.
   */
  public String toString() {
    return this.stringVal;
  }

  /**
   * Returns the integer value of the face value.
   *
   * @return The integer value.
   */
  public int toInt() {
    return this.intVal;
  }


}
