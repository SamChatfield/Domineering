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
    //    private final Set<DomineeringMove> hMoves, vMoves;
    private Set<DomineeringMove> available;

    public DomineeringBoard() {
        this(4, 4);
    }

    public DomineeringBoard(int m, int n) {
        w = m;
        h = n;
        board = new boolean[w][h];
//        hMoves = new HashSet<>();
//        vMoves = new HashSet<>();
        available = new HashSet<>();
        System.err.println(this);
    }

    private DomineeringBoard(int w, int h, boolean[][] board) { // Set<DomineeringMove> hMoves, Set<DomineeringMove> vMoves) {
        System.err.println("meep");
        this.w = w;
        this.h = h;
//        this.hMoves = hMoves;
//        this.vMoves = vMoves;
//        board = new boolean[w][h];

//        Set<DomineeringMove> unionHV = new HashSet<>();
//        unionHV.addAll(hMoves);
//        unionHV.addAll(vMoves);

//        for (DomineeringMove m : unionHV) {
//            int x = m.getX();
//            int y = m.getY();
//            board[x][y] = true;
//        }

        this.board = board;
//        available = availableMoves();

        if (nextPlayer().equals(H)) System.err.println(V);
        else System.err.println(H);

//        for (DomineeringMove m : unionHV) {
//            System.err.print("(" + m.getX() + "," + m.getY() + ") ");
//        }
//        System.err.println();

        // boolean board printing
//        for (int j = 0; j < h; j++) {
//            for (int i = 0; i < w; i++) {
//                System.out.print(board[i][j] + " ");
//            }
//            System.out.println();
//        }
//        System.out.println();
        System.err.println(this);
    }

//    private static Set<DomineeringMove> union(Set<DomineeringMove> a, Set<DomineeringMove> b) {
//        Set<DomineeringMove> c = new HashSet<>(a);
////        c.addAll(a);
//        c.addAll(b);
//        return c;
//    }
//
//    private static Set<DomineeringMove> intersect(Set<DomineeringMove> a, Set<DomineeringMove> b) {
//        Set<DomineeringMove> c = new HashSet<>();
//        c.addAll(a);
//        c.retainAll(b);
//        return c;
//    }
//
//    private static boolean disjoint(Set<DomineeringMove> a, Set<DomineeringMove> b) {
//        return intersect(a, b).isEmpty();
//    }

    private Set<DomineeringMove> allAvailable(Player p) {
        Set<DomineeringMove> av = new HashSet<>();
        int xLim = p.equals(H) ? w - 1 : w;
        int yLim = p.equals(H) ? h : h - 1;

        for (int y = 0; y < yLim; y++) {
            for (int x = 0; x < xLim; x++) {
                av.add(new DomineeringMove(x, y));
            }
        }
        return av;
    }

    @Override
    public Player nextPlayer() {
//        return available.size() % 4 == 0 ? H : V;
//        return((hMoves.size() + vMoves.size()) % 4 == 0) ? H : V;
        // TODO change this to work with board passed through
//        return available.size() % 4 == 0 ? H : V;
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
//        av.addAll(allAvailable(nextPlayer()));
//        av.removeAll(hMoves);
//        av.removeAll(vMoves);
//        av.removeAll(union(hMoves, vMoves));
//
//        if (nextPlayer().equals(H)) {
//            for (DomineeringMove m : hMoves) {
//                DomineeringMove leftM = new DomineeringMove(m.getX() - 1, m.getY());
//                if (m.getX() >= 1 && av.contains(leftM)) {
//                    av.remove(leftM);
//                }
//            }
//            for (DomineeringMove m : vMoves) {
//                DomineeringMove leftM = new DomineeringMove(m.getX() - 1, m.getY());
//                if (m.getX() >= 1 && av.contains(leftM)) {
//                    av.remove(leftM);
//                }
//            }
//        } else {
//            for (DomineeringMove m : hMoves) {
//                DomineeringMove upM = new DomineeringMove(m.getX(), m.getY() - 1);
//                if (m.getY() >= 1 && av.contains(upM)) {
//                    av.remove(upM);
//                }
//            }
//            for (DomineeringMove m : vMoves) {
//                DomineeringMove upM = new DomineeringMove(m.getX(), m.getY() - 1);
//                if (m.getY() >= 1 && av.contains(upM)) {
//                    av.remove(upM);
//                }
//            }
//        }
//        return av;
//        return nextPlayer().equals(H) ? availableH : availableV;

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
//        assert (!hMoves.contains(move) && !vMoves.contains(move));
        System.err.println("boop " + nextPlayer());
        available = availableMoves();
        assert (available.contains(move));
//        assert (availableH.contains(move) || availableV.contains(move));

//        Set<DomineeringMove> avH = new HashSet<>();
//        avH.addAll(allAvailable(H));
//        avH.removeAll(hMoves);
//        avH.removeAll(vMoves);
//        avH = remove(move, avH);

//        Set<DomineeringMove> avV = new HashSet<>();
//        avV.addAll(allAvailable(V));
//        avV.removeAll(hMoves);
//        avV.removeAll(vMoves);
//        avV = remove(move, avV);
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
//            Set<DomineeringMove> newHM = add(move, hMoves);
//            if (move.getX() >= 1) {
//                avH.remove(new DomineeringMove(move.getX() - 1, move.getY()));
//                return new DomineeringBoard(w, h, add(move, hMoves), vMoves);
            newBoard[mx + 1][my] = true;
            return new DomineeringBoard(w, h, newBoard);
//            } else {
//                return new DomineeringBoard(w, h, newHM, vMoves);
//            }
        } else {
//            Set<DomineeringMove> newVM = add(move, vMoves);
//            if (move.getY() >= 1) {
//                avV.remove(new DomineeringMove(move.getX(), move.getY() - 1));
//                return new DomineeringBoard(w, h, hMoves, add(move, vMoves));
            newBoard[mx][my + 1] = true;
            return new DomineeringBoard(w, h, newBoard);
//            } else {
//                return new DomineeringBoard(w, h, hMoves, newVM);
//            }
        }
    }

    private Set<DomineeringMove> add(DomineeringMove move, Set<DomineeringMove> set) {
        Set<DomineeringMove> setClone = new HashSet<>(set);
//        setClone.addAll(set);
        setClone.add(move);

        if (nextPlayer().equals(H)) {
            setClone.add(new DomineeringMove(move.getX() + 1, move.getY()));
        } else {
            setClone.add(new DomineeringMove(move.getX(), move.getY() + 1));
        }

        return setClone;
    }

    private Set<DomineeringMove> remove(DomineeringMove origin, Set<DomineeringMove> set) {
        Set<DomineeringMove> setClone = new HashSet<>();
        setClone.addAll(set);
        setClone.remove(origin);

        if (nextPlayer().equals(H)) {
            setClone.remove(new DomineeringMove(origin.getX() + 1, origin.getY()));
        } else {
            setClone.remove(new DomineeringMove(origin.getX(), origin.getY() + 1));
        }

        return setClone;
    }

    public String toString() {
//        String s = "\n";
//        for (int y = 0; y < h; y++) {
//            for (int x = 0; x < w; x++) {
//                if (hMoves.contains(new DomineeringMove(x, y))) s += "=== | ";
//                else if (vMoves.contains(new DomineeringMove(x, y))) s += "|.| | ";
//                else s+= (x + "," + y + " | ");
//                if (nextPlayer().equals(H)) {
//                    if (board[x][y])
//                } else {
//
//                }
//            }
//            s += " \n";
//            for (int i = 0; i < 3 * w - 1; i++) {
//                s += "--";
//            }
//            s += "\n";
//        }
//        return s;
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
