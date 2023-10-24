package cs3500.klondike;

import org.junit.Assert;
import org.junit.Test;

import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw04.KlondikeCreator;
import cs3500.klondike.model.hw04.LimitedDrawKlondike;
import cs3500.klondike.model.hw04.WhiteheadKlondike;

/**
 * Tests for the creator class.
 */
public class CreatorTests {

  /**
   * Tests whether the KlondikeCreator correctly creates a BasicKlondike instance.
   */
  @Test
  public void testCreatorBasic() {
    KlondikeModel basic = KlondikeCreator.create(KlondikeCreator.GameType.BASIC);
    Assert.assertTrue(basic instanceof BasicKlondike);
  }

  /**
   * Tests whether the KlondikeCreator correctly creates a LimitedDrawKlondike instance.
   */
  @Test
  public void testCreatorLimited() {
    KlondikeModel limited = KlondikeCreator.create(KlondikeCreator.GameType.LIMITED);
    Assert.assertTrue(limited instanceof LimitedDrawKlondike);
  }

  /**
   * Tests whether the KlondikeCreator correctly creates a WhiteheadKlondike instance.
   */
  @Test
  public void testCreatorWhitehead() {
    KlondikeModel whitehead = KlondikeCreator.create(KlondikeCreator.GameType.WHITEHEAD);
    Assert.assertTrue(whitehead instanceof WhiteheadKlondike);
  }

  /**
   * Tests whether the KlondikeCreator correctly creates a LimitedDrawKlondike
   * instance with a valid entry for the number of redraws.
   */
  @Test
  public void testCreatorLimitedDrawSpecValidEntry() {
    KlondikeModel limited = KlondikeCreator.createLimitedSpec(5);
    Assert.assertTrue(limited instanceof LimitedDrawKlondike);
  }

  /**
   * Tests whether the KlondikeCreator correctly creates a LimitedDrawKlondike
   * instance with a valid entry for the number of redraws.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCreatorLimitedDrawSpecInvalidEntry() {
    KlondikeModel limited = KlondikeCreator.createLimitedSpec(-1);
    Assert.assertTrue(limited instanceof LimitedDrawKlondike);
  }

  /**
   * Tests whether attempting to discard more cards than allowed in a default
   * LimitedDrawKlondike game raises an IllegalStateException.
   */
  @Test(expected = IllegalStateException.class)
  public void testCreatorDefaultLimitedRedrawExhaustsCards() {
    KlondikeModel limited = KlondikeCreator.create(KlondikeCreator.GameType.LIMITED);
    limited.startGame(limited.getDeck(), false, 9,3);
    //with numTimesRedrawAllowed set to 2, the use should be allotted 21 discardDraws initially
    for (int i = 0; i < 22; i++) {
      limited.discardDraw();
    }
  }

  /**
   * Tests whether attempting to discard more cards than allowed in a specific
   * LimitedDrawKlondike game with redraws set to 1 raises an IllegalStateException.
   */
  @Test(expected = IllegalStateException.class)
  public void testCreatorSpecAt1LimitedRedrawExhaustsCards() {
    KlondikeModel limited = KlondikeCreator.createLimitedSpec(1);
    limited.startGame(limited.getDeck(), false, 9,3);
    //with numTimesRedrawAllowed set to 1, the use should be allotted 14 discardDraws initially
    for (int i = 0; i < 15; i++) {
      limited.discardDraw();
    }
  }

  /**
   * Tests whether invoking getScore() on a BasicKlondike instance
   * before starting the game raises an IllegalStateException.
   */
  @Test(expected = IllegalStateException.class)
  public void testCreatorBasicDoesntStartGame() {
    KlondikeModel basic = KlondikeCreator.create(KlondikeCreator.GameType.BASIC);
    basic.getScore();
  }

  /**
   * Tests whether invoking getScore() on a LimitedDrawKlondike instance
   * before starting the game raises an IllegalStateException.
   */
  @Test(expected = IllegalStateException.class)
  public void testCreatorLimitedDoesntStartGame() {
    KlondikeModel limited = KlondikeCreator.create(KlondikeCreator.GameType.LIMITED);
    limited.getScore();
  }

  /**
   * Tests whether invoking getScore() on a WhiteheadKlondike
   * instance before starting the game raises an IllegalStateException.
   */
  @Test(expected = IllegalStateException.class)
  public void testCreatorWhiteheadDoesntStartGame() {
    KlondikeModel whitehead = KlondikeCreator.create(KlondikeCreator.GameType.WHITEHEAD);
    whitehead.getScore();
  }

  /**
   * Tests whether invoking getScore() on a specific LimitedDrawKlondike
   * instance before starting the game raises an IllegalStateException.
   */
  @Test(expected = IllegalStateException.class)
  public void testCreatorLimitedSpecDoesntStartGame() {
    KlondikeModel limited = KlondikeCreator.createLimitedSpec(5);
    limited.getScore();
  }

  /**
   * Tests whether a newly created BasicKlondike instance is not marked as over.
   */
  @Test
  public void testCreatorBasicIsGameOver() {
    KlondikeModel basic = KlondikeCreator.create(KlondikeCreator.GameType.BASIC);
    basic.startGame(basic.getDeck(), false, 7, 3);
    Assert.assertFalse(basic.isGameOver());
  }

  /**
   *  Tests whether a newly created LimitedDrawKlondike instance is not marked as over.
   */
  @Test
  public void testCreatorLimitedIsGameOver() {
    KlondikeModel limited = KlondikeCreator.create(KlondikeCreator.GameType.LIMITED);
    limited.startGame(limited.getDeck(), false, 7, 3);
    Assert.assertFalse(limited.isGameOver());
  }

  /**
   * Tests whether a newly created WhiteheadKlondike instance is not marked as over.
   */
  @Test
  public void testCreatorWhiteheadIsGameOver() {
    KlondikeModel whitehead = KlondikeCreator.create(KlondikeCreator.GameType.WHITEHEAD);
    whitehead.startGame(whitehead.getDeck(), false, 7, 3);
    Assert.assertFalse(whitehead.isGameOver());
  }

  /**
   *  Tests whether a newly created specific LimitedDrawKlondike instance is not marked as over.
   */
  @Test
  public void testCreatorLimitedSpecIsGameOver() {
    KlondikeModel limited = KlondikeCreator.create(KlondikeCreator.GameType.LIMITED);
    limited.startGame(limited.getDeck(), false, 7, 3);
    Assert.assertFalse(limited.isGameOver());
  }

  /**
   * Tests whether passing garbage input to KlondikeCreator raises an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGarbageInput() {
    KlondikeCreator.create(KlondikeCreator.GameType.valueOf("garbage"));
  }

}
