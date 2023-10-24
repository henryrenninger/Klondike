package cs3500.klondike.view;

import java.io.IOException;
import java.util.Objects;

import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.FaceValue;
import cs3500.klondike.model.hw02.KlondikeModel;

/**
 * A simple text-based rendering of the Klondike game.
 */
public class KlondikeTextualView implements TextualView {
  private final KlondikeModel model;
  private Appendable a;

  /**
   * Basic klondike text view that only initiates the model.
   *
   * @param model The current model to be viewed.
   */
  public KlondikeTextualView(KlondikeModel model) {
    try {
      this.model = Objects.requireNonNull(model);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Model and appendable cannot be null");
    }
  }

  /**
   * More advanced klondike text view that initiates the model and also the appendable
   * which is used as an output game log of the current state of the game.
   *
   * @param model The current model to be viewed.
   * @param a The StringBuilder appendable that logs the current view.
   */
  public KlondikeTextualView(KlondikeModel model, Appendable a) {
    try {
      this.model = Objects.requireNonNull(model);
      this.a = Objects.requireNonNull(a);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Model and appendable cannot be null");
    }
  }


  @Override
  public void render() throws IOException {
    try {
      a.append(toString());
    }
    catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  /**
   * String rendering of the view state.
   *
   * @return The string version of the view.
   */
  public String toString() {
    StringBuilder doc = new StringBuilder("Draw: ");

    for (int i = 0; i < this.model.getNumDraw(); i++) {
      doc.append(this.model.getDrawCards().get(i));
      if (i < this.model.getNumDraw() - 1) {
        doc.append(", ");
      }
    }

    doc.append("\nFoundation: ");

    for (int i = 0; i < this.model.getNumFoundations(); i++) {
      if (this.model.getCardAt(i) == null) {
        doc.append("<none>");
      } else {
        doc.append(this.model.getCardAt(i));
      }

      if (i < this.model.getNumFoundations() - 1) {
        doc.append(", ");
      }
    }

    doc.append("\n");

    for (int i = 0; i < this.model.getNumRows(); i++) {
      for (int j = 0; j < this.model.getNumPiles(); j++) {
        if (i > model.getPileHeight(j) - 1) {
          doc.append((i == 0 && model.getPileHeight(j) == 0) ? "  X" : "   ");
        } else {
          if (model.isCardVisible(j, i)) {
            Card card = model.getCardAt(j, i);
            String cardString = card.toString();
            doc.append((card.getFaceValue().equals(FaceValue.TEN)) ? cardString : " " + cardString);
          } else {
            doc.append("  ?");
          }
        }
      }
      doc.append("\n");
    }

    return doc.toString().trim() + "\n";
  }


}
