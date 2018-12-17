import java.util.Arrays;

public class Rectangle {

    private int intValue;
    private int xCoord;
    private int yCoord;
    /**
     * Rectangle default constructor.
     */
    public Rectangle() {
        intValue = 0;
    }

    public void setValue(int addOn) {
        intValue+= addOn;
    }

    public void resetValue() {
        intValue = 0;
    }

    public int getValue() {
        return intValue;
    }

    public void setxCoord(int x) {
        xCoord = x;
    }

    public int getxCoord() {
        return xCoord;
    }

    public void setyCoord(int y) {
        yCoord = y;
    }

    public int getyCoord() {
         return yCoord;
    }

    public void doubleValue() {
        intValue *= 2;
    }

    public String toString() {
        if (this.equals(null)) {
            return null;
        }
        return Integer.toString(intValue);
    }

    public static void main(String[] args) {
        Rectangle tester = new Rectangle();
        tester.setValue(2);
        System.out.println(tester.getValue());
    }
}
