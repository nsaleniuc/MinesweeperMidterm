/**
 * Created by user on 7/12/2017.
 */
public class Cell {
    private boolean isBomb;
    private boolean isFlag;
    private int numOfMinesNearby = 0;
    private boolean isKnown;


    public Cell(boolean isBomb, boolean isFlag, int numOfMinesNearby, boolean isKnown) {
        this.isBomb = isBomb;
        this.isFlag = isFlag;
        this.numOfMinesNearby = numOfMinesNearby;
        this.isKnown = isKnown;
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
}
