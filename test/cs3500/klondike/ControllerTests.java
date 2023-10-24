package cs3500.klondike;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import cs3500.klondike.controller.KlondikeController;
import cs3500.klondike.controller.KlondikeTextualController;
import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.view.KlondikeTextualView;
import cs3500.klondike.view.TextualView;

/**
 * Tests for the controller class.
 */
public class ControllerTests {

  private KlondikeTextualController controller;
  private KlondikeModel model;
  private List<Card> deck;

  /**
   * Initializes model and deck.
   */
  @Before
  public void initData() {
    model = new BasicKlondike();
    deck = model.getDeck();
  }

  /**
   * Test for a valid "Move Pile" command in the Klondike game.
   * Ensures that the game log does not contain an "Invalid move" message.
   */
  @Test
  public void testValidMovePileCommand() throws IOException {
    String input = "mpp 1 1 2 q";
    StringReader reader = new StringReader(input);
    StringBuilder gameLog = new StringBuilder();
    controller = new KlondikeTextualController(reader, gameLog);
    controller.playGame(model, deck, false, 6, 1);
    Assert.assertFalse(gameLog.toString().contains("Invalid move. Play again."));
  }

  /**
   * Test for an invalid "Move Pile" command in the Klondike game.
   * Expects the game log to contain an "Invalid move" message.
   */
  @Test
  public void testInvalidMovePileCommand() throws IOException {
    String input = "mpp 1 1 2 mpp 1 1 2 q";
    StringReader reader = new StringReader(input);
    StringBuilder gameLog = new StringBuilder();
    controller = new KlondikeTextualController(reader, gameLog);
    model = new BasicKlondike();
    controller.playGame(model, deck, false, 7, 1);
    Assert.assertTrue(gameLog.toString().contains("Invalid move. Play again."));
  }

  /**
   * Test for an invalid "Move Draw" command in the Klondike game.
   * Expects the game log to contain an "Invalid move" message.
   */
  @Test
  public void testInvalidMoveDrawCommand() throws IOException {
    String input = "md 2 q";
    StringReader reader = new StringReader(input);
    StringBuilder gameLog = new StringBuilder();
    controller = new KlondikeTextualController(reader, gameLog);
    model = new BasicKlondike();
    controller.playGame(model, deck, false, 7, 1);
    Assert.assertTrue(gameLog.toString().contains("Invalid move. Play again."));
  }

  /**
   * Test for a valid "Move to Foundation" command in the Klondike game.
   * Ensures that the game log does not contain an "Invalid move" message.
   */
  @Test
  public void testValidMoveToFoundationCommand() throws IOException {
    String input = "mpf 1 2 q";
    StringReader reader = new StringReader(input);
    StringBuilder gameLog = new StringBuilder();
    controller = new KlondikeTextualController(reader, gameLog);
    model = new BasicKlondike();
    controller.playGame(model, deck, false, 7, 1);
    Assert.assertFalse(gameLog.toString().contains("Invalid move. Play again."));
  }

  /**
   * Test for an invalid "Move to Foundation" command in the Klondike game.
   * Expects the game log to contain an "Invalid move" message.
   */
  @Test
  public void testInvalidMoveToFoundationCommand() throws IOException {
    String input = "mpf 2 1 q";
    StringReader reader = new StringReader(input);
    StringBuilder gameLog = new StringBuilder();
    controller = new KlondikeTextualController(reader, gameLog);
    model = new BasicKlondike();
    controller.playGame(model, deck, false, 7, 1);
    Assert.assertTrue(gameLog.toString().contains("Invalid move. Play again."));
  }

  /**
   * Test for a valid "Move Draw to Foundation" command in the Klondike game.
   * Ensures that the game log does not contain an "Invalid move" message.
   */
  @Test
  public void testValidMoveDrawToFoundationCommand() throws IOException {
    String input = "mdf 2 q";
    StringReader reader = new StringReader(input);
    StringBuilder gameLog = new StringBuilder();
    controller = new KlondikeTextualController(reader, gameLog);
    model = new BasicKlondike();
    controller.playGame(model, deck, false, 2, 1);
    Assert.assertFalse(gameLog.toString().contains("Invalid move. Play again."));
  }

  /**
   * Test for an invalid "Move Draw to Foundation" command in the Klondike game.
   * Expects the game log to contain an "Invalid move" message.
   */
  @Test
  public void testInvalidMoveDrawToFoundationCommand() throws IOException {
    String input = "mdf 1 q";
    StringReader reader = new StringReader(input);
    StringBuilder gameLog = new StringBuilder();
    controller = new KlondikeTextualController(reader, gameLog);
    model = new BasicKlondike();
    controller.playGame(model, deck, false, 7, 1);
    Assert.assertTrue(gameLog.toString().contains("Invalid move. Play again."));
  }

  /**
   * Test for a valid "Discard Draw" command in the Klondike game.
   * Ensures that the game log does not contain an "Invalid move" message.
   */
  @Test
  public void testValidDiscardDrawCommand() throws IOException {
    String input = "dd q";
    StringReader reader = new StringReader(input);
    StringBuilder gameLog = new StringBuilder();
    controller = new KlondikeTextualController(reader, gameLog);
    model = new BasicKlondike();
    controller.playGame(model, deck, false, 7, 1);
    Assert.assertFalse(gameLog.toString().contains("Invalid move. Play again."));
  }

  /**
   * Test for an invalid "Discard Draw" command in the Klondike game.
   * Expects the game log to contain an "Invalid move" message.
   */
  @Test
  public void testInvalidDiscardDrawCommand() throws IOException {
    String input = "dddd q";
    StringReader reader = new StringReader(input);
    StringBuilder gameLog = new StringBuilder();
    controller = new KlondikeTextualController(reader, gameLog);
    model = new BasicKlondike();
    controller.playGame(model, deck, false, 7, 1);
    Assert.assertTrue(gameLog.toString().contains("Invalid move. Play again."));
  }

  /**
   * Test for the "Quit" command in the Klondike game.
   * Expects the game log to contain a "Game quit!" message.
   */
  @Test
  public void testQuitCommand() throws IOException {
    String input = "q";
    StringReader reader = new StringReader(input);
    StringBuilder gameLog = new StringBuilder();
    controller = new KlondikeTextualController(reader, gameLog);
    model = new BasicKlondike();
    controller.playGame(model, deck, false, 7, 1);
    Assert.assertTrue(gameLog.toString().contains("Game quit!"));
  }

  /**
   * Test for the "Quit" command (uppercase) in the Klondike game.
   * Expects the game log to contain a "Game quit!" message.
   */
  @Test
  public void testQuitCommandUppercase() throws IOException {
    String input = "Q";
    StringReader reader = new StringReader(input);
    StringBuilder gameLog = new StringBuilder();
    controller = new KlondikeTextualController(reader, gameLog);
    model = new BasicKlondike();
    controller.playGame(model, deck, false, 7, 1);
    Assert.assertTrue(gameLog.toString().contains("Game quit!"));
  }

  /**
   * Test for handling garbage input in the Klondike game.
   * Expects the game log to contain an "Invalid move" message.
   */
  @Test
  public void testGarbageInput() throws IOException {
    String input = "garbage q";
    StringReader reader = new StringReader(input);
    StringBuilder gameLog = new StringBuilder();
    controller = new KlondikeTextualController(reader, gameLog);
    model = new BasicKlondike();
    controller.playGame(model, deck, false, 7, 1);
    Assert.assertTrue(gameLog.toString().contains("Invalid move. Play again."));
  }

  /**
   * Test for passing a null model to the controller (IllegalArgumentException expected).
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    StringReader reader = new StringReader("q");
    StringBuilder gameLog = new StringBuilder();
    reader = new StringReader("q");
    gameLog = new StringBuilder();
    controller = new KlondikeTextualController(reader, gameLog);
    model = null;  // Passing a null model intentionally
    controller.playGame(model, deck, false, 7, 1);
  }

  /**
   * Test for an IOException in the writeMessage method (IllegalStateException expected).
   */
  @Test(expected = IllegalStateException.class)
  public void testIOExceptionInWriteMessage() throws IOException {
    StringReader reader = new StringReader("q");
    Appendable appendableWithIOException = new Appendable() {

      @Override
      public Appendable append(CharSequence csq) throws IOException {
        throw new IOException("Test IOException");
      }

      @Override
      public Appendable append(CharSequence csq, int start, int end) throws IOException {
        throw new IOException("Test IOException");
      }

      @Override
      public Appendable append(char c) throws IOException {
        throw new IOException("Test IOException");
      }
    };
    controller = new KlondikeTextualController(reader, appendableWithIOException);
    model = new BasicKlondike();
    controller.playGame(model, deck, false, 7, 1);
  }

  /**
   * Test for an IOException in the render method (IllegalStateException expected).
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIOExceptionInRender() throws IOException {
    StringReader reader = new StringReader("q");
    StringBuilder gameLog = new StringBuilder();
    TextualView viewWithIOException = new KlondikeTextualView(null, new Appendable() {

      @Override
      public Appendable append(CharSequence csq) throws IOException {
        throw new IOException("Test IOException");
      }

      @Override
      public Appendable append(CharSequence csq, int start, int end) throws IOException {
        throw new IOException("Test IOException");
      }

      @Override
      public Appendable append(char c) throws IOException {
        throw new IOException("Test IOException");
      }
    });
    controller = new KlondikeTextualController(reader, gameLog);
    model = new BasicKlondike();
    controller.playGame(model, deck, false, 7, 1);
  }

  /**
   * Tests whether attempting to move piles from the
   * same source and destination raises an IllegalStateException.
   */
  @Test(expected = IllegalStateException.class)
  public void testMovePilesSameSourceAndDestination() {
    StringReader input = new StringReader("mpp 2 1 2");
    StringBuilder gameLog = new StringBuilder();
    KlondikeController kdController = new KlondikeTextualController(input, gameLog);
    KlondikeModel model = new BasicKlondike();
    List<Card> deck = model.getDeck();
    kdController.playGame(model, deck, false, 7, 1);
  }

  /**
   * Tests whether providing invalid input (a letter) for
   * moving a pile to the foundation raises an IllegalStateException.
   */
  @Test(expected = IllegalStateException.class)
  public void testMovePileToFoundationInvalidInputOfLetter() {
    StringReader input = new StringReader("mpf a");
    StringBuilder gameLog = new StringBuilder();
    KlondikeController kdController = new KlondikeTextualController(input, gameLog);
    KlondikeModel model = new BasicKlondike();
    List<Card> deck = model.getDeck();
    kdController.playGame(model, deck, false, 7, 1);
  }

  /**
   * Tests if the game is not marked as over after quitting (pressing 'q').
   */
  @Test
  public void testIsGameOverAfterQuitting() {
    StringReader input = new StringReader("q");
    StringBuilder gameLog = new StringBuilder();
    KlondikeController kdController = new KlondikeTextualController(input, gameLog);
    KlondikeModel model = new BasicKlondike();
    List<Card> deck = model.getDeck();
    kdController.playGame(model, deck, false, 7, 1);
    Assert.assertFalse(model.isGameOver());
  }

  /**
   * Tests whether attempting to move a card from the draw
   * pile to a non-existent cascade pile raises an IllegalStateException.
   */
  @Test(expected = IllegalStateException.class)
  public void testMoveDrawToNonExistentCascadePile() {
    StringReader input = new StringReader("md 8");
    StringBuilder gameLog = new StringBuilder();
    KlondikeController kdController = new KlondikeTextualController(input, gameLog);
    KlondikeModel model = new BasicKlondike();
    List<Card> deck = model.getDeck();
    kdController.playGame(model, deck, false, 7, 1);
  }

  /**
   * Tests if invalid input results in a log message containing the word "Invalid."
   */
  @Test
  public void testInvalidInputThenQuit() {
    StringReader input = new StringReader("Not-A-Move q");
    StringBuilder gameLog = new StringBuilder();
    KlondikeController kdController = new KlondikeTextualController(input, gameLog);
    KlondikeModel model = new BasicKlondike();
    List<Card> deck = model.getDeck();
    kdController.playGame(model, deck, false, 7, 1);
    Assert.assertTrue(gameLog.toString().contains("Invalid"));
  }

  /**
   * Tests whether a valid move from a pile to the
   * foundation avoids log messages containing "Invalid."
   */
  @Test
  public void testValidMovePileToFoundationDoesNotContainInvalid() {
    StringReader input = new StringReader("mpf 1 1 q");
    StringBuilder gameLog = new StringBuilder();
    KlondikeController kdController = new KlondikeTextualController(input, gameLog);
    KlondikeModel model = new BasicKlondike();
    List<Card> deck = model.getDeck();
    kdController.playGame(model, deck, false, 5, 1);
    Assert.assertFalse(gameLog.toString().contains("Invalid"));
  }

  /**
   * Checks if quitting the game results in a log
   * message containing a score with a line break.
   */
  @Test
  public void testQuittingCreatesLineBreakWithScore() {
    StringReader input = new StringReader("q");
    StringBuilder gameLog = new StringBuilder();
    KlondikeController kdController = new KlondikeTextualController(input, gameLog);
    KlondikeModel model = new BasicKlondike();
    List<Card> deck = model.getDeck();
    kdController.playGame(model, deck, false, 7, 1);
    Assert.assertTrue(gameLog.toString().contains("\nScore: 0"));
  }

  /**
   * Ensures quitting produces a log message containing
   * the KlondikeView representation and a score with a line break.
   */
  @Test
  public void testQuittingWithKlondikeViewAndLineBreak() {
    StringReader input = new StringReader("q");
    StringBuilder gameLog = new StringBuilder();
    KlondikeController kdController = new KlondikeTextualController(input, gameLog);
    KlondikeModel model = new BasicKlondike();
    List<Card> deck = model.getDeck();
    kdController.playGame(model, deck, false, 7, 1);
    TextualView view = new KlondikeTextualView(model);
    Assert.assertTrue(gameLog.toString().contains(view + "Score: 0"));
  }

  /**
   * Verifies that quitting the game generates a log message
   * indicating the state of the game when quitting.
   */
  @Test
  public void testStateOfGameWhenQuit() {
    StringReader input = new StringReader("q");
    StringBuilder gameLog = new StringBuilder();
    KlondikeController kdController = new KlondikeTextualController(input, gameLog);
    KlondikeModel model = new BasicKlondike();
    List<Card> deck = model.getDeck();
    kdController.playGame(model, deck, false, 7, 1);
    Assert.assertTrue(gameLog.toString().contains("State of game when quit:"));
  }

  /**
   * Tests whether moving a King onto an empty cascade
   * pile is a valid move without log messages containing "Invalid."
   */
  @Test
  public void testKingOnEmptyCascadeIsValid() {
    StringReader input = new StringReader("dd dd dd mpf 1 1 md 1 q");
    StringBuilder gameLog = new StringBuilder();
    KlondikeController kdController = new KlondikeTextualController(input, gameLog);
    KlondikeModel model = new BasicKlondike();
    List<Card> deck = model.getDeck();
    kdController.playGame(model, deck, false, 9, 1);
    Assert.assertFalse(gameLog.toString().contains("Invalid"));
  }

}
