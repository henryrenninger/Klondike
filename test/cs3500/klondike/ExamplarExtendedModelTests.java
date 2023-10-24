package cs3500.klondike;

import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw04.WhiteheadKlondike;
import cs3500.klondike.model.hw04.LimitedDrawKlondike;

import org.junit.Assert;
import org.junit.Test;

/**
 * Examplar edge-case tests.
 */
public class ExamplarExtendedModelTests {

  /**
   * Tests whether the first drawn card remains undiscarded after one draw cycle.
   */
  @Test
  public void testFirstDrawCardIsNotDiscardedAfterOneDrawCycle() {
    KlondikeModel model = new LimitedDrawKlondike(2);
    model.startGame(model.getDeck(), false, 9, 8);
    Card expected = model.getDrawCards().get(0);
    for (int i = 0; i < 7; i++) {
      model.discardDraw();
    }
    Card actual = model.getDrawCards().get(0);
    Assert.assertEquals(expected, actual);
  }

  /**
   * Tests whether attempting to discard more cards
   * than allowed raises an IllegalStateException.
   */
  @Test(expected = IllegalStateException.class)
  public void testDiscardingMoreTimesThanAllowed() {
    KlondikeModel model = new LimitedDrawKlondike(1);
    model.startGame(model.getDeck(), false, 9, 3);
    for (int i = 0; i < 15; i++) {
      model.discardDraw();
    }
  }

  /**
   * Tests whether moving two cards with the same color
   * but different suits raises an IllegalStateException.
   */
  @Test(expected = IllegalStateException.class)
  public void testMovingTwoCardsSameColorDifferentSuit() {
    KlondikeModel model = new WhiteheadKlondike();
    model.startGame(model.getDeck(), false, 5, 3);
    model.moveToFoundation(0, 0);
    model.movePile(2, 1, 4);
    model.movePile(4, 2, 0);
  }

  /**
   * Tests whether attempting to move one card to a pile
   * with alternating suits raises an IllegalStateException.
   */
  @Test(expected = IllegalStateException.class)
  public void testMovingOneCardToAlternatingSuit() {
    KlondikeModel model = new WhiteheadKlondike();
    model.startGame(model.getDeck(), false, 5, 3);
    model.movePile(2, 1, 3);
  }

  /**
   * Tests whether attempting to move multiple cards to a pile
   * with alternating suits raises an IllegalStateException.
   */
  @Test(expected = IllegalStateException.class)
  public void testMovingMultipleCardsToAlternatingSuit() {
    KlondikeModel model = new WhiteheadKlondike();
    model.startGame(model.getDeck(), false, 5, 3);
    model.movePile(1, 1, 2);
    model.movePile(2, 2, 3);
  }

  /**
   * Checks whether moving a non-King card from the draw
   * pile to an empty pile maintains the card's position.
   */
  @Test
  public void testCheckMovingNonKingFromDrawToEmpty() {
    KlondikeModel model = new WhiteheadKlondike();
    model.startGame(model.getDeck(), false, 5, 3);
    Card expected = model.getDrawCards().get(0);
    model.moveToFoundation(0, 0);
    model.moveDraw(0);
    Card actual = model.getCardAt(0, 0);
    Assert.assertEquals(expected, actual);
  }

}
