package cs3500.klondike.model.hw04;

import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.KlondikeModel;

/**
 * A utility class for creating instances of different Klondike game types.
 */
public class KlondikeCreator {

  /**
   * Represents different types of Klondike games.
   */
  public enum GameType {
    BASIC, LIMITED, WHITEHEAD
  }

  /**
   * Creates and returns an instance of a Klondike game based on the provided game type.
   *
   * @param gameType The type of Klondike game to create.
   * @return An instance of the specified Klondike game type.
   * @throws IllegalArgumentException if an invalid game type is provided.
   */
  public static KlondikeModel create(GameType gameType) throws IllegalArgumentException {
    switch (gameType) {
      case BASIC:
        return new BasicKlondike();
      case LIMITED:
        return new LimitedDrawKlondike(2);
      case WHITEHEAD:
        return new WhiteheadKlondike();
      default:
        throw new IllegalArgumentException("Invalid GameType");
    }
  }


  /**
   * Creates and returns an instance of a limited draw Klondike game
   * with a specified number of redraws allowed.
   *
   * @param redraws The number of redraws allowed in the limited draw game.
   * @return An instance of the limited draw Klondike game with the specified number of redraws.
   */
  public static KlondikeModel createLimitedSpec(int redraws) {
    return new LimitedDrawKlondike(redraws);
  }

}
