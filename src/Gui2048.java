import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;
import java.io.*;


public class Gui2048 extends Application {
  private static final int PADDING = 10;
  private static final int TILE_GAP = 2;
  private static final int WIDTH = 4;
  private static final int HEIGHT = 4;

  private GameBoard board;
  private KeyHandler keyHandler;
  private GridPane pane;

  private static Text title;
  private static Text score;

  private Color replaceColor(int value) {
    Color fillColor = null;

    if (value == 2) {
      fillColor = Color.LIGHTSLATEGRAY;
    }
    if (value == 4) {
      fillColor = Color.LIGHTGRAY;
    }
    if (value == 8) {
      fillColor = Color.PEACHPUFF;
    }
    if (value == 16) {
      fillColor = Color.ORANGE;
    }
    if (value == 32) {
      fillColor = Color.ORANGERED;
    }
    if (value == 64) {
      fillColor = Color.RED;
    }
    if (value == 128) {
      fillColor = Color.LIGHTYELLOW;
    }
    if (value == 256) {
      fillColor = Color.YELLOW;
    }
    if (value == 512) {
      fillColor = Color.GOLD;
    }
    if (value == 1024) {
      fillColor = Color.GOLD.darker();
    }
    if (value == 2048) {
      fillColor = Color.GOLD;
    }
    return fillColor;
  }

  @Override
  public void start(Stage primaryStage) {

  }

  private class KeyHandler implements EventHandler<KeyEvent> {
    @Override
    public void handle(KeyEvent e) {

    }
  }


}
