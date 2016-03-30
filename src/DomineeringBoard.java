import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sam on 09/03/2016.
 */
public class DomineeringBoard extends Board<DomineeringMove> {

    private static final Player H = Player.MAXIMIZER;
    private static final Player V = Player.MINIMIZER;

    private final int w, h;
    private final boolean[][] board;
    private Set<DomineeringMove> available;

    public DomineeringBoard() {
        this(4, 4);
    }

    public DomineeringBoard(int m, int n) {
        w = m;
        h = n;
        board = new boolean[w][h];
        available = new HashSet<>();
    }

    private DomineeringBoard(int w, int h, boolean[][] board) {
        this.w = w;
        this.h = h;

        this.board = board;
    }

    @Override
    public Player nextPlayer() {
        int movesTaken = 0;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                movesTaken += board[x][y] ? 1 : 0;
            }
        }
        return movesTaken % 4 == 0 ? H : V;
    }

    @Override
    public Set<DomineeringMove> availableMoves() {
        Set<DomineeringMove> av = new HashSet<>();

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (nextPlayer().equals(H)) {
                    if (x < w - 1 && !board[x][y] && !board[x + 1][y]) {
                        av.add(new DomineeringMove(x, y));
                    }
                } else {
                    if (y < h - 1 && !board[x][y] && !board[x][y + 1]) {
                        av.add(new DomineeringMove(x, y));
                    }
                }
            }
        }
        return av;
    }

    @Override
    public int value() {
        return !availableMoves().isEmpty() ? 0 : nextPlayer().equals(H) ? -1 : 1;
    }

    @Override
    public Board<DomineeringMove> play(DomineeringMove move) {
        available = availableMoves();
        assert (available.contains(move));
        boolean[][] newBoard = new boolean[w][h];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                newBoard[x][y] = board[x][y];
            }
        }
        int mx = move.getX();
        int my = move.getY();

        newBoard[mx][my] = true;

        if (nextPlayer().equals(H)) {
            newBoard[mx + 1][my] = true;
            return new DomineeringBoard(w, h, newBoard);
        } else {
            newBoard[mx][my + 1] = true;
            return new DomineeringBoard(w, h, newBoard);
        }
    }

    public String toString() {
        String s = "";
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                s += board[x][y] ? "x " : ". ";
            }
            s += "\n";
        }
        return s;
    }

}
