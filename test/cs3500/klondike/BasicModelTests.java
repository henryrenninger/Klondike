package cs3500.klondike;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.FaceValue;
import cs3500.klondike.model.hw02.KlondikeCard;
import cs3500.klondike.model.hw02.Suit;

/**
 * Tests for BasicKlondike Class.
 */
public class BasicModelTests {
  private BasicKlondike decky;

  /**
   * Initializes a new BasicKlondike.
   */
  @Before
  public void initData() {
    this.decky = new BasicKlondike();
  }


  /**
   * Test case for checking the validity of a standard 52-card deck.
   * It verifies if the deck is considered valid.
   */
  @Test
  public void testValidDeckWithStandard52CardDeck() {
    this.initData();
    List<Card> deck = decky.getDeck();
    Assert.assertTrue(decky.validDeck(deck));
  }

  /**
   * Test case for checking the validity of a deck with two runs of the same suit.
   * It verifies if the deck is considered valid.
   */
  @Test
  public void testValidDeckWithTwoRunsOfSameSuit() {
    this.initData();
    List<Card> deck = new ArrayList<>(Arrays.asList(new KlondikeCard(FaceValue.ACE,
                    Suit.DIAMOND), new KlondikeCard(FaceValue.TWO, Suit.DIAMOND),
            new KlondikeCard(FaceValue.THREE, Suit.DIAMOND),
            new KlondikeCard(FaceValue.FOUR, Suit.DIAMOND),
            new KlondikeCard(FaceValue.ACE, Suit.DIAMOND),
            new KlondikeCard(FaceValue.TWO, Suit.DIAMOND),
            new KlondikeCard(FaceValue.THREE, Suit.DIAMOND),
            new KlondikeCard(FaceValue.FOUR, Suit.DIAMOND)));
    Assert.assertTrue(decky.validDeck(deck));
  }

  /**
   * Test case for checking the validity of a deck with two runs of different lengths.
   * It expects an IllegalArgumentException to be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testValidDeckWithTwoRunsOfDifferentLength() {
    this.initData();
    List<Card> deck = new ArrayList<>(Arrays.asList(new KlondikeCard(FaceValue.ACE,
                    Suit.DIAMOND), new KlondikeCard(FaceValue.TWO, Suit.DIAMOND),
            new KlondikeCard(FaceValue.THREE, Suit.DIAMOND),
            new KlondikeCard(FaceValue.FOUR, Suit.DIAMOND),
            new KlondikeCard(FaceValue.ACE, Suit.DIAMOND),
            new KlondikeCard(FaceValue.TWO, Suit.DIAMOND),
            new KlondikeCard(FaceValue.THREE, Suit.DIAMOND),
            new KlondikeCard(FaceValue.FOUR, Suit.DIAMOND),
            new KlondikeCard(FaceValue.FIVE, Suit.DIAMOND)));
    decky.validDeck(deck);
  }

  /**
   * Test case for checking the validity of a deck with incorrect sequencing.
   * It expects an IllegalArgumentException to be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testValidWithIncorrectSequencing() {
    this.initData();
    List<Card> deck = new ArrayList<>(Arrays.asList(new KlondikeCard(FaceValue.ACE,
                    Suit.DIAMOND), new KlondikeCard(FaceValue.TWO, Suit.DIAMOND),
            new KlondikeCard(FaceValue.THREE, Suit.DIAMOND),
            new KlondikeCard(FaceValue.FOUR, Suit.DIAMOND),
            new KlondikeCard(FaceValue.SIX, Suit.DIAMOND)));
    decky.validDeck(deck);
  }

  /**
   * Test case for checking the validity of a deck with correct sequencing but no Ace cards.
   * It expects an IllegalArgumentException to be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testValidWithCorrectSequencingButNoAce() {
    this.initData();
    List<Card> deck = new ArrayList<>(Arrays.asList(new KlondikeCard(FaceValue.TWO,
                    Suit.DIAMOND), new KlondikeCard(FaceValue.THREE, Suit.DIAMOND),
            new KlondikeCard(FaceValue.FOUR, Suit.DIAMOND),
            new KlondikeCard(FaceValue.FIVE, Suit.DIAMOND)));
    decky.validDeck(deck);
  }

  /**
   * Test case for checking the validity of a null deck.
   * It expects an IllegalArgumentException to be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testValidWithNullDeck() {
    this.initData();
    List<Card> deck = null;
    decky.validDeck(deck);
  }

  /**
   * Test case for checking the legality of cascade
   * piles in a standard deck with legal cascades.
   * It verifies if the cascades are considered legal.
   */
  @Test
  public void testLegalCascadeStandardDeckLegalCascades() {
    this.initData();
    List<Card> deck = decky.getDeck();
    Assert.assertTrue(decky.legalCascade(deck, 9));
  }

  /**
   * Test case for checking the legality of cascade
   * piles in a standard deck with illegal cascades.
   * It verifies if the cascades are considered illegal.
   */
  @Test
  public void testLegalCascadeStandardDeckIllegalCascades() {
    this.initData();
    List<Card> deck = decky.getDeck();
    Assert.assertFalse(decky.legalCascade(deck, 10));
  }

  /**
   * Test case for indirectly testing the initialization
   * of foundation piles through the startGame method.
   * It checks if the number of foundation piles is correctly initialized.
   */
  @Test
  public void testInitializeFoundation() {
    this.initData();
    List<Card> deck = decky.getDeck();
    decky.startGame(deck, false, 7, 3);
    Assert.assertEquals(4, decky.getNumFoundations());
  }

  /**
   * Test case for indirectly testing the initialization
   * of foundation piles with an invalid pile number.
   * It expects an IllegalArgumentException to be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInitializeFoundationWithInvalidPileNumber() {
    this.initData();
    List<Card> deck = decky.getDeck();
    decky.startGame(deck, false, 10, 3);
  }

  /**
   * Test case for indirectly testing the initialization
   * of draw and cascade piles through the startGame method.
   * It checks if the number of cascade piles and draw piles is correctly initialized.
   */
  @Test
  public void testInitializeDrawAndCascade() {
    this.initData();
    List<Card> deck = decky.getDeck();
    decky.startGame(deck, false, 7, 3);
    Assert.assertEquals(7, decky.getNumPiles());
    Assert.assertEquals(3, decky.getNumDraw());
  }

  /**
   * Test case for indirectly testing the initialization
   * of draw and cascade piles with an invalid pile number.
   * It expects an IllegalArgumentException to be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInitializeDrawAndCascadeWithInvalidPileNumber() {
    this.initData();
    List<Card> deck = decky.getDeck();
    decky.startGame(deck, false, 10, 3);
  }

  /**
   * Test case for verifying the number of rows in the game layout.
   * It checks if the number of rows matches the expected value.
   */
  @Test
  public void testNumRows() {
    this.initData();
    List<Card> deck = decky.getDeck();
    decky.startGame(deck, false, 7, 3);
    Assert.assertEquals(7, decky.getNumRows());
  }

  /**
   * Test case for verifying the number of draw piles in the game layout.
   * It checks if the number of draw piles matches the expected value.
   */
  @Test
  public void testNumDraw() {
    this.initData();
    List<Card> deck = decky.getDeck();
    decky.startGame(deck, false, 7, 3);
    Assert.assertEquals(3, decky.getNumDraw());
  }

  /**
   * Test case for calculating the score with empty piles.
   * It checks if the score is zero when all piles are empty.
   */
  @Test
  public void testGetScoreEmptyPiles() {
    this.initData();
    List<Card> deck = decky.getDeck();
    decky.startGame(deck, false, 7, 3);
    Assert.assertEquals(0, decky.getScore());
  }

  /**
   * Test case for calculating the score with one Ace card in the piles.
   * It checks if the score is correctly updated when an Ace is moved to the foundation.
   */
  @Test
  public void testGetScoreOneAceInPiles() {
    this.initData();
    List<Card> deck = decky.getDeck();
    decky.startGame(deck, false, 7, 3);
    decky.moveToFoundation(0, 0);
    Assert.assertEquals(1, decky.getScore());
  }

  /**
   * Test case for getting the height of a cascade pile.
   * It checks if the height of a specified cascade pile matches the expected value.
   */
  @Test
  public void testPileHeight() {
    this.initData();
    List<Card> deck = decky.getDeck();
    decky.startGame(deck, false, 7, 3);
    Assert.assertEquals(7, decky.getPileHeight(6));
  }

  /**
   * Test case for getting the height of a cascade pile that is out of bounds.
   * It expects an IllegalArgumentException to be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPileHeightOutOfBounds() {
    this.initData();
    List<Card> deck = decky.getDeck();
    decky.startGame(deck, false, 7, 3);
    decky.getPileHeight(10);
  }

  /**
   * Test case for checking if a card is visible in the game layout.
   * It verifies if a card at a specified location is visible.
   */
  @Test
  public void testVisibleCardValid() {
    this.initData();
    List<Card> deck = decky.getDeck();
    decky.startGame(deck, false, 7, 3);
    Assert.assertTrue(decky.isCardVisible(6, 6));
  }

  /**
   * Test case for checking if a card is not visible in the game layout.
   * It verifies if a card at a specified location is not visible.
   */
  @Test
  public void testVisibleNonVisibleCard() {
    this.initData();
    List<Card> deck = decky.getDeck();
    decky.startGame(deck, false, 7, 3);
    Assert.assertFalse(decky.isCardVisible(6, 5));
  }

  /**
   * Test case for checking if a card is not visible due to an out-of-bounds card number.
   * It verifies if an out-of-bounds card number returns false for visibility.
   */
  @Test
  public void testVisibleOutOfBoundsCardNumber() {
    this.initData();
    List<Card> deck = decky.getDeck();
    decky.startGame(deck, false, 7, 3);
    Assert.assertFalse(decky.isCardVisible(6, 7));
  }

  /**
   * Test case for checking if a card is not visible due to an out-of-bounds pile number.
   * It expects an IllegalArgumentException to be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testVisibleOutOfBoundsPileNumber() {
    this.initData();
    List<Card> deck = decky.getDeck();
    decky.startGame(deck, false, 7, 3);
    decky.isCardVisible(7, 6);
  }

  /**
   * Test case for checking if a card is not visible when the game has not started.
   * It expects an IllegalStateException to be thrown.
   */
  @Test(expected = IllegalStateException.class)
  public void testVisibleGameHasNotStarted() {
    this.initData();
    decky.isCardVisible(0, 0);
  }

  /**
   * Test case for getting a card from a cascade pile at a valid location.
   * It checks if the card retrieved matches the expected card.
   */
  @Test
  public void testGetCardAtCascadePileValid() {
    this.initData();
    List<Card> deck = decky.getDeck();
    decky.startGame(deck, false, 7, 3);
    Card expected = new KlondikeCard(FaceValue.ACE, Suit.CLUB);
    Assert.assertEquals(expected, decky.getCardAt(0, 0));
  }

  /**
   * Test case for getting a card from a cascade pile where no card is present.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtCascadePileNoCard() {
    this.initData();
    List<Card> deck = decky.getDeck();
    decky.startGame(deck, false, 7, 3);
    decky.getCardAt(0, 1);
  }

  /**
   * Test case for getting a card from a cascade pile that is hidden (not visible).
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtCascadePileHidden() {
    this.initData();
    List<Card> deck = decky.getDeck();
    decky.startGame(deck, false, 7, 3);
    decky.getCardAt(1, 0);
  }

  /**
   * Test case for getting a card from a foundation pile at a valid location.
   * It checks if the card retrieved matches the expected card.
   */
  @Test
  public void testGetCardAtFoundationPileValid() {
    this.initData();
    List<Card> deck = decky.getDeck();
    decky.startGame(deck, false, 7, 3);
    decky.moveToFoundation(0, 0);
    Card expected = new KlondikeCard(FaceValue.ACE, Suit.CLUB);
    Assert.assertEquals(expected, decky.getCardAt(0));
  }

  /**
   * Test case for getting a card from an empty foundation pile.
   * It expects an IllegalStateException to be thrown.
   */
  @Test(expected = IllegalStateException.class)
  public void testGetCardAtFoundationPileEmpty() {
    this.initData();
    List<Card> deck = decky.getDeck();
    decky.startGame(deck, false, 7, 3);
    decky.moveToFoundation(0, 0);
    decky.moveToFoundation(0, 1);
  }

  /**
   * Test case for getting the number of foundation piles in the game layout.
   * It checks if the number of foundation piles matches the expected value.
   */
  @Test
  public void testGetNumFoundations() {
    this.initData();
    List<Card> deck = decky.getDeck();
    decky.startGame(deck, false, 9, 3);
    Assert.assertEquals(4, decky.getNumFoundations());
  }

  /**
   * Test case for converting a KlondikeCard to its string representation.
   * It checks if the toString method of KlondikeCard returns the expected string.
   */
  @Test
  public void testToStringKlondikeCard() {
    Card card = new KlondikeCard(FaceValue.QUEEN, Suit.SPADE);
    Assert.assertEquals("Q♠", card.toString());
  }

  /**
   * Test case for comparing two KlondikeCard objects for equality.
   * It checks if two KlondikeCard objects with the
   * same face value and suit are considered equal.
   */
  @Test
  public void testEqualsKlondikeCard() {
    Card card1 = new KlondikeCard(FaceValue.QUEEN, Suit.SPADE);
    Card card2 = new KlondikeCard(FaceValue.QUEEN, Suit.SPADE);
    Assert.assertTrue(card1.equals(card2));
  }

  /**
   * Test case for comparing two KlondikeCard objects for inequality.
   * It checks if two KlondikeCard objects with different suits are considered not equal.
   */
  @Test
  public void testNotEqualsKlondikeCard() {
    Card card1 = new KlondikeCard(FaceValue.QUEEN, Suit.SPADE);
    Card card2 = new KlondikeCard(FaceValue.QUEEN, Suit.CLUB);
    Assert.assertFalse(card1.equals(card2));
  }

  /**
   * Test case for checking the hashCode of two equal KlondikeCard objects.
   * It verifies if the hashCode of two equal KlondikeCard objects is the same.
   */
  @Test
  public void testHashCodeKlondikeCard() {
    Card card1 = new KlondikeCard(FaceValue.QUEEN, Suit.SPADE);
    Card card2 = new KlondikeCard(FaceValue.QUEEN, Suit.SPADE);
    Assert.assertEquals(card1.hashCode(), card2.hashCode());
  }

  /**
   * Test case for checking the hashCode of two not equal KlondikeCard objects.
   * It verifies if the hashCode of two not equal KlondikeCard objects is different.
   */
  @Test
  public void testNotEqualHashCodeKlondikeCard() {
    Card card1 = new KlondikeCard(FaceValue.QUEEN, Suit.SPADE);
    Card card2 = new KlondikeCard(FaceValue.QUEEN, Suit.CLUB);
    Assert.assertNotEquals(card1.hashCode(), card2.hashCode());
  }

  /**
   * Test case for getting the face value of a KlondikeCard.
   * It checks if the correct face value is returned.
   */
  @Test
  public void testGetFaceValueKlondikeCard() {
    Card card = new KlondikeCard(FaceValue.QUEEN, Suit.SPADE);
    Assert.assertEquals(FaceValue.QUEEN, card.getFaceValue());
  }

  /**
   * Test case for getting the suit of a KlondikeCard.
   * It checks if the correct suit is returned.
   */
  @Test
  public void testGetSuitValueKlondikeCard() {
    Card card = new KlondikeCard(FaceValue.QUEEN, Suit.SPADE);
    Assert.assertEquals(Suit.SPADE, card.getSuit());
  }

  /**
   * Test case for getting the visibility of a KlondikeCard.
   * It checks if the card is initially visible (true).
   */
  @Test
  public void testGetVisibilityKlondikeCard() {
    Card card = new KlondikeCard(FaceValue.QUEEN, Suit.SPADE);
    Assert.assertTrue(card.getVisibility());
  }

  /**
   * Test case for setting the visibility of a KlondikeCard to false.
   * It checks if the card's visibility can be changed to false.
   */
  @Test
  public void testGetVisibilityFalseWithSetVisibilityKlondikeCard() {
    Card card = new KlondikeCard(FaceValue.QUEEN, Suit.SPADE);
    card.setVisibility(false);
    Assert.assertFalse(card.getVisibility());
  }

}


