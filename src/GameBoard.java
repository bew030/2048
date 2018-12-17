import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;

public class GameBoard {
    private Square[][] overallGameBoard;
    private ArrayList<int[]> unoccupiedCoordinates;
    //private ArrayList<int[]> occupiedCoordinates;
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
    }

    public void dropLeft() {
        for (int i = 0; i < 4; i++) { // row iterator
            for (int j = 1; j < 4; j++) { // column iterator
                for (int k = 0; k < j; k++) { // loops all items in front
                    if (overallGameBoard[i][k].getValue()==0) {
                        int[] newOccupiedSpace = new int[]{i,k};

                        int[] prevLoc = new int[]{overallGameBoard[i][j].getxCoord(),overallGameBoard[i][j].getyCoord()};
                        int value = overallGameBoard[i][j].getValue();
                        overallGameBoard[i][j].resetValue();
                        overallGameBoard[i][k].setValue(value);
                        break;
                    }
                }
                if (overallGameBoard[i][j-1].getValue()==overallGameBoard[i][j].getValue()) { // if conditions for if side item is of equal value
                    overallGameBoard[i][j-1].doubleValue();
                    overallGameBoard[i][j].resetValue();
                }
            }
        }
        updateUnoccupied();
        generateRandomPiece();

    }

    public void dropRight() {

    }

    public void dropUp() {

    }

    public void dropDown() {

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
        Square rect = new Square();
        int rectVal = possibleGeneratedValue[randomValueGenerator()];
        rect.setValue(rectVal);
        int[] rectLoc = randomCoordinateGenerator();
        rect.setxCoord(rectLoc[0]);
        rect.setyCoord(rectLoc[1]);
        overallGameBoard[rect.getxCoord()][rect.getyCoord()] = rect;
        updateUnoccupied();
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
        GameBoard gameTester = new GameBoard();
        gameTester.printViewer();
        System.out.println(gameTester.unoccupiedCoordinates.size());
        System.out.println();
        gameTester.dropLeft();;
        gameTester.printViewer();
        System.out.println(gameTester.unoccupiedCoordinates.size());
    }
}
