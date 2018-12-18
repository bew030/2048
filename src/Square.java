public class Square {

    private int intValue;
    private int xCoord;
    private int yCoord;
    /**
     * Rectangle default constructor.
     */
    public Square() {
        intValue = 0;
    }

    public void setValue(int addOn) {
        intValue = addOn;
    }

    public int getValue() {
        return intValue;
    }

    public void resetValue() {
        intValue = 0;
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
        Square tester = new Square();
        tester.setValue(2);
        System.out.println(tester.getValue());
    }
}
