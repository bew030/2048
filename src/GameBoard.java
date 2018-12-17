import java.util.ArrayList;
import java.util.Random;

public class GameBoard {
    private Rectangle[][] overallGameBoard;
    private ArrayList<int[]> unoccupiedCoordinates;
    private int[] possibleGeneratedValue = new int[]{2,4}; // odds of 2 are 90%, odds of 4 are 10%
    private Random rand = new Random();

    public GameBoard() {
        unoccupiedCoordinates = new ArrayList<int[]>();
        overallGameBoard = new Rectangle[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                overallGameBoard[i][j] = null;
                unoccupiedCoordinates.add(new int[]{i,j});
            }
        }
        Rectangle firstRect = new Rectangle();
        Rectangle secondRect = new Rectangle();

        // always starts off with two random rectangles of either value 2 or 4
        int firstRectVal = possibleGeneratedValue[randomValueGenerator()];
        int secondRectVal = possibleGeneratedValue[randomValueGenerator()];
        firstRect.setValue(firstRectVal);
        secondRect.setValue(secondRectVal);

        // two random locations for the two rectangles
        int[] firstLoc = randomCoordinateGenerator();
        firstRect.setxCoord(firstLoc[0]);
        firstRect.setyCoord(firstLoc[1]);
        unoccupiedCoordinates.remove(firstLoc);

        int[] secLoc = randomCoordinateGenerator();
        secondRect.setxCoord(secLoc[0]);
        secondRect.setyCoord(secLoc[1]);
        unoccupiedCoordinates.remove(secLoc);

        // sets onto board
        overallGameBoard[firstRect.getxCoord()][firstRect.getyCoord()] = firstRect;
        overallGameBoard[secondRect.getxCoord()][secondRect.getyCoord()] = secondRect;
    }

    public int[] randomCoordinateGenerator() {
        int n = rand.nextInt(unoccupiedCoordinates.size());
        return unoccupiedCoordinates.get(n);
    }

    public int randomValueGenerator() {
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

    public static void main(String[] args) {
        GameBoard gameTester = new GameBoard();
        gameTester.printViewer();
    }
}
