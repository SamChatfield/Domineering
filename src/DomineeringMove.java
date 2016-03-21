/**
 * Created by Sam on 09/03/2016.
 */
public class DomineeringMove {

    private int x, y;

    public DomineeringMove(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DomineeringMove that = (DomineeringMove) o;

        if (x != that.x) return false;
        return y == that.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }

    public String toString() {
        return x + "," + y;
    }

}
