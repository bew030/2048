import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameBoard {
    public Square[][] overallGameBoard;
    public ArrayList<int[]> unoccupiedCoordinates;
    public int score;
    public int width;
    public int height;
    public boolean gameOver;
    final int DEFAULT_HEIGHT = 4;
    final int DEFAULT_WIDTH = 4;
    //private ArrayList<int[]> occupiedCoordinates;
    boolean piecesMoved;
    boolean combinedAlready;
    public int[] possibleGeneratedValue = new int[]{2,4}; // odds of 2 are 90%, odds of 4 are 10%
    public Random rand = new Random();

    public GameBoard() {
        unoccupiedCoordinates = new ArrayList<int[]>();
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
        //occupiedCoordinates = new ArrayList<int[]>();
        overallGameBoard = new Square[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                overallGameBoard[i][j] = new Square();
            }
        }
        updateUnoccupied();
        generateRandomPiece();
        generateRandomPiece();
        piecesMoved = false;
        combinedAlready = false;
        score = 0;
        gameOver = false;
    }

    public GameBoard(int x, int y) {
        unoccupiedCoordinates = new ArrayList<int[]>();
        width = x;
        height = y;
        //occupiedCoordinates = new ArrayList<int[]>();
        overallGameBoard = new Square[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                overallGameBoard[i][j] = new Square();
            }
        }
        updateUnoccupied();
        generateRandomPiece();
        generateRandomPiece();
        piecesMoved = false;
        combinedAlready = false;
        score = 0;
        gameOver = false;
    }

    public GameBoard(int aVal, int[] a, int bVal, int[] b) {
        unoccupiedCoordinates = new ArrayList<int[]>();
        //occupiedCoordinates = new ArrayList<int[]>();
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
        overallGameBoard = new Square[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                overallGameBoard[i][j] = new Square();
            }
        }
        updateUnoccupied();
        generateSetPiece(aVal, a);
        generateSetPiece(bVal, b);
        piecesMoved = false;
        combinedAlready = false;
        score = 0;
        gameOver = false;
    }

    public void dropLeft() {
        for (int i = 0; i < height; i++) { // if you don't include this for loop, some stuff doesn't get combined
            for (int j = 1; j < width; j++) {
                for (int k = j-1; k > -1; k--) {

                    if (overallGameBoard[i][k].getValue()!=0 && overallGameBoard[i][k].getValue() != overallGameBoard[i][j].getValue()) {
                        break;
                    }
                    else if (overallGameBoard[i][k].getValue() == overallGameBoard[i][j].getValue()) {
                        overallGameBoard[i][k].doubleValue();
                        score += overallGameBoard[i][k].getValue();
                        overallGameBoard[i][j].resetValue();
                        piecesMoved = true;
                    }
                }
            }
        }
        for (int i = 0; i < height; i++) { // row iterator
            for (int j = 1; j < width; j++) { // column iterator
                for (int k = 0; k < j; k++) { // loops all items in front
                    if (overallGameBoard[i][j].getValue()!=0 && overallGameBoard[i][k].getValue()==0) {
                        int[] newOccupiedSpace = new int[]{i,k};
                        int[] prevLoc = new int[]{overallGameBoard[i][j].getxCoord(),overallGameBoard[i][j].getyCoord()};
                        int value = overallGameBoard[i][j].getValue();
                        overallGameBoard[i][j].resetValue();
                        overallGameBoard[i][k].setValue(value);
                        piecesMoved = true;
                        break;
                    }
                }
            }
        }
        if (piecesMoved) {
            updateUnoccupied();
            generateRandomPiece();
            piecesMoved = false;
        }
    }

    public void dropRight() {
        for (int i = 0; i < height; i++) { // if you don't include this for loop, some stuff doesn't get combined
            for (int j = width-2; j > -1; j--) { // doublecheck width-2
                for (int k = j+1; k < width; k++) {
                    if (overallGameBoard[i][k].getValue()!=0 && overallGameBoard[i][k].getValue() != overallGameBoard[i][j].getValue()) {
                        break;
                    }
                    else if (overallGameBoard[i][k].getValue() == overallGameBoard[i][j].getValue()) {
                        overallGameBoard[i][k].doubleValue();
                        score += overallGameBoard[i][k].getValue();
                        overallGameBoard[i][j].resetValue();
                        piecesMoved = true;
                    }
                }
            }
        }
        for (int i = 0; i < height; i++) { // row iterator
            for (int j = width-2; j > -1; j--) { // column iterator, double check width-2
                for (int k = 3; k > j; k--) { // loops all items in front
                    if (overallGameBoard[i][j].getValue()!=0 && overallGameBoard[i][k].getValue()==0) {
                        int[] newOccupiedSpace = new int[]{i,k};
                        int[] prevLoc = new int[]{overallGameBoard[i][j].getxCoord(),overallGameBoard[i][j].getyCoord()};
                        int value = overallGameBoard[i][j].getValue();
                        overallGameBoard[i][j].resetValue();
                        overallGameBoard[i][k].setValue(value);
                        k--;
                        piecesMoved = true;
                        break;
                    }
                }
            }
        }
        if (piecesMoved) {
            updateUnoccupied();
            generateRandomPiece();
            piecesMoved = false;
        }
    }

    public void dropUp() {
        for (int i = 0; i < height; i++) { // if you don't include this for loop, some stuff doesn't get combined
            for (int j = 1; j < width; j++) {
                for (int k = j-1; k > -1; k--) {

                    if (overallGameBoard[i][k].getValue()!=0 && overallGameBoard[i][k].getValue() != overallGameBoard[i][j].getValue()) {
                        break;
                    }
                    else if (overallGameBoard[i][k].getValue() == overallGameBoard[i][j].getValue()) {
                        overallGameBoard[i][k].doubleValue();
                        score += overallGameBoard[i][k].getValue();
                        overallGameBoard[i][j].resetValue();
                        piecesMoved = true;
                    }
                }
            }
        }

        for (int i = 1; i < height; i++) { // if you don't include this for loop, some stuff doesn't get combined
            for (int j = 0; j < width; j++) {
                for (int k = i-1; k > -1; k--) {
                    if (overallGameBoard[k][j].getValue()!=0 && overallGameBoard[k][j].getValue() != overallGameBoard[i][j].getValue()) {
                        break;
                    }
                    else if (overallGameBoard[k][j].getValue() == overallGameBoard[i][j].getValue()) {
                        overallGameBoard[k][j].doubleValue();
                        score += overallGameBoard[k][j].getValue();
                        overallGameBoard[i][j].resetValue();
                        piecesMoved = true;
                    }
                }
            }
        }
        for (int i = 1; i < height; i++) { // row iterator
            for (int j = 0; j < width; j++) { // column iterator
                for (int k = 0; k < i; k++) { // loops all items in front
                    if (overallGameBoard[i][j].getValue()!=0 && overallGameBoard[k][j].getValue()==0) {
                        int[] newOccupiedSpace = new int[]{k,j};
                        int[] prevLoc = new int[]{overallGameBoard[i][j].getxCoord(),overallGameBoard[i][j].getyCoord()};
                        int value = overallGameBoard[i][j].getValue();
                        overallGameBoard[i][j].resetValue();
                        overallGameBoard[k][j].setValue(value);
                        k++;
                        piecesMoved = true;
                        break;
                    }
                }
            }
        }
        if (piecesMoved) {
            updateUnoccupied();
            generateRandomPiece();
            piecesMoved = false;
        }
    }

    public void dropDown() {
        for (int i = height-2; i > -1; i--) { // if you don't include this for loop, some stuff doesn't get combined
            for (int j = 0; j < width; j++) {
                for (int k = i+1; k < height; k++) {
                    if (overallGameBoard[k][j].getValue()!=0 && overallGameBoard[k][j].getValue() != overallGameBoard[i][j].getValue()) {
                        break;
                    }
                    else if (overallGameBoard[k][j].getValue() == overallGameBoard[i][j].getValue()) {
                        overallGameBoard[k][j].doubleValue();
                        score += overallGameBoard[k][j].getValue();
                        overallGameBoard[i][j].resetValue();
                        piecesMoved = true;
                    }
                }
            }
        }
        for (int i = height-2; i > -1; i--) { // row iterator
            for (int j = 0; j < width; j++) { // column iterator
                for (int k = height-1; k > i; k--) { // loops all items in front
                    if (overallGameBoard[i][j].getValue()!=0 && overallGameBoard[k][j].getValue()==0) {
                        int[] newOccupiedSpace = new int[]{k,j};
                        int[] prevLoc = new int[]{overallGameBoard[i][j].getxCoord(),overallGameBoard[i][j].getyCoord()};
                        int value = overallGameBoard[i][j].getValue();
                        overallGameBoard[i][j].resetValue();
                        overallGameBoard[k][j].setValue(value);
                        k--;
                        piecesMoved = true;
                        break;
                    }
                }
            }
        }
        if (piecesMoved) {
            updateUnoccupied();
            generateRandomPiece();
            piecesMoved = false;
        }
    }

    public void updateUnoccupied() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int[] coordinates = new int[]{i,j};
                if (contains(unoccupiedCoordinates,coordinates) && overallGameBoard[i][j].getValue()!=0) {
                    //System.out.println("removed");
                    remove(unoccupiedCoordinates,coordinates);
                }
                else if (!contains(unoccupiedCoordinates,coordinates) && overallGameBoard[i][j].getValue()==0) {
                    //System.out.println("added");
                    unoccupiedCoordinates.add(coordinates);
                }
            }
        }
    }

    public boolean gameOver() {
        if (unoccupiedCoordinates.size()!=0) {
            return false;
        }
        else {
            for (int i = 0; i < height; i++) { // row iterator
                for (int j = 0; j < width; j++) { // column iterator
                    // check left condition
                    if ((j-1)>0) {
                        if (overallGameBoard[i][j-1].getValue()==overallGameBoard[i][j].getValue()) {
                            return false;
                        }
                    }
                    // check right condition
                    else if ((j+1)<width) {
                        if (overallGameBoard[i][j+1].getValue()==overallGameBoard[i][j].getValue()) {
                            return false;
                        }
                    }
                    // check up condition
                    else if ((i-1)>0) {
                        if (overallGameBoard[i-1][j].getValue()==overallGameBoard[i][j].getValue()) {
                            return false;
                        }
                    }
                    // check down condition
                    else if ((i+1)<height) {
                        if (overallGameBoard[i+1][j].getValue()==overallGameBoard[i][j].getValue()) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
    }

    public void generateRandomPiece() {
        Square square = new Square();
        int squareVal = possibleGeneratedValue[randomValueGenerator()];
        square.setValue(squareVal);
        int[] squareLoc = randomCoordinateGenerator();
        square.setxCoord(squareLoc[0]);
        square.setyCoord(squareLoc[1]);
        overallGameBoard[square.getxCoord()][square.getyCoord()] = square;
        updateUnoccupied();
    }

    public void generateSetPiece(int y,int[] x) {
        try {
            if(!contains(unoccupiedCoordinates,x)) {
                throw new Exception();
            }
            Square square = new Square();
            square.setValue(y);
            square.setxCoord(x[0]);
            square.setyCoord(x[1]);
            overallGameBoard[square.getxCoord()][square.getyCoord()] = square;
            updateUnoccupied();
        }
        catch (Exception e) {
            System.out.println("ERROR: coordinate is occupied");
        }
    }

    private int[] randomCoordinateGenerator() {
        int n = rand.nextInt(unoccupiedCoordinates.size());
        return unoccupiedCoordinates.get(n);
    }

    private int randomValueGenerator() {
        double n = rand.nextDouble();
        if (n < .90) {
            return 0;
        }
        else {
            return 1;
        }
    }

    public void printViewer() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(overallGameBoard[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean contains(ArrayList<int[]> unoccupiedArrayList, int[] array) {
        for (int[] item: unoccupiedArrayList)  {
            if (Arrays.equals(item,array)) {
                return true;
            }
        }
        return false;
    }

    public void remove(ArrayList<int[]> unoccupiedArrayList, int[] array) {
        int location = -1;
        for (int i = 0; i < unoccupiedArrayList.size(); i++) {
            if (Arrays.equals(unoccupiedArrayList.get(i),array)) {
                location = i;
                break;
            }
        }
        unoccupiedArrayList.remove(location);
    }

    public int getScore() {
        return score;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to twenty-fort-neight. WASD controls. Good Luck!");
        GameBoard testerBoard = new GameBoard();
        while (!testerBoard.gameOver()) {
            System.out.println("Score: " + testerBoard.getScore());
            testerBoard.printViewer();
            String command = scan.nextLine();
            if (command.equals("w")) {
                testerBoard.dropUp();
            }
            else if (command.equals("a")) {
                testerBoard.dropLeft();
            }
            else if (command.equals("s")) {
                testerBoard.dropDown();
            }
            else if (command.equals("d")) {
                testerBoard.dropRight();
            }
            else if (command.equals("q")) {
                break;
            }
            else {
                System.out.println("ERROR: Wrong input. Try again!");
            }
            System.out.println();
        }
        System.out.println("GAME OVER. Final score: "+testerBoard.getScore());
    }
}
