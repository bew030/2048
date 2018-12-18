import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;

public class GameBoard {
    private Square[][] overallGameBoard;
    private ArrayList<int[]> unoccupiedCoordinates;
    //private ArrayList<int[]> occupiedCoordinates;
    boolean piecesMoved;
    boolean combinedAlready;
    private int[] possibleGeneratedValue = new int[]{2,4}; // odds of 2 are 90%, odds of 4 are 10%
    private Random rand = new Random();

    public GameBoard() {
        unoccupiedCoordinates = new ArrayList<int[]>();
        //occupiedCoordinates = new ArrayList<int[]>();
        overallGameBoard = new Square[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                overallGameBoard[i][j] = new Square();
            }
        }
        updateUnoccupied();
        generateRandomPiece();
        generateRandomPiece();
        piecesMoved = false;
        combinedAlready = false;
    }

    public GameBoard(int aVal, int[] a, int bVal, int[] b) {
        unoccupiedCoordinates = new ArrayList<int[]>();
        //occupiedCoordinates = new ArrayList<int[]>();
        overallGameBoard = new Square[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                overallGameBoard[i][j] = new Square();
            }
        }
        updateUnoccupied();
        generateSetPiece(aVal, a);
        generateSetPiece(bVal, b);
        piecesMoved = false;
        combinedAlready = false;
    }

    public void dropLeft() {
        for (int i = 0; i < 4; i++) { // if you don't include this for loop, some stuff doesn't get combined
            for (int j = 1; j < 4; j++) {
                if (overallGameBoard[i][j-1].getValue()!=0) {
                    if (overallGameBoard[i][j-1].getValue()==overallGameBoard[i][j].getValue()) { // if conditions for if side item is of equal value
                        overallGameBoard[i][j-1].doubleValue();
                        overallGameBoard[i][j].resetValue();
                        piecesMoved = true;
                    }
                }
            }
        }
        for (int i = 0; i < 4; i++) { // row iterator
            for (int j = 1; j < 4; j++) { // column iterator
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
        for (int i = 0; i < 4; i++) { // if you don't include this for loop, some stuff doesn't get combined
            for (int j = 2; j > -1; j--) {
                if (overallGameBoard[i][j+1].getValue()!=0) {
                    if (overallGameBoard[i][j+1].getValue()==overallGameBoard[i][j].getValue()) { // if conditions for if side item is of equal value
                        overallGameBoard[i][j+1].doubleValue();
                        overallGameBoard[i][j].resetValue();
                        piecesMoved = true;
                    }
                }
            }
        }
        for (int i = 0; i < 4; i++) { // row iterator
            for (int j = 2; j > -1; j--) { // column iterator
                for (int k = 3; k > j; k--) { // loops all items in front
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

    public void dropUp() {
        for (int i = 1; i < 4; i++) { // if you don't include this for loop, some stuff doesn't get combined
            for (int j = 0; j < 4; j++) {
                if (overallGameBoard[i-1][j].getValue()!=0) {
                    if (overallGameBoard[i-1][j].getValue()==overallGameBoard[i][j].getValue()) { // if conditions for if side item is of equal value
                        overallGameBoard[i-1][j].doubleValue();
                        overallGameBoard[i][j].resetValue();
                        piecesMoved = true;
                    }
                }
            }
        }
        for (int i = 1; i < 4; i++) { // row iterator
            for (int j = 0; j < 4; j++) { // column iterator
                for (int k = 0; k < i; k++) { // loops all items in front
                    if (overallGameBoard[i][j].getValue()!=0 && overallGameBoard[k][j].getValue()==0) {
                        int[] newOccupiedSpace = new int[]{k,j};
                        int[] prevLoc = new int[]{overallGameBoard[i][j].getxCoord(),overallGameBoard[i][j].getyCoord()};
                        int value = overallGameBoard[i][j].getValue();
                        overallGameBoard[i][j].resetValue();
                        overallGameBoard[k][j].setValue(value);
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
        for (int i = 2; i > -1; i--) { // if you don't include this for loop, some stuff doesn't get combined
            for (int j = 0; j < 4; j++) {
                if (overallGameBoard[i+1][j].getValue()!=0) {
                    if (overallGameBoard[i+1][j].getValue()==overallGameBoard[i][j].getValue()) { // if conditions for if side item is of equal value
                        overallGameBoard[i+1][j].doubleValue();
                        overallGameBoard[i][j].resetValue();
                        piecesMoved = true;
                    }
                }
            }
        }
        for (int i = 2; i > -1; i--) { // row iterator
            for (int j = 0; j < 4; j++) { // column iterator
                for (int k = 3; k > i; k--) { // loops all items in front
                    if (overallGameBoard[i][j].getValue()!=0 && overallGameBoard[k][j].getValue()==0) {
                        int[] newOccupiedSpace = new int[]{k,j};
                        int[] prevLoc = new int[]{overallGameBoard[i][j].getxCoord(),overallGameBoard[i][j].getyCoord()};
                        int value = overallGameBoard[i][j].getValue();
                        overallGameBoard[i][j].resetValue();
                        overallGameBoard[k][j].setValue(value);
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
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
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

    public static void main(String[] args) {
        GameBoard gameTester = new GameBoard(2, new int[]{3,0},4, new int[]{0,0});
        gameTester.generateSetPiece(2,new int[]{1,0});
        gameTester.generateSetPiece(2,new int[]{2,0});
        gameTester.printViewer();
        System.out.println();
        gameTester.dropUp();
        gameTester.printViewer();
    }
}
