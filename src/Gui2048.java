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
  private GridPane pane;
  private StackPane stack;
  private Scene scene;
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
    for (int i = 0; i < board.getWidth(); i++) {
      for (int j = 0; j < board.getHeight(); j++) {
        copySquares[i][j] = board.overallGameBoard[i][j];
      }
    }
  }

  private void updateFont() {
    for (int i = 0; i < textNums.length; i++) {
      for (int j = 0; j < textNums[i].length; j++) {
        if (board.overallGameBoard[i][j].getValue() < 128) {
          textNums[i][j].setFont((Font.font("Times New Roman",
                         FontWeight.BOLD, LOW_TEXT)));
        }
        else if (board.overallGameBoard[i][j].getValue() < 1024) {
          textNums[i][j].setFont((Font.font("Times New Roman",
                         FontWeight.BOLD, MID_TEXT)));
        }
        else {
          textNums[i][j].setFont((Font.font("Times New Roman",
                         FontWeight.BOLD, HIGH_TEXT)));
        }
      }
    }
  }

  private void clearGrid() {
    for (int i = 0; i < board.overallGameBoard.length; i++) {
      for (int j = 0; j < board.overallGameBoard[i].length; j++) {
        tiles[i][j].setFill(BACKGROUND);
      }
    }
  }

  private void colorBoard() {
    for (int x = 0; x < HEIGHT; x++) {
      for (int y = 0; y < WIDTH; y++) {
        Rectangle newTile = new Rectangle();
        newTile.setWidth(100);
        newTile.setHeight(100);

        tiles[x][y] = newTile;
        tiles[x][y].setFill(replaceColor(copySquares[x][y].getValue()));
        pane.add(tiles[x][y], x, y + 1);

        Text tileText = new Text();
        tileText.setText(copySquares[x][y].toString());
        textNums[x][y] = tileText;
      }
    }
    for (int i = 0; i < HEIGHT; i++) {
      for (int j = 0; j < WIDTH; j++) {
        if (copySquares[i][j].getValue() != 0) {
          pane.add(textNums[i][j], i, j + 1);
          GridPane.setHalignment(textNums[i][j], HPos.CENTER);
        }
      }
    }
  }


  private void update() {
    clearGrid();
    copyBoard();
    //update score
    score.setText("Score: " + board.getScore());
    score.setFont(Font.font("Times New Roman", 20));
    score.setFill(Color.BLACK);

    //recolor board
    colorBoard();
    updateFont();
  }

  @Override
  public void start(Stage primaryStage) {
    this.board = new GameBoard();
    //create pane to hold visual representation of game
    pane = new GridPane();
    pane.setAlignment(Pos.CENTER);
    pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
    pane.setStyle("-fx-background-color: rgb(187, 173, 160)");
    //set spacing
    pane.setHgap(15);
    pane.setVgap(15);


    copySquares = new Square[board.getWidth()][board.getHeight()];
    tiles = new Rectangle[HEIGHT][WIDTH];
    //deep copy Squares, create new array for text
    copyBoard();
    textNums = new Text[HEIGHT][WIDTH];

    //initialize game title and score headers
    title = new Text();
    title.setText("2048");
    title.setFont(Font.font("Times New Roman", 50));
    title.setFill(Color.BLACK);
    score = new Text();
    score.setText("Score: " + 0);
    score.setFont(Font.font("Times New Roman", 20));
    score.setFill(Color.BLACK);

    pane.add(title, 0, 0);
    GridPane.setHalignment(title, HPos.LEFT);
    pane.add(score, WIDTH - 1, 0);
    GridPane.setHalignment(score, HPos.RIGHT);
    colorBoard();
    updateFont();

    this.stack = new StackPane();
    stack.getChildren().addAll(pane);

    this.scene = new Scene(stack);
    scene.setOnKeyPressed(new KeyHandler());

    primaryStage.setTitle("2048");
    primaryStage.setScene(scene);
    primaryStage.show();
    }


  private class KeyHandler implements EventHandler<KeyEvent> {
    @Override
    public void handle(KeyEvent e) {
      if (board.gameOver) {
        return;
      }
      if (e.getCode() == KeyCode.LEFT) {
        board.dropLeft();
        if (board.gameOver()) {
          board.gameOver = true;
        }
      }
      else if (e.getCode() == KeyCode.RIGHT) {
        board.dropRight();
        if (board.gameOver()) {
          board.gameOver = true;
        }
      }
      else if (e.getCode() == KeyCode.DOWN) {
        board.dropDown();
        if (board.gameOver()) {
          board.gameOver = true;
        }
      }
      else if (e.getCode() == KeyCode.UP) {
        board.dropUp();
        if (board.gameOver()) {
          board.gameOver = true;
        }
      }
      else if (e.getCode() == KeyCode.Q) {
        board.gameOver = true;
      }

      update(); //updates GUI after each move

      //prints game over message
      if (board.gameOver) {
        title.setText("Game Over! Fuck you!");
        GridPane.setHalignment(title, HPos.CENTER);
      }

    }
  }


}
