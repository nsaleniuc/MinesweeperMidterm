
/**
 * Created by user on 7/12/2017.
 */
public class Cell {
    private boolean isBomb;
    private boolean isFlag;
    private int numOfMinesNearby = 0;
    private boolean isKnown;
    private int x;
    private int y;
    private int pictureNum;

    public int getPictureNum() {
        return pictureNum;
    }

    public void setPictureNum(int pictureNum) {
        this.pictureNum = pictureNum;
    }

    public Cell(boolean isBomb, boolean isFlag, int numOfMinesNearby, boolean isKnown, int x, int y) {
        this.isBomb = isBomb;
        this.isFlag = isFlag;
        this.numOfMinesNearby = numOfMinesNearby;
        this.isKnown = isKnown;
        this.x = x;
        this.y = y;
    }
    public Cell(boolean isBomb, boolean isFlag, int numOfMinesNearby, boolean isKnown) {
        this.isBomb = isBomb;
        this.isFlag = isFlag;
        this.numOfMinesNearby = numOfMinesNearby;
        this.isKnown = isKnown;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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
                return "\u2622";
            } else {
                return String.valueOf(numOfMinesNearby);
            }
        } else if (isFlag) {
            return "\u2690";
        } else {
            return "\u29C8";
        }
    }
}
