package cs3500.klondike.model.hw04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.FaceValue;
import cs3500.klondike.model.hw02.KlondikeCard;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw02.Suit;

/**
 * The abstract super class for all klondike games. Includes all the rules to a standard
 * basic klondike game, expects other klondike models to override these rules when necessary.
 */
public abstract class SupremeKlondike implements KlondikeModel {
  protected List<Card> deck;
  protected int numPiles;
  protected int numDraw;
  protected int foundationLength;
  protected List<List<Card>> cascadeTable;
  protected List<List<Card>> foundationTable;
  protected List<Card> drawList;
  protected boolean isGameStarted;

  /**
   * Initializes three fields to their basic state. Called in the constructors
   * of each subclass of SupremeKlondike.
   */
  protected void init() {
    this.cascadeTable = new ArrayList<>();
    this.foundationTable = new ArrayList<>();
    this.isGameStarted = false;
  }

  @Override
  public List<Card> getDeck() {
    FaceValue[] faceValues = FaceValue.values();
    Suit[] suits = Suit.values();
    List<Card> standardDeck = new ArrayList<>();
    for (FaceValue val : faceValues) {
      for (Suit suit : suits) {
        standardDeck.add(new KlondikeCard(val, suit));
      }
    }
    return standardDeck;
  }

  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numPiles, int numDraw) {
    if (!validDeck(deck)) {
      throw new IllegalArgumentException("Deck must be valid");
    }
    if (this.isGameStarted) {
      throw new IllegalStateException("Game has already started");
    }
    this.deck = deck;
    if (shuffle) {
      Collections.shuffle(this.deck);
    }
    if (numPiles <= 0 || numDraw <= 0) {
      throw new IllegalArgumentException("Must have positive values for numPiles and numDraw");
    }
    if (!this.legalCascade(deck, numPiles)) {
      throw new IllegalArgumentException("Full cascade cannot be dealt");
    }
    this.numPiles = numPiles;
    this.numDraw = numDraw;
    this.initializeFoundation();
    this.initializeCascadeAndDraw();
    this.isGameStarted = true;
  }

  /**
   * Validates whether the provided deck is legal for the game.
   *
   * @param deck The deck to be validated.
   * @return true if the deck is legal, false otherwise.
   */
  public boolean validDeck(List<Card> deck) {
    if (deck == null) {
      throw new IllegalArgumentException("Deck must not be null");
    }
    if (deck.isEmpty()) {
      throw new IllegalStateException("Cannot have empty deck");
    }
    List<Card> deckCopy = new ArrayList<>();
    for (Card card : deck) {
      deckCopy.add(card);
    }
    List<Suit> suits = new ArrayList<>();
    for (Card card : deckCopy) {
      if (card.getFaceValue() == FaceValue.ACE) {
        suits.add(card.getSuit());
      }
    }
    if (suits.isEmpty()) {
      throw new IllegalArgumentException("Deck must have aces");
    }
    this.foundationLength = deckCopy.size() / suits.size();
    if (deckCopy.size() % suits.size() != 0) {
      throw new IllegalArgumentException("Cards must be grouped in suits of the same length");
    }
    List<Card> idealSet = new ArrayList<>();
    FaceValue[] faceVals = Arrays.copyOfRange(FaceValue.values(), 0, this.foundationLength);
    for (Suit suit : suits) {
      for (FaceValue fv : faceVals) {
        idealSet.add(new KlondikeCard(fv, suit));
      }
    }
    for (Card card : idealSet) {
      if (!deckCopy.contains(card)) {
        throw new IllegalArgumentException("Cards must have the same length of suit runs");
      }
      deckCopy.remove(card);
    }
    if (!deckCopy.isEmpty()) {
      throw new IllegalArgumentException("Cards must have the same length of suit runs");
    }
    return true;
  }

  /**
   * Counts the number of aces in this deck.
   *
   * @return The int number of aces.
   */
  protected int numAces() {
    int count = 0;
    for (Card card : this.deck) {
      if (card.getFaceValue() == FaceValue.ACE) {
        count++;
      }
    }
    return count;
  }

  /**
   * Initializes the foundation piles based on the provided deck.
   */
  private void initializeFoundation() {
    for (Card card : this.deck) {
      if (card.getFaceValue() == FaceValue.ACE) {
        this.foundationTable.add(new ArrayList<>());
      }
    }
  }

  /**
   * Checks whether dealing a full cascade is legal based on the provided deck.
   *
   * @param deck The deck to be checked.
   * @param numPiles The number of piles to deal cards out to.
   * @return true if dealing a full cascade is legal, false otherwise.
   */
  public boolean legalCascade(List<Card> deck, int numPiles) {
    int counter = 0;
    for (int i = 0; i < numPiles; i++) {
      counter += i + 1;
    }
    return counter <= deck.size();
  }

  /**
   * Initializes the cascade piles and draw pile based on the provided deck.
   */
  protected void initializeCascadeAndDraw() {
    List<Card> deckCopy = new ArrayList<>();
    if (deck.isEmpty()) {
      throw new IllegalArgumentException("Cannot have empty deck");
    }
    for (Card card : deck) {
      deckCopy.add(card);
    }
    for (int i = 0; i < this.numPiles; i++) {
      this.cascadeTable.add(new ArrayList<>());
    }
    for (int i = 0; i < numPiles; i++) {
      for (int j = i; j < numPiles; j++) {
        Card temp = deckCopy.remove(0);
        if (i != j) {
          temp.setVisibility(false);
        }
        this.cascadeTable.get(j).add(temp);

      }
    }
    this.drawList = deckCopy;
  }


  @Override
  public void movePile(int srcPile, int numCards, int destPile) {
    if (!this.isGameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    if (srcPile < 0 || srcPile >= numPiles || destPile < 0 || destPile >= numPiles) {
      throw new IllegalArgumentException("Source and destination piles must be in bounds");
    }
    if (srcPile == destPile) {
      throw new IllegalArgumentException("Source and destination cannot be the same");
    }
    if (getPileHeight(srcPile) < numCards) {
      throw new IllegalArgumentException("Cannot remove more cards than the pile height");
    }
    Card sourceCard = cascadeTable.get(srcPile).get(getPileHeight(srcPile) - numCards);
    if (cascadeTable.get(destPile).isEmpty()) {
      if (sourceCard.getFaceValue().toInt() == deck.size() / this.numAces()) {
        cascadeTable.get(destPile).add(sourceCard);
        cascadeTable.get(srcPile).remove(getPileHeight(srcPile) - numCards);
        numCards--;
        if (cascadeTable.get(srcPile).size() > 0) {
          if (!cascadeTable.get(srcPile).get(cascadeTable.get(srcPile).size()
                  - 1).getVisibility()) {
            cascadeTable.get(srcPile).get(cascadeTable.get(srcPile).size()
                    - 1).setVisibility(true);
          }
        }
        for (int i = numCards; i > 0; i--) {
          sourceCard = cascadeTable.get(srcPile).get(getPileHeight(srcPile) - i);
          if (cascadeTable.get(destPile).get(getPileHeight(destPile) - 1).getFaceValue().toInt()
                  == sourceCard.getFaceValue().toInt() + 1) {
            if (cascadeTable.get(destPile).get(getPileHeight(destPile) - 1).getSuit().toColor()
                    != sourceCard.getSuit().toColor()) {
              cascadeTable.get(destPile).add(sourceCard);
              cascadeTable.get(srcPile).remove(getPileHeight(srcPile) - i);
              if (cascadeTable.get(srcPile).size() > 0) {
                if (!cascadeTable.get(srcPile).get(cascadeTable.get(srcPile).size()
                        - 1).getVisibility()) {
                  cascadeTable.get(srcPile).get(cascadeTable.get(srcPile).size()
                          - 1).setVisibility(true);
                }
              }
            } else {
              throw new IllegalStateException("Cards must be of alternating suits");
            }
          } else {
            throw new IllegalStateException("Cards must be in a descending order");
          }
        }
      } else {
        throw new IllegalStateException("First card on a cascade pile must"
                + "be the last face value in the deck");
      }
    } else {
      for (int i = numCards; i > 0; i--) {
        sourceCard = cascadeTable.get(srcPile).get(getPileHeight(srcPile) - i);
        if (cascadeTable.get(destPile).get(getPileHeight(destPile) - 1).getFaceValue().toInt()
                == sourceCard.getFaceValue().toInt() + 1) {
          if (cascadeTable.get(destPile).get(getPileHeight(destPile) - 1).getSuit().toColor()
                  != sourceCard.getSuit().toColor()) {
            cascadeTable.get(destPile).add(sourceCard);
            cascadeTable.get(srcPile).remove(getPileHeight(srcPile) - i);
            if (cascadeTable.get(srcPile).size() > 0) {
              if (!cascadeTable.get(srcPile).get(cascadeTable.get(srcPile).size()
                      - 1).getVisibility()) {
                cascadeTable.get(srcPile).get(cascadeTable.get(srcPile).size()
                        - 1).setVisibility(true);
              }
            }
          } else {
            throw new IllegalStateException("Cards must be of alternating suits");
          }
        } else {
          throw new IllegalStateException("Cards must be in a descending order");
        }
      }
    }
  }

  @Override
  public void moveDraw(int destPile) {
    if (!this.isGameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    if (destPile < 0 || destPile >= numPiles) {
      throw new IllegalArgumentException("Destination pile must be in bounds");
    }
    if (getDrawCards().isEmpty()) {
      throw new IllegalStateException("Cannot remove from an empty pile");
    }
    Card sourceCard = drawList.get(0);
    if (cascadeTable.get(destPile).isEmpty()) {
      if (sourceCard.getFaceValue().toInt() == deck.size() / this.numAces()) {
        cascadeTable.get(destPile).add(sourceCard);
        drawList.remove(0);
      } else {
        throw new IllegalStateException("First card on a cascade pile must"
                + "be the last face value in the deck");
      }
    } else {
      if (cascadeTable.get(destPile).get(getPileHeight(destPile) - 1).getFaceValue().toInt()
              == sourceCard.getFaceValue().toInt() + 1) {
        if (cascadeTable.get(destPile).get(getPileHeight(destPile) - 1).getSuit().toColor()
                != sourceCard.getSuit().toColor()) {
          cascadeTable.get(destPile).add(sourceCard);
          drawList.remove(0);
        } else {
          throw new IllegalStateException("Cards must be of alternating suits");
        }
      } else {
        throw new IllegalStateException("Cards must be in a descending order");
      }
    }
  }


  @Override
  public void moveToFoundation(int srcPile, int foundationPile) {
    if (!this.isGameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    if (srcPile < 0 || srcPile >= numPiles
            || foundationPile < 0 || foundationPile >= foundationTable.size()) {
      throw new IllegalArgumentException("Source and foundation piles must be in bounds");
    }

    if (getPileHeight(srcPile) == 0) {
      throw new IllegalStateException("Cannot remove from an empty pile");
    }
    Card sourceCard = cascadeTable.get(srcPile).get(getPileHeight(srcPile) - 1);
    if (foundationTable.get(foundationPile).isEmpty()) {
      if (sourceCard.getFaceValue() == FaceValue.ACE) {
        foundationTable.get(foundationPile).add(sourceCard);
        cascadeTable.get(srcPile).remove(getPileHeight(srcPile) - 1);
        if (cascadeTable.get(srcPile).size() > 0) {
          if (!cascadeTable.get(srcPile).get(cascadeTable.get(srcPile).size()
                  - 1).getVisibility()) {
            cascadeTable.get(srcPile).get(cascadeTable.get(srcPile).size()
                    - 1).setVisibility(true);
          }
        }
      } else {
        throw new IllegalStateException("First card on a foundation pile must be an ace");
      }
    } else {
      if (getCardAt(foundationPile).getFaceValue().toInt()
              == sourceCard.getFaceValue().toInt() - 1) {
        if (getCardAt(foundationPile).getSuit() == sourceCard.getSuit()) {
          foundationTable.get(foundationPile).add(sourceCard);
          cascadeTable.get(srcPile).remove(getPileHeight(srcPile) - 1);
          if (cascadeTable.get(srcPile).size() > 0) {
            if (!cascadeTable.get(srcPile).get(cascadeTable.get(srcPile).size()
                    - 1).getVisibility()) {
              cascadeTable.get(srcPile).get(cascadeTable.get(srcPile).size()
                      - 1).setVisibility(true);
            }
          }
        } else {
          throw new IllegalStateException("Cards must be of the same suit");
        }
      } else {
        throw new IllegalStateException("Cards must be in a ascending order");
      }
    }
  }

  @Override
  public void moveDrawToFoundation(int foundationPile) {
    if (!this.isGameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    if (foundationPile < 0 || foundationPile >= foundationTable.size()) {
      throw new IllegalArgumentException("Foundation pile must be in bounds");
    }
    if (getDrawCards().isEmpty()) {
      throw new IllegalStateException("Cannot remove from an empty pile");
    }
    Card sourceCard = drawList.get(0);
    if (foundationTable.get(foundationPile).isEmpty()) {
      if (sourceCard.getFaceValue() == FaceValue.ACE) {
        foundationTable.get(foundationPile).add(sourceCard);
        drawList.remove(0);
      } else {
        throw new IllegalStateException("First card on a foundation pile must be an ace");
      }
    } else {
      if (getCardAt(foundationPile).getFaceValue().toInt()
              == sourceCard.getFaceValue().toInt() - 1) {
        if (getCardAt(foundationPile).getSuit() == sourceCard.getSuit()) {
          foundationTable.get(foundationPile).add(sourceCard);
          drawList.remove(0);
        } else {
          throw new IllegalStateException("Cards must be of the same suit");
        }
      } else {
        throw new IllegalStateException("Cards must be in a ascending order");
      }
    }
  }

  @Override
  public void discardDraw() {
    if (!this.isGameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    if (drawList.isEmpty()) {
      throw new IllegalStateException("Cannot remove card from an empty draw pile");
    }
    Card temp = drawList.get(0);
    this.drawList.add(temp);
    this.drawList.remove(0);
  }

  @Override
  public int getNumRows() {
    if (!this.isGameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    int maxHeight = -1;
    for (List<Card> pile : cascadeTable) {
      if (pile.size() > maxHeight) {
        maxHeight = pile.size();
      }
    }
    return maxHeight;
  }

  @Override
  public int getNumPiles() {
    if (!this.isGameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    return cascadeTable.size();
  }

  @Override
  public int getNumDraw() {
    if (!this.isGameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    if (numDraw > drawList.size()) {
      return drawList.size();
    }
    return numDraw;
  }

  @Override
  public boolean isGameOver() {
    if (!this.isGameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    if (this.drawList.isEmpty()) {
      this.isGameStarted = false;
      return true;
    }
    for (List<Card> pile : foundationTable) {
      if (pile.size() != foundationLength) {
        return false;
      }
    }
    this.isGameStarted = false;
    return true;
  }

  @Override
  public int getScore() {
    if (!this.isGameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    int score = 0;
    for (int i = 0; i < foundationTable.size(); i++) {
      if (!foundationTable.get(i).isEmpty()) {
        score += getCardAt(i).getFaceValue().toInt();
      }
    }
    return score;
  }

  @Override
  public int getPileHeight(int pileNum) {
    if (!this.isGameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    if (pileNum < 0 || pileNum >= numPiles) {
      throw new IllegalArgumentException("Pile must be in bounds");
    }
    return this.cascadeTable.get(pileNum).size();
  }

  @Override
  public boolean isCardVisible(int pileNum, int card) {
    if (!this.isGameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    if (pileNum < 0 || pileNum >= getNumPiles()) {
      throw new IllegalArgumentException("Pile must be in bounds");
    }
    if (cascadeTable.get(pileNum).isEmpty()) {
      throw new IllegalArgumentException("Cannot find cards on an empty deck");
    }
    if (card < cascadeTable.get(pileNum).size()) {
      return cascadeTable.get(pileNum).get(card).getVisibility();
    } else {
      return false;
    }
  }

  @Override
  public Card getCardAt(int pileNum, int card) {
    if (!this.isGameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    if (pileNum < 0 || pileNum >= numPiles) {
      throw new IllegalArgumentException("Pile must be in bounds");
    }
    if (!isCardVisible(pileNum, card)) {
      throw new IllegalArgumentException("Cannot obtain non-visible cards");
    }
    if (card < getPileHeight(pileNum)) {
      return cascadeTable.get(pileNum).get(card);
    } else {
      return null;
    }
  }

  @Override
  public Card getCardAt(int foundationPile) {
    if (!this.isGameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    if (foundationPile < 0 || foundationPile >= getNumFoundations()) {
      throw new IllegalArgumentException("Pile must be in bounds");
    }
    if (!this.foundationTable.get(foundationPile).isEmpty()) {
      return foundationTable.get(foundationPile).get(
              foundationTable.get(foundationPile).size() - 1);
    } else {
      return null;
    }

  }

  @Override
  public List<Card> getDrawCards() {
    if (!this.isGameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    if (numDraw > drawList.size()) {
      return drawList;
    }
    return drawList.subList(0, numDraw);
  }

  @Override
  public int getNumFoundations() {
    if (!this.isGameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    return foundationTable.size();
  }

}