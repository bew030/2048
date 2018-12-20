import javafx.application.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;


public class Gui2048 extends Application {

  private static final int WIDTH = 4;
  private static final int HEIGHT = 4;
  private static final Color BACKGROUND = Color.rgb(238, 228, 218, 0.35);
  private static final Color COLOR_VALUE_LIGHT = Color.rgb(249, 246, 242); // tile value < 8
  private static final Color COLOR_VALUE_DARK = Color.rgb(119, 110, 101); // tile value > 8

  //text size based on value of square
  private static final int LOW_TEXT = 55;
  private static final int MID_TEXT = 45;
  private static final int HIGH_TEXT = 35;

  //handles visual game
  private GameBoard board;
  private GridPane pane;
  private StackPane stack;
  private Scene scene;
  private Rectangle[][] tiles;
  private Square[][] copySquares;
  private Text[][] textNums;


  private static Text title;
  private static Text score;


  //takes in an int value and returns corresponding color
  private Color replaceColor(int value) {
    Color fillColor = BACKGROUND;

    if (value == 2) {
      fillColor = Color.rgb(238, 228, 218);
    }
    else if (value == 4) {
      fillColor = Color.rgb(237, 224, 200);
    }
    else if (value == 8) {
      fillColor = Color.rgb(242, 177, 121);
    }
    else if (value == 16) {
      fillColor = Color.rgb(245, 149, 99);
    }
    else if (value == 32) {
      fillColor = Color.rgb(246, 124, 95);
    }
    else if (value == 64) {
      fillColor = Color.rgb(246, 94, 59);
    }
    else if (value == 128) {
      fillColor = Color.rgb(237, 207, 114);
    }
    else if (value == 256) {
      fillColor = Color.rgb(237, 204, 97);
    }
    else if (value == 512) {
      fillColor = Color.rgb(237, 200, 80);
    }
    else if (value == 1024) {
      fillColor = Color.rgb(237, 197, 63);
    }
    else if (value == 2048) {
      fillColor = Color.rgb(237, 194, 46);
    }
    return fillColor;
  }

  //deep copies gameBoard to local variable copySquares
  private void copyBoard() {
    for (int i = 0; i < board.getWidth(); i++) {
      for (int j = 0; j < board.getHeight(); j++) {
        copySquares[i][j] = board.overallGameBoard[i][j];
      }
    }
  }

  //updates font of text objects visually
  private void updateFont() {
    for (int i = 0; i < board.getHeight(); i++) {
      for (int j = 0; j < board.getWidth(); j++) {
        //updates size of text based on value
        if (copySquares[j][i].getValue() < 128) {
          textNums[i][j].setFont((Font.font("Times New Roman",
                         FontWeight.BOLD, LOW_TEXT)));
        }
        else if (copySquares[j][i].getValue() < 1024) {
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

  //clears grid and sets all text objects to null
  private void clearGrid() {
    for (int i = 0; i < board.getHeight(); i++) {
      for (int j = 0; j < board.getWidth(); j++) {
        tiles[i][j].setFill(BACKGROUND);
        textNums[i][j].setText(null);
      }
    }
  }

  //iterates through entire 4x4 grid
  //creates new tile objects for each box, sets proper color and text and aligns
  private void colorBoard() {
    for (int x = 0; x < board.getHeight(); x++) {
      for (int y = 0; y < board.getWidth(); y++) {
        tiles[x][y].setFill(replaceColor(copySquares[y][x].getValue()));
        Text tileText = new Text();
        tileText.setText(copySquares[y][x].toString());
        textNums[x][y] = tileText;
        if (copySquares[y][x].getValue() != 0) {
          pane.add(textNums[x][y], x, y + 1);
          GridPane.setHalignment(textNums[x][y], HPos.CENTER);
        }
      }
    }
  }

  //updates GUI
  private void update() {
    //clear grid, copy over newest squares array
    clearGrid();
    copyBoard();
    //update score
    score.setText("Score: " + board.getScore());
    score.setFont(Font.font("Times New Roman", 20));
    score.setFill(Color.BLACK);

    //recolor board and update the font of each square to reflect value
    colorBoard();
    updateFont();

    //updates color of text to reflect value
    for (int i = 0; i < board.getHeight(); i++) {
      for (int j = 0; j < board.getWidth(); j++) {
        if (copySquares[j][i].getValue() < 8) {
          textNums[i][j].setFill(COLOR_VALUE_DARK);
        }
        else {
          textNums[i][j].setFill(COLOR_VALUE_LIGHT);
        }
      }
    }

  }

  //handles the start of game, initializes board and variables
  @Override
  public void start(Stage primaryStage) {
    //creates new game
    this.board = new GameBoard();
    //create pane to hold visual representation of game
    pane = new GridPane();
    pane.setAlignment(Pos.CENTER);
    pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
    pane.setStyle("-fx-background-color: rgb(187, 173, 160)");
    //set spacing
    pane.setHgap(15);
    pane.setVgap(15);

    //initialize variables and copy over board
    copySquares = new Square[board.getWidth()][board.getHeight()];
    tiles = new Rectangle[board.getHeight()][board.getWidth()];
    copyBoard();
    textNums = new Text[board.getHeight()][board.getWidth()];

    //creating yellow title block
    Rectangle titleBlock = new Rectangle();
    titleBlock.setWidth(100);
    titleBlock.setHeight(100);
    titleBlock.setFill(Color.rgb(237, 204, 97));
    pane.add(titleBlock, 0, 0);

    //initialize game title and score headers, adding all to pane
    title = new Text();
    title.setText("2048");
    title.setFont(Font.font("Times New Roman", 40));
    title.setFill(Color.WHITE);
    score = new Text();
    score.setText("Score: " + 0);
    score.setFont(Font.font("Times New Roman", 20));
    score.setFill(Color.BLACK);

    pane.add(title, 0, 0);
    GridPane.setHalignment(title, HPos.CENTER);
    pane.add(score, board.getWidth() - 1, 0);
    GridPane.setHalignment(score, HPos.CENTER);

    for (int x = 0; x < board.getHeight(); x++) {
      for (int y = 0; y < board.getWidth(); y++) {
        Rectangle newTile = new Rectangle();
        newTile.setWidth(100);
        newTile.setHeight(100);

        tiles[x][y] = newTile;
        tiles[x][y].setFill(replaceColor(copySquares[y][x].getValue()));
        pane.add(tiles[x][y], x, y + 1);

        Text tileText = new Text();
        tileText.setText(copySquares[y][x].toString());
        textNums[x][y] = tileText;
        if (copySquares[y][x].getValue() != 0) {
          pane.add(textNums[x][y], x, y + 1);
          GridPane.setHalignment(textNums[x][y], HPos.CENTER);
        }
      }
    }
    colorBoard();
    updateFont();
    //updates color of text to reflect value
    for (int i = 0; i < board.getHeight(); i++) {
      for (int j = 0; j < board.getWidth(); j++) {
        if (copySquares[j][i].getValue() < 8) {
          textNums[i][j].setFill(COLOR_VALUE_DARK);
        }
        else {
          textNums[i][j].setFill(COLOR_VALUE_LIGHT);
        }
      }
    }


    //creates new stack pane to hold GridPane object, connects KeyHandler and
    //sets stage
    this.stack = new StackPane();
    stack.getChildren().addAll(pane);

    this.scene = new Scene(stack);
    scene.setOnKeyPressed(new KeyHandler());

    primaryStage.setTitle("2048");
    primaryStage.setScene(scene);
    primaryStage.show();
    }



  //inner KeyHandler class that handles key presses and performs corresponding action
  private class KeyHandler implements EventHandler<KeyEvent> {
    @Override
    public void handle(KeyEvent e) {

      //after each key press, check if game is over
      if (board.gameOver) {
        return;
      }
      if (e.getCode() == KeyCode.LEFT) {
        board.dropLeft();
      } else if (e.getCode() == KeyCode.RIGHT) {
        board.dropRight();
      } else if (e.getCode() == KeyCode.DOWN) {
        board.dropDown();
      } else if (e.getCode() == KeyCode.UP) {
        board.dropUp();
      } else if (e.getCode() == KeyCode.Q) {
        board.gameOver = true;
      }

      // NEEDS AN ELSE CONDITION FOR IF SOMEONE ENTERS ANY OTHER CHARACTER

      update(); //updates GUI after each move

      //prints game winning message
      if (board.maxNumber == 2048) {
        Text gameWon = new Text();
        gameWon.setText("You won! You can continue playing if you want.");
        gameWon.setFont(Font.font("Times New Roman", FontWeight.BOLD, 10));
        pane.add(gameWon, 1, 0, 2, 1);
        GridPane.setHalignment(gameWon, HPos.CENTER);
        board.maxNumber = 0;
      }

      //prints game over message
      if (board.gameOver()) { //|| board.gameOver) {
        board.gameOver = true;
        Text endGame = new Text();
        endGame.setText("Game Over!");
        endGame.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
        pane.add(endGame, 1, 0, 2, 1);
        GridPane.setHalignment(endGame, HPos.CENTER);
      }

    }
  }
}
