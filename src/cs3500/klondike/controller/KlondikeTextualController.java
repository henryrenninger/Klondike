package cs3500.klondike.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.view.KlondikeTextualView;
import cs3500.klondike.view.TextualView;

/**
 * Class for the controller of this klondike game. Uses a readable and an appendable
 * that allow the game to keep track of inputs and outputs as well as detect what is a valid
 * input and output.
 */
public class KlondikeTextualController implements KlondikeController {
  private Readable r;
  private Appendable a;

  /**
   * Controller constructor.
   * @param r The readable StringReader input.
   * @param a The appendable StringBuilder output.
   */
  public KlondikeTextualController(Readable r, Appendable a) {
    try {
      this.r = Objects.requireNonNull(r);
      this.a = Objects.requireNonNull(a);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Readable and appendable must not be null");
    }
  }

  /**
   * The "go" method of this program. Allows the user to input specific arguments that will be
   * passed to the model's start game method. Seeks to continuously play through a while-loop
   * until the game is over and the loop is broken.
   *
   * @param model The game of solitaire to be played.
   * @param deck The deck of cards to be used.
   * @param shuffle Whether to shuffle the deck or not.
   * @param numPiles How many piles should be in the initial deal.
   * @param numDraw How many draw cards should be visible.
   */
  @Override
  public void playGame(KlondikeModel model, List<Card> deck,
                       boolean shuffle, int numPiles, int numDraw) {
    Scanner sc = new Scanner(r);
    boolean gameOver = false;
    try {
      Objects.requireNonNull(model);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Model cannot be empty");
    }
    try {
      model.startGame(deck, shuffle, numPiles, numDraw);
    } catch (Exception e) {
      throw new IllegalStateException("Model must be correct");
    }
    TextualView view = new KlondikeTextualView(model, a);
    this.render(view);

    while (!gameOver) {
      String userInteraction;
      try {
        userInteraction = sc.next();
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("No more elements");
      }
      if (garbageInput(userInteraction)) {
        writeMessage("Invalid move. Play again. Garbage input.\n");
        this.render(view);
      }
      switch (userInteraction) {
        case "mpp":
          try {
            int from = sc.nextInt();
            int cardCount = sc.nextInt();
            int to = sc.nextInt();
            model.movePile(from - 1, cardCount, to - 1);
            this.render(view);
            gameOver = model.isGameOver();
          } catch (Exception e) {
            writeMessage("Invalid move. Play again. " + e.getMessage() + "\n");
            this.render(view);
          }
          break;
        case "md":
          try {
            int to = sc.nextInt();
            model.moveDraw(to - 1);
            this.render(view);
            gameOver = model.isGameOver();
          } catch (Exception e) {
            writeMessage("Invalid move. Play again. " + e.getMessage() + "\n");
            this.render(view);
          }
          break;
        case "mpf":
          try {
            int from = sc.nextInt();
            int to = sc.nextInt();
            model.moveToFoundation(from - 1, to - 1);
            this.render(view);
            gameOver = model.isGameOver();
          } catch (Exception e) {
            writeMessage("Invalid move. Play again. " + e.getMessage() + "\n");
            this.render(view);
          }
          break;
        case "mdf":
          try {
            int to = sc.nextInt();
            model.moveDrawToFoundation(to - 1);
            this.render(view);
            gameOver = model.isGameOver();
          } catch (Exception e) {
            writeMessage("Invalid move. Play again. " + e.getMessage() + "\n");
            this.render(view);
          }
          break;
        case "dd":
          try {
            model.discardDraw();
            this.render(view);
          } catch (Exception e) {
            writeMessage("Invalid move. Play again. " + e.getMessage() + "\n");
            this.render(view);
          }
          break;
        case "Q":
        case "q":
          writeMessage("Game quit!\n"
                  + "State of game when quit:\n");
          this.render(view);
          writeMessage("Score: " + model.getScore());
          gameOver = true;
          break;
        default:
          break;
      }
    }
  }

  /**
   * Helper method for garbage inputs.
   *
   * @param str The string command being tested.
   * @return False if inputs are valid, true if they are garbage.
   */
  private boolean garbageInput(String str) {
    List<String> validStrs = new ArrayList<>(Arrays.asList("mpp",
            "md", "mdf", "mpf", "q", "Q", "dd"));
    for (String s : validStrs) {
      if (s.equals(str)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Try catch message writer that tries to append whatever output to the StringBuilder.
   *
   * @param message The string being appended.
   * @throws IllegalStateException If the string cannot be appended.
   */
  private void writeMessage(String message) throws IllegalStateException {
    try {
      a.append(message);
    }
    catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  /**
   * Calls on the view's render method while also checking for exceptions.
   *
   * @param view The given view that needs to be rendered.
   */
  private void render(TextualView view) {
    try {
      view.render();
    } catch (IOException e) {
      throw new IllegalStateException("Cannot be rendered");
    }
  }

}
