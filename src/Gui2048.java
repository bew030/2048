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
  private static final Color BACKGROUND = Color.DARKGRAY;

  private static final int LOW_TEXT = 55;
  private static final int MID_TEXT = 45;
  private static final int HIGH_TEXT = 35;

  private GameBoard board;
  private KeyHandler keyHandler;
  private GridPane pane;
  private Rectangle[][] tiles;
  private Square[][] copySquares;
  private Text[][] textNums;


  private static Text title;
  private static Text score;


  private Color replaceColor(int value) {
    Color fillColor = null;

    if (value == 2) {
      fillColor = Color.LIGHTSLATEGRAY;
    }
    else if (value == 4) {
      fillColor = Color.LIGHTGRAY;
    }
    else if (value == 8) {
      fillColor = Color.PEACHPUFF;
    }
    else if (value == 16) {
      fillColor = Color.ORANGE;
    }
    else if (value == 32) {
      fillColor = Color.ORANGERED;
    }
    else if (value == 64) {
      fillColor = Color.RED;
    }
    else if (value == 128) {
      fillColor = Color.LIGHTYELLOW;
    }
    else if (value == 256) {
      fillColor = Color.YELLOW;
    }
    else if (value == 512) {
      fillColor = Color.GOLD;
    }
    else if (value == 1024) {
      fillColor = Color.GOLD.darker();
    }
    else if (value == 2048) {
      fillColor = Color.GOLD;
    }
    else {
      fillColor = BACKGROUND;
    }
    return fillColor;
  }

  //needs public for game board height and width
  private void copyBoard() {
    for (int i = 0; i < board.overallGameBoard.getWidth(); i++) {
      for (int j = 0; j < board.overallGameBoard.getHeight(); j++) {
        copySquares[i][j] = board.overallGameBoard[i][j];
      }
    }
  }

  @Override
  public void start(Stage primaryStage) {
    //create pane to hold visual representation of game
    pane = new GridPane();
    pane.setAlignment(Pos.CENTER);
    pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5))
    pane.setStyle("-fx-background-color: rgb(187, 173, 160)");
    //set spacing
    pane.setHgap(15);
    pane.setVgap(15);

    //initalize two arrays
    squares = copyBoard();
    textNums = new Text[WIDTH][HEIGHT + 1];

    //initialize game title and score headers
    Text title = new Text();
    title.setText("2048");
    title.setFont(Font.font("Times New Roman", 50));
    title.setFill(Color.BLACK);
    Text score = new Text();
    //score.setText("Score: " + board.getScore()); needs getScore method in GB class
    textNums[WIDTH - 1][0] = score;
    textNums[WIDTH - 1][0].setFont(Font.font("Times New Roman", 20));
    textNums[WIDTH - 1][0].setFill(Color.BLACK);

    pane.add(title, 0, 0);
    GridPane.setHalignment(title, HPos.LEFT);
    pane.add(textNums[WIDTH - 1][0], WIDTH - 1, 0);

    for (int x = 0; x < WIDTH; x++) {
      for (int y = 1; y < HEIGHT + 1; y++) {
        Rectangle newTile = new Rectangle();
        newTile.setWidth(100);
        newTile.setHeight(100);

        tiles[x][y] = newTile;
        tiles[x][y].setFill(copySquares[x][y].getValue());
        pane.add(tiles[x][y], x, y);

        Text tileText = new Text();
        tileText.setText(copySquares[x][y].toString());      
      }
    }


  }

  private class KeyHandler implements EventHandler<KeyEvent> {
    @Override
    public void handle(KeyEvent e) {

    }
  }


}
