package cs3500.klondike.model.hw02;

import java.util.Objects;

/**
 * Represents a Klondike solitaire card with a face value, suit, and hidden status.
 */
public class KlondikeCard implements Card {
  private final FaceValue faceValue;
  private final Suit suit;
  private boolean visible;

  /**
   * Constructs a KlondikeCard with the specified face value and suit, initially hidden.
   *
   * @param faceValue The face value of the card.
   * @param suit      The suit of the card.
   */
  public KlondikeCard(FaceValue faceValue, Suit suit) {
    this.faceValue = Objects.requireNonNull(faceValue);
    this.suit = Objects.requireNonNull(suit);
    this.visible = true;
  }

  @Override
  public String toString() {
    return this.faceValue.toString() + this.suit.toString();
  }

  @Override
  public boolean equals(Object object) {
    return this.hashCode() == object.hashCode();
  }

  @Override
  public int hashCode() {
    return Objects.hash(faceValue, suit);
  }

  @Override
  public FaceValue getFaceValue() {
    return faceValue;
  }

  @Override
  public Suit getSuit() {
    return suit;
  }

  @Override
  public boolean getVisibility() {
    return visible;
  }

  @Override
  public void setVisibility(boolean visible) {
    this.visible = visible;
  }

}
