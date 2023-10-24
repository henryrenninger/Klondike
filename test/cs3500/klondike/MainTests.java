package cs3500.klondike;

import org.junit.Test;

/**
 * Tests for Klondike class main method.
 */
public class MainTests {

  /**
   * Tests whether running the Klondike class with garbage
   * input arguments raises an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGarbageInMain() {
    Klondike.main(new String[]{"basically"});
  }

  /**
   *  Tests whether running the Klondike class with a single string
   *  argument containing an invalid command raises an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSingleStringArgsInMain() {
    Klondike.main(new String[]{"limited -1"});
  }

  /**
   * Tests whether running the Klondike class with no
   * arguments raises an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNothingInMain() {
    Klondike.main(new String[]{});
  }

}
