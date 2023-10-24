package cs3500.klondike.model.hw04;

import cs3500.klondike.model.hw02.Card;

import java.util.HashMap;
import java.util.List;

/**
 * The model of the LimitedKlondike. This game follows normal cascade rules of solitaire including
 * black and red alternating suits, however, it has a finite amount of redraws allowed
 * specified in the constructor.
 */
public class LimitedDrawKlondike extends SupremeKlondike {
  int numTimesRedrawAllowed;
  HashMap<Card, Integer> mapDraw;

  /**
   * Constructor for limited draw klondike.
   *
   * @param numTimesRedrawAllowed The allotted amount of redraws.
   */
  public LimitedDrawKlondike(int numTimesRedrawAllowed) {
    super.init();
    if (numTimesRedrawAllowed < 0) {
      throw new IllegalArgumentException("Redraw tries must be above 0");
    }
    this.numTimesRedrawAllowed = numTimesRedrawAllowed;
    this.mapDraw = new HashMap<>();
  }

  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numPiles, int numDraw)
          throws IllegalArgumentException {
    super.startGame(deck, shuffle, numPiles, numDraw);
    for (Card card : drawList) {
      this.mapDraw.put(card, numTimesRedrawAllowed);
    }
  }

  @Override
  public void discardDraw() throws IllegalStateException {
    if (!this.isGameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    if (!drawList.isEmpty()) {
      Card key = drawList.get(0);
      int val = mapDraw.get(key);
      if (val > 0) {
        mapDraw.replace(key, val, val - 1);
        super.discardDraw();
      } else if (val == 0) {
        mapDraw.replace(key, val, val - 1);
        this.drawList.remove(0);
      } else {
        throw new IllegalStateException("No card to discard");
      }
    } else {
      throw new IllegalStateException("Draw pile is empty");
    }
  }

}
