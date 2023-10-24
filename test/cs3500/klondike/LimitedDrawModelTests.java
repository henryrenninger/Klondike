package cs3500.klondike;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw04.LimitedDrawKlondike;

/**
 * Tests for LimitedDrawKlondike class.
 */
public class LimitedDrawModelTests {

  /**
   * Tests whether the size of the draw pile changes after discarding a card completely.
   */
  @Test
  public void testDrawCardsSizeChangesAfterGettingRidOfACardCompletely() {
    KlondikeModel model = new LimitedDrawKlondike(1);
    model.startGame(model.getDeck(), false, 9, 7);
    int expected = model.getNumDraw();
    for (int i = 0; i < 8; i++) {
      model.discardDraw();
    }
    int actual = model.getNumDraw();
    Assert.assertNotEquals(expected, actual);
  }

  /**
   * Tests whether attempting to move a draw card when no
   * draw cards are left raises an IllegalStateException.
   */
  @Test(expected = IllegalStateException.class)
  public void testNoDrawCardsLeftIllegalMove() {
    KlondikeModel model = new LimitedDrawKlondike(1);
    model.startGame(model.getDeck(), false, 9, 3);
    for (int i = 0; i < 14; i++) {
      model.discardDraw();
    }
    model.moveDraw(0);
  }

  /**
   * Tests whether moving a draw card when no draw
   * cards are left raises an IllegalStateException.
   */
  @Test(expected = IllegalStateException.class)
  public void testMoveDrawWithNoDrawCard() {
    KlondikeModel model = new LimitedDrawKlondike(1);
    model.startGame(model.getDeck(), false, 9, 3);
    for (int i = 0; i < 14; i++) {
      model.discardDraw();
    }
    model.moveDraw(0);
  }

  /**
   * Tests whether moving a draw card to the foundation when no
   * draw cards are left raises an IllegalStateException.
   */
  @Test(expected = IllegalStateException.class)
  public void testMoveDrawToFoundationWithNoDrawCard() {
    KlondikeModel model = new LimitedDrawKlondike(1);
    model.startGame(model.getDeck(), false, 9, 3);
    for (int i = 0; i < 14; i++) {
      model.discardDraw();
    }
    model.moveDrawToFoundation(0);
  }

  /**
   * Tests whether starting a LimitedDrawKlondike game with an invalid
   * number of piles raises an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testLimitedInvalidStartGameInvalidNumPiles() {
    KlondikeModel model = new LimitedDrawKlondike(1);
    model.startGame(model.getDeck(), false, 10, 3);
  }

  /**
   * Tests whether starting a LimitedDrawKlondike game with an invalid number of
   * draw cards raises an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testLimitedInvalidStartGameInvalidNumDraw() {
    KlondikeModel model = new LimitedDrawKlondike(1);
    model.startGame(model.getDeck(), false, 5, -6);
  }

  /**
   * Tests whether creating a LimitedDrawKlondike with a negative
   * number of times to redraw allowed raises an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testLimitedInvalidNumTimesRedrawAllowed() {
    new LimitedDrawKlondike(-5);
  }

  /**
   * Tests whether a newly started LimitedDrawKlondike game is not marked as over.
   */
  @Test
  public void testLimitedIsGameOverInitial() {
    KlondikeModel model = new LimitedDrawKlondike(3);
    model.startGame(model.getDeck(), false, 9, 3);
    Assert.assertFalse(model.isGameOver());
  }

  /**
   * Tests whether a LimitedDrawKlondike game is marked as
   * over when all cards are in the foundation.
   */
  @Test
  public void testLimitedIsGameOverAllCardsInFoundation() {
    KlondikeModel model = new LimitedDrawKlondike(3);
    List<Card> deck = model.getDeck().subList(0, 4);
    model.startGame(deck, false, 1, 3);
    model.moveToFoundation(0, 0);
    model.moveDrawToFoundation(1);
    model.moveDrawToFoundation(2);
    model.moveDrawToFoundation(3);
    Assert.assertTrue(model.isGameOver());
  }

  /**
   * Tests whether a LimitedDrawKlondike game is marked as over
   * when the cards needed to finish the game are discarded.
   */
  @Test
  public void testLimitedIsGameOverByGettingRidOfCardsNeededToFinish() {
    KlondikeModel model = new LimitedDrawKlondike(0);
    List<Card> deck = model.getDeck().subList(0, 4);
    model.startGame(deck, false, 1, 3);
    model.moveToFoundation(0, 0);
    model.discardDraw();
    model.discardDraw();
    model.discardDraw();
    Assert.assertTrue(model.isGameOver());
  }

}
