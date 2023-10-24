package cs3500.klondike;

import java.io.InputStreamReader;

import cs3500.klondike.controller.KlondikeController;
import cs3500.klondike.controller.KlondikeTextualController;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw04.KlondikeCreator;

/**
 * The main class for the Klondike card game, which allows you to start different variants
 * of the game and interact with them through the command line.
 */
public final class Klondike {

  /**
   * The main entry point for the Klondike game.
   *
   * @param args Command-line arguments specifying the game type and optional parameters.
   * @throws IllegalArgumentException if there is an issue with the provided
   *                                  arguments or game type.
   */
  public static void main(String[] args) {
    KlondikeModel game;
    String userInteraction;
    try {
      userInteraction = args[0];
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Must have an input in main");
    }
    switch (userInteraction.toLowerCase()) {
      case "basic":
        game = KlondikeCreator.create(KlondikeCreator.GameType.BASIC);
        start(game, args, 1);
        break;
      case "limited":
        try {
          int r = Integer.parseInt(args[1]);
          game = KlondikeCreator.createLimitedSpec(r);
          start(game, args, 2);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
          game = KlondikeCreator.create(KlondikeCreator.GameType.LIMITED);
          start(game, args, 1);
        }
        break;
      case "whitehead":
        game = KlondikeCreator.create(KlondikeCreator.GameType.WHITEHEAD);
        start(game, args, 1);
        break;
      default:
        throw new IllegalArgumentException("Invalid game type entry");
    }
  }

  /**
   * Starts the Klondike game with the given parameters.
   *
   * @param game      The KlondikeModel representing the game type.
   * @param args      Command-line arguments for customizing the game.
   * @param argsIndex The index in the arguments array where customization parameters start.
   */
  private static void start(KlondikeModel game, String[] args, int argsIndex) {
    Readable rd = new InputStreamReader(System.in);
    Appendable ap = System.out;
    KlondikeController controller = new KlondikeTextualController(rd, ap);
    try {
      int p = Integer.parseInt(args[argsIndex]);
      try {
        int d = Integer.parseInt(args[argsIndex + 1]);
        controller.playGame(game, game.getDeck(), true, p, d);
      } catch (NumberFormatException | ArrayIndexOutOfBoundsException | IllegalStateException e) {
        controller.playGame(game, game.getDeck(), false, p, 3);
      }
    } catch (NumberFormatException | ArrayIndexOutOfBoundsException | IllegalStateException e) {
      controller.playGame(game, game.getDeck(), true, 7, 3);
    }
  }

}