import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;

public class GameBoard {
    public Square[][] overallGameBoard;
    public ArrayList<int[]> unoccupiedCoordinates;
    public int score;
    public int width;
    public int height;
    public int maxNumber;
    public boolean gameOver;
    final int DEFAULT_HEIGHT = 4;
    final int DEFAULT_WIDTH = 4;
    boolean piecesMoved;
    boolean combinedAlready;
    public int[] possibleGeneratedValue = new int[]{2,4}; // odds of 2 are 90%, odds of 4 are 10%
    public Random rand = new Random();

    /**
     * Main GameBoard constructor. Utilizes a 2D array to store square objects, which are the tiles in the game board.
     */
    public GameBoard() {
        unoccupiedCoordinates = new ArrayList<int[]>();
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
        overallGameBoard = new Square[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                overallGameBoard[i][j] = new Square();
            }
        }
        updateUnoccupied();
        generateRandomPiece();
        generateRandomPiece();
        maxNumber = 0; // not technically correct (should be 2 or 4, could possibly change later)
        piecesMoved = false;
        combinedAlready = false;
        score = 0;
        gameOver = false;
    }

    public GameBoard(int x, int y) {
        unoccupiedCoordinates = new ArrayList<int[]>();
        width = x;
        height = y;
        overallGameBoard = new Square[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                overallGameBoard[i][j] = new Square();
            }
        }
        updateUnoccupied();
        generateRandomPiece();
        generateRandomPiece();
        maxNumber = 0; // not technically correct (should be 2 or 4, could possibly change later)
        piecesMoved = false;
        combinedAlready = false;
        score = 0;
        gameOver = false;
    }

    // METHODS THAT MOVE THE TILES IN THE FOUR DIRECTIONS

    /**
     * Method that drops all tiles to the left. It first combines all things that are neighbors with similar values,
     * and then shifts everything to the left direction. It will NOT combine tiles if tile values have just been
     * changed.
     */
    public void dropLeft() {
        for (int i = 0; i < height; i++) {
            int endCoord = -1;
            for (int j = 1; j < width; j++) {
                for (int k = j-1; k > endCoord; k--) {
                    if (overallGameBoard[i][k].getValue()!=0 && (overallGameBoard[i][k].getValue()
                            != overallGameBoard[i][j].getValue())) {
                        break;
                    }
                    if (overallGameBoard[i][k].getValue()!=0) {
                        if (overallGameBoard[i][k].getValue() == overallGameBoard[i][j].getValue()) {
                            overallGameBoard[i][k].doubleValue();
                            score += overallGameBoard[i][k].getValue();
                            if (overallGameBoard[i][k].getValue()>maxNumber) {
                                maxNumber = overallGameBoard[i][k].getValue();
                            }
                            overallGameBoard[i][j].resetValue();
                            endCoord = k;
                            piecesMoved = true;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < height; i++) { // row iterator
            for (int j = 1; j < width; j++) { // column iterator
                for (int k = 0; k < j; k++) { // loops all items in front
                    if (overallGameBoard[i][j].getValue()!=0 && overallGameBoard[i][k].getValue()==0) {
                        int[] newOccupiedSpace = new int[]{i,k};
                        int[] prevLoc = new int[]{overallGameBoard[i][j].getxCoord(),
                                overallGameBoard[i][j].getyCoord()};
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

    /**
     * Method that drops all tiles to the right. It first combines all things that are neighbors with similar values,
     * and then shifts everything to the right direction. It will NOT combine tiles if tile values have just been
     * changed.
     */
    public void dropRight() {
        for (int i = 0; i < height; i++) { // if you don't include this for loop, some stuff doesn't get combined
            int endCoord = width;
            for (int j = width-2; j > -1; j--) { // doublecheck width-2
                for (int k = j+1; k < endCoord; k++) {
                    if (overallGameBoard[i][k].getValue()!=0 && overallGameBoard[i][k].getValue()
                            != overallGameBoard[i][j].getValue()) {
                        break;
                    }
                    if (overallGameBoard[i][k].getValue()!=0) {
                        if (overallGameBoard[i][k].getValue() == overallGameBoard[i][j].getValue()) {
                            overallGameBoard[i][k].doubleValue();
                            score += overallGameBoard[i][k].getValue();
                            if (overallGameBoard[i][k].getValue()>maxNumber) {
                                maxNumber = overallGameBoard[i][k].getValue();
                            }
                            overallGameBoard[i][j].resetValue();
                            endCoord = k;
                            piecesMoved = true;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < height; i++) { // row iterator
            for (int j = width-2; j > -1; j--) { // column iterator, double check width-2
                for (int k = width-1; k > j; k--) { // loops all items in front
                    if (overallGameBoard[i][j].getValue()!=0 && overallGameBoard[i][k].getValue()==0) {
                        int[] newOccupiedSpace = new int[]{i,k};
                        int[] prevLoc = new int[]{overallGameBoard[i][j].getxCoord(),
                                overallGameBoard[i][j].getyCoord()};
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

    /**
     * Method that drops all tiles to the top. It first combines all things that are neighbors with similar values,
     * and then shifts everything to the up direction. It will NOT combine tiles if tile values have just been
     * changed.
     */
    public void dropUp() {
        for (int j = 0; j < width; j++) { // if you don't include this for loop, some stuff doesn't get combined
            int endCoord = -1;
            for (int i = 1; i < height; i++) {
                for (int k = i-1; k > endCoord; k--) {
                    if (overallGameBoard[k][j].getValue()!=0 && overallGameBoard[k][j].getValue()
                            != overallGameBoard[i][j].getValue()) {
                        break;
                    }
                    if (overallGameBoard[k][j].getValue()!=0) {
                        if (overallGameBoard[k][j].getValue() == overallGameBoard[i][j].getValue()) {
                            overallGameBoard[k][j].doubleValue();
                            score += overallGameBoard[k][j].getValue();
                            if (overallGameBoard[k][j].getValue()>maxNumber) {
                                maxNumber = overallGameBoard[k][j].getValue();
                            }
                            overallGameBoard[i][j].resetValue();
                            endCoord = k;
                            piecesMoved = true;
                        }
                    }
                }
            }
        }
        for (int i = 1; i < height; i++) { // row iterator
            for (int j = 0; j < width; j++) { // column iterator
                for (int k = 0; k < i; k++) { // loops all items in front
                    if (overallGameBoard[i][j].getValue()!=0 && overallGameBoard[k][j].getValue()==0) {
                        int[] newOccupiedSpace = new int[]{k,j};
                        int[] prevLoc = new int[]{overallGameBoard[i][j].getxCoord(),
                                overallGameBoard[i][j].getyCoord()};
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

    /**
     * Method that drops all tiles to the down. It first combines all things that are neighbors with similar values,
     * and then shifts everything to the down direction. It will NOT combine tiles if tile values have just been
     * changed.
     */
    public void dropDown() {
        for (int j = 0; j < width; j++) { // if you don't include this for loop, some stuff doesn't get combined
            int endCoord = height;
            for (int i = height-2; i > -1; i--) {
                for (int k = i+1; k < endCoord; k++) {
                    if (overallGameBoard[k][j].getValue()!=0 && overallGameBoard[k][j].getValue() !=
                            overallGameBoard[i][j].getValue()) {
                        break;
                    }
                    if (overallGameBoard[k][j].getValue()!=0) {
                        if (overallGameBoard[k][j].getValue() == overallGameBoard[i][j].getValue()) {
                            overallGameBoard[k][j].doubleValue();
                            score += overallGameBoard[k][j].getValue();
                            if (overallGameBoard[k][j].getValue()>maxNumber) {
                                maxNumber = overallGameBoard[k][j].getValue();
                            }
                            overallGameBoard[i][j].resetValue();
                            endCoord = k;
                            piecesMoved = true;
                        }
                    }
                }
            }
        }
        for (int i = height-2; i > -1; i--) { // row iterator
            for (int j = 0; j < width; j++) { // column iterator
                for (int k = height-1; k > i; k--) { // loops all items in front
                    if (overallGameBoard[i][j].getValue()!=0 && overallGameBoard[k][j].getValue()==0) {
                        int[] newOccupiedSpace = new int[]{k,j};
                        int[] prevLoc = new int[]{overallGameBoard[i][j].getxCoord(),
                                overallGameBoard[i][j].getyCoord()};
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

    // METHODS USED FOR RANDOM TILE THAT IS GENERATED

    /**
     * Method that checks if a coordinate is in the unoccupied array list.
     * @param unoccupiedArrayList the array list of unoccupied coordinates
     * @param array an array of 2 numbers that represents the coordinates that you're checking
     * @return returns a boolean condition of if the coordinate is in the unoccupied array list.
     */
    public boolean contains(ArrayList<int[]> unoccupiedArrayList, int[] array) {
        for (int[] item: unoccupiedArrayList)  {
            if (Arrays.equals(item,array)) {
                return true;
            }
        }
        return false;
    }

    /**
     * method that removes the coordinate from the unoccupied array list.
     * @param unoccupiedArrayList the array list of unoccupied coordinates
     * @param array an array of 2 numbers that represents the coordinates that you're removing
     */
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

    /**
     * Updates the list of unoccupied coordinates. Used primarily to select a random location for the new tile
     * to appear.
     */
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

    /**
     * Generates a random coordinate to be used by the new tile that is generated.
     * @return
     */
    private int[] randomCoordinateGenerator() {
        int n = rand.nextInt(unoccupiedCoordinates.size());
        return unoccupiedCoordinates.get(n);
    }

    /**
     * Generates a random value to be used by the new tile that is generated. It follows the proper chances of the real
     * game.
     * @return returns an int, which is the random value to be used.
     */
    private int randomValueGenerator() {
        double n = rand.nextDouble();
        if (n < .90) {
            return 0;
        }
        else {
            return 1;
        }
    }

    /**
     * Generates a random piece onto the game board.
     */
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

    // GETTER METHODS

    /**
     * getter method, returns the score
     * @return an int which represents the score
     */
    public int getScore() {
        return score;
    }

    /**
     * getter method, returns the width
     * @return returns an int which represents the width of the game board
     */
    public int getWidth() {
        return width;
    }

    /**
     * getter method, returns the height
     * @return returns an int which represents the height of the game board
     */
    public int getHeight() {
        return height;
    }

    // FINAL METHOD: CHECKS IF THE GAME IS OVER

    /**
     * method that checks if the game is over (there are no more moves that you can make). Returns a boolean, where
     * it returns true if the game is over and false otherwise.
     * @return boolean condition on if the game is over
     */
    public boolean gameOver() {
        if (unoccupiedCoordinates.size()!=0) {
            return false;
        }
        else {
            for (int i = 0; i < height; i++) { // row iterator
                for (int j = 0; j < width; j++) { // column iterator
                    // check left condition
                    if ((j-1)>=0) {
                        if (overallGameBoard[i][j-1].getValue()==overallGameBoard[i][j].getValue()) {
                            return false;
                        }
                    }
                    // check right condition
                    if ((j+1)<width) {
                        if (overallGameBoard[i][j+1].getValue()==overallGameBoard[i][j].getValue()) {
                            return false;
                        }
                    }
                    // check up condition
                    if ((i-1)>=0) {
                        if (overallGameBoard[i-1][j].getValue()==overallGameBoard[i][j].getValue()) {
                            return false;
                        }
                    }
                    // check down condition
                    if ((i+1)<height) {
                        if (overallGameBoard[i+1][j].getValue()==overallGameBoard[i][j].getValue()) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
    }

    // TESTER METHODS: USED FOR MAKING TESTS AND SETTING PIECES, NOT USED OTHERWISE

    private GameBoard(int aVal, int[] a, int bVal, int[] b) {
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

    private void generateSetPiece(int y,int[] x) {
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

    private void printViewer() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(overallGameBoard[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        GameBoard tester = new GameBoard(4,1);
        tester.printViewer();
        System.out.println();
        tester.dropDown();
        tester.printViewer();
    }
}
