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
    private final Set<DomineeringMove> hMoves, vMoves;
//    private final Set<DomineeringMove> available;
//    private final Set<DomineeringMove> availableH, availableV;

    public DomineeringBoard() {
        this(4, 4);
    }

    public DomineeringBoard(int m, int n) {
        w = m;
        h = n;
        board = new boolean[w][h];
        hMoves = new HashSet<>();
        vMoves = new HashSet<>();
    }

    private DomineeringBoard(int w, int h, Set<DomineeringMove> hMoves, Set<DomineeringMove> vMoves) {
        this.w = w;
        this.h = h;
        this.hMoves = hMoves;
        this.vMoves = vMoves;
        board = new boolean[w][h];

        Set<DomineeringMove> unionHV = new HashSet<>();
        unionHV.addAll(hMoves);
        unionHV.addAll(vMoves);

        for (DomineeringMove m : unionHV) {
            int x = m.getX();
            int y = m.getY();
            board[x][y] = true;
        }
        if (nextPlayer().equals(H)) System.err.println(V); else System.err.println(H);

        for (DomineeringMove m : unionHV) {
            System.err.print("(" + m.getX() + "," + m.getY() + ") ");
        }
        System.err.println();

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
        return((hMoves.size() + vMoves.size()) % 4 == 0) ? H : V;
    }

    @Override
    public Set<DomineeringMove> availableMoves() {
        Set<DomineeringMove> av = new HashSet<>();
        av.addAll(allAvailable(nextPlayer()));
        av.removeAll(hMoves);
        av.removeAll(vMoves);

        if (nextPlayer().equals(H)) {
            for (DomineeringMove m : hMoves) {
                DomineeringMove leftM = new DomineeringMove(m.getX() - 1, m.getY());
                if (m.getX() >= 1 && av.contains(leftM)) {
                    av.remove(leftM);
                }
            }
            for (DomineeringMove m : vMoves) {
                DomineeringMove leftM = new DomineeringMove(m.getX() - 1, m.getY());
                if (m.getX() >= 1 && av.contains(leftM)) {
                    av.remove(leftM);
                }
            }
        } else {
            for (DomineeringMove m : hMoves) {
                DomineeringMove upM = new DomineeringMove(m.getX(), m.getY() - 1);
                if (m.getY() >= 1 && av.contains(upM)) {
                    av.remove(upM);
                }
            }
            for (DomineeringMove m : vMoves) {
                DomineeringMove upM = new DomineeringMove(m.getX(), m.getY() - 1);
                if (m.getY() >= 1 && av.contains(upM)) {
                    av.remove(upM);
                }
            }
        }
        return av;
//        return nextPlayer().equals(H) ? availableH : availableV;
    }

    @Override
    public int value() {
//        boolean avH = false;
//        boolean avV = false;
//
//        for (DomineeringMove m : availableMoves()) {
//            if (availableMoves().contains(new DomineeringMove(m.getX() + 1, m.getY()))) avH = true;
//            else if (availableMoves().contains(new DomineeringMove(m.getX(), m.getY() + 1))) avV = true;
//        }
//
//        System.out.println("av: " + avH + "," + avV);
//        if (!avH && !avV) return nextPlayer().equals(H) ? -1 : 1;
//        else if (avH)     return 1;
//        else if (avV)     return -1;
//        else              return 0;

//        return availableMoves().size() > 1 ? 0 : nextPlayer().equals(H) ? -1 : 1;
        return !availableMoves().isEmpty() ? 0 : nextPlayer().equals(H) ? -1 : 1;
    }

    @Override
    public Board<DomineeringMove> play(DomineeringMove move) {
        assert (!hMoves.contains(move) && !vMoves.contains(move));
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

        if (nextPlayer().equals(H)) {
            Set<DomineeringMove> newHM = add(move, hMoves);
            if (move.getX() >= 1) {
//                avH.remove(new DomineeringMove(move.getX() - 1, move.getY()));
                return new DomineeringBoard(w, h, newHM, vMoves);
            } else {
                return new DomineeringBoard(w, h, newHM, vMoves);
            }
        } else {
            Set<DomineeringMove> newVM = add(move, vMoves);
            if (move.getY() >= 1) {
//                avV.remove(new DomineeringMove(move.getX(), move.getY() - 1));
                return new DomineeringBoard(w, h, hMoves, newVM);
            } else {
                return new DomineeringBoard(w, h, hMoves, newVM);
            }
        }
    }

    private Set<DomineeringMove> add(DomineeringMove move, Set<DomineeringMove> set) {
        Set<DomineeringMove> setClone = new HashSet<>();
        setClone.addAll(set);
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

    private static Set<DomineeringMove> union(Set<DomineeringMove> a, Set<DomineeringMove> b) {
        Set<DomineeringMove> c = new HashSet<>(a.size() + b.size());
        c.addAll(a);
        c.addAll(b);
        return c;
    }

    private static Set<DomineeringMove> intersect(Set<DomineeringMove> a, Set<DomineeringMove> b) {
        Set<DomineeringMove> c = new HashSet<>();
        c.addAll(a);
        c.retainAll(b);
        return c;
    }

    private static boolean disjoint(Set<DomineeringMove> a, Set<DomineeringMove> b) {
        return intersect(a, b).isEmpty();
    }

    public String toString() {
        String s = "\n";
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (hMoves.contains(new DomineeringMove(x, y))) s += "=== | ";
                else if (vMoves.contains(new DomineeringMove(x, y))) s += "|.| | ";
                else s+= (x + "," + y + " | ");
            }
            s += " \n";
            for (int i = 0; i < 3 * w - 1; i++) {
                s += "--";
            }
            s += "\n";
        }
        return s;
    }



}
