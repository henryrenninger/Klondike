package cs3500.klondike;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw04.WhiteheadKlondike;

/**
 * Tests for WhiteheadKlondike class.
 */
public class WhiteheadModelTests {

  /**
   * Tests whether starting a WhiteheadKlondike game with an
   * invalid number of piles raises an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testWhiteheadInvalidStartGameInvalidNumPiles() {
    KlondikeModel model = new WhiteheadKlondike();
    model.startGame(model.getDeck(), false, 10, 3);
  }

  /**
   * Tests whether starting a WhiteheadKlondike game with an
   * invalid number of draw cards raises an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testWhiteheadInvalidStartGameInvalidNumDraw() {
    KlondikeModel model = new WhiteheadKlondike();
    model.startGame(model.getDeck(), false, 10, 3);
  }

  /**
   * Tests whether a newly started WhiteheadKlondike game is not marked as over.
   */
  @Test
  public void testWhiteheadIsGameOverInitial() {
    KlondikeModel model = new WhiteheadKlondike();
    model.startGame(model.getDeck(), false, 9, 3);
    Assert.assertFalse(model.isGameOver());
  }

  /**
   * Tests whether a WhiteheadKlondike game is marked as
   * over when all cards are in the foundation.
   */
  @Test
  public void testWhiteheadIsGameOverAllCardsInFoundation() {
    KlondikeModel model = new WhiteheadKlondike();
    List<Card> deck = model.getDeck().subList(0, 4);
    model.startGame(deck, false, 1, 3);
    model.moveToFoundation(0, 0);
    model.moveDrawToFoundation(1);
    model.moveDrawToFoundation(2);
    model.moveDrawToFoundation(3);
    Assert.assertTrue(model.isGameOver());
  }

}
