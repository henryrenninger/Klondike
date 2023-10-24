package cs3500.klondike.model.hw04;

import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.Suit;

import java.util.List;

/**
 * The model of the Whitehead. This game does not follow normal cascade rules of solitaire,
 * instead of black and red alternating suits, it has same suit color piles. When moving more
 * than one card, however, cards must be of the same suit as well. There are no limits
 * to redraw, however there is one extra twist: all cards are visible.
 */
public class WhiteheadKlondike extends SupremeKlondike {

  /**
   * Constructor for whitehead klondike.
   */
  public WhiteheadKlondike() {
    super.init();
  }

  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numPiles, int numDraw)
          throws IllegalArgumentException {
    super.startGame(deck, shuffle, numPiles, numDraw);
    for (List<Card> loc : super.cascadeTable) {
      for (Card card : loc) {
        card.setVisibility(true);
      }
    }
  }

  @Override
  public void movePile(int srcPile, int numCards, int destPile) throws IllegalStateException {
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
      Suit currentSuit = cascadeTable.get(srcPile).get(cascadeTable.get(srcPile).size()
              - numCards).getSuit();
      if (cascadeTable.get(srcPile).subList(cascadeTable.get(srcPile).size() - numCards,
              cascadeTable.get(srcPile).size()).stream().allMatch(card
                  -> card.getSuit().equals(currentSuit))) {
        cascadeTable.get(destPile).add(sourceCard);
        cascadeTable.get(srcPile).remove(getPileHeight(srcPile) - numCards);
        for (int i = numCards - 1; i > 0; i--) {
          sourceCard = cascadeTable.get(srcPile).get(getPileHeight(srcPile) - i);
          if (cascadeTable.get(destPile).get(getPileHeight(destPile) - 1).getFaceValue().toInt()
                  == sourceCard.getFaceValue().toInt() + 1) {
            sourceCard = cascadeTable.get(srcPile).get(getPileHeight(srcPile) - i);
            cascadeTable.get(destPile).add(sourceCard);
            cascadeTable.get(srcPile).remove(getPileHeight(srcPile) - i);
          } else {
            cascadeTable.get(srcPile).add(cascadeTable.get(destPile).size() - numCards + 1,
                    cascadeTable.get(destPile).get(0));
            cascadeTable.get(destPile).remove(0);
            throw new IllegalStateException("Cards must be in a descending order");
          }
        }
      } else {
        throw new IllegalStateException("Cards must be in a descending order");
      }
    } else {
      Suit currentSuit = cascadeTable.get(srcPile).get(cascadeTable.get(srcPile).size()
              - numCards).getSuit();
      if (cascadeTable.get(destPile).get(cascadeTable.get(destPile).size()
              - 1).getSuit().toColor().equals(currentSuit.toColor())
              && cascadeTable.get(srcPile).subList(cascadeTable.get(srcPile).size()
              - numCards, cascadeTable.get(srcPile).size()).stream().allMatch(card
                  -> card.getSuit().equals(currentSuit))) {
        for (int i = numCards; i > 0; i--) {
          sourceCard = cascadeTable.get(srcPile).get(getPileHeight(srcPile) - i);
          if (cascadeTable.get(destPile).get(getPileHeight(destPile) - 1).getFaceValue().toInt()
                  == sourceCard.getFaceValue().toInt() + 1) {
            sourceCard = cascadeTable.get(srcPile).get(getPileHeight(srcPile) - i);
            cascadeTable.get(destPile).add(sourceCard);
            cascadeTable.get(srcPile).remove(getPileHeight(srcPile) - i);
          } else {
            throw new IllegalStateException("Cards must be in a descending order");
          }
        }
      } else {
        throw new IllegalStateException("Cascade must be same suit to move");
      }
    }
  }

  @Override
  public void moveDraw(int destPile) throws IllegalStateException {
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
      cascadeTable.get(destPile).add(sourceCard);
      drawList.remove(0);
    } else {
      if (cascadeTable.get(destPile).get(getPileHeight(destPile) - 1).getFaceValue().toInt()
              == sourceCard.getFaceValue().toInt() + 1) {
        if (cascadeTable.get(destPile).get(getPileHeight(destPile) - 1).getSuit().toColor()
                == sourceCard.getSuit().toColor()) {
          cascadeTable.get(destPile).add(sourceCard);
          drawList.remove(0);
        } else {
          throw new IllegalStateException("Cards must be of the same suits");
        }
      } else {
        throw new IllegalStateException("Cards must be in a descending order");
      }
    }
  }

}
