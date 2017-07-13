import java.awt.*;

/**
 * Created by user on 7/12/2017.
 */
public class Cell {
    private boolean isBomb;
    private boolean isFlag;
    private int numOfMinesNearby = 0;
    private boolean isKnown;
    private  int x;
    private int y;


    public Cell(boolean isBomb, boolean isFlag, int numOfMinesNearby, boolean isKnown,int x,int y) {
        this.isBomb = isBomb;
        this.isFlag = isFlag;
        this.numOfMinesNearby = numOfMinesNearby;
        this.isKnown = isKnown;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public void setBomb(boolean bomb) {
        isBomb = bomb;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }

    public int getNumOfMinesNearby() {
        return numOfMinesNearby;
    }

    public void setNumOfMinesNearby(int numOfMinesNearby) {
        this.numOfMinesNearby = numOfMinesNearby;
    }

    public boolean isKnown() {
        return isKnown;
    }

    public void setKnown(boolean known) {
        isKnown = known;
    }

    @Override
    public String toString() {
        if (isKnown) {
            if (isBomb) {
                return "*";
            }
            else {
                return String.valueOf(numOfMinesNearby);
            }
        } else if (isFlag) {
            return "#";
        }else {
            return "[]";
        }
    }
}
